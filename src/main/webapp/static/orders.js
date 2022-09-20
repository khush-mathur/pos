function getOrderUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl;
}



//BUTTON ACTIONS
function addOrder(){
	//Set the values to update
	var url =  getOrderUrl() + "/order/new-order";
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(response) {
	   console.log(response)
	        getOrderList();
	        goToCart(response.id);
	   },
	   error: handleAjaxError
	});

	return false;
}
function downloadInvoice(id){
	//Set the values to update

	var url =  getOrderUrl() + "/order/invoice/"+id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(response) {
	    console.log(response)
	   },
	   error: handleAjaxError
	});

	return false;
}

function updateOrder(event){
	$('#edit-order-modal').modal('toggle');
	//Get the ID
	var id = $("#order-view-form input[name=id]").val();
	var url = getOrderUrl() + "/update/" + id;

	//Set the values to update
	var $form = $("#order-view-form");
	var json = toJson($form);

	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   		getOrderList();
	   },
	   error: handleAjaxError
	});

	return false;
}


function getOrderList(){
	var url = getOrderUrl() + "/order/viewAll";
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayOrderList(data);
	   },
	   error: handleAjaxError
	});
}

function deleteOrder(id){
	var url = getOrderUrl() + "/order/delete/" + id;
	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		getOrderList();
	   },
	   error: handleAjaxError
	});
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData(){
	var file = $('#orderFile')[0].files[0];
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

	var json = JSON.stringify([row]);
	var url = getOrderUrl() + "/create";

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
	writeFileData(errorData,'order-errors.tsv');
}

//UI DISPLAY METHODS

function displayOrderList(data){
	var $tbody = $('#order-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml = ''
		if(e.status === 'completed'){
		    buttonHtml = ' <button  class = "btn btn-outline-info btn-sm" onclick="displayOrderItems(' + e.id + ')">view</button>'
		    buttonHtml += '<a href= "/pos/invoice/invoice' +e.id +'.pdf" target="_blank"><button  class = "btn btn-outline-success btn-sm">invoice</button></a>'
		}
		else{
		    buttonHtml = '<button  class = "btn btn-outline-secondary btn-sm" onclick ="goToCart('+ e.id + ')"> edit </button> </a>'
            buttonHtml += ' <button  class = "btn btn-outline-danger btn-sm" onclick="deleteOrder(' + e.id + ')">delete</button>'
		}
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.dateTime + '</td>'
		+ '<td>' + e.status + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

function goToCart(id){
    window.location.href = "/pos/ui/cart/" + id;
}

function displayOrderItems(id){
	var url = getOrderUrl() + "/order/view/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayOrder(data);
	   },
	   error: handleAjaxError
	});
}

function resetUploadDialog(){
	//Reset file name
	var $file = $('#orderFile');
	$file.val('');
	$('#orderFileName').html("Choose File");
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
	var $file = $('#orderFile');
	var fileName = $file.val();
	$('#orderFileName').html(fileName);
}

function displayUploadData(){
 	resetUploadDialog();
	$('#upload-order-modal').modal('toggle');
}
var totalAmount =0;
function displayOrder(data){
	$('#view-order-modal').modal('toggle');
	var $tbody = $('#order-item-table').find('tbody');
    	$tbody.empty();

    for(var i in data){
   		var e = data[i];
   		var amount = e.sellingPrice*e.quantity
   		var row = '<tr>'
   		+ '<td>' + e.productId + '</td>'
   		+ '<td>' + e.barcode + '</td>'
    	+ '<td>' + e.quantity + '</td>'
    	+ '<td>' + e.sellingPrice + '</td>'
    	+ '<td>'  + amount + '</td>'
    	+ '</tr>';
    	totalAmount +=amount;
        $tbody.append(row);
    }
    displayTotal();
}
function displayTotal(){
    document.getElementById("total").innerHTML = totalAmount;
}

//INITIALIZATION CODE
function init(){
	$('#add-order').click(addOrder);
	$('#update-order').click(updateOrder);
	$('#refresh-data').click(getOrderList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
    $('#orderFile').on('change', updateFileName)
}

function handleAjaxError(response){
	var response = JSON.parse(response.responseText);
	alert(response.message);
}
function highLight(){
highlightItem("Orders")
}

$(document).ready(init);
$(document).ready(highLight);
$(document).ready(getOrderList);

