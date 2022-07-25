package model;

import lombok.Data;

/*사원정보*/
@Data
public class EmployeeDTO {
	private String ecode; //사원코드
	private String ename; //사원이름
	private String dcode; //부서코드
	private String position; //직급
	private String email; //이메일
	private String joinday; //입사일
	private String phone; //연락처
	private String birth; //생년월일
	private String zipcode; //우편번호
	private String address; //주소
	private String sex; //성별
	private String education; //최종학력
	private boolean military; //병역
	private String maritalStatus; //결혼여부
	private String licence; //자격증
	private String bank; //은행
	private String bankbook; //계좌번호
	private String depositor; //예금주
	private String car; //차량
}
