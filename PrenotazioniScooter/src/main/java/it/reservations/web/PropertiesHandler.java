package it.reservations.web;

import it.reservations.ejb3.Configurator;
import it.reservations.web.utils.SimpleComparator;
import it.reservations.par.Comune;
import it.reservations.par.Nazione;
import it.reservations.par.Provincia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

import javax.annotation.Named;
import javax.context.SessionScoped;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.inject.Current;

import org.jboss.logging.Logger;

@SessionScoped
@Named
public class PropertiesHandler implements Serializable {

	Logger log = Logger.getLogger(getClass().getName());

	private TreeMap<Long, Comune> comuni;

	private TreeMap<Long, Provincia> province;

	private TreeMap<Long, Nazione> nazioni;

	TreeMap<Long, ArrayList<Long>> mappaProvinciaComuni = new TreeMap<Long, ArrayList<Long>>();

	private SelectItem[] nazioniItems;

	private SelectItem[] provinceItems;

	private SelectItem[] comuniItems;

	private SelectItem[] capItems;

	private SelectItem[] rowItems = new SelectItem[] {};

	@EJB
	Configurator configurator;

	@Current
	UserHandler userHandler;

	public SelectItem[] getRowItems() {
		// 10 - 20 -50 -100
		if (rowItems.length == 0) {
			rowItems = new SelectItem[3];
			rowItems[0] = new SelectItem(10);
			rowItems[1] = new SelectItem(20);
			rowItems[2] = new SelectItem(50);
		}
		return rowItems;
	}

	public SelectItem[] getCapItems() {
		Long provincia = new Long(0);
		Long comune = new Long(0);
		// LI PRENDO DALL'UTENTE IN SESSIONE
		comune = userHandler.getUser().getCity();
		provincia = userHandler.getUser().getProvincia();
		capItems = new SelectItem[] { new SelectItem(" -- ") };
		if ((comune == 0) && (provincia != 0)) {
			comune = getMappaProvinciaComuni().get(provincia).get(0);
			// log.info("comune derivato dalla provincia: - " + type
			// + " -" + comune);
		}
		if (comune == null || comune == 0) {
			return capItems;
		} else {
			int i = 0;

			ArrayList<String> capSet = (ArrayList<String>) getComuni().get(
					comune).getCap();
			if (capSet == null || capSet.size() == 0) {
				return capItems;
			}
			SelectItem[] capSel = new SelectItem[capSet.size()];
			for (String capSc : capSet) {
				capSel[i++] = new SelectItem(capSc);
			}
			capItems = capSel;
		}
		return capItems;
	}

	private SelectItem[] getComuniItems() {

		Long provincia = new Long(0);
		// log.info("getComuniItems2 - " + provincia);
		provincia = userHandler.getUser().getProvincia();
		
		comuniItems = new SelectItem[] { new SelectItem(new Long(0), " -- ") };
		if (provincia == null || provincia == 0) {
			return comuniItems;
		} else {
			int i = 0;
			ArrayList<Long> comuniSet = getMappaProvinciaComuni()
					.get(provincia);
			if (comuniSet == null || comuniSet.size() == 0) {
				return comuniItems;
			}
			SelectItem[] comuniSel = new SelectItem[comuniSet.size()];
			for (Long comuneSc : comuniSet) {
				comuniSel[i++] = new SelectItem(comuneSc, getComuni().get(
						comuneSc).getComune());
			}
			comuniItems = comuniSel;
		}
		Arrays.sort(comuniItems, new SimpleComparator());
		return comuniItems;

	}

	private void creaMappe() {
		ArrayList<Comune> comuni = new ArrayList<Comune>(getComuni().values());
		for (Comune comune : comuni) {

			if (!mappaProvinciaComuni.containsKey(comune.getId_pro())) {
				mappaProvinciaComuni.put(comune.getId_pro(),
						new ArrayList<Long>());
			}
			if (!mappaProvinciaComuni.get(comune.getId_pro()).contains(
					comune.getId())) {
				mappaProvinciaComuni.get(comune.getId_pro())
						.add(comune.getId());
			}
		}
	}

	public TreeMap<Long, ArrayList<Long>> getMappaProvinciaComuni() {
		if (mappaProvinciaComuni.size() == 0) {
			creaMappe();
		}
		return mappaProvinciaComuni;
	}

	public SelectItem[] getNazioniList() {
		if (nazioniItems == null) {
			SelectItem[] items = new SelectItem[getNazioni().size()];
			int i = 0;
			for (Long key : getNazioni().keySet()) {
				Nazione naz = (Nazione) getNazioni().get(key);
				items[i++] = new SelectItem(naz.getId(), naz.getNazione());
			}
			nazioniItems = items;
		}
		return nazioniItems;
	}

	public SelectItem[] getProvinceList() {
		if (provinceItems == null) {
			SelectItem[] items = new SelectItem[getProvince().size()];
			int i = 0;
			for (Long key : getProvince().keySet()) {
				Provincia prov = (Provincia) getProvince().get(key);
				items[i++] = new SelectItem(prov.getId(), prov.getProvincia());
			}
			provinceItems = items;
		}

		return provinceItems;
	}

	public TreeMap<Long, Comune> getComuni() {
		if (comuni == null || comuni.size() == 0)
			comuni = configurator.getComuni();
		return comuni;
	}

	public TreeMap<Long, Provincia> getProvince() {
		if (province == null || province.size() == 0)
			province = configurator.getProvince();
		return province;
	}

	public TreeMap<Long, Nazione> getNazioni() {
		if (nazioni == null || nazioni.size() == 0)
			nazioni = configurator.getNazioni();
		return nazioni;

	}

}