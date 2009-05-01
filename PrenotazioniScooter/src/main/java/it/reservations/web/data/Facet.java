package it.reservations.web.data;

import java.io.Serializable;

public class Facet implements Serializable {
	private String header;

	/**
	 * TODO Description goes here.
	 * 
	 * @param header
	 * @param footer
	 */
	public Facet(String header) {
		super();
		this.header = header;
	}

	/**
	 * @return the header
	 */
	public String getHeader() {
		return header;
	}

	/**
	 * @param header
	 *            the header to set
	 */
	public void setHeader(String header) {
		this.header = header;
	}

}
