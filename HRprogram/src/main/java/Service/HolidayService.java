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
	
	// 휴가코드가 그외일때 실행할 함수
	public String Hcode_event(Map<String,String> map, String Hcode, boolean hasOverTime) {
		
		
		return Hcode;	
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
		double holiday_count = dto.getResidualholiday(); // 잔여연차수담기
		
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
			if(holiday_count-(time_dif*0.125) <=0.0) {
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


