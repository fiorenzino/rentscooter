package it.reservations.web;

import it.reservations.ejb3.utils.JNDIUtils;
import it.reservations.par.Client;
import it.smartflower.ejb3.utils.ClassCreator;
import it.smartflower.par.RicercaI;
import it.smartflower.web.utils.JSFHandler;

import java.io.Serializable;

import javax.annotation.Named;
import javax.context.SessionScoped;

@SessionScoped
@Named
public class ClientiHandler extends JSFHandler implements Serializable {

	private Client client;

	public String addClient1() {
		this.client = new Client();
		return "/clienti/gestione-cliente.xhtml";
	}

	public String addClient2() {
		JNDIUtils.getClientiManager().persist(this.client);
		aggModel();
		return "/clienti/scheda-cliente.xhtml";
	}

	public String modClient1() {
		this.client = (Client) getModel().getRowData();
		this.editMode = true;
		return "/clienti/gestione-cliente.xhtml";
	}

	public String modClient2() {
		JNDIUtils.getClientiManager().update(this.client);
		aggModel();
		return "/clienti/scheda-cliente.xhtml";
	}

	public String delClient() {
		JNDIUtils.getClientiManager().update(this.client);
		aggModel();
		return "/clienti/scheda-cliente.xhtml";
	}

	public String detailClient() {
		this.client = (Client) getModel().getRowData();
		return "/clienti/scheda-cliente.xhtml";
	}

	public Client getClient() {
		if (client == null)
			this.client = new Client();
		return client;
	}

	public void setUser(Client client) {
		this.client = client;
	}

	@Override
	public void initRicerca() {
		try {
			this.ricerca = (RicercaI) ClassCreator
					.creaRicerca("it.reservations.par.RicercaClienti");
			System.out.println("ecco qui: " + this.ricerca.getQuery());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public ClientiHandler() {
		eJBManager = JNDIUtils.getClientiManager();
		rowsPerPage = 10;
		initRicerca();
	}

}
