package controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Service.HolidayService;
import Service.OverTimeService;
import repository.Holiday_DAO;
import repository.OverTime_DAO;

@Controller
public class HolidayController {

	@Autowired
	HolidayController holidayController;
	
	@Autowired
	Holiday_DAO holiday_DAO;

	@Autowired
	HolidayService holidayService;
	
	@Autowired
	OverTimeService overTimeService;
	
	@Autowired
	OverTime_DAO overTime_DAO;
	
	@RequestMapping("/HoliRecord.do")
	public String process_HoliRecord(@RequestParam Map<String,String> map, Model model) {
		
		/**
 			보유연차를 사용하는것 ! --> 연차, 조퇴, 반차
 		 	보유연차를 사용안하는것 ! --> 본인결혼, 조사
			
				외출 / 지각 --> 금액에서..공제?



		 	초과근무가 있을시, 신청대기던, 완료이던 상관 x 
		 	무조건막기! --> 본인결혼, 조사 , 연차, 조퇴 
			상황에따라 막기 --> 반차.외출			
			안막는것 --> 지각.
			
			
			휴가는 신청반려되거나, 신청을 취소할수있다.
			휴가가 신청반려된경우 재신청할수있다. (DB에 남음)
			신청을 취소한 경우, DB에서 삭제.
			
			completed 되기전에 신청을 취소할경우 --> 디비삭제
			신청반려된것 ---> 결제상태 반려
			결제승인후에 취소--> 결제취소로 변경.
		
		
		 * */
		
		//step 1. 남아있는 연차개수 구하기!		
		holidayService.isholidayCount(map);
		
		int Checking = overTime_DAO.Select_OverTime(map.get("Ecode"), map.get("startday"));//신청요구한날기준으로 초과근무를 하는지 안하는지 확인.
		if(Checking==0) { //실행가능
			
		}
		else {//근무코드 확인.

			
		}
		return "";
	}
	
	
}
