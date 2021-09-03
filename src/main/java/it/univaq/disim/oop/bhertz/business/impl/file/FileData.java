package it.univaq.disim.oop.bhertz.business.impl.file;

import java.util.List;

public class FileData {

	private long count;
	private List<String[]> rows;
	
	public long getCount() {
		return count;
	}
	
	public void setCount(long count) {
		this.count = count;
	}
	
	public List<String[]> getRows() {
		return rows;
	}
	
	public void setRows(List<String[]> rows) {
		this.rows = rows;
	}
}
