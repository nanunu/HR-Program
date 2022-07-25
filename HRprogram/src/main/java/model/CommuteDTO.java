package model;

import lombok.Data;

/*출퇴근 기록*/
@Data
public class CommuteDTO {
	private int cmcode; //출퇴근 기록코드 : auto_increment
	private String ecode; //사원코드
	private String cmday; //출퇴근 일자
	private String cmAtTime; //출근시간
	private String cmGetoffTime; //퇴근시간
}
