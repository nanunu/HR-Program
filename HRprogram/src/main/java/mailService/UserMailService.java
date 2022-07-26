package mailService;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import repository.Login_DAO;

public class UserMailService {
	
	@Autowired
	private Login_DAO login_DAO;
	
	@Autowired
	private MailUtil mailUtil;
	
	public String findPw(String email) throws Exception{
		//이메일이 존재하면 1, 존재하지 않으면 0
		int x = login_DAO.existEmail(email);
		String result = null;
		
		if(x==1) {
			
			//임시비밀번호 UUID 기준으로 난수생성
			//UUID : 각 개체를 고유하게 식별 가능한 값, 중복이 거의 발생하지 않음
			String tempPw = UUID.randomUUID().toString().replace("-", "");
			tempPw = tempPw.substring(0,10);
			
			//메일 전송
			mailUtil.sendMail(email, tempPw);
			
			//임시 비밀번호 업데이트
			login_DAO.updateTemporaryPw(email, tempPw);;
			
			result = "Success";
		}else {
			result = "Fail";
		}
		return result;
	}
}
