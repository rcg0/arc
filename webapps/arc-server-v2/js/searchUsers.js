
/*variable dónde se almacenan los usuarios*/
var users = new Array();
/*de prueba*/
//users[0] = {"id":1,"name":"Javier","surname1":"Rafael","surname2":"Sanchez","author":[],"moderator":[1,2],"permission":0};
//alert(users[0].id);
/*variable dónde se almacenan los usuarios que se piden y que luego se compararán con la variable users para no pintarlo si están repetidos*/
var newUsers = new Array();

var patron;

var botonPulsado;

function sendPatron(boton){

  botonPulsado = boton;
  patron = document.getElementById("patron").value;
  	if(patron != "" || patron != " "){
  		createAJAXRequest("getUsers?patron="+patron,showUserResults, true);
	}
	

	
  alert('Usted presionó el boton "' + boton.id + '"')
  alert(patron);
}


function showUserResults(){

	  if (xhr.readyState==4 && xhr.status==200){
	  		var text = xhr.responseText;
			users = JSON.parse(xhr.responseText);
			//newUsers = JSON.parse(xhr.responseText);
			//alert(newUsers[0].id);
			//comprobar que sólo se almacenen en users los que no existan ya
			//checkUserDoesNotExist();
			//printUsersIhave();
			
			printModerators();
	  }


}
/*
function checkUserDoesNotExist(){

	
	for(var i = 0; i<users.length; i++){
		for(var j=0; j< newUsers.length; j++){

			if(users[i].id == newUsers[j].id){
				alert("el usuario es el mismo, no lo meto en la estructura");
			}
			else{
				users.push(newUsers[j]);//si el usuario no es el mismo lo meto en la estructura user que es la que posteriormente voy a pintar
			}
		}

	}


}

function printUsersIhave(){

	for(var i=0; i<users.length; i++){
		//alert(users[i]);

	}

}
*/
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

	//alert(selectElement);

	//elimino todos los hijos si existen para volver a pintarlos!
	var allOptions = select.getElementsByTagName("option");
	//alert(allOptions.length);
	
	if(allOptions.length != 0){

		//alert(allOptions.length);
		var cte = allOptions.length;//necesito hacer constante esta variable antes de entrar en el loop porque cambia dinámicamente :)
		//alert("la variable contante es:" + cte);
		for(var z = 0; z < cte; z++){//ejecuto cte veces -> eliminar el primer nodo
			select.removeChild(allOptions[0]);
		}

	}

	for(var i =0; i<users.length; i++){
		var newOption = document.createElement("option");
		var newModerator = document.createTextNode(users[i].name + " " +users[i].surname1 + " " +users[i].surname2);
		newOption.setAttribute("value",users[i].id);
		newOption.appendChild(newModerator);
		select.appendChild(newOption);
	}

}

