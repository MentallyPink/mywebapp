package org.project.Storage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public interface Interfaccia {

	public static final String localHost = "http://localhost:8190/progetto/faces/";
	
	public EntityManagerFactory emf = Persistence.createEntityManagerFactory("progetto");
}
