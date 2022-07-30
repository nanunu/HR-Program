package model;

import lombok.Data;

@Data
public class PagingDTO {

	private int total_page;
	private int pageNum;
	private int startpage;
	private int endpage;
}
