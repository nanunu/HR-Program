package model;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

public class FlextimeDTO {
	private int FTCode;
	private String Ecode;
	private String FTapproval;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date FTstartday;
	private Date FTendday;
	private Date AdmissionDate;
	
	@DateTimeFormat(pattern="hh:mm")
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
	
	public int getFTCode() {
		return FTCode;
	}
	public String getEcode() {
		return Ecode;
	}
	public String getFTapproval() {
		return FTapproval;
	}
	public Date getFTstartday() {
		return FTstartday;
	}
	public Date getFTendday() {
		return FTendday;
	}
	public Date getAdmissionDate() {
		return AdmissionDate;
	}
	public LocalTime getMonStart() {
		return MonStart.toLocalTime();
	}
	public LocalTime getMonend() {
		return Monend.toLocalTime();
	}
	public LocalTime getTueStart() {
		return TueStart.toLocalTime();
	}
	public LocalTime getTueend() {
		return Tueend.toLocalTime();
	}
	public LocalTime getWedStart() {
		return WedStart.toLocalTime();
	}
	public LocalTime getWedend() {
		return Wedend.toLocalTime();
	}
	public LocalTime getThuStart() {
		return ThuStart.toLocalTime();
	}
	public LocalTime getThuend() {
		return Thuend.toLocalTime();
	}
	public LocalTime getFriStart() {
		return FriStart.toLocalTime();
	}
	public LocalTime getFriend() {
		return Friend.toLocalTime();
	}
	public void setFTCode(int fTCode) {
		FTCode = fTCode;
	}
	public void setEcode(String ecode) {
		Ecode = ecode;
	}
	public void setFTapproval(String fTapproval) {
		FTapproval = fTapproval;
	}
	public void setFTstartday(Date fTstartday) {
		FTstartday = fTstartday;
	}
	public void setFTendday(Date fTendday) {
		FTendday = fTendday;
	}
	public void setAdmissionDate(Date admissionDate) {
		AdmissionDate = admissionDate;
	}
	public void setMonStart(Time monStart) {
		MonStart = monStart;
	}
	public void setMonend(Time monend) {
		Monend = monend;
	}
	public void setTueStart(Time tueStart) {
		TueStart = tueStart;
	}
	public void setTueend(Time tueend) {
		Tueend = tueend;
	}
	public void setWedStart(Time wedStart) {
		WedStart = wedStart;
	}
	public void setWedend(Time wedend) {
		Wedend = wedend;
	}
	public void setThuStart(Time thuStart) {
		ThuStart = thuStart;
	}
	public void setThuend(Time thuend) {
		Thuend = thuend;
	}
	public void setFriStart(Time friStart) {
		FriStart = friStart;
	}
	public void setFriend(Time friend) {
		Friend = friend;
	}
	
	
}
