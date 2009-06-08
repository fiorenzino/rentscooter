package it.reservations.web;

import it.reservations.ejb3.utils.JNDIUtils;
import it.reservations.par.Cliente;
import it.reservations.par.Contratto;
import it.reservations.par.Prenotazione;
import it.reservations.par.Scooter;
import it.reservations.par.Tariffa;
import it.reservations.web.utils.TariffeUtil;
import it.reservations.web.utils.TimeUtil;
import it.reservations.web.utils.Util;
import it.smartflower.ejb3.utils.ClassCreator;
import it.smartflower.par.RicercaI;
import it.smartflower.web.utils.JSFHandler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Named;
import javax.context.SessionScoped;
import javax.inject.Current;

@SessionScoped
@Named
public class ContrattiHandler extends JSFHandler implements Serializable {

	private Contratto contratto;

	private Long numDays;

	private Long numDaysExtra;

	private boolean withCaparra = false;

	@Current
	OrganizerHandler organizerHandler;

	public String addContratto1() {
		this.contratto = new Contratto();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		this.contratto.setDataInit(cal.getTime());
		cal.add(Calendar.DAY_OF_MONTH, 5);
		this.contratto.setDataEnd(cal.getTime());
		this.contratto.setDataRiconsegna(cal.getTime());
		this.contratto.setAperto(true);
		this.editMode = false;
		return "/contratti/gestione-contratto.xhtml";
	}

	public void calcolaExtra() {
		if (this.contratto.getDataEnd().compareTo(
				this.contratto.getDataRiconsegna()) != 0) {
			Scooter sco = JNDIUtils.getScooterManager().find(
					this.contratto.getScooter().getId());
			System.out.println("SCCOTER: " + sco.getMarcaModello() + " ."
					+ sco.getId());
			Tariffa tariffa = sco.getTariffa();
			System.out.println("TARIFFA :" + tariffa.getId());
			Float extra = TariffeUtil.calcolaExtra(TimeUtil.getDiffDays(
					this.contratto.getDataEnd(),
					this.contratto.getDataRiconsegna()).intValue(), tariffa);
			System.out.println("IMPORTO EXTRA: " + extra);
			this.contratto.setExtra(extra);
			Float kmExtra = TariffeUtil.calcolaKmExtra(this.contratto
					.getKmExtra(), tariffa);
			this.contratto.setImportokmExtra(kmExtra);
		} else {
			System.out.println("DATA END = DATA RICONSEGNA!!");
		}
	}

	public void calcolaSomma() {
		if (this.contratto.getScooter().getId() > 0
				&& this.contratto.getCliente().getId() > 0) {
			System.out
					.println("SCO ID: " + this.contratto.getScooter().getId());
			System.out
					.println("CLI ID: " + this.contratto.getCliente().getId());
			if (this.contratto.getDataEnd().after(this.contratto.getDataInit())) {
				Scooter sco = JNDIUtils.getScooterManager().find(
						this.contratto.getScooter().getId());
				System.out.println("SCCOTER: " + sco.getMarcaModello() + " ."
						+ sco.getId());
				Tariffa tariffa = sco.getTariffa();
				System.out.println("TARIFFA :" + tariffa.getId());
				Float importoIniziale = TariffeUtil.calcolaTariffa(TimeUtil
						.getDiffDays(this.contratto.getDataInit(),
								this.contratto.getDataEnd()).intValue(),
						tariffa);
				System.out.println("IMPORTO INIZIALE: " + importoIniziale);
				this.contratto.setImportoIniziale(importoIniziale);
			}
		} else {
			System.out.println("NON HAI SELEZIONATO CLIENTE E SCOOTER");
		}
	}

	public String chiudiContratto1() {

		return "/contratti/chiusura-contratto.xhtml";
	}

	public String chiudiContratto2() {
		Scooter sco = JNDIUtils.getScooterManager().find(
				this.contratto.getScooter().getId());
		Cliente cli = JNDIUtils.getClientiManager().find(
				this.contratto.getCliente().getId());
		this.contratto.setScooter(sco);
		this.contratto.setCliente(cli);
		this.contratto.setAperto(false);
		this.contratto.setDataRiconsegna(this.contratto.getDataEnd());
		// DEVO CREARE I GG SINGOLI DI PRENOTAZIONE
		// VEDI PrenotazioniManagerBean.getReservationList
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.contratto.getDataInit());
		List<Prenotazione> prenotazioni = new ArrayList<Prenotazione>();
		while (cal.getTime().compareTo(this.contratto.getDataEnd()) <= 0) {
			System.out.println("DATA: " + cal.getTime());
			Prenotazione pre = new Prenotazione();
			pre.setContratto(this.contratto);
			pre.setSingleDay(cal.getTime());
			pre.setSingleDayName(cal.get(Calendar.DAY_OF_MONTH) + "-"
					+ (cal.get(Calendar.MONTH) + 1) + "-"
					+ cal.get(Calendar.YEAR));
			prenotazioni.add(pre);
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		this.contratto.setPrenotazioni(prenotazioni);
		JNDIUtils.getContrattiManager().update(this.contratto);
		aggModel();
		Util.valorizzaCliente(this.contratto.getCliente());
		organizerHandler.reset();
		return "/contratti/scheda-contratto.xhtml";
	}

	public String addContratto2() {
		Scooter sco = JNDIUtils.getScooterManager().find(
				this.contratto.getScooter().getId());
		Cliente cli = JNDIUtils.getClientiManager().find(
				this.contratto.getCliente().getId());
		this.contratto.setScooter(sco);
		this.contratto.setCliente(cli);
		this.contratto.setDataRiconsegna(this.contratto.getDataEnd());
		// DEVO CREARE I GG SINGOLI DI PRENOTAZIONE
		// VEDI PrenotazioniManagerBean.getReservationList
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.contratto.getDataInit());
		List<Prenotazione> prenotazioni = new ArrayList<Prenotazione>();
		while (cal.getTime().compareTo(this.contratto.getDataEnd()) <= 0) {
			System.out.println("DATA: " + cal.getTime());
			Prenotazione pre = new Prenotazione();
			pre.setContratto(this.contratto);
			pre.setSingleDay(cal.getTime());
			pre.setSingleDayName(cal.get(Calendar.DAY_OF_MONTH) + "-"
					+ (cal.get(Calendar.MONTH) + 1) + "-"
					+ cal.get(Calendar.YEAR));
			prenotazioni.add(pre);
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		this.contratto.setPrenotazioni(prenotazioni);
		JNDIUtils.getContrattiManager().persist(this.contratto);
		aggModel();
		Util.valorizzaCliente(this.contratto.getCliente());
		organizerHandler.reset();
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
		Util.valorizzaCliente(this.contratto.getCliente());
		return "/contratti/scheda-contratto.xhtml";
	}

	public String delContratto() {
		JNDIUtils.getContrattiManager().update(this.contratto);
		aggModel();
		return "/contratti/scheda-contratto.xhtml";
	}

	public String detailContratto() {
		System.out.println("ABS: " + Util.getAbsolutePath());
		this.contratto = (Contratto) getModel().getRowData();
		Util.valorizzaCliente(this.contratto.getCliente());
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
		if (this.contratto == null)
			this.contratto = new Contratto();
		return contratto;
	}

	public void setContratto(Contratto contratto) {
		this.contratto = contratto;
	}

	public int getNumDays() {
		return TimeUtil.getDiffDays(this.contratto.getDataInit(),
				this.contratto.getDataEnd()).intValue();
	}

	public int getNumDaysExtra() {
		return TimeUtil.getDiffDays(this.contratto.getDataEnd(),
				this.contratto.getDataRiconsegna()).intValue();
	}

	public boolean isWithCaparra() {
		return withCaparra;
	}

	public void setWithCaparra(boolean withCaparra) {
		this.withCaparra = withCaparra;
	}

}
