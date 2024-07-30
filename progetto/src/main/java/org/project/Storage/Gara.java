package org.project.Storage;

import java.io.Serializable;


public class Gara implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9124745368613331124L;

	private boolean vinto;
	private String tracciato;
	
	public Gara() {
		this.tracciato = "";
		this.vinto = false;
	}
	public Gara(String tracciato) {
		this.tracciato = tracciato;
		this.vinto = false;
	}

	public boolean isVinto() {
		return vinto;
	}

	public void setVinto(boolean vinto) {
		this.vinto = vinto;
	}

	public String getTracciato() {
		return tracciato;
	}

	public void setTracciato(String tracciato) {
		this.tracciato = tracciato;
	}
}
