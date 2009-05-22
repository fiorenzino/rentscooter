package it.reservations.ejb3;

import it.reservations.par.Tariffa;
import it.smartflower.ejb3.EJBManagerBean;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local(TariffeManager.class)
public class TariffeManagerBean extends EJBManagerBean implements
		TariffeManager {

	@PersistenceContext(unitName = "TestManager")
	EntityManager em;

	@Override
	public EntityManager getEm() {
		// TODO Auto-generated method stub
		return em;
	}

	@Override
	public void persist(Tariffa tariffa) {
		try {
			em.persist(tariffa);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void update(Tariffa tariffa) {
		try {
			em.merge(tariffa);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void remove(Tariffa tariffa) {
		try {
			Tariffa tar = em.find(Tariffa.class, tariffa.getId());
			em.remove(tar);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
