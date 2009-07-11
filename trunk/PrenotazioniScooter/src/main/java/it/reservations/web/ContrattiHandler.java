package it.reservations.web;

import it.reservations.ejb3.utils.JNDIUtils;
import it.reservations.ejb3.utils.TimeUtil;
import it.reservations.par.Cliente;
import it.reservations.par.Contratto;
import it.reservations.par.MiniPre;
import it.reservations.par.Prenotazione;
import it.reservations.par.Scooter;
import it.reservations.par.Tariffa;
import it.reservations.web.utils.TariffeUtil;
import it.reservations.web.utils.Util;
import it.smartflower.ejb3.utils.ClassCreator;
import it.smartflower.par.RicercaI;
import it.smartflower.web.utils.JSFHandler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Named;
import javax.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Current;

@SessionScoped
@Named
public class ContrattiHandler extends JSFHandler implements Serializable {

	private Contratto contratto;

	private Long numDays;

	private Long numDaysExtra;

	private boolean withCaparra = false;

	private List<MiniPre[]> searchModel;
	private List<String> colonne;
	private String cil;
	private int begin;
	private int end;

	@Current
	OrganizerHandler organizerHandler;

	public List<MiniPre[]> getSearchModel() {
		Map<Long, Map<String, MiniPre>> mappa = JNDIUtils
				.getPrenotazioniManager().getReservationList(
						getContratto().getDataInit(),
						getContratto().getDataEnd(), getCil());
		searchModel = new ArrayList<MiniPre[]>();
		for (Long nome : mappa.keySet()) {
			System.out.println("ID SCO: " + nome);
			MiniPre[] sco = new MiniPre[getColonne().size() + 1];

			Map<String, MiniPre> occ = mappa.get(nome);
			int l = 1;
			for (String key : occ.keySet()) {
				System.out.println("DATA: " + key);
				if (l == 1) {
					sco[0] = occ.get(key);
				}
				sco[l++] = occ.get(key);
			}
			System.out.println("--------------");
			searchModel.add(sco);
		}
		return searchModel;
	}

	public void setSearchModel(List<MiniPre[]> searchModel) {
		this.searchModel = searchModel;
	}

	public List<String> getColonne() {
		if (colonne == null)
			aggColonne();
		return colonne;
	}

	public void setColonne(List<String> colonne) {
		this.colonne = colonne;
	}

	public void aggColonne() {
		colonne = new LinkedList<String>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(getContratto().getDataInit());
		colonne.add("scooter");
		while (cal.getTime().compareTo(getContratto().getDataEnd()) <= 0) {
			// System.out.println("COLDATA: " + cal.get(Calendar.DAY_OF_MONTH)
			// + "-" + cal.get(Calendar.MONTH) + "-"
			// + cal.get(Calendar.YEAR));
			colonne.add(cal.get(Calendar.DAY_OF_MONTH) + "-"
					+ (cal.get(Calendar.MONTH) + 1) + "-"
					+ cal.get(Calendar.YEAR));
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
	}

	public String addContratto1() {
		this.contratto = new Contratto();
		// Calendar cal = Calendar.getInstance();
		// cal.setTime(new Date());
		// this.contratto.setDataInit(cal.getTime());
		// cal.add(Calendar.DAY_OF_MONTH, 5);
		// this.contratto.setDataEnd(cal.getTime());
		// this.contratto.setDataRiconsegna(cal.getTime());
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
		if (this.contratto.getScooter().getId() < 1) {
			System.out.println("NON HAI SELEZIONATO LO SCOOTER ");
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage message = new FacesMessage();
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			message.setSummary("Scooter non valido!");
			context.addMessage("form:scooter", message);
		} else if (this.contratto.getScooter().getId() > 0
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
			} else {
				System.out.println("DATE NON VALIDE ");
				FacesContext context = FacesContext.getCurrentInstance();
				FacesMessage message = new FacesMessage();
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				message.setSummary("Data Finale non valida");
				context.addMessage("cercaScooter:dataEnd", message);
			}
		} else {
			System.out.println("NON HAI SELEZIONATO CLIENTE ");
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage message = new FacesMessage();
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			message.setSummary("Cliente non valido!");
			context.addMessage("form:cliente", message);
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
		try {
			sco.setKmFatti(this.contratto.getKmFinali());
			JNDIUtils.getScooterManager().update(sco);
		} catch (Exception e) {
			// TODO: handle exception
		}

		this.contratto.setScooter(sco);
		this.contratto.setCliente(cli);
		this.contratto.setAperto(false);
		// this.contratto.setDataRiconsegna(this.contratto.getDataEnd());
		// DEVO CREARE I GG SINGOLI DI PRENOTAZIONE
		// VEDI PrenotazioniManagerBean.getReservationList
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.contratto.getDataInit());
		this.contratto.setPrenotazioni(null);

		List<Prenotazione> prenotazioni = new ArrayList<Prenotazione>();
		while (cal.getTime().compareTo(this.contratto.getDataRiconsegna()) <= 0) {
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
		JNDIUtils.getContrattiManager().updateSpecial(this.contratto);
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
		this.contratto.setDataStipula(new Date());
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
		JNDIUtils.getContrattiManager().delete(this.contratto);
		aggModel();
		return "/contratti/contratti.xhtml";
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

	public String getCil() {
		return cil;
	}

	public void setCil(String cil) {
		this.cil = cil;
	}

	public int getBegin() {
		return 0;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		return getColonne().size() + 1;
	}

	public void setEnd(int end) {
		this.end = end;
	}

}
