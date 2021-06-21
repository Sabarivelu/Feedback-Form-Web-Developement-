var i = 0;
var j = 0;
var m = 0;
var l = 0;
async function data() {
	// alert("inside data");
	const result = await fetch("http://localhost:8085/Shopkart/rest/call/getFeedback", {
		method : 'POST',
		headers : {
			'Content-type' : 'application/json'
		}
	});
	const response = await
	result.json();
	console.log(response);
	addRow1(response);
}
function addRow1(sessions) {
	for (var i = 0; i < sessions.length; i++) {
		var name = sessions[i].name;
		var desc = sessions[i].description;
		var venue = sessions[i].venue;
		var time = sessions[i].time;
		var breakTag = document.createElement("br");
		var list = document.getElementById("star5");
		//list.append(breakTag);
		var subList = document.createElement("div");
		subList.setAttribute("class", "star");
		subList.append(breakTag);
		subList.append(breakTag);
		var lname = document.createElement("li");
		var ldesc = document.createElement("li");
		var lvenue = document.createElement("li");
		var ltime = document.createElement("li");
		var lfeedback = document.createElement("li");
		var feedback = document.createTextNode("Feedback");
		var textBox = document.createElement("input");
		textBox.setAttribute("type", "text");
		textBox.setAttribute("id", "t"+ l++);
		textBox.setAttribute("placeholder", "Feedback..");
		textBox.setAttribute("style", "width:200px;height:70px");
		
		var buttonVal = document.createElement("button");
		buttonVal.setAttribute("type", "submit");
		buttonVal.setAttribute("id", "b1");
		buttonVal.setAttribute("onClick", "displayValue(" + j++ + ")");
		buttonVal.setAttribute("value", "Submit");
		lname.innerText = name;
		ldesc.innerText = desc;
		lvenue.innerText = venue;
		ltime.innerText = time;
		buttonVal.innerText = "Submit";
		lfeedback.innerText = "Feedback";
		var pTag = document.createElement("p");
		subList.append(breakTag);
		subList.append(lname);
		subList.append(breakTag);
		subList.append(ldesc);
		subList.append(breakTag);
		subList.append(lvenue);
		subList.append(breakTag);
		subList.append(ltime);
		subList.append(lfeedback);
		subList.append(textBox);
		subList.append(breakTag);
		subList.append(breakTag);
		subList.append(breakTag);
		subList.append(breakTag);
		subList.append(pTag);
		var spanTag = document.createElement("span");
		
		spanTag.setAttribute("class","star-rating");
		//spanTag.setAttribute("id","")
		var star1 = document.createElement("input");
		star1.setAttribute("type", "radio");
		star1.setAttribute("name", "5star");
		star1.setAttribute("id","r1");
		star1.setAttribute("class", "f1");
		star1.setAttribute("value", "5");
		var iTag1 = document.createElement("label");
		iTag1.setAttribute("for","r1");
		iTag1.innerHTML='&#10038';
		spanTag.append(star1);
		spanTag.append(iTag1);
		var star2 = document.createElement("input");
		star2.setAttribute("type", "radio");
		star2.setAttribute("name", "5star");
		star2.setAttribute("id","r2");
		star2.setAttribute("class", "f1");
		star2.setAttribute("value", "4");
		var iTag2 = document.createElement("label");
		iTag2.setAttribute("for","r2");
		iTag2.innerHTML='&#10038';
		spanTag.append(star2);
		spanTag.append(iTag2);
		var star3 = document.createElement("input");
		star3.setAttribute("type", "radio");
		star3.setAttribute("name", "5star");
		star3.setAttribute("id","r3");
		star3.setAttribute("class", "f1");
		//star3.setAttribute("class","fa fa-star-o");
		star3.setAttribute("value", "3");
		var iTag3 = document.createElement("label");
		iTag3.setAttribute("for","r3");
		iTag3.innerHTML='&#10038';
		spanTag.append(star3);
		spanTag.append(iTag3);
		var star4 = document.createElement("input");
		star4.setAttribute("type", "radio");
		star4.setAttribute("name", "5star");
		star4.setAttribute("id","r4");
		star4.setAttribute("class", "f1");
		// star4.setAttribute("class","fa fa-star-o");
		star4.setAttribute("value", "2");
		var iTag4 = document.createElement("label");
		iTag4.setAttribute("for","r4");
		iTag4.innerHTML='&#10038';
		spanTag.append(star4);
		spanTag.append(iTag4);
		var star5 = document.createElement("input");
		star5.setAttribute("type", "radio");
		star5.setAttribute("name", "5star");
		star5.setAttribute("id","r5");
		star5.setAttribute("class", "f1");
		// star5.setAttribute("class","fa fa-star-o");
		star5.setAttribute("value", "1");
		var iTag5 = document.createElement("label");
		iTag5.setAttribute("for","r5");
		iTag5.innerHTML='&#10038';
		spanTag.append(star5);
		spanTag.append(iTag5);
		spanTag.append(breakTag);
		spanTag.append(breakTag);
		subList.append(spanTag);
		subList.append(breakTag);
		subList.append(pTag);
		subList.append(buttonVal);
		list.append(subList);
		list.append(pTag);
	}
}

function myFunction() {
	var input, filter, table, tr, td, i, txtValue;
	input = document.getElementById("myInput");
	filter = input.value.toUpperCase();
	table = document.getElementsByClassName("star");
	console.log(table);
	for (i = 0; i < table.length; i++) {
		td = table[i].getElementsByTagName("li")[0];
		console.log(td);
		if (td) {
			txtValue = td.textContent || td.innerText;
			if (txtValue.toUpperCase().indexOf(filter) > -1) {
				table[i].style.display = "";
			} else {
				table[i].style.display = "none";
			}
		}
	}
}

function displayValue(n) {
	var x=n;
	var n;
	var par = document.getElementsByClassName('star')[x]
			.querySelectorAll("input[value]");
	for (var k = 0; k < par.length; k++) {
		if (par[k].checked)
			{
			n=par[k].value;
			}
	}
	var textData=document.getElementById("t"+x).value;
	var p=document.getElementById("t"+x).parentNode;
	var name=p.getElementsByTagName('li')[0].innerText;
	console.log(textData, n, name);
	var info={sname:name,rating:n,fdk:textData};
	
	$.ajax({
		type : "POST",
		dataType : "json",
		data : {
			A : JSON.stringify(info)
		},
		contentType : "application/json",
		url : "rest/call/Feedback",
		success : function(data) {
			var data1 = data.status;
			if (data1 == "success") {
				window.location.href = "ProductFeedback.html"
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			window.location.href = "ProductFeedback.html"
		}

	});


}
