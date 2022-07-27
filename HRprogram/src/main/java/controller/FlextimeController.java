package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import model.DepartmentDTO;
import model.EmployeeDTO;
import model.FlextimeCMD;
import model.FlextimeDTO;
import repository.Flextime_DAO;
import repository.Staff_DAO;

@Controller
public class FlextimeController {
	
	@Autowired
	Flextime_DAO flextime_dao;
	
	@Autowired
	Staff_DAO staff_dao;
	
	
	@RequestMapping("/insertFlextime.do")
	public String insertFlexTime(FlextimeCMD command) {
		
		/*해당 시작일에 탄력근무제 신청이력이 있는지와 결재취소상태인지 확인*/
		
		
		/*탄력근무제를 신청하므로 결재상태는 대기*/
		command.setFTapproval("결재대기");
		
		/*사원이 신청한 탄력근무제를 DB에 넣는 함수*/
		flextime_dao.insertFlexTimeDAO(command);
		
		return "redirect:/free_work.do";
	}
	
	@RequestMapping("/free_work.do")
	public String inquiryFlexTime(Model model) {
		
		List<DepartmentDTO> dList = staff_dao.getDepartmentList();
		
		/* 1. 탄력근무제를 적용하고 있는 사원들의 탄력근무의 모든 데이터를 들고옴
		 * 2. 탄력근무제를 적용하고 있는 사원들의 사원코드를 중복없이 들고옴
		 * 3. Employee 테이블에서 사원들의 정보를 들고옴
		 * 4. Map<사원코드, 사원DTO> 저장
		 * 5. Map<부서코드, 부서이름> 저장
		 */
		List<FlextimeDTO> fList = flextime_dao.getAllFlextime();
		List<String> ecodeList = flextime_dao.getEcodeList();
		Map<String, EmployeeDTO> eDTOmap = new HashMap<String, EmployeeDTO>();
		Map<String, String> dmap = new HashMap<String, String>();
		
		for(int i=0; i<ecodeList.size(); i++) {
			String ecode = ecodeList.get(i);
			EmployeeDTO eDTO = staff_dao.getEmployeeInfo(ecode, 1);
			eDTOmap.put(ecode, eDTO);
		}
		
		for(int i=0; i<dList.size(); i++) {
			dmap.put(dList.get(i).getDcode(), dList.get(i).getDname());
		}

		model.addAttribute("dList", dList);
		model.addAttribute("fList", fList);
		model.addAttribute("eDTOmap", eDTOmap);
		model.addAttribute("dmap", dmap);

		return "time/free_work";
	}
}
