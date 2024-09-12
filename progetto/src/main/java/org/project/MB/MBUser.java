package org.project.MB;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.project.EJB.EJBUser;
import org.project.Storage.CompositeUser;
import org.project.Storage.FilterUser;
import org.project.Storage.Interfaccia;

@ManagedBean
public class MBUser implements Serializable, Interfaccia {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9179306231153667720L;

	private EJBUser ejbUser;

	private FilterUser filter;

	public void init() {
		ejbUser = new EJBUser();
		filter = new FilterUser();
	}

	public void register() {
		CompositeUser user = ejbUser.findUser(filter);
		if (user == null) {
			CompositeUser u = new CompositeUser();
			u.setCognome(filter.getCognome());
			u.setNome(filter.getNome());
			u.setEmail(filter.getEmail());
			u.setPassword(filter.getPassword());
			u.refreshEntities();
			ejbUser.createUser(u);
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			try {
				externalContext.redirect(localHost + "index.xhtml");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void redirectLogin() {

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		try {
			externalContext.redirect(localHost + "login.xhtml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public FilterUser getFilter() {
		return filter;
	}

	public void setFilter(FilterUser filter) {
		this.filter = filter;
	}

}
