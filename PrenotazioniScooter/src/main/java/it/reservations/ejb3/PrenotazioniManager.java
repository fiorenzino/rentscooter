package it.reservations.ejb3;

import it.reservations.par.DaySummary;
import it.reservations.par.MiniPre;
import it.reservations.par.Prenotazione;
import it.smartflower.ejb3.EJBManager;

import java.util.Date;
import java.util.Map;

public interface PrenotazioniManager extends EJBManager {

	public Map<Long, Map<String, MiniPre>> getReservationList(Date dal,
			Date al, String cilindrata);

	public Map<String, DaySummary> getReservationData(Long scooterFilter,
			Date init, Date end);

	public void delete(Prenotazione prenotazione);
}
