package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mailService.UserMailService;
import model.SessionDTO;
import repository.Login_DAO;

@Controller
@Configuration
public class LoginController {
	
	@Autowired
	LoginController loginController;
	
	@Autowired
	Login_DAO login_DAO;
	
	@Autowired
	UserMailService user;
	
	@PostMapping("/login.do")
	//@Transactional 트랜잭션 적용하고 싶었으나, 현 로직으로는 어려움
	public String process_login(@RequestParam(value="Ecode") String Ecode, @RequestParam(value="password") String password,HttpSession session,HttpServletResponse response) throws IOException {
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		SessionDTO dto = login_DAO.login(Ecode,password); //사원코드와 비밀번호로 DB조회.
		
		if(dto==null) {
			PrintWriter print = response.getWriter();
			print.write("<script>alert('잘못된 정보입니다. 다시입력해주세요!'); location.replace('./login.jsp');</script>");
			print.close();
			return "";
		}
		else {
			
			login_DAO.Insert_CmTime(Ecode,"CmAtTime"); // 출근처리 insert실행.
			
			Integer checking_Ecode =login_DAO.Cmtime_checking(Ecode);// 오늘 출근 - 퇴근했는지 확인하는 함수 
			
			if(checking_Ecode == null) {
				return "redirect:/error.do"; // 예외처리 뷰를 출력하기.
			}
			else if(checking_Ecode == 1) { // 오늘 처음으로 출근한경우. 			
				//session.setAttribute("SessionDTO", dto); //session 에 DTO저장. ==> Session에 객체를 저장할 경우 서버가 재시작 되었을 때 session이 유지되지 않음
				session.setAttribute("Ecode", dto.getEcode());//session에 사원코드
				session.setAttribute("Ename", dto.getEname());//session에 사워명
				session.setAttribute("Dcode", dto.getDcode());//session에 부서코드
				session.setAttribute("Dname", login_DAO.getDname(dto.getDcode()));//session에 부서이름 //부서코드를 통해 부서이름 찾기.
				session.setAttribute("position", dto.getPosition());//session에 직급
				session.setAttribute("Pname", login_DAO.getPname(dto.getPosition()));// session직급이름찾기.
				session.setMaxInactiveInterval(24*60*60);//sesssion 유효시간설정 24시간
				return "redirect:/go_record.do";
								
			}			
			else {			
				//오늘하루 출근처리는 하였으나 퇴근처리는 안되었을경우.				
				if(login_DAO.Delete_LastLogin(login_DAO.Select_LastLogin(Ecode, LocalDate.now()))==1) {
					//해당직원의 마지막 로그인한 pknum을 가져와서 해당 pknum삭제하는 함수
					
					session.setAttribute("Ecode", dto.getEcode());//session에 사원코드
					session.setAttribute("Ename", dto.getEname());//session에 사워명
					session.setAttribute("Dcode", dto.getDcode());//session에 부서코드
					session.setAttribute("Dname", login_DAO.getDname(dto.getDcode()));//session에 부서이름 //부서코드를 통해 부서이름 찾기.
					session.setAttribute("position", dto.getPosition());//session에 직급
					session.setAttribute("Pname", login_DAO.getPname(dto.getPosition()));// session직급이름찾기.
	
					return "redirect:/go_record.do";
				}
				else { return "redirect:/error.do"; } // 예외처리 뷰를 출력하기.
			}
			
		}
	}
	
	@RequestMapping("/logout.do")
	public String process_logout(HttpSession session,HttpServletResponse response,HttpServletRequest request) throws IOException {
		
		String Ecode = (String) session.getAttribute("Ecode");
	
		if(Ecode!=null) {	         
	         // 오늘 출근 - 퇴근하였는지 확인하는 함수
	         if(login_DAO.Cmtime_checking(Ecode) != null) { //출근은하였으나 퇴근처리가 되지않은 상태. 0값
	        	 login_DAO.Insert_CmTime(Ecode,"CmGetoffTime");//퇴근처리하기
	            session.invalidate();//세션 삭제
	            return "redirect:/login.jsp";
	         }
	         else { // 0값이 아닐경우 무조건 예외처리!
	            return "redirect:/error.do"; // 예외처리 뷰출력
	         }
	         
			
		}
		//if문에 부합하지 않으면 session의 문제가 있다는것.
		return "redirect:/error.do"; // 예외처리 뷰출력
	}
	
	@RequestMapping("/pwfind.do")
	public String findpw(Model model, @RequestParam String email, HttpServletResponse resp) throws Exception{
	      String result = user.findPw(email);
	      String addr ="";
	      if(result.equals("Success")){
	         model.addAttribute("mailsend", result);
	         addr = "redirect:/login.jsp";
	      }else {
	         resp.setCharacterEncoding("UTF-8");
	         resp.setContentType("text/html; charset=UTF-8");
	         PrintWriter print = resp.getWriter();
	         print.write("<script>alert('존재하지 않는 이메일입니다. 다시입력해주세요!'); location.replace('./lost_pw.jsp');</script>");
	         print.close();
	      }
	      return addr;
	   }


}