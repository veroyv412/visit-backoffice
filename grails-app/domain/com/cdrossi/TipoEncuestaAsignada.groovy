package com.cdrossi

public enum TipoEncuestaAsignada {
	ENCUESTADA ("Encuestada"),
	CANCELADA ("Cancelada"),
	PENDIENTE ("Pendiente")
	
	String name
	
	TipoEncuestaAsignada(String n)
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
	   [ENCUESTADA, CANCELADA, PENDIENTE]
   }
}
