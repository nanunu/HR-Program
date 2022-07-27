package Service;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.EmployeeHolidayDTO;
import model.FlextimeDTO;
import repository.Flextime_DAO;
import repository.Holiday_DAO;

@Service
public class HolidayService {
	
	@Autowired
	Holiday_DAO holiday_DAO;
	
	@Autowired
	Flextime_DAO flexTime_DAO;
	
	public double isholidayCount(Map<String,String> map) {
		
		EmployeeHolidayDTO dto = holiday_DAO.Select_Holiday(map.get("Ecode"));
		
		String Hcode = map.get("Holiday");		
		String Hcode_frist = Hcode.substring(0, 2);		
		if(Hcode_frist.equals("H0")) {
			holidayTime(map.get("startday"),map.get("starttime"),map.get("endtime"),map.get("Ecode"));
		}
		else if(Hcode_frist.equals("H1")) {
			holidayDay();
		}
		else {}
		
		return 0.0;
	}

	private void holidayTime(String startday, String starttime, String endtime, String Ecode) {
		
		int start = Integer.parseInt(starttime);
		int end = Integer.parseInt(endtime);
		int time_def = end-start;
		//LocalDate startday = LocalDate.parse(startday);
		
		//초과근무시간조회,  탄력근무시간조회.
		FlextimeDTO dto = flexTime_DAO.Select_WeekDay(startday, Ecode);
		if(dto==null) {// 값이 null이면, 탄력근무이력없음. 
			
		}
		else { // 아니면, 탄련근무하는 주임.
			
			LocalDate date = LocalDate.parse(startday);
			int dto_start_time, dto_end_time;
			switch(date.getDayOfWeek().getValue()) {
				case 1: //월요일
						  dto_start_day=dto.getMonStart().getHours();
						  dto.getMonend().getMinutes();
				case 2:
				case 3:
				case 4: 
				case 5:			
			}
			
			
		}
		
		
		
		
	}
	
	private void holidayDay() {
		
		
		
	}
	
	
	
}//class end

































































































































































































































































































}//class jd

