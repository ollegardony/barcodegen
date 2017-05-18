$(document).ready(function() {

	$("#getPrice").submit(function(event) {	
		event.preventDefault();
		getPriceList();
	});

	$("#registration").submit(function(event) {
		event.preventDefault();
		saveBarcodeUser();
	});
});

function getPriceList() {

	var jsonParam = {}
	jsonParam["userCode"] = $("#userCode").val();
	jsonParam["barcodeType"] = $("#barcodeType").val();

	$('table tbody').empty();
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "http://localhost:8080/barcode-gen-bl/barcode/getPrice",
		headers : {
			'Access-Control-Allow-Origin' : '*'
		},
		data : jsonParam,
		dataType : 'json',
		cache : false,
		timeout : 600000,
		success : function(data) {

			var items = [];

			$.each(data, function(key, val) {

				var col = "<tr>";
				col = col + "<td id = ''" + key + "''>" + val.id + "</td>";
				col = col + "<td id = ''" + key + "''>" + val.barcodeType
						+ "</td>";
				col = col + "<td id = ''" + key + "''>" + val.charFrom
						+ "</td>";
				col = col + "<td id = ''" + key + "''>" + val.charTo + "</td>";
				col = col + "<td id = ''" + key + "''>" + val.codeNumber
						+ "</td>";
				col = col + "<td id = ''" + key + "''>" + val.price + "</td>";
				col = col + "</tr>";
				$('table tbody').append(col);

			});

		},
		error : function(e) {

			console.log("ERROR : ", e);

		}
	});

}


function saveBarcodeUser() {

	var jsonParam = {}
	jsonParam["loginName"] = $("#regUserCode").val();
	jsonParam["emailAdress"] = $("#regEmail").val();
	jsonParam["companyName"] = $("#regCompany").val();
	jsonParam["companyAdress"] = $("#regCompanyAddress").val();
	jsonParam["taxNumber"] = $("#regTaxNumber").val();

	$('table tbody').empty();
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "http://localhost:8080/barcode-gen-bl/barcode/saveUser",
		headers : {
			'Access-Control-Allow-Origin' : '*'
		},
		data : jsonParam,
		dataType : 'json',
		cache : false,
		timeout : 600000,
		success : function(data) {



		},
		error : function(e) {

			console.log("ERROR : ", e);

		}
	});

}