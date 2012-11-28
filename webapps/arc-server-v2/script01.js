window.onload = makeRequest("getTablonNames");



function makeRequest(url){ 
  
  if(window.XMLHttpRequest){
    xhr= new XMLHttpRequest();
  }
  else{
    if(window.ActiveXObject){
      try{
	xhr =new ActiveXObject("Microsoft.XMLHTTP")
      }
    }
  }
  
  if(xhr){
   
    xhr.open("GET",url, true); /* true (asynchronous) or false (synchronous)*/
    xhr.send(null);
    
  }
  
}


xhr.onreadystatechange=function(){
  if (xhr.readyState==4 && xhr.status==200){
    //document.getElementById("myDiv").innerHTML=xhr.responseText;

    }
  }
  