package org.project.Entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the money database table.
 * 
 */
@Entity
@NamedQuery(name="Money.findAll", query="SELECT m FROM Money m")
public class Money implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private int money;

	public Money() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMoney() {
		return this.money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

}