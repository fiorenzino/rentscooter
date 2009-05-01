package it.reservations.par;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tariffa implements Serializable {
	private Long id;
	private Float ung;
	private Float treg;
	private Float cinqueg;
	private Float setteg;
	private Float extragg;
	private Float mensilegg;
	private Long maxKm;
	private Float costoKm;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getUng() {
		return ung;
	}

	public void setUng(Float ung) {
		this.ung = ung;
	}

	public Float getTreg() {
		return treg;
	}

	public void setTreg(Float treg) {
		this.treg = treg;
	}

	public Float getCinqueg() {
		return cinqueg;
	}

	public void setCinqueg(Float cinqueg) {
		this.cinqueg = cinqueg;
	}

	public Float getSetteg() {
		return setteg;
	}

	public void setSetteg(Float setteg) {
		this.setteg = setteg;
	}

	public Float getExtragg() {
		return extragg;
	}

	public void setExtragg(Float extragg) {
		this.extragg = extragg;
	}

	public Float getMensilegg() {
		return mensilegg;
	}

	public void setMensilegg(Float mensilegg) {
		this.mensilegg = mensilegg;
	}

	public Long getMaxKm() {
		return maxKm;
	}

	public void setMaxKm(Long maxKm) {
		this.maxKm = maxKm;
	}

	public Float getCostoKm() {
		return costoKm;
	}

	public void setCostoKm(Float costoKm) {
		this.costoKm = costoKm;
	}
}
