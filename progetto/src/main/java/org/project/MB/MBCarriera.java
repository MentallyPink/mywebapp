package org.project.MB;

import javax.faces.bean.ManagedBean;

@ManagedBean

public class MBCarriera {

	public void showPopUp(int value){
		switch(value) {
		case 1: System.out.println("balls");
			break;
		case 2: System.out.println("adwa");
			break;
		case 3: System.out.println("basdaalls");
			break;
		}
	}
}
