package controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Service.OverTimeService;
import repository.OverTime_DAO;


@Controller
public class OverTimeController {

	@Autowired
	OverTimeController overTimeController;
	
	@Autowired
	OverTime_DAO overTimeDAO;
	
	@Autowired
	OverTimeService overTimeService;
	
	@RequestMapping("/OverTime.do")
	public String process_OverTime(@RequestParam Map<String,String> map, Model model) {
		
		int time_def = 0; //시작시간 - 종료시간 시간차담는 변수
		
		int timeSum = overTimeService.Checking_TimeSum(map.get("Ecode"),map.get("startday"));
		// 주단위로 검색하여 주간초과근무시간이 12시간 이상했을경우, if문실행. 등록처리 x
		if( timeSum >= 12 ) {			
			model.addAttribute("message","주간 초과근무 가능한 12시간 중 12시간을 이미 했습니다.");
			return "work/confirm_form_ver1";
		}
		else{
			int startTime = Integer.parseInt(map.get("starttime"));
			int endTime = Integer.parseInt(map.get("endtime"));
			time_def = endTime-startTime;
			
			if(time_def+timeSum > 12) {				
				model.addAttribute("message","요청하신 초과근무시간을 적용하면 주간 초과근무 가능한 시간이 12시간을 초과합니다.");
				return "work/confirm_form_ver1";
			}
			
		}
	
		if(overTimeDAO.Select_OverTime(map.get("Ecode"),map.get("startday"))==0) {
			// 오늘 날짜를 기준으로 이미 신청했는지 확인
			
			overTimeDAO.Insert_OverTime(map,time_def);
			
			//등록실행 초과근무조회페이지로 이동
			return "time/over_work";
		}
		else {
			//등록안함			
			model.addAttribute("message","이미 당일날짜로 초과근무를 신청하셨습니다.");
			return "work/confirm_form_ver1";
		}
		
	}//process_OverTime end
	
	
	
	
	
	
	
}//class end
