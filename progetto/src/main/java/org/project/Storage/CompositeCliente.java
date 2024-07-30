package org.project.Storage;

import org.project.Entities.Clienti;

public class CompositeCliente {
	
	CompositeCliente(){}
	CompositeCliente(Clienti cliente){
		this.nome = cliente.getNome();
		this.cognome = cliente.getCognome();
		this.eta = Integer.valueOf(cliente.getEta());
	}
	private String nome;
	private String cognome;
	private Integer eta;
	
	
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
	public Integer getEta() {
		return eta;
	}
	public void setEta(Integer eta) {
		this.eta = eta;
	}
	
}
