package repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import model.EmployeeHolidayDTO;

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
				dto.setResidualholiday(rs.getDouble("residualholiday"));
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
	
	
}//class end
