window.onload = makeRequest;
   
var xhr; 
var url="getTablonNames";
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
    tablonIdx = tablonId.replace("[","");
    tablonIdxx = tablonIdx.replace("]","");
    var tablonid = tablonIdxx.split(",")
    
    //alert(tablonId);
    //document.write(tablonNames);
    //document.getElementById("").innerHTML=xhr.responseText;
    //creo los nodos
    //var text = "prueba"; //document.createTextNode(tablonNames);
    var newText = document.createTextNode(tablonid[1]);
    var newLi = document.createElement("li");
    newLi.appendChild(newText);
    var newUl = document.createElement("ul");
    newUl.appendChild(newLi);
    var nav = document.getElementById("tablon_name");
    //añado los nodos correspondientes
    nav.appendChild(newUl);
    
    }
  }
