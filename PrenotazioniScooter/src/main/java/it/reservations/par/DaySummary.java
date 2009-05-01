package it.reservations.par;

import java.io.Serializable;

public class DaySummary implements Serializable {
	private Long num;
	private StringBuffer description;

	public DaySummary(Long num, String description) {
		this.num = num;
		this.description = new StringBuffer();
		this.description.append(description);
	}

	public Long getNum() {
		if (num == null)
			num = new Long(0);
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public void inc() {
		this.num = getNum() + 1;
	}

	public String getDescription() {
		return description.toString();
	}

	public void addDescription(String description) {
		this.description.append(description + "\n");
	}

}
