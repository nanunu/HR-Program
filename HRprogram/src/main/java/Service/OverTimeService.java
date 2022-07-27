package Service;

import java.time.DayOfWeek;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;

import repository.OverTime_DAO;


public class OverTimeService {

	@Autowired
	OverTimeService OverTimeService;
	
	@Autowired
	OverTime_DAO overTime_DAO;
	
	
	//입력받은 날짜 StartDay기준으로 월요일과 금요일을 구하는 함수
	//ex) StartDay = 2022-07-25(수요일) 일경우, 2022-07-22(월요일)~ 2022-07-29(금요일)사이 해당사원의 총근무시간을 구한다. 
	public int Checking_TimeSum(String Ecode, String StartDay) {

		LocalDate startDay = LocalDate.parse(StartDay);		
		DayOfWeek week = startDay.getDayOfWeek();
		int week_monday = startDay.getDayOfMonth();
		int week_friday = startDay.getDayOfMonth();
		
		switch(week.getValue()) { // 1: 월요일 .......7: 일요일 /// 일요일과 토요일은 js에서 걸러 줄거임..아마도.			
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
	
	
	
	
	
}//class end
