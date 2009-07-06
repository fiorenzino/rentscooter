package it.reservations.par;

import it.reservations.ejb3.utils.TimeUtil;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Scooter implements Serializable {

	private Long id;
	private String nome;
	private String marca;
	private String modello;
	private String cilindrata;
	private Tariffa tariffa;
	private String marcaModello;
	private Float caparra;
	private String targa;
	private Date scadenzaAssicurazione;
	private String scadenza;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModello() {
		return modello;
	}

	public void setModello(String modello) {
		this.modello = modello;
	}

	public String getCilindrata() {
		return cilindrata;
	}

	public void setCilindrata(String cilindrata) {
		this.cilindrata = cilindrata;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	public Tariffa getTariffa() {
		if (tariffa == null)
			tariffa = new Tariffa();
		return tariffa;
	}

	public void setTariffa(Tariffa tariffa) {
		this.tariffa = tariffa;
	}

	@Transient
	public String getMarcaModello() {
		return nome + " - " + marca + " " + modello;
	}

	public void setMarcaModello(String marcaModello) {
		this.marcaModello = marcaModello;
	}

	public String getTarga() {
		return targa;
	}

	public void setTarga(String targa) {
		this.targa = targa;
	}

	public Float getCaparra() {
		return caparra;
	}

	public void setCaparra(Float caparra) {
		this.caparra = caparra;
	}

	public Date getScadenzaAssicurazione() {
		return scadenzaAssicurazione;
	}

	public void setScadenzaAssicurazione(Date scadenzaAssicurazione) {
		this.scadenzaAssicurazione = scadenzaAssicurazione;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Transient
	public String getScadenza() {
		if (TimeUtil.getDiffDays(getScadenzaAssicurazione(), new Date()) < 7)
			return "occupato";
		return "libero";
	}

	public void setScadenza(String scadenza) {
		this.scadenza = scadenza;
	}

}
