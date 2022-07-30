package Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.EmployeeDTO;
import model.FlextimeCMD;
import model.FlextimeDTO;
import model.PagingDTO;
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
	
	@Autowired
	OverTimeService over_Serv;
	
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

	/*검색 프로세스*/
	public ArrayList<FlextimeDTO> flextime_Process(String dcode, String position, String pickdate, String ecodeNename) {
		ArrayList<FlextimeDTO> fList;
		
		/*직급별*/
		if(!position.equals("all")) {
			fList = flextime_dao.selectPosition(position);
		}else {
			fList = flextime_dao.getAllFlextime();
		}
		
		/*부서별*/
		if(!dcode.equals("all")) {
			fList = searchDepartment(fList, dcode);
		}
		
		/*날짜*/
		if(pickdate != null && !pickdate.equals("")) {
			pickdate = over_Serv.Checking_TimeSum(pickdate);
			fList = searchDate(fList, pickdate);
		}
		
		/*사원코드 OR 사원명*/
		if(ecodeNename != null && !ecodeNename.equals("")) {
			/*if : 사원코드, else : 사원명*/
			if((ecodeNename.charAt(0)>='0' && ecodeNename.charAt(0)<='9')||ecodeNename.charAt(0)=='z') {
				fList = searchEcode(fList, ecodeNename);
			}else {
				fList = searchEname(fList, ecodeNename);
			}
		}
		
		return fList;
	}
	
	/*부서별 검색이 전체("all")이 아닌 경우 - 부서별 검색할 경우*/
	public ArrayList<FlextimeDTO> searchDepartment(ArrayList<FlextimeDTO> fList, String dcode) {
		//ArrayList<Integer> numlist = new ArrayList<Integer>();
		
		for(int i=0; i<fList.size(); i++) {
			if(!(fList.get(i).getEcode().substring(6, 10)).equals(dcode)) {
				fList.remove(i);
				i--;
			}
		}
		return fList;
	}
	
	/*날짜를 포함하여 검색할 경우*/
	public ArrayList<FlextimeDTO> searchDate(ArrayList<FlextimeDTO> fList, String pickdate){
		for(int i=0; i<fList.size(); i++) {
			if(!(fList.get(i).getFTstartday().toString().equals(pickdate))) {
				fList.remove(i);
				i--;
			}
		}
		return fList;
	}
	
	/*사원코드를 포함하여 검색*/
	public ArrayList<FlextimeDTO> searchEcode(ArrayList<FlextimeDTO> fList, String ecodeNename){
		for(int i=0; i<fList.size(); i++) {
			if(!(fList.get(i).getEcode().contains(ecodeNename))) {
				fList.remove(i);
				i--;
			}
		}
		return fList;
	}
	
	/*사원이름을 포함하여 검색 - 사원이름이 포함되어있지 않으면 삭제 */
	public ArrayList<FlextimeDTO> searchEname(ArrayList<FlextimeDTO> fList, String ecodeNename){
		ArrayList<EmployeeDTO> employList = staff_dao.getEmployList();
		
		for(int i=0; i<employList.size(); i++) {
			for(int j=0; j<fList.size(); j++) {
				if(employList.get(i).getEcode().equals(fList.get(j).getEcode())) {
					if(!(employList).get(i).getEname().contains(ecodeNename)) {
						fList.remove(j);
						j--;
					}
				}
			}
		}
		return fList;
	}

	/*페이징 처리 : 탄력근무제목록을 'limit'개씩 잘라서 돌려주는 함수*/
	public ArrayList<FlextimeDTO> cutPage(ArrayList<FlextimeDTO> fList, int limit, String page){
		int pageNum = Integer.parseInt(page);
		int first = (pageNum-1)*limit; //page : 1 - 0번기록부터, page : 2 - 5번기록부터
		int index = first+limit;
		if(index>fList.size()) {
			index = fList.size();
		}
		ArrayList<FlextimeDTO> cutList= new ArrayList<FlextimeDTO>();
		for(int i=first; i<index; i++ ) {
			cutList.add(fList.get(i));
		}
		return cutList;
	}
	
	/*시작페이지, 끝페이지를 정해주는 함수*/
	public PagingDTO paging(int TotalOfFlex, int limit, String page) {

		int pageNum = Integer.parseInt(page);
		int total_page = 1;
		int startpage = 1;
		int endpage = 5;
		
		if(TotalOfFlex==0) {
			total_page = 1;
		}else if(TotalOfFlex % limit ==0) {
			total_page = TotalOfFlex/limit;
		}else {
			total_page = TotalOfFlex/limit + 1;
		}
		
		if(total_page<=5) {
			endpage = total_page;
		}
		
		if(total_page>5) {
			if(pageNum>=4) {
				startpage=pageNum-2;
				endpage=pageNum+2;
			}
			if(pageNum>=total_page-2) {
				endpage=total_page;
				startpage=endpage-4;
				
			}
		}
		
		PagingDTO pageDTO = new PagingDTO();
		pageDTO.setTotal_page(total_page);
		pageDTO.setPageNum(pageNum);
		pageDTO.setStartpage(startpage);
		pageDTO.setEndpage(endpage);
		
		return pageDTO;
	}
	
}
