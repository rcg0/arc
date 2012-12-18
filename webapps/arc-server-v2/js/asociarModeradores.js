

/*Coge todas las opciones seleccionadas (ids de usuarios) del select*/
function loopSelect(){


  var e = document.getElementById("selectUsers");//El selectUsers hay que parametrizarlo
  var selectedArray = new Array();
  var count = 0;
  var i;

  for (i=0; i<e.options.length; i++) {
    if (e.options[i].selected) {
      selectedArray[count] = e.options[i].value;
      //alert(e.options[i].value);
      count++;
      
    }
  }

  return selectedArray;//array de ids de usuario que están seleccionados

}


function select_moderators(){

    var selectedArray = new Array();
    selectedArray = loopSelect();
}




function select_users(){

	var selectedArray = new Array();
  var usersSelected = new Array();
	
  selectedArray = loopSelect();

  alert(selectedArray[0]);

  usersSelected = returnUser(selectedArray);
  
  alert(usersSelected[0].name);

  //  alert(usersSelected);

  asociarPermisoAUsuario(usersSelected);
	

}

/*dado un vector "id" de usuario devuelve los usuarios almacenados*/
function returnUser(id){

  var result = new Array();

  for(var i = 0; i < users.length; i++ ){
    for(var j=0; j<id.length; j++){

      if (users[i].id == id){
        result[i] = users[j];
      }
    }
  }
  return result;
}

/*crea los checkbox referente a cada usuario seleccionado*/
function asociarPermisoAUsuario(usersSelected){

  var e = document.getElementById("form_mis_tablones");
  var nombre;
  var text;
  var input;

 /*creo un salto de linea*/
    var p = document.createElement("p");   
    e.appendChild(p);
    /**/

  /*crea la cadena:  Asociar permiso a usuario:*/
  var p = document.createElement("p"); 

  e.appendChild(document.createTextNode("Asociar permiso a usuario:"));

  for(var i = 0; i<usersSelected.length; i++){

    /*creo un salto de linea*/
    var p = document.createElement("p");   
    e.appendChild(p);
    /**/


    nombre = document.createTextNode(usersSelected[i].name + " " + usersSelected[i].surname1 + " " +  usersSelected[i].surname2);
    e.appendChild(nombre);

    input = document.createElement('input' );
    input.type = "checkbox";
    //input.name = ;
    //input.value = ;

    e.appendChild(document.createTextNode("Lectura local"));

    
    //alert("undefined?  : " + usersSelected[i].name);

    //checkbox.id = "id";


    /*creo un salto de linea*/
    var p = document.createElement("p");   
    e.appendChild(p);
    /**/

    e.appendChild(input);
  }

}


