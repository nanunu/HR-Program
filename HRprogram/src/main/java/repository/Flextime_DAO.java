package repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import model.EmployeeDTO;
import model.FlextimeCMD;
import model.FlextimeDTO;

public class Flextime_DAO {
	
	private JdbcTemplate jt; 
	
	public Flextime_DAO(DataSource datasource) {
		this.jt = new JdbcTemplate(datasource);
	}
	
	/*공통 RowMapper*/
	RowMapper<FlextimeDTO> mapper1 = new RowMapper<FlextimeDTO>() {

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
	
	RowMapper<EmployeeDTO> mapper2 = new RowMapper<EmployeeDTO>() {

		@Override
		public EmployeeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			EmployeeDTO dto = new EmployeeDTO();
			dto.setEcode(rs.getString("Ecode"));
			dto.setEname(rs.getString("Ename"));
			dto.setDcode(rs.getString("Dcode"));
			dto.setPosition(rs.getString("position"));
			return dto;
		}
	};
	
	
	/*해당 시작일에 탄력근무제 신청이력(결재대기, 결재완료)이 있는지 확인*/
	public int checkFlexTime(FlextimeCMD command) {
		String sql = "select count(*) from Flextime where Ecode=? and FTstartday=? and (FTapproval in ('completed', 'waiting'))";
		return jt.queryForObject(sql, Integer.class, command.getEcode(), command.getStartday());
	}
	
	/*사원이 신청한 탄력근무제를 DB에 넣는 함수*/
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
		List<FlextimeDTO> fList = jt.query(sql, mapper1); 
		return fList;
	}
	
	/* Employee테이블에서 사원정보를 가져오는 함수
	 * (FlexTime테이블에서 탄력근무를 적용하고 있는 사원들을 중복없이 검색하여 사원코드 출력,
	 * 출력된 모든 사원코드로 정보를 가져옴)
	 */
	public List<EmployeeDTO> getEList_FlexTime(){
		String sql = "select * from Employee where Ecode = any(select distinct Ecode from FlexTime)";
		List<EmployeeDTO> eList = jt.query(sql, mapper2);
		return eList;
	}
	
	/*탄력근무기록코드(FTCode)로 해당 근무기록코드 가져오는 함수*/
	public FlextimeDTO getFlextimeDTO(int FTCode) {
		String sql = "select * from FlexTime where FTCode=?"; 
		return jt.queryForObject(sql, mapper1, FTCode);
	}
	
	/*사원의 기본인사정보를 가져오는 함수*/
	public EmployeeDTO getEmployeeDTO(String Ecode) {
		String sql = "select * from Employee where Ecode=?";
		return jt.queryForObject(sql, mapper2, Ecode);
	}
	
}
