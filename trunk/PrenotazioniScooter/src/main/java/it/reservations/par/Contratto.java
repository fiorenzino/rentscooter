package it.reservations.par;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Contratto implements Serializable {

	private Long id;
	private Date dataInit;
	private Date dataEnd;
	private Date dataRiconsegna;
	private Cliente cliente;
	private Scooter scooter;
	private String note;
	private Float importoIniziale;
	private Float importoFinale;
	private Float importokmExtra;
	private Float importoRitiroMezzo;
	private Float importoSottoCasco;
	private Float importoCaparra;
	private Float importoDanni;
	private Float extra;
	private Float totale;
	private Float benzinaExtra;
	private Float cascoExtra;
	private Float kmExtra;
	private Float sconto;
	private Boolean aperto;
	private List<Prenotazione> prenotazioni;

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

	@ManyToOne(fetch = FetchType.EAGER)
	public Cliente getCliente() {
		if (this.cliente == null)
			this.cliente = new Cliente();
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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
		if (getSconto() != 0) {
			return (getImportoIniziale() - (getSconto() * getImportoIniziale() / new Float(
					100)))
					+ getImportoFinale()
					+ getExtra()
					+ getBenzinaExtra()
					+ getCascoExtra()
					+ getImportokmExtra()
					+ getImportoCaparra()
					+ getImportoDanni()
					+ getImportoRitiroMezzo() + getImportoSottoCasco();
		}
		return getImportoIniziale() + getImportoFinale() + getExtra()
				+ getBenzinaExtra() + getCascoExtra() + getImportokmExtra()
				+ getImportoCaparra() + getImportoDanni()
				+ getImportoRitiroMezzo() + getImportoSottoCasco();
	}

	public void setTotale(Float totale) {
		this.totale = totale;
	}

	@OneToMany(mappedBy = "contratto", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	public List<Prenotazione> getPrenotazioni() {
		if (prenotazioni == null)
			prenotazioni = new ArrayList<Prenotazione>();
		return prenotazioni;
	}

	public void setPrenotazioni(List<Prenotazione> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	public Scooter getScooter() {
		if (this.scooter == null)
			this.scooter = new Scooter();
		return scooter;
	}

	public void setScooter(Scooter scooter) {
		this.scooter = scooter;
	}

	public Float getSconto() {
		if (sconto == null)
			sconto = new Float(0);
		return sconto;
	}

	public void setSconto(Float sconto) {
		this.sconto = sconto;
	}

	public Boolean getAperto() {
		return aperto;
	}

	public void setAperto(Boolean aperto) {
		this.aperto = aperto;
	}

	public Date getDataRiconsegna() {
		return dataRiconsegna;
	}

	public void setDataRiconsegna(Date dataRiconsegna) {
		this.dataRiconsegna = dataRiconsegna;
	}

	public Float getBenzinaExtra() {
		if (benzinaExtra == null)
			benzinaExtra = new Float(0);
		return benzinaExtra;
	}

	public void setBenzinaExtra(Float benzinaExtra) {
		this.benzinaExtra = benzinaExtra;
	}

	public Float getCascoExtra() {
		if (cascoExtra == null)
			cascoExtra = new Float(0);
		return cascoExtra;
	}

	public void setCascoExtra(Float cascoExtra) {
		this.cascoExtra = cascoExtra;
	}

	public Float getKmExtra() {
		if (kmExtra == null)
			kmExtra = new Float(0);
		return kmExtra;
	}

	public void setKmExtra(Float kmExtra) {
		this.kmExtra = kmExtra;
	}

	public Float getImportokmExtra() {
		if (importokmExtra == null)
			importokmExtra = new Float(0);
		return importokmExtra;
	}

	public void setImportokmExtra(Float importokmExtra) {
		this.importokmExtra = importokmExtra;
	}

	public Float getImportoRitiroMezzo() {
		if (importoRitiroMezzo == null)
			importoRitiroMezzo = new Float(0);
		return importoRitiroMezzo;
	}

	public void setImportoRitiroMezzo(Float importoRitiroMezzo) {
		this.importoRitiroMezzo = importoRitiroMezzo;
	}

	public Float getImportoSottoCasco() {
		if (importoSottoCasco == null)
			importoSottoCasco = new Float(0);
		return importoSottoCasco;
	}

	public void setImportoSottoCasco(Float importoSottoCasco) {
		this.importoSottoCasco = importoSottoCasco;
	}

	public Float getImportoCaparra() {
		if (importoCaparra == null)
			importoCaparra = new Float(0);
		return importoCaparra;
	}

	public void setImportoCaparra(Float importoCaparra) {
		this.importoCaparra = importoCaparra;
	}

	public Float getImportoDanni() {
		if (importoDanni == null)
			importoDanni = new Float(0);
		return importoDanni;
	}

	public void setImportoDanni(Float importoDanni) {
		this.importoDanni = importoDanni;
	}

}
