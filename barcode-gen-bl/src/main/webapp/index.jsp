<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Barcode Generator</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap3-dialog/1.35.4/css/bootstrap-dialog.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap3-dialog/1.35.4/css/bootstrap-dialog.min.css">

</head>

<body>
	
	<div class="container">
		<h2>Generate Barcode</h2>
	</div>
	<div id="exTab1" class="container">	
		<ul  class="nav nav-pills">
			<li class="active">
       			 <a  href="#1a" data-toggle="tab">Barcode Order</a>
			</li>
			<li><a href="#2a" data-toggle="tab">Registration</a>
			</li>
			<li><a href="#3a" data-toggle="tab">Price List</a>
			</li>
  
		</ul>

		<div class="tab-content clearfix">
			<div class="tab-pane active" id="1a">
				<hr></hr>
				<!-- 
        		<div class=container style="height: 100%; display: flex; justify-content: center; align-items: center">
        		 -->
	        		<form id="setOrder" style="width: 100%; height: 100%">
						<div class="form-group">
							<label for="orderUserCode">User Code</label> 
							<input type="text" class="form-control" id="orderUserCode" style="width: 280px;">
						</div>
						
						
						<div class="form-group">						
							<label for="orderBarcodeType" style="width: 280px;">Select Barcode type</label>							
							<select class="form-control" id="orderBarcodeType" style="width: 280px;">
								<option>Datamatrix</option>
								<option>Gs1</option>
							</select>
						</div>
												
						<div class="form-group">
							<label for="orderDataMatrixText">Barcode Text:</label>
							<textarea class="form-control" rows="5" id="orderDataMatrixText" style="width: 450px;"></textarea>
						</div>
	
						<div class="form-group">
							<label for="orderGs1">GS1 Codes</label> <input type="text" class="form-control" id="orderGs1" style="width: 280px;">
						</div>
						<div class="form-group">
							<label for="orderGs2">GS2 Codes</label> <input type="text" class="form-control" id="orderGs2" style="width: 280px;">
						</div>
						<div class="form-group">
							<label for="orderGs3">GS3 Codes</label> <input type="text" class="form-control" id="orderGs3" style="width: 280px;">
						</div>
						<div class="form-group">
							<label for="orderGs4">GS4 Codes</label> <input type="text" class="form-control" id="orderGs4" style="width: 280px;">
						</div>
						<div class="form-group">
							<label for="orderGs5">GS3 Codes</label> <input type="text" class="form-control" id="orderGs5" style="width: 280px;">
						</div>
	
						<button type="submit" class="btn btn-primary">Add Order</button>
	
					</form>
				<!--
        		</div>
        		 -->
			</div>
			<div class="tab-pane" id="2a">
          		<form id="registration"style=" width: 10%; height: 100%">
					<div class="form-group">
        				<label for="regUserCode">Login name</label>
        				<input type="text" class="form-control" id="regUserCode" style="width: 280px;" placeholder="Login name">
    				</div>
    				
    				<div class="form-group">
        				<label for="regEmail">Email</label>
        				<input type="text" class="form-control" id="regEmail" style="width: 280px; ">
    				</div>
    				
    				<div class="form-group">
        				<label for="regCompany">Company name</label>
        				<input type="text" class="form-control" id="regCompany" style="width: 280px; ">
    				</div>

    				<div class="form-group">
        				<label for="regCompanyAddress">Company adress</label>
        				<input type="text" class="form-control" id="regCompanyAddress" style="width: 280px; ">
    				</div>

    				<div class="form-group">
        				<label for="regTaxNumber">Tax number</label>
        				<input type="text" class="form-control" id="regTaxNumber" style="width: 280px; ">
    				</div>
									    				   				
					<button type="submit" class="btn btn-primary">Registration</button>
				</form>	
			</div>
        	<div class="tab-pane" id="3a">
          		<div class = container>
					<form id="getPrice">
						<div class="form-group">
							<label for="userCode">User code: </label>
							<input type="text" class="form-control" name="userCode" id="userCode" style="width: 280px;" />
						</div>
						
						<div class="form-group">						
							<label for="barcodeType" style="width: 280px;">Select Barcode type</label>							
							<select class="form-control" id="barcodeType" style="width: 280px;">
								<option>ALL</option>
								<option>Datamatrix</option>
								<option>Gs1</option>
							</select>
						</div>						
						
						 <button type='submit' class="btn btn-primary" >Get price list</button>
						 			 
					</form>
				</div>
	
				<br/>
				<div>
					<div class = container>
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
			</div>
		</div>
  	</div>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script src="http://code.jquery.com/mobile/1.4.2/jquery.mobile-1.4.2.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap3-dialog/1.35.4/js/bootstrap-dialog.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap3-dialog/1.35.4/js/bootstrap-dialog.min.js"></script>
	<script src="resources/js/main.js" type="text/javascript"></script>

</body>
</html>