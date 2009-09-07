package it.reservations.web;

import it.reservations.par.Prenotazione;
import it.reservations.web.data.Columns;
import it.reservations.web.data.Facet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Named;
import javax.context.SessionScoped;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;

import org.jboss.logging.Logger;

@SessionScoped
@Named
public class PrenotazioniHandler implements Serializable {

	Logger log = Logger.getLogger(PrenotazioniHandler.class.getName());

	private ArrayList<Prenotazione[]> lista;

	@Columns
	private ArrayList<Facet> columns;
	private int begin;
	private int end;

	private Date dal;
	private Date al;
	private String cilindrata;

	List<Prenotazione> reservationsList;

	public ArrayList<Facet> getColumns() {
		if (columns == null)
			initColumns();
		return columns;
	}

	public void setColumns(ArrayList<Facet> columns) {
		this.columns = columns;
	}

	public void initColumns() {
		columns = new ArrayList<Facet>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(getDal());
		Facet fac = new Facet("SCOOTER", cal.getTime());
		columns.add(fac);
		while (cal.getTime().compareTo(getAl()) <= 0) {
			// log.info("DATA: " + cal.getTime());
			fac = new Facet(cal.get(Calendar.DAY_OF_MONTH) + "-"
					+ (cal.get(Calendar.MONTH) + 1) + "-"
					+ cal.get(Calendar.YEAR), cal.getTime());
			columns.add(fac);
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
	}

	public Date getDal() {
		if (dal == null) {
			dal = new Date();
		}
		return dal;
	}

	public void setDal(Date dal) {
		this.dal = dal;
	}

	public Date getAl() {
		if (al == null) {
			Calendar calA = Calendar.getInstance();
			calA.setTime(getDal());
			calA.add(Calendar.DAY_OF_MONTH, 6);
			al = calA.getTime();

		}
		return al;
	}

	public void setAl(Date al) {
		this.al = al;
	}

	// riga[colonna][]
	// riga[scooter1][giornoDAL] - riga[scooter1][giornoAL]
	// riga[scooter2][giornoDAL] - riga[scooter2][giornoAL]
	public ArrayList<Prenotazione[]> getModel() {
		if (getDal() != null && getAl() != null) {

			for (int i = 0; i < 10; i++) {
				log.info("i:" + i);
				Prenotazione[] res = new Prenotazione[getColumns().size()];
				for (int j = 0; j < getColumns().size(); j++) {
					if (j == 0) {
						res[j] = new Prenotazione();
					} else {
						log.info("j:" + j + "-"
								+ getColumns().get(j).getHeader());
						res[j] = new Prenotazione();

					}
				}
				getLista().add(res);
			}
		} else {
			log.info("DATE NULL");
		}
		return lista;
	}

	public ArrayList<Prenotazione[]> getLista() {
		if (lista == null)
			lista = new ArrayList<Prenotazione[]>();
		return lista;
	}

	public void setLista(ArrayList<Prenotazione[]> lista) {
		this.lista = lista;
	}

	public void resetLista() {
		initColumns();
		lista = new ArrayList<Prenotazione[]>();
	}

	public int getBegin() {
		begin = 1;
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		end = getColumns().size();
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public DataModel getReservations() {
		// SE NULLO LO RICREO
		ArrayDataModel data = new ArrayDataModel();
		data.setWrappedData(getReservationsList());
		return data;
	}

	public void addReservation(Prenotazione reservation) {
		getReservationsList().add(reservation);
	}

	public List<Prenotazione> getReservationsList() {
		if (reservationsList == null) {
			reservationsList = new ArrayList<Prenotazione>();
			Prenotazione res = new Prenotazione();
			reservationsList.add(res);

		}
		return reservationsList;
	}

	public void setReservationsList(List<Prenotazione> reservationsList) {
		this.reservationsList = reservationsList;
	}

	public String getCilindrata() {
		return cilindrata;
	}

	public void setCilindrata(String cilindrata) {
		this.cilindrata = cilindrata;
	}

}
