package it.reservations.ejb3;

import java.util.ArrayList;
import java.util.List;

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

	@Override
	public List<Tariffa> getAllTariffe() {
		List<Tariffa> result = new ArrayList<Tariffa>();
		try {
			result = em.createQuery("select t from Tariffa t order by t.nome")
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}

	@Override
	public Tariffa find(Long id) {
		try {
			return em.find(Tariffa.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
