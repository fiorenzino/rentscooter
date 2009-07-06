package it.reservations.par;

import java.io.Serializable;

public class MiniPre implements Serializable {
	private Long id;
	private String nome;
	private boolean occupato;

	public String getStyleClass() {
		return getOccupato() ? "occupato" : "libero";
	}

	public String getSymbol() {
		return getOccupato() ? "x" : "o";
	}

	public MiniPre(Long id, String nome) {
		this.id = id;
		this.nome = nome;
		this.occupato = false;
	}

	public MiniPre(Long id, String nome, Boolean occupato) {
		this.id = id;
		this.nome = nome;
		this.occupato = occupato;
	}

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

	public boolean getOccupato() {
		return occupato;
	}

	public void setOccupato(boolean occupato) {
		this.occupato = occupato;
	}
}
