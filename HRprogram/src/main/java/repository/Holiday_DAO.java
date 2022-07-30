package repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import model.EmployeeHolidayDTO;
import model.HolidayRecordDTO;
import model.SessionDTO;

@Repository
public class Holiday_DAO {

	JdbcTemplate jt;
	
	public Holiday_DAO(DataSource ds) { this.jt = new JdbcTemplate(ds); }

	// 특정사원의 연차현황 DTO로 가져오기
	public EmployeeHolidayDTO Select_Holiday(String Ecode) {
		String sql = "select * from EH where Ecode=?";
		RowMapper<EmployeeHolidayDTO> mapper = new RowMapper<EmployeeHolidayDTO>() {

			@Override
			public EmployeeHolidayDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				EmployeeHolidayDTO dto = new EmployeeHolidayDTO();
				dto.setEcode(rs.getString("ecode"));
				dto.setNumOfmyholiday(rs.getDouble("numOfmyholiday"));				
				dto.setUsenumOfholiday(rs.getDouble("usenumOfholiday"));
				return dto;
			}
			
		};
		
		List<EmployeeHolidayDTO> list = jt.query(sql,mapper,Ecode);
		
		if(list!=null) { return list.get(0); }
		else { return null; }
		
	}
	
	//잔여 연차구하는 함수
	public Double Select_Residualholiday(String Ecode) {
		String sql = "select residualholiday from EH where Ecode=?";
		Double result = jt.queryForObject(sql, Double.class, Ecode);
		if(result == null) { return 0.0; }
		else { return result; }
	}
	
	//특정사원의 잔여연차수량 과 사용수량 수정하는 함수.
	public int Update_ResidualHoliday(EmployeeHolidayDTO dto) {
		String sql = "update EH set residualholiday=?, usenumOfholiday=? where Ecode=?";
		return jt.update(sql, dto.getResidualholiday(),dto.getUsenumOfholiday(), dto.getEcode());
	}
	
	//휴가레코드 테이블에 삽입하는 함수
	public int Insert_Holidayrecode(String Ecode, String Hcode, String useday, String starttime, String endtime, int settime, String Rday, String reason) {
		String sql = "insert into HoliRecord(Ecode,Holicode,HoliRuseday,HoliRstarttime,HoliRendtime,HoliRusetime,HoliRdays,HoliRreason) values(?,?,?,?,?,?,?,?)";
		return jt.update(sql,Ecode,Hcode,useday,String.format("%s:00:00", starttime),String.format("%s:00:00", endtime),settime,Rday,reason);
	}
	
	//특정사원의 특정기간의 휴가레코드 가져오기 
	public HolidayRecordDTO Select_HolidayRecord(String Ecode, String startday) {
		String sql = "select * from HoliRecord where Ecode=? and holiRuseday >= ? and ? <= holiRuseday ";
		
		RowMapper<HolidayRecordDTO> mapper = new RowMapper<HolidayRecordDTO>() {

			@Override
			public HolidayRecordDTO mapRow(ResultSet rs, int rowNum) throws SQLException {				
				HolidayRecordDTO dto = new HolidayRecordDTO();
				dto.setHoliRcode(rs.getInt("holiRcode"));
				dto.setEcode(rs.getString("Ecode"));
				dto.setHolicode(rs.getString("holicode"));
				dto.setHoliRuseday(rs.getString("holiRuseday"));
				dto.setHoliRstarttime(rs.getString("holiRstarttime"));
				dto.setHoliRendtime(rs.getString("holiRendtime"));
				dto.setHoliRusetime(rs.getInt("holiRusetime"));
				dto.setHoliRdays(rs.getInt("holiRdays"));
				dto.setHoliRreason(rs.getString("holiRreason"));
				dto.setHoliRapproval(rs.getString("holiRapproval"));
				return dto;
			}
			
		};
		
		List<HolidayRecordDTO> list = jt.query(sql, mapper, Ecode, startday, startday);
		
		if(list==null||list.size()==0) { return null; }
		else { return list.get(0); }
		
	} 
	
	//휴가코드가져오기
	public String Select_Hcode(String Ecode, String startday) {
		String sql = "select Hcode from HoliRecored where Ecode=? and holiRuseday >= ? and ? <= holiRuseday";	
		return jt.queryForObject(sql, String.class, Ecode, startday);
	}
	
	/*한 주 시작일과 종료일 사이에 휴가신청이력(결재대기, 결재승인)이 몇 개 존재하는지 확인하는 함수*/
	public int checkHoliday(String Ecode, String monday, String friday) {
		String sql = "select count(*) from  holiRecord where Ecode=? and holiRuseday between ? and ? and (holiRapproval in ('completed','waiting'))";
		return jt.queryForObject(sql, Integer.class, Ecode, monday, friday);
	}
	
	// 휴가레코드 다 가져오는 함수
	public ArrayList<HolidayRecordDTO> Select_AllHoliRecord() {
		String sql = "select * from HoliRecord";
		
		RowMapper<HolidayRecordDTO> mapper = new RowMapper<HolidayRecordDTO>() {

			@Override
			public HolidayRecordDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				HolidayRecordDTO dto = new HolidayRecordDTO();
				dto.setHoliRcode(rs.getInt("holiRcode"));
				dto.setEcode(rs.getString("Ecode"));
				dto.setHolicode(rs.getString("holicode"));
				dto.setHoliRuseday(rs.getString("holiRuseday"));
				dto.setHoliRstarttime(rs.getString("holiRstarttime"));
				dto.setHoliRendtime(rs.getString("holiRendtime"));
				dto.setHoliRusetime(rs.getInt("holiRusetime"));
				dto.setHoliRdays(rs.getInt("holiRdays"));
				dto.setHoliRreason(rs.getString("holiRreason"));
				dto.setHoliRapproval(rs.getString("holiRapproval"));
				return dto;
			}
			
		};
		
		ArrayList<HolidayRecordDTO> list = (ArrayList<HolidayRecordDTO>) jt.query(sql,mapper); 
		
		if(list!=null) { return list; }
		else { return new ArrayList<HolidayRecordDTO>(); }		
	}
		
	//사원번호 or 사원명을 동시 검색해서 일치하는 사원번호 받아오는 함수
	public ArrayList<HolidayRecordDTO> Select_HoliRecord(String EcodeName){
		String sql = "select * from HoliRecord where Ecode like '%"+EcodeName+"%' or Ecode in (select Ecode from Employee where Ename like '%"+EcodeName+"%')";
		RowMapper<HolidayRecordDTO> mapper = new RowMapper<HolidayRecordDTO>() {

			@Override
			public HolidayRecordDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				HolidayRecordDTO dto = new HolidayRecordDTO();
				dto.setHoliRcode(rs.getInt("holiRcode"));
				dto.setEcode(rs.getString("Ecode"));
				dto.setHolicode(rs.getString("holicode"));
				dto.setHoliRuseday(rs.getString("holiRuseday"));
				dto.setHoliRstarttime(rs.getString("holiRstarttime"));
				dto.setHoliRendtime(rs.getString("holiRendtime"));
				dto.setHoliRusetime(rs.getInt("holiRusetime"));
				dto.setHoliRdays(rs.getInt("holiRdays"));
				dto.setHoliRreason(rs.getString("holiRreason"));
				dto.setHoliRapproval(rs.getString("holiRapproval"));
				return dto;
			}
			
		};
		ArrayList<HolidayRecordDTO> list = (ArrayList<HolidayRecordDTO>) jt.query(sql, mapper);
		
		if(list==null) { return new ArrayList<HolidayRecordDTO>(); }
		else { return list; }
	}
	
}//class end

