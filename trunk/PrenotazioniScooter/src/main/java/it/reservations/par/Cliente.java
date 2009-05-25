package it.reservations.par;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Transient;

@Entity
public class Cliente implements Serializable {

	private Long id;
	private String nome;
	private String cognome;
	private String nomeCognome;
	private String cartaidentita;
	private String codicefiscale;
	private String rilasciataDa;
	private Date rilasciataData;
	private String rilasciataDove;
	private String indirizzo;
	private Long city;
	private Long provincia;
	private Long cap;
	private Long nazione;
	private Date dataNascita;
	private Long cityNascita;
	private Long provinciaNascita;
	private String telefono;
	private String cellulare;

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

	public Long getNazione() {
		return nazione;
	}

	public void setNazione(Long nazione) {
		this.nazione = nazione;
	}

	@Transient
	public String getNomeCognome() {
		return getNome() + " " + getCognome();
	}

	public void setNomeCognome(String nomeCognome) {
		this.nomeCognome = nomeCognome;
	}

	public String getRilasciataDa() {
		return rilasciataDa;
	}

	public void setRilasciataDa(String rilasciataDa) {
		this.rilasciataDa = rilasciataDa;
	}

	public Date getRilasciataData() {
		return rilasciataData;
	}

	public void setRilasciataData(Date rilasciataData) {
		this.rilasciataData = rilasciataData;
	}

	public String getRilasciataDove() {
		return rilasciataDove;
	}

	public void setRilasciataDove(String rilasciataDove) {
		this.rilasciataDove = rilasciataDove;
	}

	public Long getCityNascita() {
		return cityNascita;
	}

	public void setCityNascita(Long cityNascita) {
		this.cityNascita = cityNascita;
	}

	public Long getProvinciaNascita() {
		return provinciaNascita;
	}

	public void setProvinciaNascita(Long provinciaNascita) {
		this.provinciaNascita = provinciaNascita;
	}

	public String getCodicefiscale() {
		return codicefiscale;
	}

	public void setCodicefiscale(String codicefiscale) {
		this.codicefiscale = codicefiscale;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCellulare() {
		return cellulare;
	}

	public void setCellulare(String cellulare) {
		this.cellulare = cellulare;
	}

}
