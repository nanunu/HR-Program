package Service;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.WeekFlextimeDTO;
import repository.Flextime_DAO;
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
	
	
	
}//class end
