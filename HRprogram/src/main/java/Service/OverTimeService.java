package Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;

import repository.OverTime_DAO;

public class OverTimeService {

	@Autowired
	OverTimeService OverTimeService;
	
	@Autowired
	OverTime_DAO overTime_DAO;
	
	public int Checking_TimeSum(String Ecode, String StartTime, String EndTime, String StartDay) {
		
		int starttime = Integer.valueOf(StartTime);
		int endtime = Integer.valueOf(EndTime);
		
		
		LocalDate startDay = LocalDate.parse(StartDay);
		DayOfWeek week = startDay.getDayOfWeek();
		
		
		switch(week.getValue()) { // 1: 일요일 .......7: 토요일 /// 일요일과 토요일은 js에서 걸러줌			
			case 2:  
				break;
			case 3: break;
			case 4: break;
			case 5: break;
			case 6:	break;
			default :
		}
		
		overTime_DAO.Select_OverTimeSum(Ecode);
		
		
		return 1;
		
	}
	
}
