package org.project.Entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the clienti database table.
 * 
 */
@Entity
@NamedQuery(name="Clienti.findAll", query="SELECT c FROM Clienti c")
public class Clienti implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idClienti;

	private String cognome;

	private String eta;

	private String nome;

	public Clienti() {
	}

	public int getIdClienti() {
		return this.idClienti;
	}

	public void setIdClienti(int idClienti) {
		this.idClienti = idClienti;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEta() {
		return this.eta;
	}

	public void setEta(String eta) {
		this.eta = eta;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}