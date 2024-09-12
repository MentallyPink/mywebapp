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
public class MBLogin implements Serializable, Interfaccia {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EJBUser ejbUser;

	private FilterUser filter;

	public void init() {
		ejbUser = new EJBUser();
		filter = new FilterUser();
	}

	public void login() {

		CompositeUser user = ejbUser.findUser(filter);
		if (user != null && user.getEntity() != null) {
			try {
				ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
				externalContext.redirect(localHost + "index.xhtml");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void redirectRegister() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		try {
			externalContext.redirect(localHost + "register.xhtml");
		} catch (Exception e) {

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
