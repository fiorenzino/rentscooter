package it.reservations.ejb3;

import it.reservations.par.Contratto;
import it.reservations.par.Prenotazione;
import it.smartflower.ejb3.EJBManagerBean;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local(ContrattiManager.class)
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
	public void delete(Contratto contratto) {
		try {
			Contratto cl = em.find(Contratto.class, contratto.getId());
			em.remove(cl);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void persist(Contratto contratto) {
		try {
			em.persist(contratto);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void update(Contratto contratto) {
		try {
			em.merge(contratto);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
