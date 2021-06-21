$(document).ready(function() {

	$("#l1").click(function() {
		var info = {
			name : $("#d1").val(),
			password : $("#d2").val()
		};
		$.ajax({
			type : "POST",
			dataType : "json",
			data : {
				A : JSON.stringify(info)
			},
			contentType : "application/json",
			url : "rest/call/login",
			success : function(data) {
				var data1 = data.status;
				// alert("Hello " + _spPageContextInfo.userLoginName);
				if (data1 == "success") {
					// document.getElementById("s1").innerHTML="Hi "+info.name;
					window.location.href = "AdminFeedback.html"
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("Error!!");
				window.location.href = "WelcomeFeedback.html"
			}

		});

	});

});
