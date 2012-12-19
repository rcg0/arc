

/*Coge todas las opciones seleccionadas (ids de usuarios) del select*/
function loopSelect(){


  var e = document.getElementById("selectUsers");//El selectUsers hay que parametrizarlo
  var selectedArray = new Array();
  var count = 0;
  var i;

  for (i=0; i<e.options.length; i++) {
    if (e.options[i].selected) {
      selectedArray[count] = e.options[i].value;
      alert(e.options[i].value);
      count++;
      
    }
  }

  return selectedArray;//array de ids de usuario que están seleccionados

}


function select_moderators(){

    var selectedArray = new Array();
    selectedArray = loopSelect();
    alert("Moderadores asociados, no olvide guardar los datos.");
}




function select_users(){

	var selectedArray = new Array();
  var usersSelected = new Array();
	
  selectedArray = loopSelect();

  //alert(selectedArray[0]);

  usersSelected = returnUser(selectedArray);
  
  //alert(usersSelected[0].name);
  //alert(usersSelected[1].name);
  //  alert(usersSelected);

  asociarPermisoAUsuario(usersSelected);
	

}

/*dado un vector "id" de usuario devuelve los usuarios almacenados*/
function returnUser(id){

  var result = new Array();
  


  for(var j=0; j<id.length; j++){
    for(var i = 0; i < users.length; i++ ){
    
      if (users[i].id == id[j]){
        result[j] = users[i];
        alert(j++);
      }
    }
  }
  return result;
}

/*crea los checkbox referente a cada usuario seleccionado*/
function asociarPermisoAUsuario(usersSelected){

  var form = document.getElementById("form_mis_tablones");
  var e;
  var nombre;
  var text;
  var input;

  //alert(document.getElementById("paraeliminar"));

  if(document.getElementById("paraeliminar") != null){//si existe

    form.removeChild(document.getElementById("paraeliminar"));
  }

/*creo un div dinámicamente, estoy me favorece mucho a la hora de eliminar después todos sus nodos para reescribirlos*/
  e = document.createElement("div");
  e.id = "paraeliminar";
  form.appendChild(e);
 

 /*creo un salto de linea*/
    var p = document.createElement("p");   
    e.appendChild(p);
    /**/

  /*crea la cadena:  Asociar permiso a usuario:*/
  var p = document.createElement("p"); 
  var b = document.createElement("b");
  b.name= "eliminar";
  var asociarCadena = document.createTextNode("Asociar permiso a usuario:");
  b.appendChild(asociarCadena);
  e.appendChild(b);


  for(var i = 0; i<usersSelected.length; i++){

    /*creo un salto de linea*/
    var p = document.createElement("p");   
    p.name = "eliminar";
    e.appendChild(p);
    /**/


    nombre = document.createTextNode(usersSelected[i].name + " " + usersSelected[i].surname1 + " " +  usersSelected[i].surname2);
    e.appendChild(nombre);

    input = document.createElement('input' );
    input.type = "checkbox";
    //input.name = ;
    //input.value = ;

      /*creo un salto de linea*/
    var p = document.createElement("p");   
    e.appendChild(p);
    /**/

    e.appendChild(document.createTextNode("Lectura local"));
    e.appendChild(input);


    input = document.createElement('input' );
    input.type = "checkbox";

    e.appendChild(document.createTextNode("Escritura local"));
    e.appendChild(input);


    input = document.createElement('input' );
    input.type = "checkbox";

    e.appendChild(document.createTextNode("Lectura remota"));
    e.appendChild(input);


    input = document.createElement('input' );
    input.type = "checkbox";

    e.appendChild(document.createTextNode("Escritura remota"));
    e.appendChild(input);

    
    //alert("undefined?  : " + usersSelected[i].name);

    //checkbox.id = "id";


  

    e.appendChild(input);
  }

}


