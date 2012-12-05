  

function makeDoubleDelegate(function1, function2) {
  return function() {
  if (function1)
    function1();
  if (function2)
    function2();
  }
}


window.onload = makeDoubleDelegate(window.onload, obtainTablonIdAndNames);
//window.onload = makeDoubleDelegate(window.onload, obtainTablon);


/*variables globales*/
var xhr;
//var url="getTablonNames";
var tablonid= null; //importante, esta variable es un array de ids de tablon, la voy a utilizar para ponerlos en el href de las etiquetas a del menú
var tablones = new Array(); //array de tablones.


function obtainTablonIdAndNames(){
  //alert(window.tablonid);
  if(window.tablonid==null){
  createAJAXRequest("getTablonNames",showContents, false);
  }
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
    creaMenu(tablones,"submenu_tablones","showModifyTablones");//el array de nombre de tablones y el id del ul donde quiero meterlo y la url a donde voy
    creaMenu(tablones,"submenu_mensajes", "getTablones");
    }
  }


  function creaMenu(nombreTablones, submenu, url){
      var i;
      var newLi;
      var newA;
      var ul = document.getElementById(submenu);


      for(i =0; i<nombreTablones.length; i++){
        text = document.createTextNode(nombreTablones[i]);
        newA = document.createElement("a");
        newA.href=url+"?tablonId="+tablonid[i];
        //newA.setAttribute("onclick", "javascript:obtainTablon("+tablonid[i]+")");
        newA.appendChild(text);  
        newLi = document.createElement("li");
        newLi.appendChild(newA);
        ul.appendChild(newLi);
        
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
      //createAJAXRequest("mis-tablones.jsp",x, true);
    }
    
  }
