$("#form-bookmanage-edit").validate({
	submitHandler : function(form) {
		update();
	}
});

// 绑定监听事件
// $(function() {
// 	$('#bookClassification').bind('change', function() {
// 		var bookClassification = $(this).val();
// 		if (bookClassification == "none") {
// 			$("#bookClassification2").show();
// 		} else {
// 			$("#bookClassification2").hide();
// 		}
// 	});
// 	$('#bookBinding').bind('change', function() {
// 		var bookBinding = $(this).val();
// 		if (bookBinding == "none") {
// 			$("#bookBinding2").show();
// 		} else {
// 			$("#bookBinding2").hide();
// 		}
// 	});
// });

function update() {
    var id = $("input[name='id']").val();
    var roomID = $("input[name='roomID']").val();
	var type = $("input[name='type']").val();
	var floor = $("input[name='floor']").val();
	var status = $("input[name='status']").val();
	var user = $("input[name='user']").val();
	var bz = $("input[name='bz']").val();
	var more = $("input[name='more']").val();
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "room/roommanage/save",
		data : {
            "id" : id,
            "roomID" : roomID,
			"type" : type,
			"floor" : floor,
			"status" : status,
			"user" : user,
			"bz" : bz,
			"more" : more
		},
		async : false,
		error : function(request) {
			$.modalAlert("系统错误", modal_status.FAIL);
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("修改成功,正在刷新数据请稍后……", {
					icon : 1,
					time : 500,
					shade : [ 0.1, '#fff' ]
				}, function() {
					$.parentReload();
				});
			} else {
				$.modalAlert(data.msg, modal_status.FAIL);
			}

		}
	});
}