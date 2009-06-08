package it.reservations.ejb3;

import it.reservations.par.Contratto;
import it.reservations.par.DaySummary;
import it.reservations.par.Prenotazione;
import it.reservations.par.Scooter;
import it.smartflower.ejb3.EJBManagerBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local(PrenotazioniManager.class)
public class PrenotazioniManagerBean extends EJBManagerBean implements
		PrenotazioniManager {

	@PersistenceContext(unitName = "TestManager")
	EntityManager em;

	public Map<String, Map<String, Boolean>> getReservationList(Date dal,
			Date al, String cilindrata) {
		// SELEZIONO SCOOTER CHE HANNO CILINDRATA SCELTA
		List<Scooter> lista = null;
		if (cilindrata != null) {
			lista = (List<Scooter>) em.createNamedQuery(
					"GET_SCOOTER_BY_CILINDRATA").setParameter("CILINDRATA",
					cilindrata).getResultList();
		} else {
			lista = (List<Scooter>) em.createNamedQuery("GET_ALL_SCOOTER")
					.getResultList();
		}
		Map<String, Map<String, Boolean>> prenotazioniTot = new TreeMap<String, Map<String, Boolean>>();
		for (Scooter scooter : lista) {
			Map<String, Boolean> resMap = new TreeMap<String, Boolean>();
			Calendar cal = Calendar.getInstance();
			cal.setTime(dal);
			while (cal.getTime().compareTo(al) <= 0) {
				System.out.println("DATA: " + cal.getTime());
				resMap.put(cal.get(Calendar.DAY_OF_MONTH) + "-"
						+ (cal.get(Calendar.MONTH) + 1) + "-"
						+ cal.get(Calendar.YEAR), false);
				cal.add(Calendar.DAY_OF_MONTH, 1);
			}
			prenotazioniTot.put(scooter.getMarcaModello(), resMap);
		}
		// SELEZIONI LE PRENOTAZIONI NEL PERIODO
		List<Prenotazione> prenotazioni = (List<Prenotazione>) em
				.createNamedQuery("GET_RESERVATIONS_BY_DATA_AND_CILINDRATA")
				.setParameter("DAL", dal).setParameter("AL", al).setParameter(
						"CILINDRATA", cilindrata).getResultList();
		for (Prenotazione reservation : prenotazioni) {
			if (prenotazioniTot.containsKey(reservation.getContratto()
					.getScooter().getMarcaModello())) {
				Map<String, Boolean> listaPre = prenotazioniTot.get(reservation
						.getContratto().getScooter().getMarcaModello());
				listaPre.put(reservation.getSingleDayName(), true);
			}
		}
		return prenotazioniTot;
	}

	public Map<String, DaySummary> getReservationData(Long scooterFilter,
			Date dal, Date al) {

		Map<String, DaySummary> resMap = new TreeMap<String, DaySummary>();
		try {

			Calendar cal = Calendar.getInstance();
			cal.setTime(dal);
			TimeZone tz = TimeZone.getTimeZone("Europe/Rome");
			tz.setDefault(TimeZone.getTimeZone("GMT+2"));
			cal.setTimeZone(tz);
			while (cal.getTime().compareTo(al) <= 0) {
				System.out.println("DATA: " + cal.getTime());
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				resMap.put(cal.get(Calendar.YEAR) + ""
						+ cal.get(Calendar.MONTH) + ""
						+ cal.get(Calendar.DAY_OF_MONTH), new DaySummary(
						new Long(0), "", cal.getTime()));
				cal.add(Calendar.DAY_OF_MONTH, 1);
			}
			System.out.println("DAL: " + dal);
			System.out.println("AL: " + al);
			System.out.println("FILTER: " + scooterFilter);

			List<Prenotazione> prenotazioni = new ArrayList<Prenotazione>();
			if (scooterFilter != null && scooterFilter > 0) {
				System.out
						.println("QUERY GET_RESERVATIONS_BY_DATA_AND_IDSCOOTER");
				prenotazioni = (List<Prenotazione>) em.createNamedQuery(
						"GET_RESERVATIONS_BY_DATA_AND_IDSCOOTER").setParameter(
						"DAL", dal).setParameter("AL", al).setParameter(
						"IDSCOOTER", scooterFilter).getResultList();
			} else {
				System.out.println("QUERY GET_RESERVATIONS_BY_DATA");
				prenotazioni = (List<Prenotazione>) em.createNamedQuery(
						"GET_RESERVATIONS_BY_DATA").setParameter("DAL", dal)
						.setParameter("AL", al).getResultList();
			}
			System.out.println("NUM RES: " + prenotazioni.size());
			for (Prenotazione reservation : prenotazioni) {
				cal.setTime(reservation.getSingleDay());
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				System.out.println("DAY: " + cal.getTime());
				if (resMap.containsKey(cal.get(Calendar.YEAR) + ""
						+ cal.get(Calendar.MONTH) + ""
						+ cal.get(Calendar.DAY_OF_MONTH))) {
					DaySummary day = resMap.get(cal.get(Calendar.YEAR) + ""
							+ cal.get(Calendar.MONTH) + ""
							+ cal.get(Calendar.DAY_OF_MONTH));
					day.inc();
					day.addDescription(reservation.getContratto().getScooter()
							.getMarcaModello());
					resMap.put(cal.get(Calendar.YEAR) + ""
							+ cal.get(Calendar.MONTH) + ""
							+ cal.get(Calendar.DAY_OF_MONTH), day);
				} else {
					DaySummary day = new DaySummary(new Long(1), reservation
							.getContratto().getScooter().getMarcaModello(), cal
							.getTime());
					resMap.put(cal.get(Calendar.YEAR) + ""
							+ cal.get(Calendar.MONTH) + ""
							+ cal.get(Calendar.DAY_OF_MONTH), day);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resMap;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addReservation(Contratto contract) {
		Date dal = contract.getDataInit();
		Date al = contract.getDataEnd();
		Calendar cal = Calendar.getInstance();
		cal.setTime(dal);
		for (Prenotazione reservation : contract.getPrenotazioni()) {
			em.persist(reservation);
		}
		// while (cal.getTime().compareTo(al) <= 0) {
		// System.out.println("DATA: " + cal.getTime());
		// cal.set(Calendar.HOUR_OF_DAY, 0);
		// cal.set(Calendar.MINUTE, 0);
		// Reservation res = new Reservation();
		// res.setSingleDay(cal.getTime());
		// res.setUser(contract.getUser());
		// res.setScooter(contract.getScooter());
		// em.persist(res);
		// cal.add(Calendar.DAY_OF_MONTH, 1);
		// }
		em.persist(contract);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void removeReservation(Contratto contract) {
		List<Prenotazione> prenotazioni = (List<Prenotazione>) em
				.createNamedQuery("GET_RESERVATIONS_BY_DATA").setParameter(
						"DAL", contract.getDataInit()).setParameter("AL",
						contract.getDataEnd()).getResultList();
		for (Prenotazione reservation : prenotazioni) {
			em.remove(reservation);
		}
		em.remove(contract);
	}

	@Override
	public EntityManager getEm() {
		// TODO Auto-generated method stub
		return em;
	}
}
