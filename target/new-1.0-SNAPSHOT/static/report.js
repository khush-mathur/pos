function getBrandUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/report";
}

//BUTTON ACTIONS

function getBrandReport(){
	var url = getBrandUrl() + "/brand-report";

	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(response) {
	   		writeFileData(response,'brand-report.tsv')
	   },
	   error: handleAjaxError
	});

	return false;
}
function getInventoryReport(){
	var url = getBrandUrl() + "/inventory-report";

	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(response) {
	   		writeFileData(response,'inventory-report.tsv')
	   },
	   error: handleAjaxError
	});

	return false;
}
function getSalesReport(event){
    var $form = $("#sales-report-form");

    var json = convertJson($form);
    console.log(json)
	var url = getBrandUrl() + "/sales-report";

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   		writeFileData(response,'sales-report.tsv')
	   },
	   error: handleAjaxError
	});
	return false;
}

function convertJson($form){
    var serialized = $form.serializeArray();
    console.log(serialized);
    var s = '';
    var data = {};
    for(s in serialized){
        data[serialized[s]['name']] = serialized[s]['value']
    }
    data['startDate'] = new Date(data['startDate'])
    data['endDate'] = new Date(data['endDate'])
    var json = JSON.stringify(data);
    return json;
}

//INITIALIZATION CODE
function init(){
	$('#get-brand-report').click(getBrandReport);
	$('#get-inventory-report').click(getInventoryReport);
	$('#get-sales-report').click(getSalesReport);
}

$(document).ready(init);

