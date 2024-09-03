package org.project.Enum;

public enum EnumGenerico {
	Razor,  Viper ,  Shadow ,  Ghost ,  Spike ,  Reaper ,  Snake ,  Scar ,  Bones ,  Wolf ,
     Joker ,  Savage ,  Raven ,  Blade ,  Cobra ,  Ace ,  Diesel ,  Rogue ,  Killer ,  Maverick ,
     Mamba ,  Raptor ,  Bullet ,  Havoc ,  Vandal ,  Bandit ,  Sinner ,  Fury ,  Gunner ,  Crash ,
     Skull ,  Rage ,  Venom ,  Thrasher ,  Wraith ,  Talon ,  Grizzly ,  Slick ,
     Sledge ,  Ruckus ,  Bruiser ,  Slayer ,  Trigger ,  Outlaw ,  Blitz ,  Torch ,  Stitch ,  Psycho;
	
	
	
	
     
     public enum CarClass{
    	 	X("Tutti", "tutte le classi"),
    		T("Tuner", "classe tuner"),
    		E("Exotic", "classe exotic"),
    		M("Muscle", "classe muscle");
    		
    	private String codice;
    	 private String descrizione;
    	 
		CarClass(String codice, String descrizione) {
			this.codice = codice;
			this.descrizione = descrizione;
		}
		public String getCodice() {
			return codice;
		}
		public void setCodice(String codice) {
			this.codice = codice;
		}
		public String getDescrizione() {
			return descrizione;
		}
		public void setDescrizione(String descrizione) {
			this.descrizione = descrizione;
		}
     }
}


