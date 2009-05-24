package it.reservations.web;

import it.reservations.ejb3.utils.JNDIUtils;
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
public class TariffeHandler extends JSFHandler implements Serializable {

	private Tariffa tariffa;

	private SelectItem[] tariffeItems;

	public SelectItem[] getTariffeList() {
		if (tariffeItems == null) {
			List<Tariffa> tariffe = JNDIUtils.getTariffeManager()
					.getAllTariffe();
			SelectItem[] items = new SelectItem[tariffe.size()];
			int i = 0;
			for (Tariffa tariffa : tariffe) {
				items[i++] = new SelectItem(tariffa.getId(), tariffa.getNome());
			}
			tariffeItems = items;
		}
		return tariffeItems;
	}

	public String addTariffa1() {
		this.editMode=false;
		this.tariffa = new Tariffa();
		return "/tariffe/gestione-tariffa.xhtml";
	}

	public String addTariffa2() {
		JNDIUtils.getTariffeManager().persist(this.tariffa);
		tariffeItems = null;
		aggModel();
		return "/tariffe/scheda-tariffa.xhtml";
	}

	public String modTariffa1() {
		this.tariffa = (Tariffa) getModel().getRowData();
		this.editMode = true;
		return "/tariffe/gestione-tariffa.xhtml";
	}

	public String modTariffa2() {
		JNDIUtils.getTariffeManager().update(this.tariffa);
		tariffeItems = null;
		aggModel();
		return "/tariffe/scheda-tariffa.xhtml";
	}

	public String delTariffa() {
		JNDIUtils.getTariffeManager().update(this.tariffa);
		tariffeItems = null;
		aggModel();
		return "/tariffe/scheda-tariffa.xhtml";
	}

	public String detailTariffa() {
		this.tariffa = (Tariffa) getModel().getRowData();
		return "/tariffe/scheda-tariffa.xhtml";
	}

	@Override
	public void initRicerca() {
		try {
			this.ricerca = (RicercaI) ClassCreator
					.creaRicerca("it.reservations.par.RicercaTariffe");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public TariffeHandler() {
		eJBManager = JNDIUtils.getTariffeManager();
		rowsPerPage = 10;
		initRicerca();
	}

	public Tariffa getTariffa() {
		return tariffa;
	}

	public void setTariffa(Tariffa tariffa) {
		this.tariffa = tariffa;
	}

}
