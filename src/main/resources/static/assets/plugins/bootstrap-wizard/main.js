
/**
 * converts array-like object to array
 * @param  collection the object to be converted
 * @return {Array} the converted object
 */
function arrayify(collection) {
  return Array.prototype.slice.call(collection);
}

/**
 * generates factory functions to convert table rows to objects,
 * based on the titles in the table's <thead>
 * @param  {Array[String]} headings the values of the table's <thead>
 * @return {Function}      a function that takes a table row and spits out an object
 */
function factory(headings) {
  return function(row) {
    return arrayify(row.cells).reduce(function(prev, curr, i) {
      prev[headings[i]] = curr.innerText;
      return prev;
    }, {});
  }
}

/**
 * given a table, generate an array of objects.
 * each object corresponds to a row in the table.
 * each object's key/value pairs correspond to a column's heading and the row's value for that column
 * 
 * @param  {HTMLTableElement} table the table to convert
 * @return {Array[Object]}       array of objects representing each row in the table
 */
function parseTable(table) {
  var headings = arrayify(table.tHead.rows[0].cells).map(function(heading) {
    return heading.innerText;
  });
  return arrayify(table.tBodies[0].rows).map(factory(headings));
}



$(document).ready(function () {

    $("#sub1").click(function (event) {

        //stop submit the form, we will post it manually.
    	event.preventDefault();
        fire_ajax_submit();

    });
    
    $("#sub2").click(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();
        fire_ajax_submit();

    });
    
    $("#sub3").click(function (event) {

        //stop submit the form, we will post it manually.
    	event.preventDefault();
        fire_ajax_submit();

    });
    
    $("#sub4").click(function (event) {

        //stop submit the form, we will post it manually.
    	event.preventDefault();
    	var table7 = document.querySelector("#payment-tab");
    	var data7 = parseTable(table7);
    	var search7 = data7;
    	
    	$.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/api/paymentList",
            dataType: "json",
            data: JSON.stringify(search7),
            success: function (data1) {
            	            	 
                var json = "<h4>Ajax Response</h4><pre>"
                    + JSON.stringify(data1, null, 4) + "</pre>";
                $('#feedback').html(json);

                console.log("SUCCESS : ", data7);
                $("#btn-search").prop("disabled", false);

            },
            error: function (e) {

                var json = "<h4>Ajax Response</h4><pre>"
                    + e.responseText + "</pre>";
                $('#feedback').html(json);

                console.log("ERROR : ", e);
                $("#btn-search").prop("disabled", false);

            },
           
        });
    	
    	///submit the form here
   	 $('#paymentEntry').submit();
       
    })

});

function fire_ajax_submit() {
	 var table = document.querySelector("#lang-prof-tab");
	 var table2 = document.querySelector("#test-tab");
	 var table3 = document.querySelector("#family-tab");
	 var table4 = document.querySelector("#rel-tab");
	 var table5 = document.querySelector("#edu-tab");
	 var table6 = document.querySelector("#work-tab");
	
	
	 var data  = parseTable(table);
	 var data2  = parseTable(table2);
	 var data3  = parseTable(table3);
	 var data4  = parseTable(table4);
	 var data5  = parseTable(table5);
	 var data6  = parseTable(table6);
	
	
	 console.log(data);
	 console.log(data2);
	 console.log(data3);
	 console.log(data4);
	 console.log(data5);
	 console.log(data6);
	
     var search = data;
     var search2 = data2;
     var search3 = data3;
     var search4 = data4;
     var search5 = data5;
     var search6 = data6;
     
     

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api/languageProficiency",
        dataType: "json",
        data: JSON.stringify(search),
        success: function (data1) {
        	
            var json = "<h4>Ajax Response</h4><pre>"
                + JSON.stringify(data1, null, 4) + "</pre>";
            $('#feedback').html(json);

            console.log("SUCCESS : ", data);
            $("#btn-search").prop("disabled", false);

        },
        error: function (e) {

            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);

            console.log("ERROR : ", e);
            $("#btn-search").prop("disabled", false);

        },
       
    });
    
    $.ajax({
    	
        type: "POST",
        contentType: "application/json",
        url: "/api/testScores",
        dataType: "json",
        data: JSON.stringify(search2),
        success: function (data1) {
        	
            var json = "<h4>Ajax Response</h4><pre>"
                + JSON.stringify(data1, null, 4) + "</pre>";
            $('#feedback').html(json);

            console.log("SUCCESS : ", data2);
            $("#btn-search").prop("disabled", false);

        },
        error: function (e) {

            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);

            console.log("ERROR : ", e);
            $("#btn-search").prop("disabled", false);

        },
       
    });
    
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api/familyDetails",
        dataType: "json",
        data: JSON.stringify(search3),
        success: function (data1) {
        	
            var json = "<h4>Ajax Response</h4><pre>"
                + JSON.stringify(data1, null, 4) + "</pre>";
            $('#feedback').html(json);

            console.log("SUCCESS : ", data3);
            $("#btn-search").prop("disabled", false);

        },
        error: function (e) {

            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);

            console.log("ERROR : ", e);
            $("#btn-search").prop("disabled", false);

        },
       
    });
    
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api/relativesAbroad",
        dataType: "json",
        data: JSON.stringify(search4),
        success: function (data1) {
        	
            var json = "<h4>Ajax Response</h4><pre>"
                + JSON.stringify(data1, null, 4) + "</pre>";
            $('#feedback').html(json);

            console.log("SUCCESS : ", data4);
            $("#btn-search").prop("disabled", false);

        },
        error: function (e) {

            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);

            console.log("ERROR : ", e);
            $("#btn-search").prop("disabled", false);

        },
       
    });
    
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api/educationBackground",
        dataType: "json",
        data: JSON.stringify(search5),
        success: function (data1) {
        	
            var json = "<h4>Ajax Response</h4><pre>"
                + JSON.stringify(data1, null, 4) + "</pre>";
            $('#feedback').html(json);

            console.log("SUCCESS : ", data5);
            $("#btn-search").prop("disabled", false);

        },
        error: function (e) {

            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);

            console.log("ERROR : ", e);
            $("#btn-search").prop("disabled", false);

        },
       
    });
    
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api/workExperience",
        dataType: "json",
        data: JSON.stringify(search6),
        success: function (data1) {
        	
            var json = "<h4>Ajax Response</h4><pre>"
                + JSON.stringify(data1, null, 4) + "</pre>";
            $('#feedback').html(json);

            console.log("SUCCESS : ", data6);
            $("#btn-search").prop("disabled", false);

        },
        error: function (e) {

            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);

            console.log("ERROR : ", e);
            $("#btn-search").prop("disabled", false);

        },
       
    });
   
  ///submit the form here
	 $('#search-formNew').submit();
    
}