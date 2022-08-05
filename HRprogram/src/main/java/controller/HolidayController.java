package controller;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Service.*;
import repository.*;
import model.FormSearchCMD;
import model.HolidayRecordDTO;


@Controller
public class HolidayController {

	@Autowired
	HolidayController holidayController;
	
	@Autowired
	Holiday_DAO holiday_DAO;

	@Autowired
	HolidayService holidayService;
	
	@Autowired
	Flextime_DAO flexTime_DAO;
	
	@Autowired
	Staff_DAO staff_DAO;
	
	@Autowired
	SearchService searchService;

	/* *
	  
	보유연차를 사용하는것 ! --> 연차, 조퇴, 반차
	보유연차를 사용안하는것 ! --> 본인결혼, 조사
	
	외출 / 지각 --> 금액에서..공제?

 	초과근무가 있을시, 신청대기던, 완료이던 상관 x 
 	무조건막기! --> 본인결혼, 조사 , 연차, 조퇴 
	상황에따라 막기 --> 반차.외출			
	안막는것 --> 지각.	
	
	휴가는 신청반려되거나, 신청을 취소할수있다.
	휴가가 신청반려된경우 재신청할수있다. (DB에 남음)
	신청을 취소한 경우, DB에서 삭제.
	
	completed 되기전에 신청을 취소할경우 --> 디비삭제
	신청반려된것 ---> 결재상태 반려
	결제승인후에 취소--> 결재취소로 변경.

	 * */	
	
	/* 
	 completed 결재승인을 받아야 실행시킬함수임.
		// 미리 출석해주는 함수 / 탄력검색후 , 탄력일시 / 아닐시로구분후 입력.
		holidayService.alread_attendance(map.get("Ecode"), map.get("startday"), map.get("endday"), map.get("starttime"), map.get("endtime"));
	*/
	
	@RequestMapping("/HoliRecord.do")
	public String process_HoliRecord(@RequestParam Map<String,String> map, Model model, RedirectAttributes redirect) {		
		String message = "";// model객체에 담아서 보낼 alert창 메세지		
				
		String Hcode = map.get("Hcode");
		String Hcode_first = Hcode.substring(0,2);
		
		
		// 해당날짜에 휴가 등록이 먼저 되어있는지 확인할것.
		if(holiday_DAO.Select_HolidayRecord(map.get("Ecode"), map.get("startday"))!=null) {
			redirect.addAttribute("message","먼저 신청하신 휴가가 있습니다.");
			return "redirect:/form_ver1.do";
		}
		
		
		if(Hcode_first.equals("H0")) {												
			//우선 탄력근무제인지 아닌지 검사.
			if(flexTime_DAO.Select_WeekDay(Hcode, map.get("startday"))==0) { message = holidayService.hasFlexTime(map,Hcode,false); }
			else { message = holidayService.hasFlexTime(map, Hcode, true); }
		}
		else { message = holidayService.Hcode_event(map,Hcode); }// 결혼/조사일경우 , 초과근무일자가 겹치면 해당 초과근무 삭제.
		
		redirect.addAttribute("message",message);
		
		return "redirect:/form_ver1.do";
		
	}//process_Holirecode()end
	
	@RequestMapping("/Holiday_record.do")
	public String process_record(FormSearchCMD cmd, Model model) {	
		
		//휴가레코드 전체 데이터 가져오기. + 사원정보와 비교를위해 사원전체 데이터가져오기
		ArrayList<HolidayRecordDTO> holiday_list = holiday_DAO.Select_AllHoliRecord();
		Map<String,String> staff_list = staff_DAO.Select_EmployMap();
		
		if(cmd.getDcode()!=null) { // 부서명으로 검색했을시 실행 할 함수
			model.addAttribute("Dcode",cmd.getDcode());
			holiday_list = searchService.Remove_Dcode(holiday_list,cmd.getDcode(),staff_list);
		}		
		
		if(cmd.getPosition()!=null) {  // 직급명으로 검색했을시 실행 할 함수					
			model.addAttribute("Position",cmd.getPosition());
			holiday_list = searchService.Remove_Position(holiday_list, cmd.getPosition(), staff_list);
		}		
		
		if(cmd.getDate()!=null&&cmd.getDate()!="") { // 날짜별로 검색했을시 실행 할 함수
			holiday_list = searchService.Remove_Date(holiday_list, cmd.getDate(), staff_list);
		}
		
		if(cmd.getEcodeN()!=null) {// 사원코드 혹은 사원명으로 검색했을시
			//사원코드 이거나 사원명으로 검색하였을시 나올 list
			ArrayList<HolidayRecordDTO> EcodeName_list = holiday_DAO.Select_HoliRecord(cmd.getEcodeN().trim());//좌우 공백제거			
			holiday_list = searchService.Remove_EcodeNeame(holiday_list, EcodeName_list);
		}
		
		model.addAttribute("SearchLIST", holiday_list);// 검색결과 리스트 모델에 저장
		model.addAttribute("DepartLIST",staff_DAO.getDepartmentList());// 부서리스트가져오는함수
		model.addAttribute("PositionLIST",staff_DAO.getPositionList());// 직급리스트가져오는함수
		model.addAttribute("Allstaff",staff_list); // 모든 사원정보 

		return "time/Holiday_record";

	}// process_dayoff() end
	
	@RequestMapping("/Holidaycard.do")
	public String process_card(@RequestParam String pknum, @RequestParam String Ecode, Model model) {
		//해당사원의 휴가 정보를 모델에 담음
		model.addAttribute("SearchDTO", holiday_DAO.Select_HolidayRecord(pknum));
		model.addAttribute("DepartLIST",staff_DAO.getDepartmentList());// 부서리스트가져오는함수
		model.addAttribute("PositionLIST",staff_DAO.getPositionList());// 직급리스트가져오는함수
		model.addAttribute("StaffDTO",staff_DAO.getEmployeeInfo(Ecode));
		model.addAttribute("Card_class","Holiday");
		
		return "work/desc_card_ver1";
	}
	
	
}//class end
