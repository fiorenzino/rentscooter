package it.reservations.ejb3;

import java.util.ArrayList;
import java.util.List;

import it.reservations.par.Scooter;
import it.reservations.par.Tariffa;
import it.smartflower.ejb3.EJBManagerBean;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local(ScooterManager.class)
public class ScooterManagerBean extends EJBManagerBean implements
		ScooterManager {

	@PersistenceContext(unitName = "TestManager")
	EntityManager em;

	@Override
	public EntityManager getEm() {
		// TODO Auto-generated method stub
		return em;
	}

	@Override
	public void persist(Scooter scooter) {
		try {
			em.persist(scooter);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void update(Scooter scooter) {
		try {
			em.merge(scooter);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void remove(Scooter scooter) {
		try {
			Scooter sco = em.find(Scooter.class, scooter.getId());
			em.remove(sco);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public Scooter find(Long id) {
		try {
			return em.find(Scooter.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Scooter> getAllScooter() {
		List<Scooter> result = new ArrayList<Scooter>();
		try {
			result = em.createQuery("select t from Scooter t order by t.marca, t.modello")
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}

}
