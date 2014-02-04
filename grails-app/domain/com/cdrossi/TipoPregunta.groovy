package com.cdrossi

public enum TipoPregunta {
	MULTIPLE_CHOICE ( "Multiple Choice" ),
	SINGLE_CHOICE ( "Single Choice" )
	
	String name
	
	TipoPregunta(String n)
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
	   [MULTIPLE_CHOICE, SINGLE_CHOICE]
   }
}
