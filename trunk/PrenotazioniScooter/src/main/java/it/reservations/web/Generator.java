package it.reservations.web;

import it.reservations.web.data.Columns;
import it.reservations.web.data.Facet;

import java.io.Serializable;
import java.util.ArrayList;

import javax.context.ApplicationScoped;
import javax.inject.Current;
import javax.inject.Produces;

@ApplicationScoped
public class Generator implements Serializable {

	@Current
	ReservationHandler reservationHandler;

	private ArrayList<Facet> columns;

	@Produces
	@Columns
	public ArrayList<Facet> getColumns() {
		reservationHandler.initColumns();
		return reservationHandler.getColumns();
	}

}
