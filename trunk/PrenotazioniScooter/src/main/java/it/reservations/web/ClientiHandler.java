package it.reservations.web;

import java.io.Serializable;

import it.reservations.par.User;

import javax.annotation.Named;
import javax.context.SessionScoped;

@SessionScoped
@Named
public class ClientiHandler implements Serializable{

	private User user;

	public String addUser1() {
		this.user = new User();
		return "/clienti/aggiungi-cliente.xhtml";
	}

	public String addUser2() {
		return "";
	}

	public String modUser1() {
		return "";
	}

	public String modUser2() {
		return "";
	}

	public String detailUser() {
		return "";
	}

	public User getUser() {
		if (user == null)
			this.user = new User();
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
