package model;

import lombok.Data;

@Data
public class FlextimeCMD {
	private String Dname;
	private String Pname;
	private String Ecode;
	private String Ename;
	private String startday;
	private String endday;
	private String[] freedaystart;
	private String[] freedayend;
	private String FTapproval;
}
