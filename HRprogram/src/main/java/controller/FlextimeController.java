package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Service.FlexTimeService;
import model.DepartmentDTO;
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
	
	@RequestMapping("/free_work.do")
	public String FlexTime2(Model model) {
		
		String msg = (String) model.getAttribute("msg");
		if(msg!=null) {
			model.addAttribute("msg", msg);
		}
		
		/* 1. 부서리스트 가져옴
		 * 2. Map<부서코드, 부서이름> 저장
		 * 3. 탄력근무제를 진행하고 있는 사원들의 탄력근무데이터를 들고옴
		 * 4. 탄력근무제를 적용하고 있는 사원들의 사원코드를 중복없이 Employee 테이블에서 사원들의 정보를 들고옴
		 * 5. Map<사원코드, 사원DTO> 저장
		 */
		
		List<DepartmentDTO> dList = staff_dao.getDepartmentList();
		Map<String, String> dmap = new HashMap<String, String>();
		for(int i=0; i<dList.size(); i++) {
			dmap.put(dList.get(i).getDcode(), dList.get(i).getDname());
		}

		List<FlextimeDTO> fList = flextime_dao.getAllFlextime();
		List<EmployeeDTO> eList = flextime_dao.getEList_FlexTime();
		Map<String, EmployeeDTO> eDTOmap = ftService.changeListToMap(eList);
			
		model.addAttribute("dList", dList);
		model.addAttribute("fList", fList);
		model.addAttribute("eDTOmap", eDTOmap);
		model.addAttribute("dmap", dmap);

		return "time/free_work";
	}

	/*탄력근무 상세정보*/
	@RequestMapping("/desc_card_ver2.do")
	public String FlexTime3(Model model, @RequestParam(value="number") int FTCode, @RequestParam(value="ecode") String Ecode) {
		/*
		 * 1. 탄력근무코드를 이용해서 탄력근무이력을 들고옴
		 * 2. 사원의 정보를 가져옴
		 * 3. 부서의 이름을 가져옴
		 * 4. 요일별 탄력근무의 시간을 계산함
		 */
		
		FlextimeDTO ftDTO = flextime_dao.getFlextimeDTO(FTCode);
		EmployeeDTO eDTO = flextime_dao.getEmployeeDTO(Ecode);
		model.addAttribute("ftDTO", ftDTO);
		model.addAttribute("eDTO", eDTO);
		model.addAttribute("dname", logindao.getDname(eDTO.getDcode()));
		model.addAttribute("time", ftService.calculatorTime(ftDTO));
		return "work/desc_card_ver2";
	}
}
