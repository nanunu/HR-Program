package repository;

import java.util.Map;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OverTime_DAO {

	JdbcTemplate jt;
	
	public OverTime_DAO(DataSource ds) { this.jt = new JdbcTemplate(ds); }
	
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

}
