package it.reservations.ejb3;

import it.reservations.par.Contratto;
import it.smartflower.ejb3.EJBManagerBean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ContrattiManagerBean extends EJBManagerBean implements
		ContrattiManager {

	@PersistenceContext(unitName = "TestManager")
	EntityManager em;

	@Override
	public EntityManager getEm() {
		// TODO Auto-generated method stub
		return em;
	}

	@Override
	public void delete(Contratto contract) {
		try {
			Contratto cl = em.find(Contratto.class, contract.getId());
			em.remove(cl);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void persist(Contratto contract) {
		try {
			em.persist(contract);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void update(Contratto contract) {
		try {
			em.merge(contract);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
