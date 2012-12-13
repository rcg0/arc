
/*variable donde se almacenan los usuarios*/
var users = new Array();

var patron;

var botonPulsado;

function sendPatron(boton){

  botonPulsado = boton;
  patron = document.getElementById("patron").value;
  	if(patron != "" || patron != " "){
  		createAJAXRequest("getUsers?patron="+patron,showUserResults, true);
	}
	

	
	//alert('Usted presionó el boton "' + boton.id + '"')
  //alert(patron);
}


function showUserResults(){

	  if (xhr.readyState==4 && xhr.status==200){
	  		var text = xhr.responseText;
			
			users = JSON.parse(xhr.responseText);
			//alert(users[1].surname1);
			printModerators();
	  }


}

function printModerators(){


	var selectElement = new String();
	var boton = new String(botonPulsado.id);

	var busqueda1 = new String("busqueda1");
	var busqueda2 = new String("busqueda2");

	if(busqueda1.localeCompare(boton) == 0){
		
		selectElement = "selectModerators";

	}else if(busqueda2.localeCompare(boton) == 0){

		selectElement = "selectUsers";

	}


	var select = document.getElementById(selectElement);

	alert(selectElement);

	for(var i =0; i<users.length; i++){
		var newOption = document.createElement("option");
		var newModerator = document.createTextNode(users[i].name + " " +users[i].surname1 + " " +users[i].surname2);
		newOption.appendChild(newModerator);
		select.appendChild(newOption);
	}

}