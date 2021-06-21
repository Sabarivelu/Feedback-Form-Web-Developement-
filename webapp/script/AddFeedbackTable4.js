var value2;
var jqueryFunc;
var id;

function addRow1(sessions) {

	for (var i = 0; i < sessions.length; i++) {
		var id = sessions[i].fid;
		var name = sessions[i].name;
		var des = sessions[i].description;
		var venue = sessions[i].venue;
		var time = sessions[i].time;
		var table = document.getElementsByTagName('table')[0];
		var row = table.getElementsByTagName('th')[4];
		var row1 = table.getElementsByTagName('th')[5];
		var aTag = document.createElement('a');
		aTag.setAttribute('href', 'EditFeedback.html');
		// aTag.setAttribute('class', 'fa fa-edit');
		aTag.setAttribute('id', 'e1');
		aTag.setAttribute('onClick', 'editRow(this)');
		aTag.innerText = "Edit"
		/*
		 * var aTag1 = document.createElement('a'); aTag1.setAttribute('href',
		 * "#"); aTag1.setAttribute('id','g1'); aTag1.innerText = "Delete";
		 */
		var aTag1 = document.createElement('button');
		aTag1.setAttribute('type', 'submit');
		aTag1.setAttribute('class', 'fa fa-trash');
		aTag1.setAttribute('id', 'g1');
		aTag1.setAttribute('onClick', 'deleteRow(this)');
		// aTag1.setAttribute('onClick', "");
		// aTag1.innerText = "Delete";*/
		var newRow = table.insertRow(1);
		var cell1 = newRow.insertCell(0);
		var cell2 = newRow.insertCell(1);
		var cell3 = newRow.insertCell(2);
		var cell4 = newRow.insertCell(3);
		var cell5 = newRow.insertCell(4);
		var cell6 = newRow.insertCell(5);
		var cell7 = newRow.insertCell(6);
		cell1.setAttribute("class", "t1");
		cell1.innerHTML = id;
		cell2.innerHTML = name;
		cell3.innerHTML = des;
		cell4.innerHTML = venue;
		cell5.innerHTML = time;
		cell6.append(aTag1);
		cell7.append(aTag);
	}
}

async function data() {
	// alert("inside data");

	const
	result = await
	fetch("http://localhost:8085/Shopkart/rest/call/getFeedback", {
		method : 'POST',
		headers : {
			'Content-type' : 'application/json'
		}
	});
	const
	response = await
	result.json();
	addRow1(response);
}
function deleteRow(r) {
	var i = r.parentNode.parentNode.rowIndex;
	console.log(i);
	document.getElementById("conference-list").deleteRow(i);
	// deleteValue(r);
	var currentRow = r.closest('tr');
	console.log(currentRow);
	var value1 = currentRow.getElementsByTagName('td')[1];
	var value1 = value1.innerText;
	window.value2 = value1.toUpperCase();
	jqueryFunc();
}
function editRow(r) {
	var i = r.parentNode.parentNode.rowIndex;
	var currentRow = r.closest('tr');
	window.id = currentRow.getElementsByTagName('td')[0].innerText;
	//console.log(id);
	sessionStorage.removeItem("id");
	sessionStorage.setItem("id", id);

}
function editValue() {
	console.log(sessionStorage.getItem("id"));
	document.getElementById('e1').innerText = sessionStorage.getItem("id");
}

$(document).ready(function() {

	$("#e6").click(function() {
		var info = {
			id : sessionStorage.getItem("id"),
			name : $("#e2").val(),
			des : $("#e3").val(),
			venue : $("#e4").val(),
			time : $("#e5").val(),
		};
	/*	alert(info.id);
		alert(info.name);
		alert(info.des);
		alert(info.venue);
		alert(info.time);*/
		$.ajax({
			type : "POST",
			dataType : "json",
			data : {
				A : JSON.stringify(info)
			},
			contentType : "application/json",
			url : "rest/call/updateFeedback",
			success : function(data) {
				var data1 = data.status;
				if (data1 == "success") {
					window.location.href = "AdminFeedback.html"
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				window.location.href = "AdminFeedback.html"
			}

		});

	});
});
$(function() {

	jqueryFunc = function() {
		// alert("click done");
		var info = {
			name : value2,
		};
		// alert(info.name);
		$.ajax({
			type : "POST",
			dataType : "json",
			data : {
				A : JSON.stringify(info)
			},
			contentType : "application/json",
			url : "rest/call/deleteFeedback",
			success : function(data) {
				// alert("My data!!!" + data.status);
				var data1 = data.status;
				// alert("My data!!!" + data1);
				// alert("Hello " + _spPageContextInfo.userLoginName);
				if (data1 == "success") {
					// alert("Data Printed Successfully")
					window.location.href = "AdminFeedback.html"
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				// alert("Error!!");
				window.location.href = "CreateSessionFeedback.html"
			}

		});
	}
});

function myFunction() {
	var input, filter, table, tr, td, i, txtValue;
	input = document.getElementById("myInput");
	filter = input.value.toUpperCase();
	table = document.getElementById("conference-list");
	tr = table.getElementsByTagName("tr");
	for (i = 0; i < tr.length; i++) {
		td = tr[i].getElementsByTagName("td")[1];
		if (td) {
			txtValue = td.textContent || td.innerText;
			if (txtValue.toUpperCase().indexOf(filter) > -1) {
				tr[i].style.display = "";
			} else {
				tr[i].style.display = "none";
			}
		}
	}
}
function exportData() {
	/* Get the HTML data using Element by Id */
	var table = document.getElementById("conference-list");
	console.log(table);
	/* Declaring array variable */
	var rows = [];

	// iterate through rows of table
	for (var i = 0, row; row = table.rows[i]; i++) {
		// rows would be accessed using the "row" variable assigned in the for loop
		// Get each cell value/column from the row
		column1 = row.cells[0].innerText;
		column2 = row.cells[1].innerText;
		column3 = row.cells[2].innerText;
		column4 = row.cells[3].innerText;
		column5 = row.cells[4].innerText;
		//console.log(column4);
		//console.log(column5);
		/* add a new records in the array */
		rows.push([ column1, column2, column3, column4, column5 ]);

	}
	csvContent = "data:text/csv;charset=utf-8,";
	/*
	 * add the column delimiter as comma(,) and each row splitted by new line
	 * character (\n)
	 */
	rows.forEach(function(rowArray) {
		row = rowArray.join(",");
		csvContent += row + "\r\n";
	});

	/* create a hidden <a> DOM node and set its download attribute */
	var encodedUri = encodeURI(csvContent);
	var link = document.createElement("a");
	link.setAttribute("href", encodedUri);
	link.setAttribute("download", "Session_Report.csv");
	document.body.appendChild(link);
	/* download the data file named "Stock_Price_Report.csv" */
	link.click();
}
