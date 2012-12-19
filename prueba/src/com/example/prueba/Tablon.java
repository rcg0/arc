package com.example.prueba;

import java.util.Vector;

public class Tablon {

	String id;
	Vector <Mensaje> msg = new Vector<Mensaje>(); 
	
	public void setId(String id){
		
		this.id = id;
		
	}
	
	public String getId (){
		
		return id;
	}
	
	public void setMsg(Mensaje mensaje){
		
		msg.addElement(mensaje);
		
		
	}
	
	public Vector<Mensaje> getMsg(){		
		
		return 	msg;
		
	}
	
}
