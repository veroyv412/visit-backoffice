package com.cdrossi

public enum EstadoUsuario {
	ACTIVO("Activo"),
	INACTIVO("Inactivo")
	
	String name
	
	EstadoUsuario(String n)
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
