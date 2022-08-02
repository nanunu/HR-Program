package Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.EmployeeHolidayDTO;
import model.FlextimeDTO;
import model.WeekFlextimeDTO;
import repository.Flextime_DAO;
import repository.Holiday_DAO;
import repository.Login_DAO;
import repository.OverTime_DAO;

@Service
public class HolidayService {
	
	@Autowired
	Holiday_DAO holiday_DAO;
	
	@Autowired
	Flextime_DAO flexTime_DAO;
	
	@Autowired
	OverTime_DAO overTime_DAO;

	@Autowired
	Login_DAO login_DAO;

	// 휴가코드가 결혼/조사일때 실행할 함수
	public String Hcode_event(Map<String,String> map, String Hcode) {
		String message = "";// model객체에 담아서 보낼 alert창 메세지	
		String useDay = "3";
		if(Hcode.equals("H1001")) { useDay = "5"; }// 결혼은 5일 조사는 3일
		
		//해당일자안에 초과근무하는지 확인
		if(overTime_DAO.Select_OverTime(map.get("Ecode"), map.get("startday"), map.get("endday"))!=0) {
			//근무가 있다면 초과근무삭제.
			if(overTime_DAO.Delete_OverTime(map.get("Ecode"),map.get("startday"),map.get("endday"))!=0) { 
				message="신청/승인완료된 초과근무일을 취소하고, 휴가신청을 완료했습니다.";				
			}
			else {
				message="신청/승인완료된 초과근무일을 취소하지못했습니다. 에러에러!";				
			}
		}
		//휴가레코드에 삽입.
		int useTime = Integer.valueOf(map.get("endtime"))-Integer.valueOf(map.get("starttime"));
		holiday_DAO.Insert_Holidayrecode(map.get("Ecode"), map.get("Hcode"), map.get("startday"), map.get("starttime"), map.get("endtime"), useTime, useDay, map.get("Reason"));
		
		return message;
	
	}//Hcode_event() end
	
	// 잔여연차수가 차감가능하면 Insert실행하는 함수
	public String cannot_Insert(Map<String,String> map, String Hcode) {
		String message;
		if(hasResidualholiday(map)) {//잔여연차수가 차감가능하면 DB에 삽입
			if(holiday_DAO.Insert_Holidayrecode(map.get("Ecode"), Hcode, map.get("startday"), map.get("starttime"), map.get("endtime"),(Integer.valueOf(map.get("endtime"))-Integer.valueOf(map.get("starttime"))), "1", map.get("Reason"))==1) {
				message="휴가신청을 완료했습니다.";
			}
			else { message="휴가신청을 완료못했습니다!"; }
		}
		else{ message="사용할 연차가 부족합니다."; }
		
		return message;
	}	
	
	// 휴가등록시 탄력근무 여부에 따라 // 정규근무자의 휴가등록 process와 탄력근무자의 휴가등록 process로 구분됨.
	// boolean isFlextime --> false . 정규근무자 // true. 탄력근무자
	public String hasFlexTime(Map<String,String> map, String Hcode, boolean isFlextime) {
		String message;
		
		if(!isFlextime) { //정규근무자이면
			//step2. 초과근무가 있는지 없는지 확인. 
			if(overTime_DAO.Select_OverTime(map.get("Ecode"), map.get("startday"), map.get("endday"))==0) {
				//step3. 초과근무가 없을경우, 연차확인.

				message = cannot_Insert(map, Hcode);// 잔여연차수가 차감가능하면 Insert실행하는 함수
				
			}
			else {//step3. 초과근무가 있는경우 연차, 조퇴 x / 반차는 오전만가능 / 나머지는 정규근무종료시간 이전에 끝나야함.
				//연차,조퇴일경우 실행 x
				if(Hcode.equals("H0001")||Hcode.equals("H0005")) { message="해당 날짜와 중복되는 초과근무가있습니다! 다시 시도해주세요./연차/조퇴는 안됩니다."; }
				else if(Hcode.equals("H0002")) { //반차일경우 오전만 가능.
					LocalTime halftime = LocalTime.of(Integer.valueOf(map.get("endtime")), 0);
					LocalTime time = LocalTime.of(13, 0);// 점심시간 이전.
					if(!halftime.isBefore(time)) { message="해당날짜와 중복되는 초과근무가 있습니다. 반차는 오전에만 가능합니다."; }
					else {
						message = cannot_Insert(map, Hcode);// 잔여연차수가 차감가능하면 Insert실행하는 함수
					}
				}
				else { // 나머지는 정규근무종료시간이전
					LocalTime halftime = LocalTime.of(Integer.valueOf(map.get("endtime")), 0);
					LocalTime time = LocalTime.of(17, 0);// 정규퇴근시간 이전.
					if(halftime.isBefore(time)) { message="해당시간와 중복되는 초과근무가 있습니다.정규시간이전"; }
					else {
						message = cannot_Insert(map, Hcode);// 잔여연차수가 차감가능하면 Insert실행하는 함수
					}
				}
			}
		}
		else { // 탄력근무자일경우
			if(Hcode.equals("H0001")||Hcode.equals("H0002")) { message="탄력근무제인 직원은 연차와 반차를 사용할수없습니다."; }
			else { //step2. 초과근무가 있는지 없는지 확인. 있으면, 조퇴x / 외출,지각(탄력근무종료시간 이전에 끝나야함)
				
				if(overTime_DAO.Select_OverTime(map.get("Ecode"), map.get("startday"), map.get("endday"))==0) {
					//step3. 초과근무가 없을경우, 연차확인.
					
					message = cannot_Insert(map, Hcode);// 잔여연차수가 차감가능하면 Insert실행하는 함수
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
							message = cannot_Insert(map, Hcode);// 잔여연차수가 차감가능하면 Insert실행하는 함수
						}
					}
				}
			}
		}
		
		return message;
	}
	
	//휴가 결제 승인받을시, 미리 출석해놓을 함수.
	public void alread_attendance(String Ecode, String startday, String endday, String starttime, String endtime) {
		
		LocalDate startdate = LocalDate.parse(startday);
		LocalDate enddate = LocalDate.parse(endday);
		
		if(flexTime_DAO.Select_WeekDay(startday, Ecode)!=0) {//탄력근무하는 주가 아닐경우 미리 출석.
			
			//종료되는시간과 시작하는 시간이 같지않으면 while문 반복된다.
			while(!enddate.equals(startdate)){
				//+1을 먼저 하지않는 이유는 현재 startdate의 값부터 로그인처리를 해야하기때문이다.
				login_DAO.Insert_alreadyLogin(Ecode, startdate.toString(), starttime, endtime);
				startdate = startdate.plusDays(1); // 시작하는 시간을 +1일 씩 추가하는 함수				
			}//while end
			
		}
		else { // 탄력근무를 하는 사람일경우, DB에 들어있는 탄력근무시작시간 - 종료시간만큼 출석처리함.
		
			while(!enddate.equals(startdate)) {
				String daytext = WeekDay_column(startdate.getDayOfWeek().getValue());
				String worktime = daytext+"Start,"+daytext+"End";
				
				WeekFlextimeDTO dto = flexTime_DAO.Select_Worktime(Ecode, startday, worktime);
				
				login_DAO.Insert_alreadyLogin(Ecode, startdate.toString(), dto.getStart().toString(), dto.getEnd().toString());								
				startdate = startdate.plusDays(1);
			}
		}
		
	}// alread_attendance() end
	
	//int값으로 요일값가져오기
	public String WeekDay_column(int weekday_value) {
		String weekday_text="";
	
		switch(weekday_value) {
			case 1: weekday_text = "Mon";	break;
			case 2:	weekday_text = "Tue";   break;
			case 3: weekday_text = "Wed";	break;
			case 4: weekday_text = "Thu";	break;
			case 5: weekday_text = "Fri";	break;			
		}
		
		return weekday_text; 
	}
		
	//잔여 연차가 있는지 없는지 확인하는 함수
	public Boolean hasResidualholiday(Map<String,String> map) {
		
		// 사원번호로 연차정보불러온다
		EmployeeHolidayDTO dto = holiday_DAO.Select_Holiday(map.get("Ecode"));
		double holiday_count = dto.getNumOfmyholiday()-dto.getUsenumOfholiday(); // 잔여연차수담기
		
		String Hcode = map.get("Hcode");		
		String Hcode_frist = Hcode.substring(0, 2);
		
		//잔여연차수에 상관없이 Hcode가 H1 --> 본인결혼,조사일경우 무조건 통과.
		if(Hcode_frist.equals("H1")) { return true; } 
		else if(holiday_count==0.0) { return false; }	//잔여연차수가 0일경우 무조건 fail	
		else { // 잔여연차수가 0이상일경우
			
			// 시간차 = (입력받은 종료시간 - 시작시간);
			//시간차를 구하는이유 시간단위로 0.125를 곱한단위로 연차를 계산할것임.
			double time_dif = Integer.valueOf(map.get("endtime"))-Integer.valueOf(map.get("starttime"));
		
			//잔여 연차수 - (시간차*0.125) 가 0보다 같거나 커야 통과가능
			if(holiday_count-(time_dif*0.125) >= 0.0) {
				/*
				dto.setUsenumOfholiday(dto.getUsenumOfholiday()+(holiday_count-(time_dif*0.125)));
				dto.setResidualholiday(dto.getNumOfmyholiday()-dto.getUsenumOfholiday());
				holiday_DAO.Update_ResidualHoliday(dto);
				*/
				return true;
			}
			else { return false; }// 0보다 크지않으면 fail
			
		}
		
	}
	


	
}//class end


