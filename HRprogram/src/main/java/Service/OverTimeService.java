package Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.WeekFlextimeDTO;
import repository.Flextime_DAO;
import repository.Holiday_DAO;
import repository.OverTime_DAO;

@Service
public class OverTimeService {

	@Autowired
	OverTimeService overTimeService;
	
	@Autowired
	OverTime_DAO overTime_DAO;
	
	@Autowired
	Flextime_DAO flexTime_DAO;
	
	@Autowired
	HolidayService holidayService;
	
	@Autowired
	Holiday_DAO holiday_DAO;
	
	//입력받은 날짜 StartDay기준으로 월요일과 금요일을 구하는 함수
	//ex) StartDay = 2022-07-25(수요일) 일경우, 2022-07-22(월요일)~ 2022-07-29(금요일)사이 해당사원의 총근무시간을 구한다.
	public int Checking_TimeSum(String Ecode, String StartDay) {

		LocalDate startDay = LocalDate.parse(StartDay);	
		int week_monday = startDay.getDayOfMonth();
		int week_friday = startDay.getDayOfMonth();
		
		switch(startDay.getDayOfWeek().getValue()) { // 1: 월요일 .......7: 일요일 /// 일요일과 토요일은 js에서 걸러 줄거임..아마도.			
			case 1: //월요일
					week_friday +=4; 
					break;
			case 2: //화요일
					week_monday -=1;
					week_friday +=3;
					break;
			case 3: 
					week_monday -=2;
					week_friday +=2;
					break;
			case 4: 
					week_monday -=3;
					week_friday +=1;
					break;
			case 5:	
					week_monday -=4;
					break;			 		
		}
		
		LocalDate setMonday = LocalDate.of(startDay.getYear(),startDay.getMonth(),week_monday);
		LocalDate setFriday = LocalDate.of(startDay.getYear(),startDay.getMonth(),week_friday);
		/*
			System.out.println(week.getValue());
			System.out.println(startDay.toString());
			System.out.println(setMonday.toString());
			System.out.println(setFriday.toString());
		*/
		return overTime_DAO.Select_OverTimeSum(Ecode,setMonday.toString(),setFriday.toString());
		
	}
	
	/*오버로딩 : 선택된날짜를 기준으로 월요일에 해당하는 날짜를 돌려줌*/
	public String Checking_TimeSum(String pickDay) {
		LocalDate startDay = LocalDate.parse(pickDay);	
		int week_monday = startDay.getDayOfMonth();

		switch(startDay.getDayOfWeek().getValue()) { // 1: 월요일 .......7: 일요일 /// 일요일과 토요일은 js에서 걸러 줄거임..아마도.			
			case 1: //월요일
					break;
			case 2: //화요일
					week_monday -=1;
					break;
			case 3: 
					week_monday -=2;
					break;
			case 4: 
					week_monday -=3;
					break;
			case 5:	
					week_monday -=4;
					break;			 		
		}
		return LocalDate.of(startDay.getYear(),startDay.getMonth(),week_monday).toString();
	}
	
	//휴가신청이력이 있고 (탄력/정규)근무시간에따라 초과근무등록할것인지 결정할 함수
	//boolean false - 탄력근무 아님. true - 탄력근무맞음.
	public Boolean Checking_CantOverTime(String Ecode, String Hcode, String Startday, String Starttime, boolean hasFlextime) {
		LocalDate Over_date = LocalDate.parse(Startday);
		LocalTime Over_startTime = LocalTime.parse(Starttime);
		
		if(hasFlextime) { //탄력근무 맞을때			
			// 해당일자의 근무시간을 가져온다.
			String text = holidayService.WeekDay_column(Over_date.getDayOfWeek().getValue());
			WeekFlextimeDTO dto = flexTime_DAO.Select_Worktime(Ecode, Startday, text+"Start,"+text+"End");

			// 외출, 지각 일 경우--> 시간 조회 후 탄력근무 일정안이면 true 아님 false; 이지만,
			// 이는 휴가등록할때에 검토를 했다.  
			//근무시간과 현재 초과근무시간을 비교한다. 근무시간 이후부터 작업가능			
			if(Over_startTime.isAfter(dto.getEnd())||Over_startTime.equals(dto.getEnd())) { return true; }
			else { return false; }
			
		}
		else { //탄력근무가 아닐떄 
			LocalTime Origin_Time = LocalTime.of(17, 0);//정규 근무종료시간설정
			if(Over_startTime.isAfter(Origin_Time)||Over_startTime.equals(Origin_Time)) { return true; }
			else { return false; }
			
		}
		
	}// Checking_CantOverTime() end
	
	//휴가신청이력이 없을경우 실행
	public String NotHoliday(Map<String,String> map) {
		String message="";
		
		int time_def = 0; //시작시간 - 종료시간 시간차담는 변수

		if(flexTime_DAO.Select_WeekDay(map.get("startday"), map.get("Ecode"))==0) {//탄력근무제인지 확인.
			if(overTime_DAO.Insert_OverTime(map,time_def)==1) { message="정상등록되었습니다."; }
			else { message="등록이 정상적으로 되지않았습니다. error!"; }
		}
		else { //탄력근무제일경우				
			if(overTimeService.Checking_CantOverTime(map.get("Ecode"), "", map.get("startday"), map.get("startime"), true)) {//신청이 가능하면
				if(overTime_DAO.Insert_OverTime(map,time_def)==1) { message="정상등록되었습니다."; }
				else { message="등록이 정상적으로 되지않았습니다. error!"; }				
			}
			else { message = "탄력근무 업무종료시간보다 빠르게 설정되어있습니다. 다시 시도해주세요."; }
		}
						
		return message;
	}
	
	// 휴가신청이력이 있는경우
	public String HaveHoliday(Map<String,String> map, String Hcode, int time_def) {
		String message="";
		
		if(flexTime_DAO.Select_WeekDay(map.get("startday"), map.get("Ecode"))==0) {	//신청한날이 탄력근무인지아닌지 확인.
			//현재상황 휴가신청이력+탄력근무는 아닌경우
			// 휴가신청이 무엇인지보고 시간에 맞춰서 결과전송.
			if(overTimeService.Checking_CantOverTime(map.get("Ecode"), Hcode, map.get("startday"), map.get("starttime"), false)) {
				if(overTime_DAO.Insert_OverTime(map,time_def)==1) { // 드디어 등록
					message="정상등록되었습니다.";
				}
				else { message="등록이 정상적으로 되지않았습니다. error!"; }
			}
			else { message = "탄력근무 업무종료시간보다 빠르게 설정되어있습니다. 다시 시도해주세요."; }
		}
		else {// 휴가신청 + 탄력근무가 맞을때
			//휴가신청이 무엇인지보고 탄력근무 시간에 맞춰서 결과전송.
			if(overTimeService.Checking_CantOverTime(map.get("Ecode"), Hcode, map.get("startday"), map.get("starttime"), true)) {
				if(overTime_DAO.Insert_OverTime(map,time_def)==1) { // 드디어 등록
					message="정상등록되었습니다.";
				}
				else { message="등록이 정상적으로 되지않았습니다. error!"; }
			}
			else { message = "탄력근무 업무종료시간보다 빠르게 설정되어있습니다. 다시 시도해주세요."; }
		}			
		
		return message;
	}
	
	
}//class end
