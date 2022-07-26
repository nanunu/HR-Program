package model;

import lombok.Data;

@Data
public class FlextimeDTO {
	private int FTCode;
	private String Dname;
	private String position;
	private String Ecode;
	private String Ename;
	private String startday;
	private String endday;
	private String[] freedaystart;
	private String[] freedayend;
	private String FTapproval;
	private String AdmissionDate;
}
