package it.reservations.ejb3.test;

import it.reservations.par.Contratto;

import javax.faces.model.DataModel;

public interface ExtTest {

	public Contratto getContratto();

	public void setContratto(Contratto contratto);

	public Contratto find(Long id);

	public String detailContratto();

	public void destroy();

	public DataModel getDataModel();

}
