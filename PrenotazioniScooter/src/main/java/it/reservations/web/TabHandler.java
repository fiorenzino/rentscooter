package it.reservations.web;

import it.reservations.web.utils.Util;

import java.io.Serializable;

import javax.annotation.Named;
import javax.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;

import org.jboss.logging.Logger;

@SessionScoped
@Named
public class TabHandler implements Serializable {

	Logger log = Logger.getLogger(TabHandler.class.getName());
	
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
		log.info("VF UNO: " + getUno());
		log.info("VF DUE: " + getDue());
		log.info("VF TRE: " + getTre());
	}

	public void valueChanged(ValueChangeEvent event) {
		log.info("VC UNO: " + getUno());
		log.info("VC DUE: " + getDue());
		log.info("VC TRE: " + getTre());
		log.info("PAGE: " + Util.getCurrentPage());
		log.info("PATH: " + Util.getContextPath());

	}

	public String closeForm() {
		log.info("CF UNO: " + getUno());
		log.info("CF DUE: " + getDue());
		log.info("CF TRE: " + getTre());
		return "/tabForm.xhtml";
	}
}
