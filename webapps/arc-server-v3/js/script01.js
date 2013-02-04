  

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

    if(text != ""){

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
      //var timestamp = Math.random(); // Aleatorio por Math()
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




  function getMoreMessages(){//el messageId lo obtengo del menor id de los div que ha mostrado el html
    
    var divs = document.getElementById("left").getElementsByTagName("div");//todos los divs de la section left
    var url="getMessages?messageId="+divs[0].id;
    createAJAXRequest(url, printMessages, true);

  }

  function printMessages(){
      var Tablon;


      if (xhr.readyState==4 && xhr.status==200){

        
        tablon =  JSON.parse(xhr.responseText);
       

        for(var i=0; i<tablon.msg.length; i++){

          createDivMessage(tablon.msg[i]);

        }
      }

  }
  

    function insertAfter(e,i){ //i -> nodo a insertar, e->nodo tras el que se va a insertar
        if(e.nextSibling){ 
            e.parentNode.insertBefore(i,e.nextSibling); 
        } else { 
            e.parentNode.appendChild(i); 
        }
    }

  function createDivMessage(msg){

    var father = document.getElementById("left");
    var firstNode = document.getElementById("moreMessages");

    var espacio = document.createElement("p");
    
    insertAfter(firstNode, espacio);//inserta un espacio

    //father.insertBefore(espacio, father.firstChild);
    
    var text=document.createTextNode(msg.msg);
    //alert(msg.msg);
    var autor = document.createTextNode(msg.creator.name + " " + msg.creator.surname1 + " " + msg.creator.surname2 );
    var nuevoDiv = document.createElement("div");
    

    var p = document.createElement("p");
    var p2 = document.createElement("p");
    var b = document.createElement("b");
    b.appendChild(autor);
    p2.appendChild(text);
    p.appendChild(b);
    var a = document.createElement("a");
    var img = document.createElement("img");
    img.src = "IMG/delete.jpg";
    img.setAttribute("onClick","removeMessage("+msg.id+")");
    img.className = "delete";

    //img.onClick = "removeMessage("+msg.id+")";
    a.appendChild(img);
    nuevoDiv.appendChild(p);
    nuevoDiv.appendChild(p2);
    nuevoDiv.appendChild(a);
    //father.insertBefore(nuevoDiv, firstNode);//tengo que insertar antes del segundo nodo, la imagen es el primer nodo!
    nuevoDiv.id=msg.id;
    nuevoDiv.className="mensaje";

    insertAfter(espacio,nuevoDiv);
    //alert(" id: "+ msg.id+ " mensaje: "+ msg.msg + " autor: " + msg.creator.name);
    insertAfter(nuevoDiv, espacio);//inserta un espacio


  }
  

  var messageID;

  function removeMessage(messageId){
    messageID = messageId;
    var url="deleteMessage?messageId="+messageId;
    createAJAXRequest(url, removeDivMessage, true);
    

    //alert(url);
  }

  function removeDivMessage(){

      var x = document.getElementById(messageID);
      //alert("se ejecuta");
      x.parentNode.removeChild(x);

  }


  function sendMessage() {


    var form = document.getElementById("form_envio");


    var tablon = document.getElementById("tablonId").text;
    form.tablonId.value = tablon;
    //alert(tablon);
    form.action = "sendMessage";
    form.submit();
    
    //alert();
    
  }
  

function removeTablon(){

    var form = document.getElementById("form_delete");

    var tablon = document.getElementById("messageTablonId");

    var id = tablon.textContent;
    
    form.action = "deleteTablon?tablonId="+id;

    form.submit();

  }


  /*asigna clase opaca al elemento*/

  function setOpaque (id) {

    /*primero reseteo */
    document.getElementById("label_privado").className ="notopaque";
    document.getElementById("label_publico").className ="notopaque";
    document.getElementById("label_grupo").className ="notopaque";
    /*cojo la referencial al label y le cambio la clase a opaca*/
    e = document.getElementById(id);
    e.className= "opaque";
    
  }

  function utf8_decode (str_data) {

  var tmp_arr = [],
    i = 0,
    ac = 0,
    c1 = 0,
    c2 = 0,
    c3 = 0;

  str_data += '';

  while (i < str_data.length) {
    c1 = str_data.charCodeAt(i);
    if (c1 < 128) {
      tmp_arr[ac++] = String.fromCharCode(c1);
      i++;
    } else if (c1 > 191 && c1 < 224) {
      c2 = str_data.charCodeAt(i + 1);
      tmp_arr[ac++] = String.fromCharCode(((c1 & 31) << 6) | (c2 & 63));
      i += 2;
    } else {
      c2 = str_data.charCodeAt(i + 1);
      c3 = str_data.charCodeAt(i + 2);
      tmp_arr[ac++] = String.fromCharCode(((c1 & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
      i += 3;
    }
  }

  return tmp_arr.join('');
}