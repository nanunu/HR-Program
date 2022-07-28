package model;

import lombok.Data;

/*session에 저장할 개인정보*/
@Data
public class SessionDTO {
	private String ecode; //사원코드
	private String ename; //사원이름
	private String dcode; //부서코드
	private String dname; //부서이름
	private String position; //직급코드
	private String pname;
}
