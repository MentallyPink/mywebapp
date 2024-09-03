package org.project.Storage;

import java.io.Serializable;

import org.project.Enum.EnumGenerico.CarClass;

public class FilterCar implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3201024146786088608L;

	private String nome;
	private CarClass classe;
	private int prezzo;
	private int tier;

	public FilterCar() {
		this.classe = CarClass.X;
	}

	public FilterCar(String nome, CarClass classe, int prezzo, int tier) {
		this.nome = nome;
		this.classe = classe;
		this.prezzo = prezzo;
		this.tier = tier;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public CarClass getClasse() {
		return classe;
	}

	public void setClasse(CarClass classe) {
		this.classe = classe;
	}

	public int getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(int prezzo) {
		this.prezzo = prezzo;
	}

	public int getTier() {
		return tier;
	}

	public void setTier(int tier) {
		this.tier = tier;
	}

}
