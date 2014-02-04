package com.cdrossi

public enum EstadoMedico {
	ACTIVO ("Activo"),
	INACTIVO ("Inactivo")
	
	String name
	
	EstadoMedico(String n)
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
	   [ACTIVO, INACTIVO]
   }
}
