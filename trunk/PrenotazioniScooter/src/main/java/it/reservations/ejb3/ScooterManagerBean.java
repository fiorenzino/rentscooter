package it.reservations.ejb3;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import it.reservations.par.Scooter;
import it.reservations.par.Tariffa;
import it.smartflower.ejb3.EJBManagerBean;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

@Stateless
@Local(ScooterManager.class)
public class ScooterManagerBean extends EJBManagerBean implements
		ScooterManager {

	@PersistenceContext(unitName = "TestManager")
	EntityManager em;

	@Resource(mappedName = "java:/RentScooterDS")
	DataSource dataSource;

	@Override
	public EntityManager getEm() {
		// TODO Auto-generated method stub
		return em;
	}

	public void persist(Scooter scooter) {
		try {
			em.persist(scooter);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void update(Scooter scooter) {
		try {
			em.merge(scooter);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void remove(Scooter scooter) {
		try {
			Scooter sco = em.find(Scooter.class, scooter.getId());
			em.remove(sco);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Scooter find(Long id) {
		System.out.println("FIND SCOOTER ID: " + id);
		try {

			return em.find(Scooter.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Scooter> getAllScooter() {
		List<Scooter> result = new ArrayList<Scooter>();
		try {
			result = em.createQuery(
					"select t from Scooter t order by t.marca, t.modello")
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}

	public List<String> getCilindrate() {
		List<String> result = new ArrayList<String>();
		try {
			Connection con = dataSource.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = null;
			String query = "select distinct(cilindrata) as cil from Scooter order by cilindrata asc";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				result.add(rs.getString("cil"));
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

}
