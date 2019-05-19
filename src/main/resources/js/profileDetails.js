$(function() {
  
	  var detailRunnerSelected = null;
	  var oldColor = null;
      $(document).ready(function() {
    	  $(".searchResult").hover(function(event) {
    		    //mouse on the item (hover)
    		    if (event.currentTarget != detailRunnerSelected) {
    		    	if (detailRunnerSelected != null) {
        		    	detailRunnerSelected.style.backgroundColor = oldColor;
    		    	}
        		    detailRunnerSelected = event.currentTarget;
        		    oldColor = detailRunnerSelected.style.backgroundColor;
        		    event.currentTarget.style.backgroundColor = "#FFFFDD";
        		    var runnerId = event.currentTarget.getAttribute("data-runner");

        		    $("#profileDetails").html("<span class='shy'>Loading...</span>");


        		    
        		    

        			$.ajax({
        		    	  url: "admin/loadDynamicRunnerDetails.page?runner="+runnerId,
        		    	  dataType: "script",
        		    	  success: function() {
        		    		  
        		    
        		    		  $("#profileDetails").html("Payment data of <br/><b>"+runner.firstname+"</b>  <b>"+runner.lastname+"</b>:<br/>"+
      		    				  	"<p/>Payment by Credit Card: <br/>"+runner.cc);
        		    		  
        		    	  }
        		    });


        			

    		    
    		    }
    		}/*, function() {
    		    //mouse off the item
    		}*/);
      });
  
});


