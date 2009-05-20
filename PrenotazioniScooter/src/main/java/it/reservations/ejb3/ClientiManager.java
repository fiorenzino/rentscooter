package it.reservations.ejb3;

import it.reservations.par.Client;
import it.smartflower.ejb3.EJBManager;

public interface ClientiManager extends EJBManager {

	public void persist(Client cliente);

	public void update(Client cliente);

	public void delete(Client cliente);

}
