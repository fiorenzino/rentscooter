package it.reservations.ejb3;

import it.reservations.par.Client;
import it.smartflower.ejb3.EJBManagerBean;

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
	public void persist(Client cliente) {
		try {
			em.persist(cliente);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void update(Client cliente) {
		try {
			em.merge(cliente);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void delete(Client cliente) {
		try {
			Client cl = em.find(Client.class, cliente.getId());
			em.remove(cl);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
