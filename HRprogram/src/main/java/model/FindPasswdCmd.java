package model;

import lombok.Data;

@Data
public class FindPasswdCmd {
	private String email;
	private String password;
}
