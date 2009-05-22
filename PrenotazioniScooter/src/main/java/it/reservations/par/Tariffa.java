package it.reservations.par;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Tariffa implements Serializable {
	private Long id;
	private String name;
	private Float extragg;
	private Long maxKm;
	private Float costoKm;
	private List<TariffaPeriodo> periodi;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getExtragg() {
		return extragg;
	}

	public void setExtragg(Float extragg) {
		this.extragg = extragg;
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

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH,
			CascadeType.PERSIST, CascadeType.MERGE })
	public List<TariffaPeriodo> getPeriodi() {
		if (this.periodi == null)
			this.periodi = new ArrayList<TariffaPeriodo>();
		return periodi;
	}

	public void setPeriodi(List<TariffaPeriodo> periodi) {
		this.periodi = periodi;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
