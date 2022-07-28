package model;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;


//특정요일의 근무시간만 들고오는 DTO
//사용처 --> Flextime_DAO, HolidayService
public class WeekFlextimeDTO {

	private int FTCode;
	private String Ecode;
	private String FTapproval;
	
	@DateTimeFormat(pattern="hh:mm")
	private LocalTime Start;
	private LocalTime End;
	
	public int getFTCode() {
		return FTCode;
	}
	public void setFTCode(int fTCode) {
		FTCode = fTCode;
	}
	public String getEcode() {
		return Ecode;
	}
	public void setEcode(String ecode) {
		Ecode = ecode;
	}
	public String getFTapproval() {
		return FTapproval;
	}
	public void setFTapproval(String fTapproval) {
		FTapproval = fTapproval;
	}
	public LocalTime getStart() {
		return Start;
	}
	public void setStart(String start) {
		Start = LocalTime.parse(start);
	}
	public LocalTime getEnd() {
		return End;
	}
	public void setEnd(String end) {
		End = LocalTime.parse(end);
	}

	


}
