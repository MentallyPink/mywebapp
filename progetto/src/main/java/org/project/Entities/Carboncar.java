package org.project.Entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the carboncars database table.
 * 
 */
@Entity
@Table(name="carboncars")
@NamedQuery(name="Carboncar.findAll", query="SELECT c FROM Carboncar c")
public class Carboncar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private float acceleration;

	@Column(name="Class")
	private String class_;

	private float handling;

	private String nome;

	private int price;

	private int tier;

	@Column(name="top_speed")
	private float topSpeed;

	public Carboncar() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getAcceleration() {
		return this.acceleration;
	}

	public void setAcceleration(float acceleration) {
		this.acceleration = acceleration;
	}

	public String getClass_() {
		return this.class_;
	}

	public void setClass_(String class_) {
		this.class_ = class_;
	}

	public float getHandling() {
		return this.handling;
	}

	public void setHandling(float handling) {
		this.handling = handling;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getPrice() {
		return this.price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getTier() {
		return this.tier;
	}

	public void setTier(int tier) {
		this.tier = tier;
	}

	public float getTopSpeed() {
		return this.topSpeed;
	}

	public void setTopSpeed(float topSpeed) {
		this.topSpeed = topSpeed;
	}
	@Override
	public String toString() {
		return "nome veicolo: " + getNome() + " classe: " + getClass_() + " tier: " + getTier();
	}

}