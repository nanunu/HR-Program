package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import model.SessionDTO;
import repository.Login_DAO;

@Controller
@Configuration
public class LoginController {
	
	@Autowired
	LoginController loginController;
	
	@Autowired
	Login_DAO dao;
	
	@PostMapping("/login.do")
	public String process_login(@RequestParam(value="Ecode") String Ecode, @RequestParam(value="password") String password,HttpSession session,HttpServletResponse response) throws IOException {
		
		SessionDTO dto = dao.login(Ecode,password); //사원코드와 비밀번호로 DB조회.
		
		if(dto==null) {
			PrintWriter print = response.getWriter();
			print.write("<script>alert('잘못된 정보입니다. 다시입력해주세요!');location.replace('./login.jsp');</script>");
			print.close();
			return "";
		}
		else { 
			dto.setDname(dao.getDname(dto.getDcode()));	//부서코드를 통해 부서이름 찾기.
			
			if(dao.Cmtime_checking(Ecode) != 0) { 
				// 오늘 출근한적있는지 체크하기. 있으면 예외발생시킬것.
				// throws Exception;
			}
			else {	 //출근처리 하기 . 출근한적이 없으면 실행		
				dao.CmTime(Ecode,"CmAtTime");							
				session.setAttribute("SessionDTO", dto); //session 에 DTO저장.
			}
			
			return "redirect:/go_record.do";
		}
	}
	
	@RequestMapping("/logout.do")
	public String process_logout(HttpSession session,HttpServletResponse response,HttpServletRequest request) throws IOException {
		
		SessionDTO dto = (SessionDTO) session.getAttribute("SessionDTO");
		
		if(dto!=null) {
			
			dao.CmTime(dto.getEcode(),"CmGetoffTime");//퇴근처리하기
						
			session.invalidate();//세션 삭제
			
		}
		
		return "redirect:/login.jsp";
	}
	
}
