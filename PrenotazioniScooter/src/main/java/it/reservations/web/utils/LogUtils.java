package it.reservations.web.utils;

import org.jboss.logging.Logger;

public class LogUtils {
	// static Logger log = Logger.getLogger(LogUtils.class.getName());

	public static void logga(Class classe, String log) {
		Logger.getLogger(classe.getName()).info(log);
	}
}
