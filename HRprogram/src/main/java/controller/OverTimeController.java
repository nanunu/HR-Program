package controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Service.HolidayService;
import Service.OverTimeService;
import model.WeekFlextimeDTO;
import repository.Flextime_DAO;
import repository.Holiday_DAO;
import repository.OverTime_DAO;


@Controller
public class OverTimeController {
	
	@Autowired
	OverTimeController overTimeController;
	
	@Autowired
	OverTime_DAO overTimeDAO;
	
	@Autowired
	OverTimeService overTimeService;
	
	@Autowired
	Holiday_DAO holiday_DAO;
	
	@Autowired
	Flextime_DAO flexTime_DAO;
	
	@Autowired
	HolidayService holidayService;
	
	
	@RequestMapping("/OverTime.do")
	public String process_OverTime(@RequestParam Map<String,String> map, Model model) {
		
		String message="";
		
		int time_def = 0; //시작시간 - 종료시간 시간차담는 변수
		
		int timeSum = overTimeService.Checking_TimeSum(map.get("Ecode"),map.get("startday"));
		// 주단위로 검색하여 주간초과근무시간이 12시간 이상했을경우, if문실행. 등록처리 x
		if( timeSum >= 12 ) {			
			model.addAttribute("message","주간 초과근무 가능한 12시간 중 12시간을 이미 했습니다.");
			return "work/confirm_form_ver1";
		}
		else{
			int startTime = Integer.parseInt(map.get("starttime"));
			int endTime = Integer.parseInt(map.get("endtime"));
			time_def = endTime-startTime;
			
			if(time_def+timeSum > 12) {				
				model.addAttribute("message","요청하신 초과근무시간을 적용하면 주간 초과근무 가능한 시간이 12시간을 초과합니다.");
				return "work/confirm_form_ver1";
			}
			
		}
	
		if(overTimeDAO.Select_OverTime(map.get("Ecode"),map.get("startday"))==0) { // 오늘 날짜를 기준으로 이미 신청했는지 확인			
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
			
			LocalDate date = LocalDate.parse(map.get("startday"));
			LocalDate now = LocalDate.parse(map.get("startday"));
			//1이 월요일...
			now = now.minusDays(4);
			
			
			if(holiday_DAO.checkHoliday(map.get("Ecode"), date.toString(), now.toString())==0) {//휴가신청이력이 없을경우.
				if(flexTime_DAO.Select_WeekDay(map.get("startday"), map.get("Ecode"))==0) {//탄력근무제인지 확인.
					//탄력근무 아님.
					if(overTimeDAO.Insert_OverTime(map,time_def)==1) { // 드디어 등록
						message="정상등록되었습니다.";
					}
					else { message="등록이 정상적으로 되지않았습니다. error!"; }
				}
				else { //탄력근무제일경우				
					
					String tmp = holidayService.WeekDay_column(date.getDayOfWeek().getValue());//초과근무일자의 요일명가져오기 
					
					//해당요일의 탄력근무시간가져오기
					WeekFlextimeDTO dto = flexTime_DAO.Select_Worktime(map.get("Ecode"), map.get("startday"), tmp+"start,"+tmp+"end");
					
					LocalTime thistime = LocalTime.of(Integer.valueOf(map.get("starttime")),0);
					
					if(dto.getEnd().isAfter(thistime)||dto.getEnd().equals(thistime)) {// 업무종료시간이 초과근무시간보다 이후이거나 같아야함.
						if(overTimeDAO.Insert_OverTime(map,time_def)==1) { // 드디어 등록
							message="정상등록되었습니다.";
						}
						else { message="등록이 정상적으로 되지않았습니다. error!"; }
					}
					else {//초과근무시작시간이 업무종료시간보다 이전일경우
						message = "탄력근무 업무종료시간보다 빠르게 설정되어있습니다. 다시 시도해주세요.";
					}		
					
					
				}
							
			}
			else {// 휴가신청이력이 있는경우
				
				//휴가코드가져오기
				String Hcode = holiday_DAO.Select_Hcode(map.get("Ecode"), map.get("startday"));
				//holiday_DAO.Select_HolidayRecord(map.get("Ecode"));								
				String Hcode_f = Hcode.substring(0,2);

				if(Hcode_f.equals("H1")) {// 결혼/조사 일경우.--> 안됨. 
					message="신청하신 날과 중복되는 휴가신청이 있습니다.";
					model.addAttribute("");
					return "redirect:/work/confirm_form_ver1.jsp";
				}	
				
				//탄력근무인지아닌지 확인.
				if(flexTime_DAO.Select_WeekDay(map.get("startday"), map.get("Ecode"))==0) {
					//현재상황 휴가신청이력+탄력근무는 아닌경우 
					
					
					
				}
				else {// 휴가신청 + 탄력근무가 맞을때
					
				}				
				
					
			}
				
		}// if( Select_Overtime () ) end
			
			
			
			
	
			
			
			
			//등록실행 초과근무조회페이지로 이동
			return "time/over_work";
			
	}//process_OverTime end
	

}//class end
