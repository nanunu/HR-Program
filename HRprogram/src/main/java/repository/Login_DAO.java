package repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import model.SessionDTO;

public class Login_DAO {
	
	public Login_DAO(DataSource ds) { this.jt = new JdbcTemplate(ds); }
	
	private JdbcTemplate jt;
	
	public SessionDTO login(String Ecode, String password) {// 사원코드와 비밀번호로 조회
		String sql = "select Ecode,Ename,Dcode,position from Employee where Ecode=? and password=?";
		
		RowMapper<SessionDTO> mapper = new RowMapper<SessionDTO>() {

			@Override
			public SessionDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				SessionDTO dto = new SessionDTO();
				dto.setEcode(rs.getString("Ecode"));
				dto.setEname(rs.getString("Ename"));
				dto.setDcode(rs.getString("Dcode"));
				dto.setPosition(rs.getString("position"));
				return dto;
			}
			
		};
		
		List<SessionDTO> list = jt.query(sql,mapper, Ecode, password);
		
		if(list.size()>0) { return list.get(0); }
		else { return null; }
		 
	}
	
	public String getDname(String Dcode) {// 부서코드를 이용해 부서명 받아오는 함수
		
		String sql = "select Dname from Department where Dcode=?";
		
		return jt.queryForObject(sql, String.class, Dcode);
		
	}
	
	public Integer Cmtime_checking(String Ecode) { //퇴근 시간이 null값인 오늘 날짜로 검색
		LocalDateTime datetime = LocalDateTime.now();
		
		String month;
		int mon = datetime.getMonth().getValue();		
		if(mon < 10) { month="0"+mon; }
		else { month = String.valueOf(mon); }
		
		String date = datetime.getYear()+"-"+month+"-"+datetime.getDayOfMonth();
		
		String sql = "select count(Ecode) from Commute where CmDay=? and Ecode=? and CmGetoffTime=null";
		
		return jt.queryForObject(sql, Integer.class, date, Ecode);		
	}
	
	
	public int CmTime(String Ecode, String type) {
		LocalDateTime datetime = LocalDateTime.now();
		
		String sql = "insert into Commute(Ecode,CmDay,CmAtTime) value(?,?,?)"; //기본값 출근처리용
		
		String month, minute;
		int mon = datetime.getMonth().getValue();
		int min = datetime.getMinute();
		
		if(mon < 10) { month="0"+mon; }
		else { month = String.valueOf(mon); }
		
		if(min < 10) { minute="0"+mon; }
		else { minute = String.valueOf(min); }		
		
		String date = datetime.getYear()+"-"+month+"-"+datetime.getDayOfMonth();//DB 데이터타입형식에 맞게 변경
		String time = datetime.getHour()+":"+minute+":"+datetime.getSecond();//DB 데이터타입형식에 맞게 변경
				
		if(type.equals("CmGetoffTime")) { // 퇴근처리요청.
			sql = "update Commute set CmGetoffTime=? where Ecode=?";
			
			return jt.update(sql,time,Ecode); // 퇴근시간 삽입.
		}
		else { return jt.update(sql,Ecode,date,time); } // 아니라면 출근처리.
		
	}

	
}
