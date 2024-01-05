package org.project.Entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the carbonboss database table.
 * 
 */
@Entity
@NamedQuery(name="Carbonboss.findAll", query="SELECT c FROM Carbonboss c")
public class Carbonboss implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idboss;

	private String gang;

	private String macchina;

	private String nome;

	public Carbonboss() {
	}

	public int getIdboss() {
		return this.idboss;
	}

	public void setIdboss(int idboss) {
		this.idboss = idboss;
	}

	public String getGang() {
		return this.gang;
	}

	public void setGang(String gang) {
		this.gang = gang;
	}

	public String getMacchina() {
		return this.macchina;
	}

	public void setMacchina(String macchina) {
		this.macchina = macchina;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	


}