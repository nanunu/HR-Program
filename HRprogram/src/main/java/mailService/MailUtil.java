package mailService;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

public class MailUtil {
	public void sendMail(String email, String tempPw) throws Exception{
		
		String charSet="utf-8";
		String hostSMTP = "smtp.naver.com";
		String hostSMTPid = "아이디";
		String hostSMTPpwd = "비밀번호";
		//본인의 아이디와 비밀번호로 수정하세요!
		
		String fromEmail = "아이디@naver.com";
		String fromName = "관리자";
		
		String subject = "";
		
		subject = "[ITFREEDOM] 임시 비밀번호 발급 안내";
		
		StringBuilder sb = new StringBuilder();
        sb.append(email+"님의 임시 비밀번호입니다.\n");
        sb.append("비밀번호를 변경하여 사용하세요.\n\n");
        sb.append("임시비밀번호 : " + tempPw);
		
		try {
			Email mail = new SimpleEmail();
			mail.setHostName(hostSMTP);
			mail.setSmtpPort(465);
			mail.setCharset(charSet);
			mail.setAuthenticator(new DefaultAuthenticator(hostSMTPid, hostSMTPpwd));
			mail.setSSL(true);
			mail.setStartTLSEnabled(true);
			mail.setFrom(fromEmail, fromName, charSet);
			mail.setSubject(subject);
			mail.setMsg(sb.toString());
			mail.addTo(email);
			mail.send();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
