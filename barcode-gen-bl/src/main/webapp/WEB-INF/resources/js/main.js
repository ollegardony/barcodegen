$(document).ready(function() {

	$("#getPrice").submit(function(event) {
		event.preventDefault();
		getPriceList();
	});

	$("#registration").submit(function(event) {
		event.preventDefault();
		saveBarcodeUser();
	});

	$("#setOrder").submit(function(event) {
		event.preventDefault();
		saveOrder();
	});

});

function getPriceList() {
	
	var jsonParam = {}
	var bt
	if ($("#barcodeType").val() == "Gs1") {
		bt = 1;
	} else {
		if ($("#barcodeType").val() == "ALL") {
			bt = 3
		}else{
			bt = 0;	
		}		
	}
		
	jsonParam["userCode"] = $("#userCode").val();
	jsonParam["barcodeType"] = bt;

	$('table tbody').empty();
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : window.location.protocol + "//" + window.location.host + "/barcode-gen-bl/barcode/getPrice",
		headers : {'Access-Control-Allow-Origin' : '*'},
		data : jsonParam,
		dataType : 'json',
		cache : false,
		timeout : 600000,
		success : function(data) {
			if (data.hasOwnProperty('errorCode')) {
				BootstrapDialog.alert(data.errorMessage);
			} else {
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
			}

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

	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : window.location.protocol + "//" + window.location.host
				+ "/barcode-gen-bl/barcode/saveUser",
		headers : {
			'Access-Control-Allow-Origin' : '*'
		},
		data : jsonParam,
		dataType : 'json',
		cache : false,
		timeout : 600000,
		success : function(data) {
			if (data.hasOwnProperty('errorCode')) {
				BootstrapDialog.alert(data.errorMessage);
			} else {
				BootstrapDialog.show({
					message : 'Sikeres regisztáció!',
					buttons : [ {
						label : 'Close',
						action : function(dialogItself) {
							dialogItself.close();
						}
					} ]
				});
			}

		},
		error : function(e) {
			console.log("ERROR : ", e);
		}
	});

}

function saveOrder() {

	var bt
	if ($("#orderBarcodeType").val() == "Gs1") {
		bt = 1;
	} else {
		bt = 0;
	}

	var jsonParam = {}
	jsonParam["loginName"] = $("#orderUserCode").val();
	jsonParam["barcodeType"] = bt;
	jsonParam["datamatrixText"] = $("#orderDataMatrixText").val();
	jsonParam["gs1Code1"] = $("#orderGs1").val();
	jsonParam["gs1Code2"] = $("#orderGs2").val();
	jsonParam["gs1Code3"] = $("#orderGs3").val();
	jsonParam["gs1Code4"] = $("#orderGs4").val();
	jsonParam["gs1Code5"] = $("#orderGs5").val();

	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : window.location.protocol + "//" + window.location.host + "/barcode-gen-bl/barcode/saveOrder",
		headers : {'Access-Control-Allow-Origin' : '*'},
		data : jsonParam,
		dataType : 'json',
		cache : false,
		timeout : 600000,
		success : function(data) {
		
			if (data.hasOwnProperty('errorCode')) {
				BootstrapDialog.alert(data.errorMessage);
			} else {
				BootstrapDialog.show({
					message : 'Atonosito: ' + data.orderNumber + '. Osszeg: ' + data.orderPrice,
					data : {
							'userCode' : data.userCode,
							'orderNumber' : data.orderNumber,
							'orderPrice' : data.orderPrice
							},
					buttons : [ {
								label : 'Close',					
								cssClass : 'btn-primary',
								action : function(dialogRef) {
									dialogRef.close();
								}
								},
								{label : 'Accept',
								cssClass : 'btn-primary',
								action : function(dialogRef) {
									var jsonParam = {}
									jsonParam["orderNumber"] = dialogRef.getData('orderNumber');

									$.ajax({
										type : "GET",
										contentType : "application/json",
										url : window.location.protocol + "//" + window.location.host + "/barcode-gen-bl/barcode/getBarcode",
										headers : {'Access-Control-Allow-Origin' : '*'},
										data : jsonParam,
										dataType : 'json',
										cache : false,
										timeout : 600000,
										success : function(data) {
											if (data.hasOwnProperty('errorCode')) {
												BootstrapDialog.alert(data.errorMessage);
											} else {
												BootstrapDialog.show({
													message : 'Sikeres visszaigazolás. Az email címére elküldtük a kért Barcode-ot.',
													buttons : [ {
														label : 'Close',
														action : function(dialogItself) {
															dialogItself.close();
														}
													} ]
											});
											dialogRef.close();
								}

									},
									error : function(e) {
												console.log("ERROR : ",	e);
											}
									});
							}
						} ]
				});
			}

		},
		error : function(e) {
					console.log("ERROR : ", e);

				}
		});

}

function initMap() {

    var location = new google.maps.LatLng(50.0875726, 14.4189987);

    var mapCanvas = document.getElementById('map');
    var mapOptions = {
        center: location,
        zoom: 16,
        panControl: false,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    }
    var map = new google.maps.Map(mapCanvas, mapOptions);

}

