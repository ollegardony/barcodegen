$(document).ready(function () {

    $("#getPrice").submit(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        fire_ajax_submit();

    });

});

function fire_ajax_submit() {
	
	
    var search = {}
    search["userCode"] = $("#userCode").val();
	  search["barcodeType"] = $("#barcodeType").val();
	 	

	$('table tbody').empty(); 
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "http://localhost:8080/barcode-gen-bl/barcode/getPrice",
		headers: { 'Access-Control-Allow-Origin': '*' },
        data:search,
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
        	
        	var items = [];
        	        	
        	$.each(data, function(key, val){
        		
        		var col = "<tr>";
        		col = col + "<td id = ''"+key+"''>"+val.id+"</td>";
        		col = col + "<td id = ''"+key+"''>"+val.barcodeType+"</td>";
        		col = col + "<td id = ''"+key+"''>"+val.charFrom+"</td>";
        		col = col + "<td id = ''"+key+"''>"+val.charTo+"</td>"; 
        		col = col + "<td id = ''"+key+"''>"+val.codeNumber+"</td>"; 
        		col = col + "<td id = ''"+key+"''>"+val.price+"</td>"; 
        		col = col + "</tr>";
        		$('table tbody').append(col);
        		
        	});

        },
        error: function (e) {

            console.log("ERROR : ", e);
          
        }
    });

}