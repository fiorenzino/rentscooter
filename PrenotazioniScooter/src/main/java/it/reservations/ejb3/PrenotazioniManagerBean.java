package it.reservations.ejb3;

import it.reservations.par.Contratto;
import it.reservations.par.DaySummary;
import it.reservations.par.Prenotazione;
import it.reservations.par.Scooter;
import it.smartflower.ejb3.EJBManagerBean;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
		List<Scooter> lista = (List<Scooter>) em.createNamedQuery(
				"GET_SCOTEER_BY_CILINDRATA").setParameter("CILINDRATA",
				cilindrata).getResultList();
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
			prenotazioniTot.put(scooter.getName(), resMap);
		}
		// SELEZIONI LE PRENOTAZIONI NEL PERIODO
		List<Prenotazione> prenotazioni = (List<Prenotazione>) em
				.createNamedQuery("GET_RESERVATIONS_BY_DATA_AND_CILINDRATA")
				.setParameter("DAL", dal).setParameter("AL", al).setParameter(
						"CILINDRATA", cilindrata).getResultList();
		for (Prenotazione reservation : prenotazioni) {
			if (prenotazioniTot.containsKey(reservation.getContratto()
					.getScooter().getName())) {
				Map<String, Boolean> listaPre = prenotazioniTot.get(reservation
						.getContratto().getScooter().getName());
				listaPre.put(reservation.getSingleDayName(), true);
			}
		}
		return prenotazioniTot;
	}

	public Map<Date, DaySummary> getReservationData(Date dal, Date al) {
		Map<Date, DaySummary> resMap = new TreeMap<Date, DaySummary>();
		try {

			Calendar cal = Calendar.getInstance();
			cal.setTime(dal);
			while (cal.getTime().compareTo(al) <= 0) {
				System.out.println("DATA: " + cal.getTime());
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				resMap.put(cal.getTime(), new DaySummary(new Long(0), ""));
				cal.add(Calendar.DAY_OF_MONTH, 1);
			}
			System.out.println("DAL: " + dal);
			System.out.println("AL: " + al);
			System.out.println("qUERY GET_RESERVATIONS_BY_DATA");
			List<Prenotazione> prenotazioni = (List<Prenotazione>) em
					.createNamedQuery("GET_RESERVATIONS_BY_DATA").setParameter(
							"DAL", dal).setParameter("AL", al).getResultList();
			for (Prenotazione reservation : prenotazioni) {
				System.out.println("DAY: " + reservation.getSingleDay());
				if (resMap.containsKey(reservation.getSingleDay())) {
					DaySummary day = resMap.get(reservation.getSingleDay());
					day.inc();
					day.addDescription(reservation.getContratto().getScooter()
							.getName());
					resMap.put(reservation.getSingleDay(), day);
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
