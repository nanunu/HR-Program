package model;

import lombok.Data;

/*사원휴가*/
@Data
public class EmployeeHolidayDTO {
	private String ecode;
	private double numOfmyholiday; //부여휴가수
	private double usenumOfholiday; //사용휴가수
	private double residualholiday; //잔여휴가수
}
