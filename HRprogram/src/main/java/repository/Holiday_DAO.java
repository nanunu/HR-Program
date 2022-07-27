package repository;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class Holiday_DAO {

	JdbcTemplate jt;
	
	public Holiday_DAO(DataSource ds) { this.jt = new JdbcTemplate(ds); }
	
	/*한 주 시작일과 종료일 사이에 휴가신청이력(결재대기, 결재승인)이 몇 개 존재하는지 확인하는 함수*/
	public int checkHoliday(String Ecode, String monday, String friday) {
		String sql = "select count(*) from  holiRecord where Ecode=? and holiRuseday between ? and ? and (holiRapproval in ('completed','waiting'))";
		return jt.queryForObject(sql, Integer.class, Ecode, monday, friday);
	}
}
