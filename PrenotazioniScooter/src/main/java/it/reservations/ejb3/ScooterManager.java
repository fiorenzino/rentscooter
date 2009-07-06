package it.reservations.ejb3;

import it.reservations.par.Scooter;
import it.smartflower.ejb3.EJBManager;

import java.util.List;

public interface ScooterManager extends EJBManager {
	public void persist(Scooter scooter);

	public void update(Scooter scooter);

	public void remove(Scooter scooter);

	public Scooter find(Long id);

	public List<Scooter> getAllScooter();

	public List<String> getCilindrate();

}
