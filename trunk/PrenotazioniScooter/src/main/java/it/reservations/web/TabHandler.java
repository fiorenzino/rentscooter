package it.reservations.web;

import it.reservations.web.utils.Util;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Named;
import javax.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;

@SessionScoped
@Named
public class TabHandler implements Serializable {
	private String uno;
	private String due;
	private String tre;

	public String getUno() {
		return uno;
	}

	public void setUno(String uno) {
		this.uno = uno;
	}

	public String getDue() {
		return due;
	}

	public void setDue(String due) {
		this.due = due;
	}

	public String getTre() {
		return tre;
	}

	public void setTre(String tre) {
		this.tre = tre;
	}

	public void validateForm() {
		System.out.println("VF UNO: " + getUno());
		System.out.println("VF DUE: " + getDue());
		System.out.println("VF TRE: " + getTre());
	}

	public void valueChanged(ValueChangeEvent event) {
		System.out.println("VC UNO: " + getUno());
		System.out.println("VC DUE: " + getDue());
		System.out.println("VC TRE: " + getTre());
		System.out.println("PAGE: " + Util.getCurrentPage());
		System.out.println("PATH: " + Util.getContextPath());

	}

	public String closeForm() {
		System.out.println("CF UNO: " + getUno());
		System.out.println("CF DUE: " + getDue());
		System.out.println("CF TRE: " + getTre());
		return "/tabForm.xhtml";
	}
}
