$(document).ready(function(){
	function activeLoading(){
		var width = $(window).width();
		var height = $(window).height();
		$("body").append('<div class="loadingBox"></div>');
		$(".loadingBox").css("width",width + "px").css('height',height + "px");		
	}

	function deactiveLoading(){
		$(".loadingBox").remove();
	}

	$(window).resize(function(){
		/*activeLoading();
		deactiveLoading();*/
	});

	$("body").on("click",".principalButton, .mainmenu a", function(){
        activeLoading();
        var href = $(this).attr("href");
        if(href){
        	window.location = href;	
        }else{
        	href = $(this).attr("rel");
        	if(href){
        		window.location = href;		
        	}
        }
        

    });

    $("body").on("click","ul.nav.navbar-nav li a", function(){    	
        activeLoading();        
        var href = $(this).attr("href");
        if(href != "#"){
        	window.location = href;	
        }
        
    });

    var navMain = $("#nav-main");

     navMain.on("click", "a", null, function () {
         navMain.collapse('hide');
     });



    //activeLoading();
});