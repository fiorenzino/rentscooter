package it.reservations.web;

import it.reservations.ejb3.utils.JNDIUtils;
import it.reservations.par.Scooter;
import it.smartflower.ejb3.utils.ClassCreator;
import it.smartflower.par.RicercaI;
import it.smartflower.web.utils.JSFHandler;

import java.io.Serializable;

import javax.annotation.Named;
import javax.context.SessionScoped;

@SessionScoped
@Named
public class TariffeHandler extends JSFHandler implements Serializable {

	private Scooter scooter;

	public String addScooter1() {
		this.scooter = new Scooter();
		return "/scooters/gestione-scooter.xhtml";
	}

	public String addScooter2() {
		JNDIUtils.getScooterManager().persist(this.scooter);
		aggModel();
		return "/scooters/scheda-scooter.xhtml";
	}

	public String modScooter1() {
		this.scooter = (Scooter) getModel().getRowData();
		this.editMode = true;
		return "/scooters/gestione-scooter.xhtml";
	}

	public String modScooter2() {
		JNDIUtils.getScooterManager().update(this.scooter);
		aggModel();
		return "/scooters/scheda-Scooter.xhtml";
	}

	public String delScooter() {
		JNDIUtils.getScooterManager().update(this.scooter);
		aggModel();
		return "/scooters/scheda-Scootere.xhtml";
	}

	public String detailScooter() {
		this.scooter = (Scooter) getModel().getRowData();
		return "/scooters/scheda-scooter.xhtml";
	}

	@Override
	public void initRicerca() {
		try {
			this.ricerca = (RicercaI) ClassCreator
					.creaRicerca("it.reservations.par.RicercaScooter");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public TariffeHandler() {
		eJBManager = JNDIUtils.getScooterManager();
		rowsPerPage = 10;
		initRicerca();
	}

	public Scooter getScooter() {
		return scooter;
	}

	public void setScooter(Scooter scooter) {
		this.scooter = scooter;
	}

}
