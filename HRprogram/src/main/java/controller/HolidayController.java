package controller;

import java.time.LocalTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Service.HolidayService;
import Service.OverTimeService;
import repository.Flextime_DAO;
import repository.Holiday_DAO;
import repository.Login_DAO;
import repository.OverTime_DAO;

@Controller
public class HolidayController {

	@Autowired
	HolidayController holidayController;
	
	@Autowired
	Holiday_DAO holiday_DAO;

	@Autowired
	HolidayService holidayService;
	
	@Autowired
	OverTimeService overTimeService;
	
	@Autowired
	OverTime_DAO overTime_DAO;
	
	@Autowired
	Flextime_DAO flexTime_DAO;
	
	@RequestMapping("/HoliRecord.do")
	public String process_HoliRecord(@RequestParam Map<String,String> map, Model model) {		
		/**
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
				
		String message = "";// model객체에 담아서 보낼 alert창 메세지		
				
		String Hcode = map.get("Hcode");
		String Hcode_first = Hcode.substring(0,2);
		
		if(Hcode_first.equals("H0")) {			
									
			//우선 탄력근무제인지 아닌지 검사.
			//step1.탄력근무가 아니라면
			if(flexTime_DAO.Select_WeekDay(Hcode, Hcode_first)==0) {
			
				//step2. 초과근무가 있는지 없는지 확인. 
				if(overTime_DAO.Select_OverTime(map.get("Ecode"), map.get("startday"), map.get("endday"))==0) {
					//step3. 초과근무가 없을경우, 연차확인.
					
					if(holidayService.hasResidualholiday(map)) {//잔여연차수가 차감가능하면 DB에 삽입
						if(holiday_DAO.Insert_Holidayrecode(map.get("Ecode"), Hcode, map.get("startday"), map.get("starttime"), map.get("endtime"),(Integer.valueOf(map.get("endtime"))-Integer.valueOf(map.get("starttime"))), "1", map.get("Reason"))==1) {
							message="휴가신청을 완료했습니다.";
						}
						else { message="휴가신청을 완료못했습니다!"; }
					}
					else{ message="사용할 연차가 부족합니다."; }	
					
				}
				else {//step3. 초과근무가 있는경우 연차, 조퇴 x / 반차는 오전만가능 / 나머지는 정규근무종료시간 이전에 끝나야함.
					//연차,조퇴일경우 실행 x
					if(Hcode.equals("H0001")||Hcode.equals("H0005")) { message="해당 날짜와 중복되는 초과근무가있습니다! 다시 시도해주세요./연차/조퇴는 안됩니다."; }
					else if(Hcode.equals("H0002")) { //반차일경우 오전만 가능.
						LocalTime halftime = LocalTime.of(Integer.valueOf(map.get("endtime")), 0);
						LocalTime time = LocalTime.of(13, 0);// 점심시간 이전.
						if(!halftime.isBefore(time)) { message="해당날짜와 중복되는 초과근무가 있습니다. 반차는 오전에만 가능합니다."; }
						else {
							if(holiday_DAO.Insert_Holidayrecode(map.get("Ecode"), Hcode, map.get("startday"), map.get("starttime"), map.get("endtime"),(Integer.valueOf(map.get("endtime"))-Integer.valueOf(map.get("starttime"))), "1", map.get("Reason"))==1) {
								message="휴가신청을 완료했습니다.";
							}
							else { message="휴가신청을 완료못했습니다!"; }
						}
					}
					else { // 나머지는 정규근무종료시간이전
						LocalTime halftime = LocalTime.of(Integer.valueOf(map.get("endtime")), 0);
						LocalTime time = LocalTime.of(17, 0);// 정규퇴근시간 이전.
						if(halftime.isBefore(time)) { message="해당시간와 중복되는 초과근무가 있습니다.정규시간이전"; }
						else {
							if(holiday_DAO.Insert_Holidayrecode(map.get("Ecode"), Hcode, map.get("startday"), map.get("starttime"), map.get("endtime"),(Integer.valueOf(map.get("endtime"))-Integer.valueOf(map.get("starttime"))), "1", map.get("Reason"))==1) {
								message="휴가신청을 완료했습니다.";
							}
							else { message="휴가신청을 완료못했습니다!"; }
						}
					}
				}				
			}
			else { //step1.탄력근무라면 --> 연차,반차 안됨
				if(Hcode.equals("H0001")||Hcode.equals("H0002")) { message="탄력근무제인 직원은 연차와 반차를 사용할수없습니다."; }
				else { //step2. 초과근무가 있는지 없는지 확인. 있으면, 조퇴x / 외출,지각(탄력근무종료시간 이전에 끝나야함)
					
					if(overTime_DAO.Select_OverTime(map.get("Ecode"), map.get("startday"), map.get("endday"))==0) {
						//step3. 초과근무가 없을경우, 연차확인.
						
						if(holidayService.hasResidualholiday(map)) {//잔여연차수가 차감가능하면 DB에 삽입
							if(holiday_DAO.Insert_Holidayrecode(map.get("Ecode"), Hcode, map.get("startday"), map.get("starttime"), map.get("endtime"),(Integer.valueOf(map.get("endtime"))-Integer.valueOf(map.get("starttime"))), "1", map.get("Reason"))==1) {
								message="휴가신청을 완료했습니다.";
							}
							else { message="휴가신청을 완료못했습니다!"; }
						}
						else{ message="사용할 연차가 부족합니다."; }
					}
					else {
						//step3. 초과근무가 있는경우 조퇴 x / 나머지는 정규근무종료시간 이전에 끝나야함.
						//조퇴일경우
						if(Hcode.equals("H0005")) { message="해당날짜와 중복되는 초과근무가 있습니다. 조퇴를 할수없습니다."; }						
						else { // 나머지는 정규근무종료시간이전
							LocalTime halftime = LocalTime.of(Integer.valueOf(map.get("endday")), 0);
							LocalTime time = LocalTime.of(17, 0);// 점심시간 이전.
							if(!halftime.isBefore(time)) { message="해당시간와 중복되는 초과근무가 있습니다."; }
							else {
								if(holiday_DAO.Insert_Holidayrecode(map.get("Ecode"), Hcode, map.get("startday"), map.get("starttime"), map.get("endtime"),(Integer.valueOf(map.get("endtime"))-Integer.valueOf(map.get("starttime"))), "1", map.get("Reason"))==1) {
									message="휴가신청을 완료했습니다.";
								}
								else { message="휴가신청을 완료못했습니다!"; }
							}
						}
					}	
					
					
				}
			}
		}// if( H0 ) end 
		else { 
			// 결혼/조사일경우 , 초과근무일자가 겹치면 해당 초과근무 삭제.			
			message = holidayService.Hcode_event(map,Hcode);
			
			
			/* completed 결재승인을 받아야 실행시킬함수임.
			// 미리 출석해주는 함수 / 탄력검색후 , 탄력일시 / 아닐시로구분후 입력.
			holidayService.alread_attendance(map.get("Ecode"), map.get("startday"), map.get("endday"), map.get("starttime"), map.get("endtime"));
			*/
		}
		model.addAttribute("message",message);
		
		return "redirect:/work/confirm_form_ver1.jsp";
		
	}//process_Holirecode()end
	
	
}//class end
