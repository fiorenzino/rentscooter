package it.reservations.web;

import it.reservations.ejb3.ScooterManager;
import it.reservations.ejb3.utils.JNDIUtils;
import it.reservations.par.Scooter;
import it.smartflower.ejb3.utils.ClassCreator;
import it.smartflower.par.RicercaI;
import it.smartflower.web.utils.JSFHandler;

import java.io.Serializable;

import javax.annotation.Named;
import javax.context.SessionScoped;
import javax.ejb.EJB;

@SessionScoped
@Named
public class ScootersHandler extends JSFHandler implements Serializable {

	Scooter scooter;

	@Override
	public void initRicerca() {
		try {
			this.ricerca = (RicercaI) ClassCreator
					.creaRicerca("it.reservations.par.RicercaScooter");
			System.out.println("ecco qui: " + this.ricerca.getSelect());
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
