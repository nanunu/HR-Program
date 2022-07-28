package Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.EmployeeDTO;
import model.FlextimeCMD;
import model.FlextimeDTO;
import repository.Flextime_DAO;
import repository.Holiday_DAO;
import repository.OverTime_DAO;
import repository.Staff_DAO;

@Service
public class FlexTimeService {
	@Autowired
	Flextime_DAO flextime_dao;
	
	@Autowired
	Staff_DAO staff_dao;
	
	@Autowired
	OverTime_DAO over_dao;
	
	@Autowired
	Holiday_DAO holi_dao;
	
	public int insertFlexTime(FlextimeCMD command) {
		LocalDate today = LocalDate.now();
		LocalDate monday = LocalDate.parse(command.getStartday());
		LocalDate friday = monday.plusDays(4);		
		
		/*1. 탄력근무 시작일이 오늘날짜 이후인지 확인*/
		if(!today.isBefore(monday)) {
			return 1;
		}
		
		/*2. 탄력근무를 신청하는 주에 초과근무가 있는지 확인*/
		int over = over_dao.Select_OverTimeSum(command.getEcode(), monday.toString(), friday.toString());
		if(over!=0) {
			return 2;
		}
		
		/*3. 탄력근무를 신청하는 주에 휴가신청이력이 있는지 확인*/
		int holi = holi_dao.checkHoliday(command.getEcode(), monday.toString(), friday.toString());
		if(holi!=0) {
			return 3;
		}
		
		/*4. 해당 시작일에 탄력근무제 신청이력이 있는지 확인*/
		int flex = flextime_dao.checkFlexTime(command);
		if(flex!=0) {
			return 4;
		}

		/*전처리 - 탄력근무제를 신청하므로 결재상태는 대기*/
		command.setFTapproval("waiting");
		
		/*5. 사원이 신청한 탄력근무제를 DB에 넣는 함수*/
		flextime_dao.insertFlexTimeDAO(command);
		return 5;
	}
	
	/*List<EmployeeDTO>를 Map<Ecode, EmployeeDTO>로 변환하는 함수*/
	public Map<String, EmployeeDTO> changeListToMap(List<EmployeeDTO> eList){
		Map<String, EmployeeDTO> eDTOmap = new HashMap<String, EmployeeDTO>();
		
		for(int i=0; i<eList.size(); i++) {
			eDTOmap.put(eList.get(i).getEcode(), eList.get(i));
		}
		
		return eDTOmap;
	}
	
	/*요일별 탄력근무의 시간을 계산하는 함수*/
	public long[] calculatorTime(FlextimeDTO ftDTO) {
		long[] time = new long[6];
	
		time[0] = ChronoUnit.HOURS.between(ftDTO.getMonStart(), ftDTO.getMonend());
		time[1] = ChronoUnit.HOURS.between(ftDTO.getTueStart(), ftDTO.getTueend());
		time[2] = ChronoUnit.HOURS.between(ftDTO.getWedStart(), ftDTO.getWedend());
		time[3] = ChronoUnit.HOURS.between(ftDTO.getThuStart(), ftDTO.getThuend());
		time[4] = ChronoUnit.HOURS.between(ftDTO.getFriStart(), ftDTO.getFriend());
		time[5] = 0;
		for(int i=0; i<5; i++) {
			time[5] += time[i];
		}
		return time;
	}
}
