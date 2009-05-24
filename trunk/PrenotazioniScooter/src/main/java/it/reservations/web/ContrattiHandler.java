package it.reservations.web;

import it.reservations.ejb3.utils.JNDIUtils;
import it.reservations.par.Contratto;
import it.smartflower.ejb3.utils.ClassCreator;
import it.smartflower.par.RicercaI;
import it.smartflower.web.utils.JSFHandler;

import java.io.Serializable;

import javax.annotation.Named;
import javax.context.SessionScoped;

@SessionScoped
@Named
public class ContrattiHandler extends JSFHandler implements Serializable {

	private Contratto contratto;

	public String addContratto1() {
		this.contratto = new Contratto();
		this.editMode=false;
		return "/contratti/gestione-contratto.xhtml";
	}

	public String addContratto2() {
		JNDIUtils.getContrattiManager().persist(this.contratto);
		aggModel();
		return "/contratti/scheda-contratto.xhtml";
	}

	public String modContratto1() {
		this.contratto = (Contratto) getModel().getRowData();
		this.editMode = true;
		return "/contratti/gestione-contratto.xhtml";
	}

	public String modContratto2() {
		JNDIUtils.getContrattiManager().update(this.contratto);
		aggModel();
		return "/contratti/scheda-contratto.xhtml";
	}

	public String delContratto() {
		JNDIUtils.getContrattiManager().update(this.contratto);
		aggModel();
		return "/contratti/scheda-contratto.xhtml";
	}

	public String detailContratto() {
		this.contratto = (Contratto) getModel().getRowData();
		return "/contratti/scheda-contratto.xhtml";
	}

	@Override
	public void initRicerca() {
		try {
			this.ricerca = (RicercaI) ClassCreator
					.creaRicerca("it.reservations.par.RicercaContratti");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ContrattiHandler() {
		eJBManager = JNDIUtils.getContrattiManager();
		rowsPerPage = 10;
		initRicerca();
	}

	public Contratto getContratto() {
		return contratto;
	}

	public void setContratto(Contratto contratto) {
		this.contratto = contratto;
	}

}
