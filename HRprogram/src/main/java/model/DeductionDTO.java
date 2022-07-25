package model;

import lombok.Data;

/*공제*/
@Data
public class DeductionDTO {
	private String deCode;
	private String deName;
	private double deRate;
	private int deAllowance;
}
