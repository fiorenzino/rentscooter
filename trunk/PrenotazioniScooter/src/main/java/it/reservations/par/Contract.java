package it.reservations.par;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Contract implements Serializable {

	private Long id;
	private Date dataInit;
	private Date dataEnd;
	private User user;
	private String note;
	private Float importoIniziale;
	private Float importoFinale;
	private Float extra;
	private Float totale;
	private List<Reservation> prenotazioni;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataInit() {
		return dataInit;
	}

	public void setDataInit(Date dataInit) {
		this.dataInit = dataInit;
	}

	public Date getDataEnd() {
		return dataEnd;
	}

	public void setDataEnd(Date dataEnd) {
		this.dataEnd = dataEnd;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Lob
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Float getImportoIniziale() {
		if (importoIniziale == null)
			importoIniziale = new Float(0);
		return importoIniziale;
	}

	public void setImportoIniziale(Float importoIniziale) {
		this.importoIniziale = importoIniziale;
	}

	public Float getImportoFinale() {
		if (importoFinale == null)
			importoFinale = new Float(0);
		return importoFinale;
	}

	public void setImportoFinale(Float importoFinale) {
		this.importoFinale = importoFinale;
	}

	public Float getExtra() {
		if (extra == null)
			extra = new Float(0);
		return extra;
	}

	public void setExtra(Float extra) {
		this.extra = extra;
	}

	@Transient
	public Float getTotale() {

		return getImportoIniziale() + getImportoFinale() + getExtra();
	}

	public void setTotale(Float totale) {
		this.totale = totale;
	}

	@OneToMany
	public List<Reservation> getPrenotazioni() {
		if (prenotazioni == null)
			prenotazioni = new ArrayList<Reservation>();
		return prenotazioni;
	}

	public void setPrenotazioni(List<Reservation> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}

}
