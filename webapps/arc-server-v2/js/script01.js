  

function makeDoubleDelegate(function1, function2) {
  return function() {
  if (function1)
    function1();
  if (function2)
    function2();
  }
}


window.onload = makeDoubleDelegate(window.onload, obtainTablonIdAndNames);
window.onload = makeDoubleDelegate(window.onload, obtainTablon);


/*variables globales*/
var xhr;
//var url="getTablonNames";
var tablonid; //importante, esta variable es un array de ids de tablon, la voy a utilizar para ponerlos en el href de las etiquetas a del menú
var tablones = new Array(); //array de tablones.


function obtainTablonIdAndNames(){
  createAJAXRequest("getTablonNames",showContents, false);
}


function createXMLHttpRequest(){


  if(window.XMLHttpRequest){
     xhr= new XMLHttpRequest();
  }
  else{
         xhr =new ActiveXObject("Microsoft.XMLHTTP");
  }
  if(xhr){ 
    var timestamp = Math.random(); // Aleatorio por Math()
    xhr.onreadystatechange=showContents;
    xhr.open("GET",url+"?time="+timestamp, false); //true (asynchronous) or false (synchronous)
    xhr.send(null);
  }
}

function makeRequest(){
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
      var newUl = document.createElement("ul");

      for(i =0; i<nombreTablones.length; i++){
        text = document.createTextNode(nombreTablones[i]);
        newA = document.createElement("a");
        newA.href="getTablon?tablonId="+tablonid[i];
        if(i==0){//se marcará la primera por defecto
        newA.className="current";
        }
        newA.appendChild(text);  
        newLi = document.createElement("li");
        newLi.appendChild(newA);
        newUl.appendChild(newLi);
        nav.appendChild(newUl);
      }

  }


  function obtainTablon(){

      if(tablones[0].id != tablonid[0]){ //si lo tengo no lo pido
       var url= "getTablon?tablonId="+tablonid[0];
      createAJAXRequest(url,createTablon, true);//url a la que voy a hacer la petición y 
    }
  }

  function createAJAXRequest(url, callback, mode){

    if(window.XMLHttpRequest){
       xhr= new XMLHttpRequest();
    }
    else{
       xhr =new ActiveXObject("Microsoft.XMLHTTP");
    }
    if(xhr){ 
      var timestamp = Math.random(); // Aleatorio por Math()
      xhr.onreadystatechange=callback;
      xhr.open("GET", url, mode);

      //xhr.open("GET",url+"?time="+timestamp, mode); //true (asynchronous) or false (synchronous)
      xhr.send();//error aquí
    }
  }


  function createTablon(){

    if (xhr.readyState==4 && xhr.status==200){
      alert(xhr.responseText);
      tablones.push(JSON.parse(xhr.responseText));
    }
    
  }
