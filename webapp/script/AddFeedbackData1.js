$(document).ready(function() {
	$("#f1").click(function() {
	var info = {
		name : $("#d1").val(),
		description : $("#d2").val(),
		venue : $("d3").val(),
		time : $("#d4").val()
	};
	alert(info.name);
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
			alert("Inside success func");
			alert("My data!!!" + data.status);
			var data1 = data.status;
			if (data.status == "success") {
				window.location.href = "AdminFeedback.html"
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {

			window.location.href = "Admin.html"
		}
	});

	});

});
