package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import repository.Holiday_DAO;

@Controller
public class HolidayController {

	@Autowired
	HolidayController holidayController;
	
	@Autowired
	Holiday_DAO hoilday_DAO;

	
	
	
	
}
