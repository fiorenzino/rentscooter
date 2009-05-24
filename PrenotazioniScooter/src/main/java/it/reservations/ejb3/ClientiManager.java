package it.reservations.ejb3;

import it.reservations.par.Cliente;
import it.smartflower.ejb3.EJBManager;

public interface ClientiManager extends EJBManager {

	public void persist(Cliente cliente);

	public void update(Cliente cliente);

	public void delete(Cliente cliente);

}
