package model;

import lombok.Data;

/*급여(수당)*/
@Data
public class PayDTO {
	private String payCode; //급여(수당)코드
	private String payName; //급여(수당)명
	private int payMone; //금액
}
