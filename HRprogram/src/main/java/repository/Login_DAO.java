package repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import model.SessionDTO;

@Repository
public class Login_DAO {

	private JdbcTemplate jt;
	
	public Login_DAO(DataSource ds) { this.jt = new JdbcTemplate(ds); }
		
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
	
	/*직급코드로 직급명 찾기*/
	public String getPname(String position) {
		String sql = "select Pname from PositionT where position=?";
		return jt.queryForObject(sql, String.class, position);
	}
	
	//출근여부 확인하기
	public Integer Cmtime_checking(String Ecode) { //퇴근 시간이 null값인 오늘 날짜로 검색
		LocalDateTime datetime = LocalDateTime.now();
		
		String month;
		int mon = datetime.getMonth().getValue();		
		if(mon < 10) { month="0"+mon; }
		else { month = String.valueOf(mon); }
		
		String date = datetime.getYear()+"-"+month+"-"+datetime.getDayOfMonth();
		
		String sql = "select Ecode from Commute where CmDay=? and Ecode=? and CmGetoffTime is null";
		// null값을 검색하고 싶을때에는 is null // is not null 로 조건이 들어가야함
		
		RowMapper<String> mapper = new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("Ecode");
			}
			
		};
		
		List<String> list = jt.query(sql, mapper, date, Ecode);
		
		if(list!=null) { return list.size(); }
		else { return null; }		
		
	}
		
	public int Insert_CmTime(String Ecode, String type) {
		LocalDateTime datetime = LocalDateTime.now();
		
		String sql = "insert into Commute(Ecode,CmDay,CmAtTime) values(?,?,?)"; //기본값 출근처리용
		
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
	
	//휴가신청 승인완료시 미리 출석값 넣어주는 함수. 부작용 --> 해당날짜에 로그인이 되질않음.
	public int Insert_alreadyLogin(String Ecode, String CmDay, String CmAtTime, String CmGetoffTime) {
		String sql = "insert into Commute(Ecode,CmDay,CmAtTime,CmGetoffTime) values(?,?,?,?)";
		return jt.update(sql,Ecode,CmDay,CmAtTime,CmGetoffTime);
	}
		
	/* 비밀번호 찾기 : 이메일이 존재하는지 확인*/
	public int existEmail(String email) {
		return jt.queryForObject("select count(*) from Employee where email=?", Integer.class, email);
	}
	
	/* 비밀번호 찾기 : 임시 비밀번호 업데이트*/
	public void updateTemporaryPw(String email, String pw) {
		jt.update("update Employee set password=? where email=?", pw, email);
	}
	
	// 오늘 마지막으로 로그인한 pknum 가져오기 --> 후에 삭제예정
	public int Select_LastLogin(String Ecode, LocalDate today) {
		String sql ="Select Cmcode from Commute where Ecode=? and Cmday";
		return jt.queryForObject(sql,Integer.class,Ecode,today.toString());
	}
	
	// pknum 삭제하기 사원의 오늘날짜 마지막로그인 수동삭제...
	public int Delete_LastLogin(int pknum) {
		String sql = "Delete from Commute where Cmcode=?";
		return jt.update(sql,pknum);		
	}

	
}
