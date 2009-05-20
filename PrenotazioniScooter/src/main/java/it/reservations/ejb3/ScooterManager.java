package it.reservations.ejb3;

import it.reservations.par.Scooter;
import it.smartflower.ejb3.EJBManager;

public interface ScooterManager extends EJBManager {
	public void persist(Scooter scooter);

	public void update(Scooter scooter);

	public void remove(Scooter scooter);

}
