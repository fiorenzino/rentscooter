package it.reservations.ejb3;

import java.util.ArrayList;
import java.util.List;

import it.reservations.par.Scooter;
import it.reservations.par.Tariffa;
import it.reservations.web.utils.TariffeUtil;
import it.smartflower.ejb3.EJBManagerBean;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

@Stateless
@Local(TariffeManager.class)
public class TariffeManagerBean extends EJBManagerBean implements
		TariffeManager {

	Logger log = Logger.getLogger(TariffeManagerBean.class.getName());

	@PersistenceContext(unitName = "TestManager")
	EntityManager em;

	@Override
	public EntityManager getEm() {
		// TODO Auto-generated method stub
		return em;
	}

	public void persist(Tariffa tariffa) {
		try {
			em.persist(tariffa);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void update(Tariffa tariffa) {
		try {
			em.merge(tariffa);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void remove(Tariffa tariffa) {
		try {
			Tariffa tar = em.find(Tariffa.class, tariffa.getId());
			em.remove(tar);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

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

	public Tariffa find(Long id) {
		try {
			return em.find(Tariffa.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Tariffa getTariffaByCilindrata(String cilindrata) {
		log.info("GET SCOOTER BY CILINDRATA");
		List<Scooter> result = new ArrayList<Scooter>();
		try {
			result = em.createQuery(
					"select t from Scooter t where t.cilindrata = :CIL")
					.setParameter("CIL", cilindrata).getResultList();
			if (result.size() > 0 && result.get(0) != null)
				return result.get(0).getTariffa();
		} catch (Exception e) {
			log.error("GET SCOOTER BY CILINDRATA EXC");
			e.printStackTrace();
			return null;
		}
		return null;
	}
}
