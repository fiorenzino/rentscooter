package it.reservations.ejb3;

import it.reservations.par.Scooter;
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

}
