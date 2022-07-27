package model;

import java.sql.Time;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class FlextimeDTO {
	private int FTCode;
	private String Ecode;
	private String FTapproval;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date FTstartday;
	private Date FTendday;
	private Date AdmissionDate;
	
	@DateTimeFormat
	private Time MonStart;
	private Time Monend;
	private Time TueStart;
	private Time Tueend;
	private Time WedStart;
	private Time Wedend;
	private Time ThuStart;
	private Time Thuend;
	private Time FriStart;
	private Time Friend;
	
}
