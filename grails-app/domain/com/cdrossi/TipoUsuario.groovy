package com.cdrossi

public enum TipoUsuario {
	VISITADOR ("Visitador"),
	SUPERVISOR ("Supervisor")
	
	String name
	
	TipoUsuario(String n)
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
	   [VISITADOR, SUPERVISOR]
   }
}
