package controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Service.HolidayService;
import Service.OverTimeService;
import Service.SearchService;
import model.FormSearchCMD;
import model.HolidayRecordDTO;
import model.OverTimeDTO;
import model.WeekFlextimeDTO;
import repository.Flextime_DAO;
import repository.Holiday_DAO;
import repository.OverTime_DAO;
import repository.Staff_DAO;


@Controller
public class OverTimeController {
	
	@Autowired
	OverTimeController overTimeController;
	
	@Autowired
	OverTime_DAO overTime_DAO;
	
	@Autowired
	OverTimeService overTimeService;
	
	@Autowired
	Holiday_DAO holiday_DAO;
	
	@Autowired
	Staff_DAO staff_DAO;
	
	@Autowired
	SearchService searchService;	
	
	/* * 
	 * 
	 * 탄력근무일경우 , 탄력근무 일과시간에 맞춰서 끝나는시간인지 아닌지 확인.
	 * 탄력이 아닐경우 , 정규퇴근시간부터 시작인지 아닌지 확인.
	 * 
	 * 휴가가 겹치는경우 , 휴가코드에 맞춰서 처리
	 * 휴가가 겹치지않는경우, 진행
	 * 
	 * 탄력근무+휴가가있을경우. 
	 * 초과근무는 휴가보다 낮은사항...
	 * 휴가가 신청이 있을시 초과근무는 신청되어선 안됨.
	 * 결혼/조사일때는 무조건 안됨.
	 * 연차랑 오후반차.조퇴안됨.
	 * 오전반차 외출,지각,됨.
	 *
	 * 탄력근무제 일 경우, 결혼/조사절대안됨
	 * 연차랑반차,조퇴 안됨.
	 * 외출지각.
	 * 
	 * */
	
	@RequestMapping("/OverTime.do")
	public String process_OverTime(@RequestParam Map<String,String> map, Model model) {
		
		String message="";
		
		int time_def = 0; //시작시간 - 종료시간 시간차담는 변수
		
		int timeSum = overTimeService.Checking_TimeSum(map.get("Ecode"),map.get("startday"));
		// 주단위로 검색하여 주간초과근무시간이 12시간 이상했을경우, if문실행. 등록처리 x
		if( timeSum >= 12 ) {			
			model.addAttribute("message","주간 초과근무 가능한 12시간 중 12시간을 이미 했습니다.");
			return "redirect:/form_ver1.do";
		}
		else{
			int startTime = Integer.parseInt(map.get("starttime"));
			int endTime = Integer.parseInt(map.get("endtime"));
			time_def = endTime-startTime;
			
			if(time_def+timeSum > 12) {				
				model.addAttribute("message","요청하신 초과근무시간을 적용하면 주간 초과근무 가능한 시간이 12시간을 초과합니다.");
				return "redirect:/form_ver1.do";
			}
			
		}
		
		// 오늘 날짜를 기준으로 이미 신청했는지 확인
		if(overTime_DAO.Select_OverTime(map.get("Ecode"),map.get("startday"))==0) {			
			LocalDate date = LocalDate.parse(map.get("startday"));
			LocalDate now = LocalDate.parse(map.get("startday"));		
			now = now.minusDays(4);//1이 월요일...
			
			//휴가신청이력이 없을경우.
			if(holiday_DAO.checkHoliday(map.get("Ecode"), date.toString(), now.toString())==0) { message = overTimeService.NotHoliday(map); }
			else {
				//휴가코드가져오기
				String Hcode = holiday_DAO.Select_Hcode(map.get("Ecode"), map.get("startday"));
				//holiday_DAO.Select_HolidayRecord(map.get("Ecode"));								
				String Hcode_f = Hcode.substring(0,2);

				if(Hcode_f.equals("H1")) {// 결혼/조사 일경우.--> 안됨. 
					message="신청하신 날과 중복되는 날에 경조사 휴가신청이 있습니다.";
					model.addAttribute("message",message);
					return "redirect:/form_ver1.do";
				}				
				else if(Hcode.equals("H0001")||Hcode.equals("H0002")||Hcode.equals("H0005")) {//연차,반차,조퇴 일경우 초과근무신청 안됨
					message = "신청하신 날과 중복되는 날에 연차/반차/조퇴 신청이 있습니다.";
					model.addAttribute("message",message);
					return "redirect:/form_ver1.do";
				}
				else { message = overTimeService.HaveHoliday(map, Hcode, time_def); }
			}
			
		}
		else {
			message=" 이미 같은날에 등록한 초과근무가있습니다.";
			model.addAttribute("message",message);
			return "redirect:/form_ver1.do";
		}
			
		model.addAttribute("message",message);
		
		//등록실행 초과근무조회페이지로 이동
		return "time/over_work";
			
	}//process_OverTime end
	
	@RequestMapping("/OverTime_record.do")
	public String process_record(FormSearchCMD cmd, Model model) {
		
		//휴가레코드 전체 데이터 가져오기. + 사원정보와 비교를위해 사원전체 데이터가져오기
		ArrayList<OverTimeDTO> overtime_list = overTime_DAO.Select_AllOverTime();
		Map<String,String> staff_list = staff_DAO.Select_EmployMap();
				
		if(cmd.getDcode()!=null) {	// 부서명으로 검색했을시 실행 할 함수
			model.addAttribute("Dcode",cmd.getDcode());						
			overtime_list = searchService.Remove_Dcode_OverTime(overtime_list,cmd.getDcode(),staff_list);
		}		
		
		if(cmd.getPosition()!=null) {// 직급명으로 검색했을시 실행 할 함수
			model.addAttribute("Position",cmd.getPosition());
			overtime_list = searchService.Remove_Position_OverTime(overtime_list, cmd.getPosition(), staff_list);
		}		
		
		if(cmd.getDate()!=null&&cmd.getDate()!="") {// 날짜별로 검색했을시 실행 할 함수
			overtime_list = searchService.Remove_Date_OverTime(overtime_list, cmd.getDate(), staff_list);
		}
		
		if(cmd.getEcodeN()!=null) {  // 사원코드 혹은 사원명으로 검색했을시
			//사원코드 이거나 사원명으로 검색하였을시 나올 list
			ArrayList<OverTimeDTO> EcodeName_list = overTime_DAO.Select_OverTime(cmd.getEcodeN().trim());//좌우 공백제거			
			overtime_list = searchService.Remove_EcodeNeame_OverTime(overtime_list, EcodeName_list);
		}			
		
		model.addAttribute("SearchLIST", overtime_list);// 검색결과 리스트 모델에 저장
		model.addAttribute("DepartLIST",staff_DAO.getDepartmentList());// 부서리스트가져오는함수
		model.addAttribute("PositionLIST",staff_DAO.getPositionList());// 직급리스트가져오는함수
		model.addAttribute("Allstaff",staff_list); // 모든 사원정보 
		
		return "time/OverTime_record";
	}
	
	@RequestMapping("/OverTimecard.do")
	public String process_card(@RequestParam String pknum, @RequestParam String Ecode, Model model) {
		//해당사원의 휴가 정보를 모델에 담음
		model.addAttribute("SearchDTO", overTime_DAO.Select_OverTime(Integer.parseInt(pknum)));
		model.addAttribute("DepartLIST",staff_DAO.getDepartmentList());// 부서리스트가져오는함수
		model.addAttribute("PositionLIST",staff_DAO.getPositionList());// 직급리스트가져오는함수
		model.addAttribute("StaffDTO",staff_DAO.getEmployeeInfo(Ecode));
		model.addAttribute("Card_class","OverTime");
		
		return "work/desc_card_ver1";
	}

}//class end
