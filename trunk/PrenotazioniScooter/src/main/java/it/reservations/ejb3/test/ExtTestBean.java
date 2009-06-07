package it.reservations.ejb3.test;

import java.util.ArrayList;
import java.util.List;

import it.reservations.par.Contratto;

import javax.annotation.Named;
import javax.context.Conversation;
import javax.context.ConversationScoped;
import javax.context.SessionScoped;
import javax.ejb.Local;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@Stateful
@Named(value = "extTest")
@SessionScoped
@Local(ExtTest.class)
public class ExtTestBean implements ExtTest {

	@PersistenceContext(unitName = "TestManager", type = PersistenceContextType.EXTENDED)
	EntityManager em;

	private Contratto contratto;
	private DataModel dataModel;

	public ExtTestBean() {

		System.out.println("creo ExtTestBean");
	}

	public Contratto getContratto() {
		return contratto;
	}

	public void setContratto(Contratto contratto) {
		this.contratto = contratto;
	}

	public Contratto find(Long id) {
		try {
			Contratto cl = em.find(Contratto.class, id);
			return cl;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public List<Contratto> getContratti() {

		List<Contratto> lista = new ArrayList<Contratto>();
		try {

			lista = em.createQuery("select c from Contratto c ")
					.getResultList();
			System.out.println("GET CONTRATTI SIZE:" + lista.size());
			return lista;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String detailContratto() {

		this.contratto = (Contratto) getDataModel().getRowData();

		return "/test/scheda-contrattoExt.xhtml";
	}

	@Remove
	public void destroy() {
		System.out.println("destroy ExtTestBean");
	}

	public DataModel getDataModel() {
		System.out.println("GET DATA MODEL");
		if (this.dataModel == null) {
			this.dataModel = new ListDataModel();
			this.dataModel.setWrappedData(getContratti());
			System.out.println("GET DATA MODEL NEW");
		}
		return dataModel;
	}

	public void setDataModel(DataModel dataModel) {
		this.dataModel = dataModel;
	}

}
