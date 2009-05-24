package it.reservations.web;

import it.reservations.ejb3.utils.JNDIUtils;
import it.reservations.par.Scooter;
import it.reservations.par.Tariffa;
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
public class ScootersHandler extends JSFHandler implements Serializable {

	private Scooter scooter;

	private SelectItem[] scooterItems;

	public SelectItem[] getScooterList() {
		if (scooterItems == null) {
			List<Scooter> scooters = JNDIUtils.getScooterManager()
					.getAllScooter();
			SelectItem[] items = new SelectItem[scooters.size() + 1];
			items[0] = new SelectItem(0, "tutti");
			int i = 1;
			for (Scooter scooter : scooters) {
				items[i++] = new SelectItem(scooter.getId(), scooter.getName());
			}
			scooterItems = items;
		}
		return scooterItems;
	}

	public String addScooter1() {
		this.editMode = false;
		this.scooter = new Scooter();
		return "/scooters/gestione-scooter.xhtml";
	}

	public String addScooter2() {
		Tariffa tariffa = JNDIUtils.getTariffeManager().find(
				this.scooter.getTariffa().getId());
		this.scooter.setTariffa(tariffa);
		JNDIUtils.getScooterManager().persist(this.scooter);
		aggModel();
		this.scooterItems=null;
		return "/scooters/scheda-scooter.xhtml";
	}

	public String modScooter1() {
		this.scooter = (Scooter) getModel().getRowData();
		this.editMode = true;
		return "/scooters/gestione-scooter.xhtml";
	}

	public String modScooter2() {
		Tariffa tariffa = JNDIUtils.getTariffeManager().find(
				this.scooter.getTariffa().getId());
		this.scooter.setTariffa(tariffa);
		JNDIUtils.getScooterManager().update(this.scooter);
		aggModel();
		this.scooterItems=null;
		return "/scooters/scheda-scooter.xhtml";
	}

	public String delScooter() {
		JNDIUtils.getScooterManager().update(this.scooter);
		aggModel();
		this.scooterItems=null;
		return "/scooters/scheda-scooter.xhtml";
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

	public ScootersHandler() {
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
