package it.reservations.ejb3;

import it.reservations.par.Tariffa;
import it.smartflower.ejb3.EJBManager;

public interface TariffeManager extends EJBManager {
	public void persist(Tariffa tariffa);

	public void update(Tariffa tariffa);

	public void remove(Tariffa tariffa);

}
