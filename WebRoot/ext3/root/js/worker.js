// Copyright 2007, Google Inc.
//
// Redistribution and use in source and binary forms, with or without 
// modification, are permitted provided that the following conditions are met:
//
//  1. Redistributions of source code must retain the above copyright notice, 
//     this list of conditions and the following disclaimer.
//  2. Redistributions in binary form must reproduce the above copyright notice,
//     this list of conditions and the following disclaimer in the documentation
//     and/or other materials provided with the distribution.
//  3. Neither the name of Google Inc. nor the names of its contributors may be
//     used to endorse or promote products derived from this software without
//     specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR IMPLIED
// WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO
// EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, 
// SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
// PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
// OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
// WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR 
// OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
// ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

google.gears.workerPool.onmessage = function(messageText, senderId, message) {
  google.gears.workerPool.sendMessage(identity(message.body), senderId);
};

var db1;
function identity(n) {
  var count=0;
  var result = 0;
  var rev="";
  
    try {
      db1 = google.gears.factory.create('beta.database', '1.0');
      if (db1) {
        db1.open('database-bpm');
      }
    } catch (ex) {
       return "create db fail"+ex.message;
    }
    
  var mc="1";
  var bm = "2";
    var gg = "3";
      var jx = "4";
    var py = "5";
      var dw = "6";
    var json= eval("(" + n + ")");

    //alert(json[0].mc+json[0].gg+json[0].bm+json[0].jx+json[0].dw+json[0].py);
     var drugscount=0;
        try{
      var temp=json[0].mc;
     db1.execute("BEGIN");
 
    if (json[0].mc+""!="undefined"){
   try{
  	
  	 for (drugscount=0;drugscount<json.length-1;drugscount++){
  	
       db1.execute('insert into drugsTemp values (?, ?,?,?,?,?)', [json[drugscount].mc,json[drugscount].bm,json[drugscount].gg,json[drugscount].jx,json[drugscount].py,json[drugscount].dw]);    
    
      }
      
      rev = "drugs";
    }catch(ex){
    	
     	rev="drugs:"+ ex.message;
    }
  }else{
     try{
    	for (drugscount=0;drugscount<json.length-1;drugscount++){
  	
       db1.execute('insert into drugsHospitalTemp values (?, ?,?)', [json[drugscount].yy,json[drugscount].bm,json[drugscount].sm]);    
    
      }
      
      
       rev = "mydrugs";
     }catch(ex){
      	
      	rev="mydrugs:"+ ex.message;
      }
    }  
    db1.execute("COMMIT");
    }catch(fail){
    	  rev = "mydrugs:"+fail.message;
    }
    return rev;
}