package it.reservations.web;

import it.reservations.ejb3.ReservationManager;
import it.reservations.par.DaySummary;
import it.reservations.web.model.CalendarDataModelItemImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Named;
import javax.context.SessionScoped;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.richfaces.event.CurrentDateChangeEvent;
import org.richfaces.model.CalendarDataModel;
import org.richfaces.model.CalendarDataModelItem;

@SessionScoped
@Named
public class CalendarModel implements CalendarDataModel, Serializable {

	@EJB
	ReservationManager reservationManager;
	private CalendarDataModelItem[] items;
	private String currentDescription;
	private String currentShortDescription;
	private Date currentDate;
	private boolean currentDisabled;
	private Date dataInit;
	private Date dataEnd;

	public Date getDataInit() {
		if (dataInit == null)
			dataInit = new Date();
		return dataInit;
	}

	public void setDataInit(Date dataInit) {
		this.dataInit = dataInit;
	}

	public Date getDataEnd() {
		if (dataEnd == null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(getDataInit());
			cal.add(Calendar.DAY_OF_MONTH, 1);
			dataEnd = cal.getTime();
		}
		return dataEnd;
	}

	public void setDataEnd(Date dataEnd) {
		this.dataEnd = dataEnd;
	}

	public void controllaDate(ActionEvent event) {
		System.out.println("DATA SCELTA: " + currentDate);
		items = null;

	}

	public void reset() {
		System.out.println("RESET");
		items = null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.richfaces.model.CalendarDataModel#getData(java.util.Date[])
	 */
	public CalendarDataModelItem[] getData(Date[] dateArray) {
		if (dateArray == null) {
			return null;
		}
		if (items == null) {
			if (dateArray.length != 0) {
				// System.out.println("DATA INIT: " + dateArray[0]);
				// System.out.println("DATA END: "
				// + dateArray[dateArray.length - 1]);
				if (dateArray[0].before(getDataInit())
						|| dateArray[dateArray.length - 1].after(getDataEnd())) {
					ricaricaItems(dateArray[0], dateArray[dateArray.length - 1]);
				}
			}
			// for (int i = 0; i < dateArray.length; i++) {
			// System.out.println("ARRAY DATA: " + dateArray[i]);
			// }

		}
		return items;
	}

	private void ricaricaItems(Date init, Date end) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(init);
		// CARICO DATI DAL DB

		// CREO MAP CON DATA E PRENOTAZIONI

		// CREO LISTA CHE VERRA' VISUALIZZATA
		List<CalendarDataModelItem> lista = new ArrayList<CalendarDataModelItem>();

		Map<Date, DaySummary> mapp = reservationManager.getReservationData(
				init, end);
		for (Date data : mapp.keySet()) {
			System.out.println("DATA: " + data);
			DaySummary dayS = mapp.get(data);
			lista.add(createDataModelItem(data, "occupate:" + dayS.getNum(),
					dayS.getDescription(), dayS.getNum()));
		}

		items = new CalendarDataModelItem[lista.size()];
		items = lista.toArray(items);
		// items = new CalendarDataModelItem[dateArray.length];
		// for (int i = 0; i < dateArray.length; i++) {
		//
		// }
	}

	/**
	 * @param date
	 * @return CalendarDataModelItem for date
	 */
	protected CalendarDataModelItem createDataModelItem(Date date,
			String titolo, String testo, Long num) {
		CalendarDataModelItemImpl item = new CalendarDataModelItemImpl();
		Map<String, String> data = new HashMap<String, String>();
		data.put("shortDescription", titolo);
		data.put("description", testo);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		item.setDay(c.get(Calendar.DAY_OF_MONTH));
		item.setEnabled(true);
		if (num > 0)
			item.setStyleClass("occupato");
		else
			item.setStyleClass("libero");
		item.setData(data);
		return item;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.richfaces.model.CalendarDataModel#getToolTip(java.util.Date)
	 */
	public Object getToolTip(Date date) {

		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return items
	 */
	public CalendarDataModelItem[] getItems() {
		return items;
	}

	/**
	 * @param setter
	 *            for items
	 */
	public void setItems(CalendarDataModelItem[] items) {
		this.items = items;
	}

	/**
	 * @param valueChangeEvent
	 *            handling
	 */
	public void valueChanged(ValueChangeEvent event) {
		System.out.println(event.getNewValue() + "selected");
		setCurrentDate((Date) event.getNewValue());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getCurrentDate());
		setCurrentDescription((String) ((HashMap) items[calendar
				.get(Calendar.DAY_OF_MONTH) - 1].getData()).get("description"));
		setCurrentShortDescription((String) ((HashMap) items[calendar
				.get(Calendar.DAY_OF_MONTH) - 1].getData())
				.get("shortDescription"));

	}

	public void changeEvent(CurrentDateChangeEvent event) {
		System.out.println(event.getCurrentDateString() + "CHANGE");

	}

	/**
	 * Storing changes action
	 */
	public void storeDayDetails() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getCurrentDate());
		((HashMap) items[calendar.get(Calendar.DAY_OF_MONTH) - 1].getData())
				.put("shortDescription", getCurrentShortDescription());
		((HashMap) items[calendar.get(Calendar.DAY_OF_MONTH) - 1].getData())
				.put("description", getCurrentDescription());
	}

	/**
	 * @return currentDescription
	 */
	public String getCurrentDescription() {
		return currentDescription;
	}

	/**
	 * @param currentDescription
	 */
	public void setCurrentDescription(String currentDescription) {
		this.currentDescription = currentDescription;
	}

	/**
	 * @return currentDisabled
	 */
	public boolean isCurrentDisabled() {
		return currentDisabled;
	}

	/**
	 * @param currentDisabled
	 */
	public void setCurrentDisabled(boolean currentDisabled) {
		this.currentDisabled = currentDisabled;
	}

	/**
	 * @return currentShortDescription
	 */
	public String getCurrentShortDescription() {
		return currentShortDescription;
	}

	/**
	 * @param currentShortDescription
	 */
	public void setCurrentShortDescription(String currentShortDescription) {
		this.currentShortDescription = currentShortDescription;
	}

	/**
	 * @return currentDate
	 */
	public Date getCurrentDate() {
		if (currentDate == null)
			currentDate = new Date();
		return currentDate;
	}

	/**
	 * @param currentDate
	 */
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}
}
