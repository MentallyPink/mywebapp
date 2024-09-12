package org.project.Storage;

import java.io.Serializable;

import org.project.Entities.User;
import org.project.Utilities.StringUtilities;

public class CompositeUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private User entity;
	private String email;
	private String nome;
	private String cognome;
	private String password;
	
	public CompositeUser() {
		this.entity = new User();
	}
	
	public CompositeUser(User entity) {
		this.entity = entity;
	}
	
	public void refreshEntities() {
		entity.setCognome(StringUtilities.isEmpty(cognome)? "" : cognome);
		entity.setNome(StringUtilities.isEmpty(nome)? "" : nome);
		entity.setEmail(StringUtilities.isEmpty(email)? "" : email);
		entity.setPassword(StringUtilities.isEmpty(password)? "" : password);
	}
	
	public void refreshComposite() {
		this.email = entity.getEmail();
		this.nome = entity.getNome();
		this.cognome = entity.getCognome();
		this.password = entity.getPassword();
	}
	
	public User getEntity() {
		return entity;
	}
	public void setEntity(User entity) {
		this.entity = entity;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void encryptPassword() {
		this.password = StringUtilities.encryptString(this.password);
		refreshEntities();
	}
	

}
