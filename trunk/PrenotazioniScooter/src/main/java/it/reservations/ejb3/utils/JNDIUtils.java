package it.reservations.ejb3.utils;

import it.reservations.ejb3.ClientiManager;
import it.reservations.ejb3.ContrattiManager;
import it.reservations.ejb3.PrenotazioniManager;
import it.reservations.ejb3.ScooterManager;
import it.reservations.ejb3.TariffeManager;

import javax.naming.InitialContext;

import org.jboss.logging.Logger;

public class JNDIUtils {

	static Logger log = Logger.getLogger(JNDIUtils.class.getName());
	private static ClientiManager clientiManager = null;
	public static ScooterManager scooterManager = null;
	public static TariffeManager tariffeManager = null;
	public static ContrattiManager contrattiManager = null;
	public static PrenotazioniManager prenotazioniManager = null;

	public static PrenotazioniManager getPrenotazioniManager() {
		if (prenotazioniManager != null) {
			return prenotazioniManager;
		} else {
			try {
				InitialContext ctx = new InitialContext();
				prenotazioniManager = (PrenotazioniManager) ctx
						.lookup("prenotazioniScooter/PrenotazioniManagerBean/local");
				if (prenotazioniManager != null) {
					log.info("comp: " + contrattiManager);
				}
				return prenotazioniManager;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public static ContrattiManager getContrattiManager() {
		if (contrattiManager != null) {
			return contrattiManager;
		} else {
			try {
				InitialContext ctx = new InitialContext();
				contrattiManager = (ContrattiManager) ctx
						.lookup("prenotazioniScooter/ContrattiManagerBean/local");
				if (contrattiManager != null) {
					log.info("comp: " + contrattiManager);
				}
				return contrattiManager;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public static TariffeManager getTariffeManager() {
		if (tariffeManager != null) {
			return tariffeManager;
		} else {
			try {
				InitialContext ctx = new InitialContext();
				tariffeManager = (TariffeManager) ctx
						.lookup("prenotazioniScooter/TariffeManagerBean/local");
				if (tariffeManager != null) {
					log.info("comp: " + tariffeManager);
				}
				return tariffeManager;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

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
