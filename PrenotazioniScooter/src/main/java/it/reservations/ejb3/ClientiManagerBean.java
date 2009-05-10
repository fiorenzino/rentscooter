package it.reservations.ejb3;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local(ClientiManager.class)
public class ClientiManagerBean implements ClientiManager {

	@PersistenceContext(unitName = "TestManager")
	EntityManager em;
}
