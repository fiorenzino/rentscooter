package it.reservations.ejb3;

import java.util.List;

import it.reservations.par.Tariffa;
import it.smartflower.ejb3.EJBManager;

public interface TariffeManager extends EJBManager {
	public void persist(Tariffa tariffa);

	public void update(Tariffa tariffa);

	public void remove(Tariffa tariffa);

	public List<Tariffa> getAllTariffe();

	public Tariffa find(Long id);

}
