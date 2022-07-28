package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import repository.Staff_DAO;

@Controller
public class StaffController {
	
	@Autowired
	Staff_DAO dao;
	

}
