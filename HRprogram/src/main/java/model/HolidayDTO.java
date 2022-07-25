package model;

import lombok.Data;

/*휴가*/
@Data
public class HolidayDTO {
	private String hcode; // 휴가코드
	private String hname; //휴가명
	private int htime; //부여시간
	private int hday; //부여일
}
