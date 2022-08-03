package repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import model.OverTimeDTO;

@Repository
public class OverTime_DAO {

	JdbcTemplate jt;
	
	public OverTime_DAO(DataSource ds) { this.jt = new JdbcTemplate(ds); }
	
	
	public ArrayList<OverTimeDTO> Select_OverTime(String EcodeName){
		
		String sql = "select * from HoliRecord where Ecode like '%"+EcodeName+"%' or Ecode in (select Ecode from Employee where Ename like '%"+EcodeName+"%')";
		
		RowMapper<OverTimeDTO> mapper = new RowMapper<OverTimeDTO>() {

			@Override
			public OverTimeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				OverTimeDTO dto = new OverTimeDTO();
				dto.setOtCode(rs.getInt(1));
				dto.setEcode(rs.getString(2));
				dto.setOtDay(rs.getString(3));
				dto.setOtStartTime(rs.getString(4));
				dto.setOtEndTime(rs.getString(5));
				dto.setTimeSum(rs.getInt(6));
				dto.setOtReason(rs.getString(7));
				dto.setOtApproval(rs.getString(8));
				return dto;
			}
			
		};
		
		ArrayList<OverTimeDTO> list = (ArrayList<OverTimeDTO>) jt.query(sql, mapper);
		
		return list;
	}
	
	// 사원코드와 초과근무시작일을 기준으로 검색.
	public Integer Select_OverTime(String Ecode, String OTday) { 
		
		String sql = "select count(Ecode) from OverTime where Ecode=? and OTDay=?";
		
		return jt.queryForObject(sql, Integer.class, Ecode, OTday);
	}
	
	public Integer Select_OverTime(String Ecode, String startday, String endday) {
		
		String sql = "select count(Ecode) from OverTime where Ecode=? and OTDay >= ? and OTDay <= ?";
		
		return jt.queryForObject(sql, Integer.class, Ecode, startday, endday);
		
	}
	
	//사원코드로 총 초과근무시간 리턴받음. 주단위로 가져옴.
	public Integer Select_OverTimeSum(String Ecode, String week_monday, String week_friday) {
		
		String sql = "select Sum(TimeSum) from OverTime where Ecode=? and otApproval='completed' and OTday between ? and ? ";
		
		Integer result = jt.queryForObject(sql,Integer.class,Ecode,week_monday,week_friday);
		
		if(result==null) { return 0; }
		else { return result; }
		
	}
		
	public Integer Insert_OverTime(Map<String,String> map, int time_dif) {
		
		String sql = "insert into OverTime(Ecode,OTDay,OTStartTime,OTEndTime,TimeSum,OTReason) values(?,?,?,?,?,?)";
		
		return jt.update(sql,map.get("Ecode"),map.get("startday"),String.format("%s:00:00",map.get("starttime")),String.format("%s:00:00",map.get("endtime")),time_dif,map.get("Reason"));
		
	}	

	//startday, endday사이의 특정사원의 초과근무를 삭제하는 함수
	public Integer Delete_OverTime(String Ecode, String startday, String endday) {
		String sql = "delete from OverTime where Ecode=? and OTDay between ? and  ?";
		return jt.update(sql, Ecode, startday, endday);
	}

	//초과근무한 모든 직원 가져오기
	public ArrayList<OverTimeDTO> Select_AllOverTime(){
		String sql = "select * from OverTime";
		RowMapper<OverTimeDTO> mapper = new RowMapper<OverTimeDTO>() {

			@Override
			public OverTimeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				OverTimeDTO dto = new OverTimeDTO();
				dto.setOtCode(rs.getInt(1));
				dto.setEcode(rs.getString(2));
				dto.setOtDay(rs.getString(3));
				dto.setOtStartTime(rs.getString(4));
				dto.setOtEndTime(rs.getString(5));
				dto.setTimeSum(rs.getInt(6));
				dto.setOtReason(rs.getString(7));
				dto.setOtApproval(rs.getString(8));
				return dto;
			}
			
		};
		
		ArrayList<OverTimeDTO> list = (ArrayList<OverTimeDTO>) jt.query(sql, mapper);
		
		return list;
	}
	
	
}
