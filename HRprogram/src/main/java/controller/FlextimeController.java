package controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import model.DepartmentDTO;
import model.FlextimeDTO;
import repository.Flextime_DAO;
import repository.Staff_DAO;

@Controller
public class FlextimeController {
	
	@Autowired
	Flextime_DAO flextime_dao;
	
	@Autowired
	Staff_DAO staff_dao;
	
	/*view 페이지 처리해야함~~~~~~~~~~~~~~~~~~~~~*/
	@RequestMapping("/insertFlextime.do")
	public String insertFlexTime(FlextimeDTO command) {

		/*탄력근무제를 신청하므로 결재상태는 대기*/
		command.setFTapproval("결재대기");
		
		/*사원이 신청한 탄력근무제를 DB에 넣는 함수*/
		flextime_dao.insertFlexTimeDAO(command);
		return null;
	}
	
	@RequestMapping("/flextimeInquiry.do")
	public String inquiryFlexTime(Model model) {
		
		ArrayList<DepartmentDTO> dList = staff_dao.getDepartmentList();
		model.addAttribute("dList", dList);
		
		return null;
	}
}
