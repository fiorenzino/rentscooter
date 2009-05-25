package it.reservations.ejb3;

import it.reservations.par.Cliente;
import it.smartflower.ejb3.EJBManagerBean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local(ClientiManager.class)
public class ClientiManagerBean extends EJBManagerBean implements
		ClientiManager {

	@PersistenceContext(unitName = "TestManager")
	EntityManager em;

	@Override
	public EntityManager getEm() {
		// TODO Auto-generated method stub
		return em;
	}

	@Override
	public void persist(Cliente cliente) {
		try {
			em.persist(cliente);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void update(Cliente cliente) {
		try {
			em.merge(cliente);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void delete(Cliente cliente) {
		try {
			Cliente cl = em.find(Cliente.class, cliente.getId());
			em.remove(cl);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Cliente> getAllClienti() {
		List<Cliente> result = new ArrayList<Cliente>();
		try {
			result = em.createQuery(
					"select t from Cliente t order by t.cognome, t.nome")
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}

	@Override
	public Cliente find(Long id) {
		try {
			return em.find(Cliente.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
