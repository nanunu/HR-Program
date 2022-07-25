package model;

import lombok.Data;

/*휴가기록*/
@Data
public class HolidayRecordDTO {
	private int holiRcode; //휴가기록코드
	private String ecode; //사원코드
	private String holicode; //휴가코드
	private String holiRuseday; //휴가적용일
	private String holiRstarttime; //휴가시작시간
	private String holiRendtime; //휴가종료시간
	private int holiRusetime; //휴가사용시간
	private int holiRdays; //휴가사용일수 : 휴가적용일이 하루이상일 경우 위 3개의 항목을 사용하지 않고 해당 항목 사용
	private String holiRreason; // 휴가사유
	private String holiRapproval; //결재상태
}
