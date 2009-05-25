package it.reservations.web;

import it.reservations.ejb3.utils.JNDIUtils;
import it.reservations.par.Cliente;
import it.smartflower.ejb3.utils.ClassCreator;
import it.smartflower.par.RicercaI;
import it.smartflower.web.utils.JSFHandler;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Named;
import javax.context.SessionScoped;
import javax.faces.model.SelectItem;

@SessionScoped
@Named
public class ClientiHandler extends JSFHandler implements Serializable {

	private Cliente cliente;

	private SelectItem[] clientiItems;

	public SelectItem[] getClientiItems() {
		if (clientiItems == null) {
			List<Cliente> clienti = JNDIUtils.getClientiManager()
					.getAllClienti();
			SelectItem[] items = new SelectItem[clienti.size() + 1];
			items[0] = new SelectItem(0, "scegli");
			int i = 1;
			for (Cliente cliente : clienti) {
				items[i++] = new SelectItem(cliente.getId(), cliente
						.getCognome());
			}
			clientiItems = items;
		}
		return clientiItems;
	}

	public String addCliente1() {
		this.cliente = new Cliente();
		return "/clienti/gestione-cliente.xhtml";
	}

	public String addCliente2() {
		JNDIUtils.getClientiManager().persist(this.cliente);
		aggModel();
		clientiItems = null;
		return "/clienti/scheda-cliente.xhtml";
	}

	public String modCliente1() {
		this.cliente = (Cliente) getModel().getRowData();
		this.editMode = true;
		return "/clienti/gestione-cliente.xhtml";
	}

	public String modCliente2() {
		JNDIUtils.getClientiManager().update(this.cliente);
		aggModel();
		clientiItems = null;
		return "/clienti/scheda-cliente.xhtml";
	}

	public String delCliente() {
		JNDIUtils.getClientiManager().update(this.cliente);
		aggModel();
		return "/clienti/scheda-cliente.xhtml";
	}

	public String detailCliente() {
		this.cliente = (Cliente) getModel().getRowData();
		return "/clienti/scheda-cliente.xhtml";
	}

	public Cliente getCliente() {
		if (cliente == null)
			this.cliente = new Cliente();
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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
