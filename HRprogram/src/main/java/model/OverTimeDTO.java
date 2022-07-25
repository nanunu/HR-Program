package model;

import lombok.Data;

/*초과근무기록*/
@Data
public class OverTimeDTO {
	private int otCode; //초과근무기록코드
	private String ecode; //사원코드
	private String otDay; //초과근무일
	private String otStartTime; //시작시간
	private String otEndTime; //종료시간
	private int timeSum; //총 초과근무시간
	private String otApproval; //결재상태
}
