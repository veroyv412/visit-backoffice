package com.cdrossi

public enum TipoTelefono {
	CELULAR ("Celular"),
	CONSULTORIO ("Consultorio"),
	PARTICULAR ("Particular")
	
	String name
	
	TipoTelefono(String n)
	{
		this.name = n
	}
	
	String toString() {
		this.name
	}
  
   String getKey() {
	   name()
   }
  
   static list(){
	   [CELULAR, CONSULTORIO, PARTICULAR]
   }
}
