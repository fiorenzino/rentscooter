package it.reservations.ejb3;

import it.reservations.par.Contratto;
import it.smartflower.ejb3.EJBManager;

public interface ContrattiManager extends EJBManager {

	public void persist(Contratto contract);

	public void update(Contratto contract);

	public void delete(Contratto contract);
	
	public Long getNumContrattiCliente(Long id);

}
