package repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import model.FlextimeCMD;
import model.FlextimeDTO;

public class Flextime_DAO {
	
	private JdbcTemplate jt; 
	
	public Flextime_DAO(DataSource datasource) {
		this.jt = new JdbcTemplate(datasource);
	}
	
	public void checkFlexTime(FlextimeCMD command) {
		String sql = "select count(*) from FlexTime where Ecode=? and FTstartday=?";
		jt.queryForObject(sql, Integer.class, command.getEcode(), command.getStartday());
	}
	
	/*사원이 신청한 탄력근무제를 DB에 넣는 함수*/
	/*DB에 신청한 적이 있는지 없는지 확인하는 작업 거쳐야함*/
	public void insertFlexTimeDAO(FlextimeCMD command) {

		String sql = "insert into FlexTime(Ecode, FTstartday, Ftendday, MonStart, MonEnd, TueStart, TueEnd, WedStart, WedEnd, "
				+ "ThuStart, ThuEnd, FriStart, FriEnd, FTapproval) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		jt.update(sql, command.getEcode(), command.getStartday(), command.getEndday(),
				String.format("%s:00:00", command.getFreedaystart()[0]), 
				String.format("%s:00:00", command.getFreedayend()[0]), 
				String.format("%s:00:00", command.getFreedaystart()[1]), 
				String.format("%s:00:00", command.getFreedayend()[1]),
				String.format("%s:00:00", command.getFreedaystart()[2]), 
				String.format("%s:00:00", command.getFreedayend()[2]),
				String.format("%s:00:00", command.getFreedaystart()[3]), 
				String.format("%s:00:00", command.getFreedayend()[3]),
				String.format("%s:00:00", command.getFreedaystart()[4]), 
				String.format("%s:00:00", command.getFreedayend()[4]),
				command.getFTapproval());
	}
	
	/*탄력근무제를 진행하고 있는 사원들의 탄력근무데이터 들고오는 함수*/
	public List<FlextimeDTO> getAllFlextime(){
		
		String sql = "select * from FlexTime order by FTCode DESC";
		
		RowMapper<FlextimeDTO> mapper = new RowMapper<FlextimeDTO>() {

			@Override
			public FlextimeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				FlextimeDTO dto = new FlextimeDTO();
				dto.setFTCode(rs.getInt("FTCode"));
				dto.setEcode(rs.getString("Ecode"));
				dto.setFTstartday(rs.getDate("FTstartday"));
				dto.setFTendday(rs.getDate("FTendday"));
				dto.setMonStart(rs.getTime("MonStart"));
				dto.setMonend(rs.getTime("MonEnd"));
				dto.setTueStart(rs.getTime("TueStart"));
				dto.setTueend(rs.getTime("TueEnd"));
				dto.setWedStart(rs.getTime("WedStart"));
				dto.setWedend(rs.getTime("WedEnd"));
				dto.setThuStart(rs.getTime("ThuStart"));
				dto.setThuend(rs.getTime("ThuEnd"));
				dto.setFriStart(rs.getTime("FriStart"));
				dto.setFriend(rs.getTime("FriEnd"));
				dto.setFTapproval(rs.getString("FTapproval"));
				dto.setAdmissionDate(rs.getDate("AdmissionDate"));
				return dto;
			}
		};
		List<FlextimeDTO> fList = jt.query(sql, mapper); 
		return fList;
	}
	
	/*탄력근무제를 적용하고 있는 사원들의 사원코드를 중복없이 들고오는 함수*/
	public List<String> getEcodeList(){
		String sql = "select distinct Ecode from FlexTime";
		return jt.queryForList(sql, String.class);
	}
	
	/*
	//입력받은 날짜가 탄력근무 하는 주간인지 검사하는 함수 return value 0/1
	public Integer Select_WeekDay(String startday, String ecode) {
		String sql = "select count(Ecode) from FlexTime where FTstartday <= ? and FTendday >= ? and Ecode =?";
		return jt.queryForObject(sql, Integer.class, startday,startday, ecode);
	}
	*/
	
	//입력받은 날짜가 탄력근무 하는 주간인지 검사하는 함수
	public FlextimeDTO Select_WeekDay(String startday, String ecode) {
		String sql = "select * from FlexTime where FTstartday <= ? and FTendday >= ? and Ecode =?";
		RowMapper<FlextimeDTO> mapper = new RowMapper<FlextimeDTO>() {

			@Override
			public FlextimeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				FlextimeDTO dto = new FlextimeDTO();
				dto.setFTCode(rs.getInt("FTCode"));
				dto.setFTapproval(rs.getString("FTapproval"));
				dto.setFTstartday(rs.getDate("FTstartday"));
				dto.setFTendday(rs.getDate("FTendday"));
				dto.setAdmissionDate(rs.getDate("AdmissionDate"));
				dto.setMonStart(rs.getTime("MonStart"));
				dto.setMonend(rs.getTime("Monend"));
				dto.setTueStart(rs.getTime("TueStart"));
				dto.setTueend(rs.getTime("Tueend"));
				dto.setWedStart(rs.getTime("WedStart"));
				dto.setWedend(rs.getTime("Wedend"));
				dto.setThuStart(rs.getTime("ThuStart"));
				dto.setThuend(rs.getTime("Thuend"));
				dto.setFriStart(rs.getTime("FriStart"));
				dto.setFriend(rs.getTime("Friend"));
				return dto;
			}
			
		};
		
		List<FlextimeDTO> list = jt.query(sql,mapper, startday,startday, ecode);
		
		if(list!=null) { return list.get(0); }
		else { return null; }

	}	 
	
	
	
	
	
	
}//class end
