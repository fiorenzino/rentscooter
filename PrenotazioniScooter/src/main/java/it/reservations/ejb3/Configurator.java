package it.reservations.ejb3;


import it.reservations.par.Comune;
import it.reservations.par.Nazione;
import it.reservations.par.Provincia;

import java.util.TreeMap;

public interface Configurator {

	public TreeMap<Long, Comune> getComuni();

	public TreeMap<Long, Provincia> getProvince();

	public TreeMap<Long, Nazione> getNazioni();

}
