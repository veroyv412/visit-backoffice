package com.cdrossi

public enum Sexo {
	FEMENINO ("Femenino"),
	MASCULINO ("Masculino")
	
	String name
	
	Sexo(String n)
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
	   [FEMENINO, MASCULINO]
   }
}
