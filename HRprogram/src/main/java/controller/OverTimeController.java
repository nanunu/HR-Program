package controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
	public String process_OverTime(@RequestParam Map<String,String> map) {
		
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		if(overTimeService.Checking_TimeSum(map.get("Ecode"),map.get("starttime"),map.get("endtime"),map.get("startday")) > 12 ) {
			return "fail";
		}
		
		
		int i=0;
		Iterator<String> keys = map.keySet().iterator();
		while(keys.hasNext()) {
			String name = keys.next();
			names.add(name);
			values.add(map.get(name));
			
			System.out.println(names.get(i)+" 의 값 :"+values.get(i));
			++i;
		}
		
		
		
		
		
		if(overTimeDAO.Select_OverTime(map.get("Ecode"),map.get("startday"))==0) {
			
			overTimeDAO.Insert_OverTime(map);
			
			//등록실행
			return "";
		}
		else {
			//등록안함
			
			return "";
		}
		
		
	}
	
	
}
