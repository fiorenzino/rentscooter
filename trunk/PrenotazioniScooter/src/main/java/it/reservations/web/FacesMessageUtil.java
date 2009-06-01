package it.reservations.web;

import java.io.Serializable;

import javax.annotation.Named;
import javax.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

@Named(value = "facesMessage")
@Dependent
public class FacesMessageUtil implements Serializable {
	
	public FacesMessageUtil() {

	}

	public void addMessage(Severity severity, String summary, String detail) {
		FacesMessage message = new FacesMessage(severity, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

}
