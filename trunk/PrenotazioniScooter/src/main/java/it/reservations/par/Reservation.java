package it.reservations.par;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Reservation implements Serializable {

	private Long id;
	private Date singleDay;
	private Client client;
	private Scooter scooter;
	private String singleDayName;
	private String scooterName;
	private Contract contratto;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	@ManyToOne
	public Scooter getScooter() {
		if (scooter == null)
			scooter = new Scooter();
		return scooter;
	}

	public void setScooter(Scooter scooter) {
		this.scooter = scooter;
	}

	public Date getSingleDay() {
		return singleDay;
	}

	public void setSingleDay(Date singleDay) {
		this.singleDay = singleDay;
	}

	@Transient
	public String getSingleDayName() {
		if (singleDay != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(singleDay);
			return cal.get(Calendar.DAY_OF_MONTH) + "-"
					+ (cal.get(Calendar.MONTH) + 1) + "-"
					+ cal.get(Calendar.YEAR);
		}
		return "";
	}

	public void setSingleDayName(String singleDayName) {
		this.singleDayName = singleDayName;
	}

	@Transient
	public String getScooterName() {
		if (scooter != null) {
			return scooter.getName();
		}
		return "";
	}

	public void setScooterName(String scooterName) {
		this.scooterName = scooterName;
	}

	@ManyToOne
	public Contract getContratto() {
		return contratto;
	}

	public void setContratto(Contract contratto) {
		this.contratto = contratto;
	}

}
