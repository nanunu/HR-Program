package repository;

import java.util.Map;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class OverTime_DAO {

	JdbcTemplate jt;
	
	public OverTime_DAO(DataSource ds) { this.jt = new JdbcTemplate(ds); }
	
	// 사원코드와 초과근무시작일을 기준으로 검색.
	public Integer Select_OverTime(String Ecode, String OTday) { 
		
		String sql = "select count(Ecode) from OverTime where Ecode=? and OTDay=?";
		
		return jt.queryForObject(sql, Integer.class, Ecode, OTday);
	}
	
	//사원코드로 총 초과근무시간 리턴받음. 주단위로 가져옴.
	public Integer Select_OverTimeSum(String Ecode) {
		
		String sql = "select TimeSum form OverTime where Ecode=? and OTday beteewn ? and ?";
		
		return jt.queryForObject(sql,Integer.class,Ecode);
		
	}
	
	
	public Integer Insert_OverTime(Map<String,String> map) {
		
		String sql = "insert into OverTime(Ecode,OTDay,OTStartTime,OTEndTime,TimeSum,OTReason) valuse(?,?,?,?,?,?)";
		
		return jt.update(sql,map.get(sql),map.get(sql),map.get(sql),map.get(sql),map.get(sql));
		
	}
	
	
}
