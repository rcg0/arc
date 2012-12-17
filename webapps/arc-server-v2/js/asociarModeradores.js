

/*Coge todas las opciones seleccionadas (ids de usuarios) del select*/
function loopSelect(){


  var e = document.getElementById("selectUsers");
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

  usersSelected = returnUser(selectedArray);

  asociarPermisoAUsuario(usersSelected);
	

}


/*crea los checkbox referente a cada usuario seleccionado*/
function asociarPermisoAUsuario(usersSelected){



  for(var i = 0; i<usersSelected.length; i++){

    document.createTextNode(usersSelected[i].name);

    var checkbox = document.createElement('input' );
    checkbox.type = "checkbox";
    checkbox.name = usersSelected[i].name + " " + usersSelected[i].surname1 + " " +usersSelected[i].surname2;
    checkbox.value = usersSelected[i].id;
    //checkbox.id = "id";

  }


}


/*dado un vector id de usuario devuelve los usuarios almacenado*/
function returnUser(id){

  var result;

  for(var i = 0; i < users.length; i++ ){
    for(var j=0; j<i.length; j++){

      if (users[i].id == id){
        result = users[i];
      }
    }
  }
  return result;
}