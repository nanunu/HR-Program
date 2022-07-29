package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Service.FlexTimeService;
import model.EmployeeDTO;
import model.FlextimeCMD;
import model.FlextimeDTO;
import repository.Flextime_DAO;
import repository.Login_DAO;
import repository.Staff_DAO;

@Controller
public class FlextimeController {
	
	@Autowired
	Flextime_DAO flextime_dao;
	
	@Autowired
	Staff_DAO staff_dao;
	
	@Autowired
	FlexTimeService ftService;
	
	@Autowired
	Login_DAO logindao;
	
	@RequestMapping("/flextime1.do")
	public String FlexTime1(Model model, FlextimeCMD command, RedirectAttributes re) {
		
		/* 
		 * 1. 탄력근무 시작일이 오늘날짜 이후인지 확인
		 * 2. 탄력근무를 신청하는 주에 초과근무가 있는지 확인
		 * 3. 탄력근무를 신청하는 주에 휴가신청이력(결재대기, 결재완료)이 있는지 확인(
		 * 4. 탄력근무제 신청이력(결재대기, 결재완료)이 있는지 확인
		 * 5. 신청
		 */
		
		String addr="";
		String msg1="오늘날짜 이후로 신청해주세요!";
		String msg2="탄력근무를 신청하는 '주'에 초과근무신청내역이 존재합니다!";
		String msg3="탄력근무를 신청하는 '주'에 휴가신청내역이 존재합니다!";
		String msg4="해당신청일에 신청내역이 존재합니다. 확인해주세요!";
		String msg5="신청되었습니다.";
		switch(ftService.insertFlexTime(command)) {
			case 1:
				model.addAttribute("msg", msg1);
				addr = "work/confirm_form_ver2";
				break;
			case 2:
				model.addAttribute("msg", msg2);
				addr = "work/confirm_form_ver2";
				break;
			case 3:
				model.addAttribute("msg", msg3);
				addr = "work/confirm_form_ver2";
				break;
			case 4:
				model.addAttribute("msg", msg4);
				addr = "work/confirm_form_ver2";
				break;
			case 5:
				re.addFlashAttribute("msg", msg5);
				addr = "redirect:/free_work.do";
				break;
		}
		return addr;
	}
	
	/*탄력근무 페이지로 이동*/
	@RequestMapping("/free_work.do")
	public String FlexTime2(Model model, HttpSession session, @RequestParam(defaultValue = "1") int pageNum) {
		
		/* 1. 부서리스트 가져옴
		 * 2. Map<부서코드, 부서이름> 저장
		 * 3. 직급리스트 가져옴
		 * 4. Map<직급코드, 직급이름> 저장
		 * 5. 탄력근무제를 진행하고 있는 사원들의 탄력근무데이터를 들고옴
		 * 6. 탄력근무제를 적용하고 있는 사원들의 사원코드를 중복없이 Employee 테이블에서 사원들의 정보를 들고옴
		 * 7. Map<사원코드, 사원DTO> 저장
		 */
		
		int limit = 5;
		int TotalOfFlex=0; // 총 탄력근무기록 갯수구하기
		int total_page;
		
		String msg = (String) model.getAttribute("msg");
		if(msg!=null) {
			model.addAttribute("msg", msg);
		}
		
		Map<String, String> dMap = staff_dao.getDepartmentMap(staff_dao.getDepartmentList());
		Map<String, String> pMap = staff_dao.getPositionMap(staff_dao.getPositionList());
	
		
		ArrayList<FlextimeDTO> flexList = (ArrayList<FlextimeDTO>) model.getAttribute("flexList");
		ArrayList<FlextimeDTO> cutList = new ArrayList<FlextimeDTO>();
		if(flexList!=null) {
			String dcode = (String) model.getAttribute("dcode");
			String position = (String) model.getAttribute("position");
			String pickdate = (String) model.getAttribute("pickdate");
			String ecodeNename = (String) model.getAttribute("ecodeNename");
			pageNum = (Integer) model.getAttribute("pageNum");
			TotalOfFlex = flexList.size();
			cutList = ftService.cutPage(flexList, limit, pageNum);
			model.addAttribute("fList", cutList);
			model.addAttribute("dc", dcode);
			model.addAttribute("po", position);
			model.addAttribute("pi", pickdate);
			model.addAttribute("eNe", ecodeNename);
		}else {
			String dcode2 = (String) session.getAttribute("Dcode");
			ArrayList<FlextimeDTO> fList = flextime_dao.getAllFlextime(dcode2);
			//List<FlextimeDTO> fList = flextime_dao.getAllFlextime(dcode2);
			TotalOfFlex = fList.size();
			cutList = ftService.cutPage(fList, limit, pageNum);
			model.addAttribute("fList", cutList);
			model.addAttribute("dc", dcode2);
		}
		
		if(TotalOfFlex==0) {
			total_page=1;
		}
		else if(TotalOfFlex % limit == 0) {
			total_page = TotalOfFlex/limit;
		}
		else {
			total_page =TotalOfFlex/limit;
			total_page =  total_page + 1;
		}
		
		List<EmployeeDTO> eList = flextime_dao.getEList_FlexTime();
		Map<String, EmployeeDTO> eDTOmap = ftService.changeListToMap(eList);
		
		model.addAttribute("total", total_page);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("dMap", dMap);
		model.addAttribute("pMap", pMap);
		model.addAttribute("eDTOmap", eDTOmap);

		return "time/free_work";
	}

	/*탄력근무 상세정보*/
	@RequestMapping("/desc_card_ver2.do")
	public String FlexTime3(Model model, @RequestParam(value="number") int FTCode, @RequestParam(value="ecode") String Ecode, HttpServletResponse resp) throws IOException {
		/*
		 * 1. 탄력근무코드를 이용해서 탄력근무이력을 들고옴
		 * 2. 사원의 정보를 가져옴
		 * 3. 부서의 이름을 가져옴
		 * 4. 요일별 탄력근무의 시간을 계산함
		 */
		resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
		PrintWriter print = resp.getWriter();
		FlextimeDTO ftDTO;
		try {
			ftDTO = flextime_dao.getFlextimeDTO(FTCode);
			model.addAttribute("ftDTO", ftDTO);
			model.addAttribute("time", ftService.calculatorTime(ftDTO));
		} catch (Exception e) {
			System.out.println("왓을까?");
			return "redirect:/error.do";
		}
		EmployeeDTO eDTO = flextime_dao.getEmployeeDTO(Ecode);
		
		model.addAttribute("eDTO", eDTO);
		model.addAttribute("dname", logindao.getDname(eDTO.getDcode()));
		model.addAttribute("pname", logindao.getPname(eDTO.getPosition()));
		return "work/desc_card_ver2";
	}
	
	/*탄력근무 검색*/
	@RequestMapping("/flextimeProcess.do")
	public String FlexTime4(@RequestParam(defaultValue="all") String dcode, 
			@RequestParam(defaultValue="all") String position,
			@RequestParam(required=false, value="date") String pickdate, 
			@RequestParam(required=false) String ecodeNename,
			RedirectAttributes re, @RequestParam(defaultValue="1") int pageNum) {
		/*
		 * 1. 부서별 , 직급별, 날짜별, 사원번호 또는 사원명 조회
		 * 2. 부서별 & (직급, 날짜, 사원번호 또는 사원명) 조회
		 * 3. 직급별 & (날짜, 사원번호 또는 사원명) 조회
		 * 4. 날짜 & 사원번호 또는 사원명 조회
		 * 5. 부서 & 직급 & (날짜 , 사원번호 또는 사원명) 조회
		 * 6. 부서 & 날짜 & 사원번호 또는 사원명 조회
		 * 7. 직급 & 날짜 & 사원번호 또는 사원명 조회
		 * 8. 부서 & 직급 & 날짜 & 사원번호 또는 사원명 조회
		 */
		
		ArrayList<FlextimeDTO> flexList = ftService.flextime_Process(dcode, position, pickdate, ecodeNename);
		re.addFlashAttribute("flexList", flexList);
		re.addFlashAttribute("dcode", dcode);
		re.addFlashAttribute("position", position);
		
		re.addFlashAttribute("pickdate", pickdate);
		re.addFlashAttribute("ecodeNename", ecodeNename);
		re.addFlashAttribute("pageNum", pageNum);
		return "redirect:/free_work.do";
	}
	
	/*결재승인을 하는 경우 : 결재대기 ==> 승인*/
	@RequestMapping("/result_ok2.do")
	public void FlexTime5(@RequestParam String FTcode, HttpServletResponse resp) throws IOException {
		flextime_dao.updateFlexApproval1(FTcode);
		resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter print = resp.getWriter();
        print.write("<script>alert('승인 완료되었습니다.');"
        		+ "window.close(); opener.location.reload();</script>");
        print.close();
	}
	
	/*결재승인을 하는 경우 : 결재대기(waiting) ==> 반려(back)*/
	/*결재승인을 하는 경우 : 결재완료(completed) ==> 결재취소(cancel)*/
	@RequestMapping("/result_fail11.do")
	public void FlexTime6(@RequestParam String FTcode, @RequestParam String FTapproval, HttpServletResponse resp) throws IOException{
		resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
		PrintWriter print = resp.getWriter();
		flextime_dao.updateFlexApproval2(FTcode, FTapproval);
		print.write("<script>alert('결재가 취소되었습니다.');"
	       		+ "window.close(); opener.location.reload();</script>");
	    print.close();
	}
	
	/*결재요청을 하는 경우 : 결재대기(waiting) ==> 결재취소(DB삭제)*/
	/*결재요청을 하는 경우 : 결재완료(completed) ==> 결재취소(cancel)*/
	@RequestMapping("/result_fail22.do")
	public void FlexTime7(@RequestParam String FTcode, @RequestParam String FTapproval, HttpServletResponse resp) throws IOException{
		resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
		PrintWriter print = resp.getWriter();
		flextime_dao.updateFlexApproval3(FTcode, FTapproval);
		print.write("<script>alert('결재가 취소되었습니다.');"
	       		+ "window.close(); opener.location.reload();</script>");
	    print.close();
	}
}
