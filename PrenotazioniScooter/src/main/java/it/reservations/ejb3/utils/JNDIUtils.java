package it.reservations.ejb3.utils;

import it.reservations.ejb3.ClientiManager;
import it.reservations.ejb3.ScooterManager;
import it.smartflower.ejb3.EJBManager;

import javax.naming.InitialContext;

import org.jboss.logging.Logger;

public class JNDIUtils {

	static Logger log = Logger.getLogger(JNDIUtils.class.getName());
	private static ClientiManager clientiManager = null;
	public static ScooterManager scooterManager;

	public static ClientiManager getClientiManager() {
		if (clientiManager != null) {
			return clientiManager;
		} else {
			try {
				InitialContext ctx = new InitialContext();
				clientiManager = (ClientiManager) ctx
						.lookup("prenotazioniScooter/ClientiManagerBean/local");
				if (clientiManager != null) {
					log.info("comp: " + clientiManager);
				}
				return clientiManager;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public static ScooterManager getScooterManager() {
		if (scooterManager != null) {
			return scooterManager;
		} else {
			try {
				InitialContext ctx = new InitialContext();
				scooterManager = (ScooterManager) ctx
						.lookup("prenotazioniScooter/ScooterManagerBean/local");
				if (clientiManager != null) {
					log.info("comp: " + scooterManager);
				}
				return scooterManager;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

}
