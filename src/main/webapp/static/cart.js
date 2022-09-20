
var orderId;
function getCartUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl ;
}

function getOrderId(){
var arr = window.location.href.split('/')
return arr[arr.length-1]
}
function displayOrderId(){
    document.getElementById("orderId").innerHTML = getOrderId();
}

//BUTTON ACTIONS
function addOrderItem(event){
	//Set the values to update
	var $form = $("#cart-form");
	var json = toJson($form);
	var url =  getCartUrl() + "/cart/add/" + getOrderId();

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		getCartItems();
	   },
	   error: handleAjaxError
	});
	return false;
}

function placeOrder(){
	//Set the values to update
	var url =  getCartUrl() + "/order/placeOrder/" + getOrderId();

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: {},
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
            generateInvoice(getOrderId());
            redirectToOrderPage();
	   },
	   error: handleAjaxError
	});

	return false;
}

function generateInvoice(id){
	//Set the values to update

	var url =  getCartUrl() + "/order/invoice/"+id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(response) {
	    console.log(response)
	   },
	   error: handleAjaxError
	});
}

function redirectToOrderPage(){
window.location.href = "/pos/ui/order"
}
function updateOrderItem(event){
	$('#edit-cart-modal').modal('toggle');
	//Get the ID
	var id = $("#cart-edit-form input[name=id]").val();
	var url = getCartUrl() + "/cart/update/" + id;

	//Set the values to update
	var $form = $("#cart-edit-form");
	var json = toJson($form);

	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		getCartItems();
	   },
	   error: handleAjaxError
	});

	return false;
}


function getCartItems(){
	var url = getCartUrl() + "/order/view/"+ getOrderId();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayCartItems(data);
	   },
	   error: handleAjaxError
	});
}

function deleteCartItem(id){
	var url = getCartUrl() + "/cart/delete/" + id;
	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		getCartItems();
	   },
	   error: handleAjaxError
	});
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData(){
	var file = $('#cartFile')[0].files[0];
	readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results){
	fileData = results.data;
	uploadRows();
}

function uploadRows(){
	//Update progress
	updateUploadDialog();
	//If everything processed then return
	if(processCount==fileData.length){
		return;
	}
	
	//Process next row
	var row = fileData[processCount];
	processCount++;
	
	var json = JSON.stringify(row);
	var url = getCartUrl() + "/create";

	//Make ajax call
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		uploadRows();  
	   },
	   error: function(response){
	   		row.error=response.responseText
	   		errorData.push(row);
	   		uploadRows();
	   }
	});

}

function downloadErrors(){
	writeFileData(errorData,'upload-cart-errors.tsv');
}

//UI DISPLAY METHODS

var totalAmount =0;
function displayCartItems(data){
	var $tbody = $('#cart-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml =" <button class = 'btn btn-outline-secondary mr3 btn-sm' onclick='displayEditCart(" + e.id + ")'>edit</button>"
		 buttonHtml += "<button class = 'btn btn-outline-danger btn-sm' onclick='deleteCartItem(" + e.id + ")'>delete</button>"
		var amount = e.sellingPrice*e.quantity
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.barcode + '</td>'
		+ '<td>'  + e.quantity + '</td>'
		+ '<td>'  + e.sellingPrice + '</td>'
		+ '<td>'  + amount + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
		totalAmount +=amount;
        $tbody.append(row);
        displayTotal();
	}
}

function setOrderId(id){
 this.orderId = id;
 console.log(orderId);
}

function displayEditCart(id){
	var url = getCartUrl() + "/cart/view/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayCart(data);
	   },
	   error: handleAjaxError
	});	
}

function resetUploadDialog(){
	//Reset file name
	var $file = $('#cartFile');
	$file.val('');
	$('#cartFileName').html("Choose File");
	//Reset various counts
	processCount = 0;
	fileData = [];
	errorData = [];
	//Update counts	
	updateUploadDialog();
}

function updateUploadDialog(){
	$('#rowCount').html("" + fileData.length);
	$('#processCount').html("" + processCount);
	$('#errorCount').html("" + errorData.length);
}

function updateFileName(){
	var $file = $('#cartFile');
	var fileName = $file.val();
	$('#cartFileName').html(fileName);
}

function displayUploadData(){
 	resetUploadDialog(); 	
	$('#upload-employee-modal').modal('toggle');
}

function displayCart(data){
	$("#cart-edit-form input[name=quantity]").val(data.quantity);
	$("#cart-edit-form input[name=orderId]").val(data.orderId);
	$("#cart-edit-form input[name=id]").val(data.id);
	$("#cart-edit-form input[name=productId]").val(data.productId);
	$("#cart-edit-form input[name=barcode]").val(data.barcode);
	$('#edit-cart-modal').modal('toggle');
}

function displayTotal(){
    document.getElementById("total").innerHTML = totalAmount;
}


//INITIALIZATION CODE
function init(){
	$('#add-cart').click(addOrderItem);
	$('#update-cart').click(updateOrderItem);
	$('#refresh-data').click(getCartItems);
	$('#place-order').click(placeOrder);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
    $('#cartFile').on('change', updateFileName)
}

function handleAjaxError(response){
	var response = JSON.parse(response.responseText);
	alert(response.message);
}

$(document).ready(init);
$(document).ready(getOrderId());
$(document).ready(displayOrderId());
$(document).ready(displayTotal());
$(document).ready(getCartItems());
