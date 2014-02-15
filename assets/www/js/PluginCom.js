window.numEstablecimientos = function(callback) {	
 	console.log("about to execute getNumEstablecimientos");
  	cordova.exec(
    	callback,
    	function(err) { console.log(err);  alert('error : ' + JSON.stringify(err)); },
    	"JPluginCom",
    	"getNumEstablecimientos",
    	[]
  	);
};

window.getEstablecimientos = function(init, numRows, callback) {	
 	  console.log("about to execute getEstablecimientos");
  	cordova.exec(
    	callback,
    	function(err) { console.log(err);  alert('error : ' + JSON.stringify(err)); },
    	"JPluginCom",
    	"getEstablecimientos",
    	[init, numRows]
  	);
};

window.getMatriculaHistorica = function(codigoUDI, callback) {  
    console.log("about to execute getMatriculaHistorica");
    cordova.exec(
      callback,
      function(err) { console.log(err);  alert('error : ' + JSON.stringify(err)); },
      "JPluginCom",
      "getMatriculaHistorica",
      [codigoUDI]
    );
};


window.getEstablecimientoInfo = function(codigoUDI, callback) {  
    console.log("about to execute getEstablecimientoInfo");
    cordova.exec(
      callback,
      function(err) { console.log(err);  alert('error : ' + JSON.stringify(err)); },
      "JPluginCom",
      "getEstablecimientoInfo",
      [codigoUDI]
    );
};

