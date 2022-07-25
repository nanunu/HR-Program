package model;

import lombok.Data;

/*급여명세서*/
@Data
public class PayStubDTO {
	private int psCode; //명세서코드
	private String ecode; //사원코드
	private int psMonth; //근무월
	private String allPayCode; //급여(수당)코드
	private String allDeCode; //공제코드
	private int sum; //총액
	private boolean send; //명세서발송
	private boolean psStatus; //지급여부
}
