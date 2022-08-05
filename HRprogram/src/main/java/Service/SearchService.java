package Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Service;

import model.HolidayRecordDTO;
import model.OverTimeDTO;

@Service
public class SearchService {

	public ArrayList<HolidayRecordDTO> Remove_Dcode(ArrayList<HolidayRecordDTO> list, String Dcode, Map<String,String> staff){
		
		for(int i=0;i<list.size();i++) {
			HolidayRecordDTO dto = list.get(i);
			String Ecode = staff.get(dto.getEcode()+"_Ecode");			
			if(Ecode!=null && dto.getEcode().equals(Ecode)) { // map에 Ecode값이 있고, 휴가레코드의 사원코드와 일치하면 실행
				String map_Dcode = staff.get(Ecode+"_Dcode");
				if(!map_Dcode.equals(Dcode)) {					
					//arraylist 삭제
					list.remove(i);
					i--;
				}
			}			
			
		}
		
		return list;
	}//부서명 Dcode 분류
	
	public ArrayList<OverTimeDTO> Remove_Dcode_OverTime(ArrayList<OverTimeDTO> list, String Dcode, Map<String,String> staff){
		
		for(int i=0;i<list.size();i++) {
			OverTimeDTO dto = list.get(i);
			String Ecode = staff.get(dto.getEcode()+"_Ecode");			
			if(Ecode!=null && dto.getEcode().equals(Ecode)) { // map에 Ecode값이 있고, 휴가레코드의 사원코드와 일치하면 실행
				String map_Dcode = staff.get(Ecode+"_Dcode");
				if(!map_Dcode.equals(Dcode)) {					
					//arraylist 삭제
					list.remove(i);
					i--;
				}
			}			
			
		}
		
		return list;
	}//부서명 Dcode 분류
	
	public ArrayList<HolidayRecordDTO> Remove_Position(ArrayList<HolidayRecordDTO> list, String Position, Map<String,String> staff){
		
		for(int i=0;i<list.size();i++) {
			HolidayRecordDTO dto = list.get(i);
			String Ecode = staff.get(dto.getEcode()+"_Ecode");			
			if(Ecode!=null && dto.getEcode().equals(Ecode)) { // map에 Ecode값이 있고, 휴가레코드의 사원코드와 일치하면 실행
				String map_position = staff.get(Ecode+"_position");
				if(!map_position.equals(Position)) {
					//arraylist 삭제
					list.remove(i);
					i--;
				}
			}			
			
		}
		
		return list;
	}//Remove_position() end
	
	public ArrayList<OverTimeDTO> Remove_Position_OverTime(ArrayList<OverTimeDTO> list, String Position, Map<String,String> staff){
		
		for(int i=0;i<list.size();i++) {
			OverTimeDTO dto = list.get(i);
			String Ecode = staff.get(dto.getEcode()+"_Ecode");			
			if(Ecode!=null && dto.getEcode().equals(Ecode)) { 
				String map_position = staff.get(Ecode+"_position");
				System.out.println(map_position+"-------------사원의 직급");
				System.out.println(Position+"------------내가 선택한 직급");
				if(!map_position.equals(Position)) {
					//arraylist 삭제
					list.remove(i);
					i--;
				}
			}			
			
		}
		
		return list;
	}//Remove_position() end
	
	public ArrayList<HolidayRecordDTO> Remove_Date(ArrayList<HolidayRecordDTO> list, String date, Map<String,String> staff){
			
		for(int i=0;i<list.size();i++) {
			HolidayRecordDTO dto = list.get(i);
			String Ecode = staff.get(dto.getEcode()+"_Ecode");			
			if(Ecode!=null && dto.getEcode().equals(Ecode)) { // map에 Ecode값이 있고, 휴가레코드의 사원코드와 일치하면 실행
				LocalDate staff_date = LocalDate.parse(date);
				LocalDate Holi_date = LocalDate.parse(dto.getHoliRuseday());
				LocalDate Holi_end = null;
				
				if(dto.getHoliRdays()!=0) {//결혼/조사이면 
					Holi_end = Holi_date.plusDays(dto.getHoliRdays());
					if(!((staff_date.isAfter(Holi_date)||staff_date.isEqual(Holi_date))&&(staff_date.isBefore(Holi_end)||staff_date.isEqual(Holi_end)))) {
						list.remove(i);
						i--;
					}
				}
				else { //아니면
					if(!staff_date.isEqual(Holi_date)) { // 일수가 같지않으면,
						//arraylist 삭제
						list.remove(i);
						i--;
					}
				}
				
			}			
			
		}
		
		return list;
		
	}//Remove_Date() end
	
	public ArrayList<OverTimeDTO> Remove_Date_OverTime(ArrayList<OverTimeDTO> list, String date, Map<String,String> staff){
		
		for(int i=0;i<list.size();i++) {
			OverTimeDTO dto = list.get(i);
			String Ecode = staff.get(dto.getEcode()+"_Ecode");			
			if(Ecode!=null && dto.getEcode().equals(Ecode)) { // map에 Ecode값이 있고, 사원코드와 일치하면 실행
				LocalDate staff_date = LocalDate.parse(date);
				LocalDate Over_date = LocalDate.parse(dto.getOtDay());
				LocalDate Over_end = null;			
			
				if(!staff_date.isEqual(Over_date)) { // 일수가 같지않으면,
					//arraylist 삭제
					list.remove(i);
					i--;
				}
			
			}			
			
		}
		
		return list;
		
	}//Remove_Date() end
	
	public ArrayList<HolidayRecordDTO> Remove_EcodeNeame(ArrayList<HolidayRecordDTO> list, ArrayList<HolidayRecordDTO> EcodeName_list){
		
		for(int x=0;x<list.size();x++) {
			HolidayRecordDTO dto = list.get(x);
			boolean eques_for = false;
			for(int y=0;y<EcodeName_list.size();y++) {
				HolidayRecordDTO Ecode_dto = EcodeName_list.get(y);
				if(Ecode_dto.getEcode().equals(dto.getEcode())) {
					eques_for = true;
					break;
				}
			}
			if(!eques_for) { list.remove(x); --x; }
		}
		
		return list;
		
	}//Remove_EcodeNeame() end
	
	public ArrayList<OverTimeDTO> Remove_EcodeNeame_OverTime(ArrayList<OverTimeDTO> list, ArrayList<OverTimeDTO> EcodeName_list){
		
		for(int x=0;x<list.size();x++) {
			OverTimeDTO dto = list.get(x);
			boolean eques_for = false;
			for(int y=0;y<EcodeName_list.size();y++) {
				OverTimeDTO Ecode_dto = EcodeName_list.get(y);
				if(Ecode_dto.getEcode().equals(dto.getEcode())) {
					eques_for = true;
					break;
				}
			}
			if(!eques_for) { list.remove(x); --x; }
		}
		
		return list;
		
	}//Remove_EcodeNeame() end
	
	
	
}
