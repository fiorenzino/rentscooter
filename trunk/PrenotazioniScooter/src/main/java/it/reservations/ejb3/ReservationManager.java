package it.reservations.ejb3;

import it.reservations.par.Contract;
import it.reservations.par.DaySummary;

import java.util.Date;
import java.util.Map;

public interface ReservationManager {

	public Map<String, Map<String, Boolean>> getReservationList(Date dal,
			Date al, String cilindrata);

	public Map<Date, DaySummary> getReservationData(Date init, Date end);

	public void addReservation(Contract contract);

	public void removeReservation(Contract contract);
}
