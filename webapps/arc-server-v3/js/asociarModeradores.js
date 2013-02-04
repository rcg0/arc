

/*Coge todas las opciones seleccionadas (ids de usuarios) del select*/
function loopSelect(select){


  var e = document.getElementById(select);//El selectUsers hay que parametrizarlo
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

var moderatorsSelected = new Array();

function select_moderators(){

    var selectedArray = new Array();
    selectedArray = loopSelect("selectModerators");
    moderatorsSelected= selectedArray;
    //alert(selectedArray[0]);
    alert("Moderadores asociados, no olvide guardar los datos.");
}


var usersSelected = new Array();

function select_users(){

	var selectedArray = new Array();
	
  selectedArray = loopSelect("selectUsers");

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

 
      /*creo un salto de linea*/
    var p = document.createElement("p");   
    e.appendChild(p);
    /**/

    input = document.createElement('input' );
    input.type = "checkbox";
    input.name = usersSelected[i].id;
    input.value = "1";


    e.appendChild(document.createTextNode("Lectura local"));
    e.appendChild(input);


    input = document.createElement('input' );
    input.type = "checkbox";
    input.name = usersSelected[i].id;
    input.value = "2";

    e.appendChild(document.createTextNode("Escritura local"));
    e.appendChild(input);


    input = document.createElement('input' );
    input.type = "checkbox";
    input.name = usersSelected[i].id;
    input.value = "4";

    e.appendChild(document.createTextNode("Lectura remota"));
    e.appendChild(input);


    input = document.createElement('input' );
    input.type = "checkbox";
    input.name = usersSelected[i].id;
    input.value = "8";

    e.appendChild(document.createTextNode("Escritura remota"));
    e.appendChild(input);

    
    //alert("undefined?  : " + usersSelected[i].name);

    //checkbox.id = "id";


  

    e.appendChild(input);
  }

}



function save_data() {

  mitablon = {};

  var id;
  /*Compruebo si existe la variable, si no, */
  if(document.getElementById("messageTablonId") == undefined){

    id = -1;//-1 means you are creating a new Tablon! not mofifying
  }else{
    id = document.getElementById("messageTablonId").text;
  }
  
  var tablon_name = document.getElementById("tablon_name").value;
  //moderators are in variable "moderatorsSelected"

  //users are in variable "usersSelected"
  var k = document.getElementById("form_mis_tablones");
  
  //var permission =
  var numberOfCheckbox = 0;

  var permisoBinary=0;
  var hash = new Array();// [id user, permiso]
  var hashArray = new Array(); // [hash[0], hash[1], ...]

  for(var i=0; i<k.elements.length; i++){

    if(k.elements[i].type == "checkbox"){
      numberOfCheckbox++;
      if(k.elements[i].checked){
        //alert("numberOfCheckbox/4: "+Math.floor(numberOfCheckbox/4));
        permisoBinary = permisoBinary | k.elements[i].value;
        //alert("el permiso en binario es: "+permisoBinary);
        //alert("se ha dado el permiso : "+k.elements[i].value + "al usuario: "+usersSelected[Math.floor(numberOfCheckbox/4)].name);// 1/4 = 0 2/4 = 0 3/4 = 0 5/4 = 1 :)z  
        
        if(numberOfCheckbox % 4 == 0){//hay que comprobar el caso en el que están todos los checkbox pulsados ya que se puede ir a una posición incorrecta del array.
          numberOfCheckbox = numberOfCheckbox - 0.01;
          hash = [usersSelected[Math.floor(numberOfCheckbox/4)].id , permisoBinary];
        }
        hashArray.push(hash);
      } 
    }
  }

  
  mitablon.id = id;
  mitablon.name = tablon_name;


  var url = "sendTablonInformation?tablon_id="+id +"&tablon_name="+tablon_name;
  
  for(var k = 0; k < hashArray.length; k++ ){

    alert(hashArray[k][0]); 

  }
  
    
 
  createAJAXRequest(url, "recargar", false);



  //alert(k);
}
