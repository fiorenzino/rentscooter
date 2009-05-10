package it.reservations.par;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Client implements Serializable {

	private Long id;
	private String nome;
	private String cognome;
	private String cartaidentita;
	private String indirizzo;
	private Long city;
	private Long provincia;
	private Long cap;
	private Date dataNascita;

	public Client() {
		this.city = new Long(0);
		this.provincia = new Long(0);
		this.cap = new Long(0);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCartaidentita() {
		return cartaidentita;
	}

	public void setCartaidentita(String cartaidentita) {
		this.cartaidentita = cartaidentita;
	}

	@Lob
	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public Long getCity() {
		return city;
	}

	public void setCity(Long city) {
		this.city = city;
	}

	public Long getProvincia() {
		return provincia;
	}

	public void setProvincia(Long provincia) {
		this.provincia = provincia;
	}

	public Long getCap() {
		return cap;
	}

	public void setCap(Long cap) {
		this.cap = cap;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

}
