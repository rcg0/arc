window.onload = makeRequest;
   
var xhr; 
var url="getTablonNames";
var tablonid; //importante, esta variable es un array de ids de tablon, la voy a utilizar para ponerlos en el href de las etiquetas a del menú

function createXMLHttpRequest(){
  if(window.XMLHttpRequest){
     xhr= new XMLHttpRequest();
  }
    /*else{
         xhr =new ActiveXObject("Microsoft.XMLHTTP");
    }*/
  if(xhr){ 
    var timestamp = Math.random(); // Aleatorio por Math()
    xhr.onreadystatechange=showContents;
    xhr.open("GET",url+"?time="+timestamp, true); //true (asynchronous) or false (synchronous)
  //  xhr.setRequestHeader( "If-Modified-Since", "Sat, 1 Jan 2000 00:00:00 GMT" );

    xhr.send(null);
  }
  }

function makeRequest(){
 // url= "getTablonNames";
  createXMLHttpRequest();  
}
  
function showContents(){

  if (xhr.readyState==4 && xhr.status==200){

    var text = xhr.responseText;
    pos = text.indexOf("]");
    var tablonId = text.substring(0,pos+1);
    var tablonNames= text.substring(pos+1,text.length);
    tablonIdx = tablonId.replace("[","").replace("]","");
    tablonid = tablonIdx.split(",");


    for(var i=0;i<tablonNames.length;i++){//tengo que quitar las "" y eso a cada elemento
      var tablonNames = tablonNames.replace("[","").replace("]","").replace("\"","").replace("\"","");
    }    

    var tablones = tablonNames.split(",");
    if(tablones == undefined){//es undefined si tiene un elemento

      tablones= tablonNames;

    }
    creaMenu(tablones);   
    
    }
  }


  function creaMenu(nombreTablones){
      var i;
      var newLi;
      var newA;
      var newUl;
      var nav = document.getElementById("tablon_name");

      for(i =0; i<nombreTablones.length; i++){
        text = document.createTextNode(nombreTablones[0]);
        newA = document.createElement("a");
        newA.href="getTablon?id="+tablonid[i];
        if(i==0){//se marcará la primera por defecto
        newA.className="current";
        }
        newA.appendChild(text);
        
        newLi = document.createElement("li");
        newLi.appendChild(newA);
        newUl = document.createElement("ul");
        newUl.appendChild(newLi);
        nav.appendChild(newUl);
      }

  }
