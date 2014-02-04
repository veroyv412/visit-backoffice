package com.cdrossi

public enum EstadoEncuesta {
	ACTIVO ("Activa"),
	NO_ACTIVO ("No Activa"),
	BORRADOR ("Borrador"),
	FINALIZADA ("Finalizada"),
	CANCELADA ("Cancelada")
	
	String name
	
	EstadoEncuesta(String n)
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
	   [ACTIVO, NO_ACTIVO, BORRADOR, FINALIZADA, CANCELADA]
   }
	
}
