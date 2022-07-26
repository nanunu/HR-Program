package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
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
	Login_DAO dao;
	
	@Autowired
	UserMailService user;
	
	@PostMapping("/login.do")
	public String process_login(@RequestParam(value="Ecode") String Ecode, @RequestParam(value="password") String password,HttpSession session,HttpServletResponse response) throws IOException {
		
		response.setCharacterEncoding("UTF-8");
		
		SessionDTO dto = dao.login(Ecode,password); //사원코드와 비밀번호로 DB조회.
		
		if(dto==null) {
			PrintWriter print = response.getWriter();
			print.write("<script>alert('잘못된 정보입니다. 다시입력해주세요!');location.replace('./login.jsp');</script>");
			print.close();
			return "";
		}
		else { 
			dto.setDname(dao.getDname(dto.getDcode()));	//부서코드를 통해 부서이름 찾기.
			
			dao.CmTime(Ecode,"CmAtTime"); // 출근처리 insert실행.
			
			int checking_counting =dao.Cmtime_checking(Ecode);// 오늘 출근 - 퇴근했는지 확인하는 함수 
			
			if(checking_counting > 1) { 
				// 오늘 출근-퇴근한적있는지 체크하고 예외발생시킬것.
				// 결과가 1초과 (2)일경우, 오늘 날짜로 출근을하고, 퇴근까지한 경우인데 추가로 출근까지 시도했음.
				// 트랜젝션 적용해볼것.!!!!!!!!!!!!!!
				return "redirect:/error.do"; // 예외처리 뷰를 출력하기.
			}

			//오늘하루 출근처리는 하였으나 퇴근처리는 안되었을경우 혹은 오늘 출근한적 없으면 실행.			
			session.setAttribute("SessionDTO", dto); //session 에 DTO저장.
			return "redirect:/go_record.do";
			
		}
	}
	
	@RequestMapping("/logout.do")
	public String process_logout(HttpSession session,HttpServletResponse response,HttpServletRequest request) throws IOException {
		
		SessionDTO dto = (SessionDTO) session.getAttribute("SessionDTO");
		
		if(dto!=null) {			
			
			// 오늘 출근 - 퇴근하였는지 확인하는 함수
			if(dao.Cmtime_checking(dto.getEcode()) == 0) { //출근은하였으나 퇴근처리가 되지않은 상태. 0값
				dao.CmTime(dto.getEcode(),"CmGetoffTime");//퇴근처리하기
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
