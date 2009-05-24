package it.reservations.web;

import it.reservations.ejb3.utils.JNDIUtils;
import it.reservations.par.Contratto;
import it.reservations.par.Tariffa;
import it.smartflower.ejb3.utils.ClassCreator;
import it.smartflower.par.RicercaI;
import it.smartflower.web.utils.JSFHandler;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Named;
import javax.context.SessionScoped;

@SessionScoped
@Named
public class ContrattiHandler extends JSFHandler implements Serializable {

	private Contratto contratto;

	public String addContratto1() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		this.contratto = new Contratto();
		this.contratto.setDataInit(cal.getTime());
		cal.add(Calendar.DAY_OF_MONTH, 6);
		this.contratto.setDataEnd(cal.getTime());
		this.contratto.setSconto(Long.getLong("0"));
		this.contratto.setImportoFinale(Float.valueOf("0"));
		this.contratto.setImportoIniziale(Float.valueOf("0"));
		this.contratto.setExtra(Float.valueOf("0"));
		this.editMode = false;
		return "/contratti/gestione-contratto.xhtml";
	}

	public String calcolaSomma() {
		if (this.contratto.getDataEnd().after(this.contratto.getDataInit())) {
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(this.contratto.getDataInit());
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(this.contratto.getDataEnd());

			Long numDay = new Long(cal2.getTimeInMillis()
					- cal1.getTimeInMillis() / (24 * 60 * 60 * 1000));
			switch (numDay.intValue()) {
			case 1:

				break;
			case 2:

				break;
			case 3:

				break;
			case 4:

				break;
			case 5:

				break;
			case 6:

				break;
			case 7:

				break;
			case 30:

				break;

			default:
				break;
			}

			Tariffa tariffa = this.contratto.getScooter().getTariffa();
		}
		return "";
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
