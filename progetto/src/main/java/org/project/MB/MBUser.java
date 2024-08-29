package org.project.MB;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.project.Entities.User;
import org.project.Storage.Interfaccia;

@ManagedBean
public class MBUser implements Serializable, Interfaccia {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9179306231153667720L;

	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("progetto");
	private EntityManager em = entityManagerFactory.createEntityManager();
	private String name;
	private String surname;
	private String email;
	private String password;
	
	
	public void register() {
		
		em.getTransaction().begin();
		User u = new User();
		u.setNome(name);
		u.setCognome(surname);
		u.setEmail(email);
		this.password = _encryptPassword(password);
		u.setPassword(password);
		User check = em.find(User.class, email);
		if(check != null) {
			System.out.println("email gia presente");
			em.getTransaction().commit();
		}else {
			em.persist(u);
			em.getTransaction().commit();
			System.out.println("worka");
			
			 ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext(); //server per la navigazione tra le pagine
			 try {
					externalContext.redirect("http://localhost:8080/progetto/faces/index.xhtml");//redirect alla mia pagina di lista
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		
	}
	
    private static String _encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	public void redirectLogin() {
		
		 ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext(); //server per la navigazione tra le pagine
	        try {
				externalContext.redirect(localHost + "login.xhtml");//redirect alla mia pagina di lista
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
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
