package it.reservations.ejb3;

import it.reservations.par.Contratto;
import it.reservations.par.Prenotazione;
import it.smartflower.ejb3.EJBManagerBean;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
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

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(Contratto contratto) {
		try {
			Contratto cl = em.find(Contratto.class, contratto.getId());
			em.remove(cl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void persist(Contratto contratto) {
		try {
			em.persist(contratto);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void update(Contratto contratto) {
		try {
			em.merge(contratto);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Long getNumContrattiCliente(Long id) {
		Long numContratti = new Long(0);
		try {
			numContratti = (Long) em.createNamedQuery(
					"GET_NUM_CONTRATTI_BY_CLIENTE").setParameter("CLIENTE_ID",
					id).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return numContratti;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Contratto updateSpecial(Contratto contratto) {
		try {
			Contratto oldContratto = em
					.find(Contratto.class, contratto.getId());
			for (Prenotazione prenotazione : oldContratto.getPrenotazioni()) {
				em.remove(prenotazione);
			}
			oldContratto = contratto;
			em.merge(oldContratto);
			return oldContratto;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
