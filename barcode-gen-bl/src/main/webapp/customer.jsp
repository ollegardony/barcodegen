<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Customer page</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
 
</head>
<body style="width: 1109px; ">
	<div class = container >
		<div class="row">
			<div class="col-sm-6" style="background-color:yellow;">
				<form id="setOrder" style="width: 637px; height: 186px">
					 <label for="userCode">User code: </label>
					 <input type="text" name="userCode" id="userCode">
					 <br/>
					 <label for="barcodeType">Barcode type: </label>
					 <input type="text" name="barcodeType" id="barcodeType">
					 <br/>
					 <button type="submit" id="btn-search">Add Order</button>
					 			 
				</form>
			</div>
			<div class="col-sm-6" style="background-color:pink;">
				<form id="registration" style="width: 460px; ">
					 <label for="userCode">User code: </label>
					 <input type="text" name="userCode" id="userCode" />
					 
					 <label for="barcodeType">Price type: </label>
					 <input type="text" name="barcodeType" id="barcodeType" />
					 
					 <button type='submit' id="btn-search" >Get price list</button>
					 			 
				</form>	
			</div>
		</div>
	</div>
	<div class = container>
		<form id="getPrice">
			 <label for="userCode">User code: </label>
			 <input type="text" name="userCode" id="userCode" />
			 
			 <label for="barcodeType">Price type: </label>
			 <input type="text" name="barcodeType" id="barcodeType" />
			 
			 <button type='submit' id="btn-search" >Get price list</button>
			 			 
		</form>
		<br/>
		<div>
			<table class = "table table-bordered table-stripped table-hover">
				<thead>
					<tr>
						<th>id</th>
						<th>Barcode type</th>
						<th>Datamatrix charater from</th>
						<th>Datamatrix charater to</th>
						<th>Gs1 Code number</th>
						<th>Price</th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
		</div>
				
	</div>
		
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
 	
	<script src="resources/js/main.js" type="text/javascript"></script>
	
</body>
</html>