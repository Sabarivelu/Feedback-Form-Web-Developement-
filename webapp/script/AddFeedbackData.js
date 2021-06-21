
	var sessions = JSON.parse(localStorage.getItem('sessions')) || [];
	var name = document.getElementById('d1').value;
	var description = document.getElementById('d2').value;
	var venue = document.getElementById('d3').value;
	var time = document.getElementById('d4').value;
	var obj = {
		name : name,
		description : description,
		venue : venue,
		time : time
	};
	// obj.name=name;
	// obj.description=description;
	// obj.venue=venue;
	// obj.time=time;
	sessions.push(obj);
	localStorage.setItem('sessions', JSON.stringify(sessions));

$(document).ready(function() {
	$("#f1").click(function() {
	var info = {
		name : $("#d1").val(),
		description : $("#d2").val(),
		venue : $("#d3").val(),
		time : $("#d4").val()
	};
	//alert(info.name);
	// alert(info.image);
	$.ajax({
		type : "POST",
		dataType : 'json',
		data : {
			A : JSON.stringify(info)
		},
		contentType : "application/json",
		url : "rest/call/addFeedback",
		success : function(data) {
			//alert("Inside success func");
			//alert("My data!!!" + data.status);
			var data1 = data.status;
			if (data.status == "success") {
				window.location.href = "AdminFeedback.html"
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {

			window.location.href = "CreateSessionFeedback.html"
		}
	});

	});

});
