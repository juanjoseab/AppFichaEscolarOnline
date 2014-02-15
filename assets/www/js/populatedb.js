var db;
var numEst;
db = window.openDatabase("fichaescolar", "1.0", "Ficha Escolar", 100000000);
$(document).ready(function() {
    var nEst = 0;
    var nMatricula = 0;
    var responseEstablecimientos = "";
    var mensaje = $("#mensaje");
    var currentRow;
    document.addEventListener("deviceready", onDeviceReady, false);
    function onDeviceReady() {        
                       
        var dbo = window.openDatabase("fichaescolar", "1.0", "Ficha Escolar", 100000000); 
        dbo.transaction(createTableEstablecimiento,
        function(e) {
            console.log(e);
            mensaje.append('<li><span class="btn btn-xs"><i class="glyphicon glyphicon-remove"></i></span><span class="text-danger">Error al sincrinizar la tabla de establecimientos <strong>Error: </strong>' + e.code + ' - ' + e.message + ' </span></li>');
            mensaje.after('<a href="./dbsinc.html" class="btn btn-info btn-block"> intentar de nuevo </a>');
        },
        function(tx) {            
            mensaje.append('<li><span class="btn btn-xs"><i class="glyphicon glyphicon-info-sign"></i></span><span class="text-info">sincronizando tabla establecimientos <strong id="estporc">0</strong></span></li>');
        });

        
        window.numEstablecimientos(function(echoValue) {
            nEst = echoValue;
            nEst = nEst * 1;
            //alert(nEsta);
        });
        var roundsOf = 1000
        var ciclos = Math.ceil( nEst  / roundsOf );
        var totalEst = nEst;

        //ciclos = 90; 
        var flagInit = 47000;
        for(var i = 0; i < ciclos; i++){
            console.log("iteracion No. : " + i);
            if(nEst < roundsOf){
                    window.getEstablecimientos(flagInit,nEst,function(values){
                        //console.log(values);
                        responseEstablecimientos = values;
                        var sql = responseEstablecimientos.split("||");
                        for(var i = 0; i < sql.length ; i++){
                            currentRow = sql[i];
                            //

                            dbo.transaction(populateTablaEstablecimiento,
                                function(e) {
                                    console.log(e);
                                    mensaje.append('<li><span class="btn btn-xs"><i class="glyphicon glyphicon-remove"></i></span><span class="text-danger">Error al sincrinizar la tabla de establecimientos <strong>Error: </strong>' + e.code + ' - ' + e.message + ' </span></li>');
                                    mensaje.after('<a href="./dbsinc.html" class="btn btn-info btn-block"> intentar de nuevo </a>');
                                },
                                function(tx) {
                                    var procentajeEst = flagInit / totalEst *  100;
                                    $('#estporc').text(Math.ceil(procentajeEst) + "%");
                                });
                        }
                        

                    });
                    flagInit = flagInit + nEst;
                    nEst = 0;
                }else{
                    window.getEstablecimientos(flagInit,roundsOf,function(values){
                        //console.log(values);
                        responseEstablecimientos = values;
                        var sql = responseEstablecimientos.split("||");
                        for(var i = 0; i < sql.length ; i++){
                            currentRow = sql[i];
                            //console.log("TX : " + currentRow);
                            
                            dbo.transaction(populateTablaEstablecimiento,
                                function(e) {
                                    console.log(e);
                                    mensaje.append('<li><span class="btn btn-xs"><i class="glyphicon glyphicon-remove"></i></span><span class="text-danger">Error al sincrinizar la tabla de establecimientos <strong>Error: </strong>' + e.code + ' - ' + e.message + ' </span></li>');
                                    mensaje.after('<a href="./dbsinc.html" class="btn btn-info btn-block"> intentar de nuevo </a>');
                                },
                                function(tx) {
                                    var procentajeEst = flagInit / totalEst *  100;
                                    $('#estporc').text(Math.ceil(procentajeEst) + "%");
                                });
                        }
                    });                    
                    flagInit = flagInit + roundsOf;
                    nEst = nEst - roundsOf;

                    console.log("TX : " + currentRow);
                }

                console.log("Datos restantes : " + nEst);

                //estResult
                
                //console.log(nEst);
        }


        /*
            window.getEstablecimientos('0', '5000', function(echoValue) {
            alert(echoValue);
            //console.log('DATA : ' + echoValue);
            //var json = jQuery.parseJSON(echoValue);
            //alert(json.CodigoUDI.length);
            //var estList = echoValue;
            //estFlag = estFlag + 5000;
        });*/
        return false;
        
        //populateTableEstablecimiento();

    }



    function activeLoading() {
        var width = $(window).width();
        var height = $(window).height();
        $("body").append('<div class="loadingBox"></div>');
        $(".loadingBox").css("width", width + "px").css('height', height + "px");
    }
    ;

    function deactiveLoading() {
        $(".loadingBox").remove();
    }
    $("body").on("click", "a.principalButton", function() {
        activeLoading();
        var href = $(this).attr("href");
        window.location = href;
    });

    /*function onDeviceReady() {
     
     db.transaction(checkDB, errorCB, successCB);
     }*/

    function successCB() {
        deactiveLoading();
    }


    function queryDB(tx) {
        var code = $("input#establecimientoCodigo").val();
        tx.executeSql('SELECT * FROM establecimiento', [], querySuccess, errorCB);
    }

    function checkDB(tx) {
        var db1 = window.openDatabase("fichaescolar", "1.0", "Ficha Escolar", 100000000);
        db1.transaction(populateTableEstablecimiento,
                function(e) {
                    console.log(e);
                    mensaje.append('<li><span class="btn btn-xs"><i class="glyphicon glyphicon-remove"></i></span><span class="text-danger">Error al sincrinizar la tabla de establecimientos <strong>Error: </strong>' + e.code + ' - ' + e.message + ' </span></li>');
                    mensaje.after('<a href="./dbsinc.html" class="btn btn-info btn-block"> intentar de nuevo </a>');
                },
                function(tx) {
                    mensaje.append('<li><span class="btn btn-xs"><i class="glyphicon glyphicon-ok"></i></span><span class="text-success">Tabla establecimientos sincronizada</span></li>');
                });
        db1.transaction(populatetablaMatricula,
                function(e) {
                    mensaje.append('<li><span class="btn btn-xs"><i class="glyphicon glyphicon-remove"></i></span><span class="text-danger">Error al sincrinizar la tabla de matricula historica <strong>Error: </strong>' + e.code + ' - ' + e.message + ' </span></li>');
                    mensaje.after('<a href="./dbsinc.html" class="btn btn-info btn-block"> intentar de nuevo </a>');
                    mensaje.after('');
                },
                function(tx) {
                    mensaje.append('<li><span class="text-success"><span class="btn btn-xs"><i class="glyphicon glyphicon-ok"></i></span>Tabla de matricula historica sincronizada</span></li>');
                });
        db1.transaction(populatetablaConfig,
                function(e) {
                    console.log(tx);
                    mensaje.append('<li><span class="btn btn-xs"><i class="glyphicon glyphicon-remove"></i></span><span class="text-danger">Error al sincrinizar la tabla de configuracion <strong>Error: </strong>' + e.code + ' - ' + e.message + ' </span></li>');
                    mensaje.after('<a href="./dbsinc.html" class="btn btn-info btn-block"> intentar de nuevo </a>');
                    mensaje.after('');
                },
                function(tx) {
                    mensaje.append('<li><span class="btn btn-xs"><i class="glyphicon glyphicon-ok"></i></span><span class="text-success">Tabla de configuración historica sincronizada</span></li>');
                    mensaje.after('<a href="./main.html" role="link" class="btn btn-success btn-block"> Ir al inicio </a>');
                    $("#loadingImage").remove();
                    //window.location = "./main.html";
                });

    }


    function errorCB(tx, err) {
        mensaje.append('<li><span class="text-danger">error al procesar el sql ' + err.code + ': ' + err.message + ' </span></li>');
    }

    function createTableEstablecimiento(tx) {
        tx.executeSql('DROP TABLE IF EXISTS establecimiento ');
        tx.executeSql("CREATE TABLE IF NOT EXISTS  establecimiento ( NombreSectorActual, NombrePlan, NombreAreaActual, NombreTipo, Telefono, NombreCiclo, CorreoElectronico," +
                "NombreJornada, NombreRegion, NombreDepartamento, Longitud, SituacionActual, CodigoUDI, NombreNivel, NombreMunicipio, CORREO_ELECTRONICO_SUP, Latitud," +
                "DIRECTOR, NombreCategoria, NombreModalidad, TEL_SEDE, CantidadDocentes, Direccion, NombreEstablecimiento, NombreSupervisor, DIR_SEDE  );");
    }

    function populateTablaEstablecimiento(tx) {
        //alert(responseEstablecimientos);/*
        
        var insertSentence  = "INSERT INTO establecimiento (NombreSectorActual, NombrePlan, NombreAreaActual, NombreTipo, Telefono, NombreCiclo, CorreoElectronico, NombreJornada, NombreRegion," +
                "NombreDepartamento, Longitud, SituacionActual, CodigoUDI, NombreNivel, NombreMunicipio, CORREO_ELECTRONICO_SUP, Latitud, DIRECTOR, NombreCategoria, NombreModalidad," +
               "TEL_SEDE, CantidadDocentes, Direccion, NombreEstablecimiento, NombreSupervisor, DIR_SEDE ) VALUES ";
        
            //console.log("LINEA DE EJECUCION : " + i)
            tx.executeSql(insertSentence + currentRow);    

               

        /*$(".toreadSQL").append("INSERT INTO establecimiento (NombreSectorActual, NombrePlan, NombreAreaActual, NombreTipo, Telefono, NombreCiclo, CorreoElectronico, NombreJornada, NombreRegion," +
                "NombreDepartamento, Longitud, SituacionActual, CodigoUDI, NombreNivel, NombreMunicipio, CORREO_ELECTRONICO_SUP, Latitud, DIRECTOR, NombreCategoria, NombreModalidad," +
                "TEL_SEDE, CantidadDocentes, Direccion, NombreEstablecimiento, NombreSupervisor, DIR_SEDE ) VALUES " + responseEstablecimientos)
*/
        responseEstablecimientos = "";        
    }



    function populatetablaConfig(tx) {
        mensaje.append('<li><span class="btn btn-xs"><i class="glyphicon glyphicon-info-sign"></i></span><span class="text-info">Sincronizando la tabla de configuración</span></li>');
        tx.executeSql('DROP TABLE IF EXISTS config');
        tx.executeSql('CREATE TABLE IF NOT EXISTS config (param unique, val)');
        tx.executeSql('INSERT INTO config (param, val)  VALUES ("db_install", "1")');
    }

    function populatetablaMatricula(tx) {
        mensaje.append('<li><span class="btn btn-xs"><i class="glyphicon glyphicon-info-sign"></i></span><span class="text-info">Sincronizando la tabla de matricula</span></li>');
        // DUMP de la tabla para los indicadores de matricula historica	
        tx.executeSql('DROP TABLE IF EXISTS matricula');
        tx.executeSql('CREATE TABLE IF NOT EXISTS matricula (CodigoUDI,Anio int,grado int,InscritosInicialMujeres int,InscritosInicialHombres int,CantidadEstudiantes,PromovidosMujeres int,PromovidosHombres int,RepitentesMujeres int,RepitentesHombres int,NoPromovidosMujeres int,NoPromovidosHombres int,InscritosFinalMujeres int,InscritosFinalHombres int,RetiradosMujeres int,RetiradosHombres int,FracasoMujeres,FracasoHombres,TasaRetencionMujeres,TasaRetencionHombres,TasaDesercionMujeres,TasaDesercionHombres,TasaRepitenciaMujeres,TasaRepitenciaHombres,inscritos_anio_ant_mujeres,inscritos_anio_ant_hombres)');



    }







})
