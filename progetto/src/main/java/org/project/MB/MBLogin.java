package org.project.MB;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.project.Entities.User;
import org.project.Storage.Interfaccia;

@ManagedBean
public class MBLogin implements Serializable, Interfaccia{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("progetto");
	private EntityManager em = entityManagerFactory.createEntityManager();
	private String password;
	private String email;

	public void login() {
		User u = em.find(User.class, email);
		this.password = _encryptPassword(this.password);
		try {

			if (email.equals(u.getEmail()) && password.equals(u.getPassword())) {

				ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
				externalContext.redirect(localHost + "index.xhtml");// redirect alla mia pagina
																								// di lista
			} else {
				System.out.println("credenziali sbagliate");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void redirectRegister() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext(); // serve per la
																									// navigazione tra
																									// le pagine, prende
																									// l'istanza
																									// corrente
		try {
			externalContext.redirect(localHost + "register.xhtml");// redirect alla mia pagina
																							// di lista
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	
    private String _encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

	public void onRedirect() {
		this.email = null;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
