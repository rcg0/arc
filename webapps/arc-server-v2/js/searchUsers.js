
/*variable donde se almacenan los usuarios*/
var users = new Array();

var patron;
/*Este método se ejecuta una vez */
function sendPatron(){

	//window.setTimeout(afterWeWait, 1500);
	afterWeWait();
}



function afterWeWait(){

  patron = document.getElementById("patron").value;
  	if(patron != "" || patron != " "){
  		createAJAXRequest("getUsers?patron="+patron,showUserResults, true);
	}
  //alert(patron);
}


function showUserResults(){

	  if (xhr.readyState==4 && xhr.status==200){
	  		var text = xhr.responseText;
			
			users.push(JSON.parse(xhr.responseText));
			alert(users.name);

	  }


}