package it.reservations.web;

import java.io.Serializable;

import it.reservations.par.Client;
import javax.annotation.Named;
import javax.context.SessionScoped;

@SessionScoped
@Named
public class ClientiHandler implements Serializable{

	private Client client;

	public String addClient1() {
		this.client = new Client();
		return "/clienti/aggiungi-cliente.xhtml";
	}

	public String addClient2() {
		return "";
	}

	public String modClient1() {
		return "";
	}

	public String modClient2() {
		return "";
	}

	public String detailClient() {
		return "";
	}

	public Client getClient() {
		if (client == null)
			this.client = new Client();
		return client;
	}

	public void setUser(Client client) {
		this.client = client;
	}

}
