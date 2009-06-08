package it.reservations.ejb3;

import it.reservations.par.Contratto;
import it.reservations.par.DaySummary;
import it.smartflower.ejb3.EJBManager;

import java.util.Date;
import java.util.Map;

public interface PrenotazioniManager extends EJBManager {

	public Map<String, Map<String, Boolean>> getReservationList(Date dal,
			Date al, String cilindrata);

	public Map<String, DaySummary> getReservationData(Long scooterFilter,
			Date init, Date end);

	public void addReservation(Contratto contract);

	public void removeReservation(Contratto contract);
}
