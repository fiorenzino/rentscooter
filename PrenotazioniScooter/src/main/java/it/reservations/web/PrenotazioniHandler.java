package it.reservations.web;

import it.reservations.ejb3.ReservationManager;
import it.reservations.par.Reservation;
import it.reservations.web.data.Columns;
import it.reservations.web.data.Facet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Named;
import javax.context.SessionScoped;
import javax.ejb.EJB;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;

@SessionScoped
@Named
public class PrenotazioniHandler implements Serializable {

	@EJB
	ReservationManager reservationManager;
	private ArrayList<Reservation[]> lista;
	@Columns
	private ArrayList<Facet> columns;

	private Date dal;
	private Date al;
	private int begin;
	private int end;

	List<Reservation> reservationsList;

	public PrenotazioniHandler() {
	}

	public ArrayList<Facet> getColumns() {
		return columns;
	}

	public void setColumns(ArrayList<Facet> columns) {
		this.columns = columns;
	}

	public void initColumns() {
		columns = new ArrayList<Facet>();
		Date d = getDal();
		Date a = getAl();
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		Facet fac = new Facet("SCOOTER");
		columns.add(fac);
		while (cal.getTime().compareTo(a) <= 0) {
			// System.out.println("DATA: " + cal.getTime());
			fac = new Facet(cal.get(Calendar.DAY_OF_MONTH) + "-"
					+ (cal.get(Calendar.MONTH) + 1) + "-"
					+ cal.get(Calendar.YEAR));
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
			Calendar cal = Calendar.getInstance();
			cal.setTime(getDal());
			cal.add(Calendar.DAY_OF_MONTH, 7);
			al = cal.getTime();
		}
		return al;
	}

	public void setAl(Date al) {
		this.al = al;
	}

	public ArrayList<Reservation[]> getModel() {
		if (getDal() != null && getAl() != null) {

			for (int i = 0; i < 10; i++) {
				System.out.println("i:" + i);
				Reservation[] res = new Reservation[getColumns().size()];
				for (int j = 0; j < getColumns().size(); j++) {
					if (j == 0) {
						res[j] = new Reservation();
						res[j].setScooterName("SCOCOTER" + i);
					} else {
						System.out.println("j:" + j + "-"
								+ getColumns().get(j).getHeader());
						res[j] = new Reservation();
						res[j].setScooterName("SCOCOTER"
								+ getColumns().get(j).getHeader());
					}
				}
				getLista().add(res);
			}
		} else {
			System.out.println("DATE NULL");
		}
		return lista;
	}

	public ArrayList<Reservation[]> getLista() {
		if (lista == null)
			lista = new ArrayList<Reservation[]>();
		return lista;
	}

	public void setLista(ArrayList<Reservation[]> lista) {
		this.lista = lista;
	}

	public void resetLista() {
		initColumns();
		lista = new ArrayList<Reservation[]>();
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

	public void addReservation(Reservation reservation) {
		getReservationsList().add(reservation);
	}

	public List<Reservation> getReservationsList() {
		if (reservationsList == null) {
			reservationsList = new ArrayList<Reservation>();
			Reservation res = new Reservation();
			reservationsList.add(res);

		}
		return reservationsList;
	}

	public void setReservationsList(List<Reservation> reservationsList) {
		this.reservationsList = reservationsList;
	}

}
