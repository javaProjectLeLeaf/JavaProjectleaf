$("#form-bookmanage-edit").validate({

	submitHandler : function(form) {
		update();
	}
});

// 绑定监听事件
$(function() {
	$('#bookClassification').bind('change', function() {
		var bookClassification = $(this).val();
		if (bookClassification == "none") {
			$("#bookClassification2").show();
		} else {
			$("#bookClassification2").hide();
		}
	});
	$('#bookBinding').bind('change', function() {
		var bookBinding = $(this).val();
		if (bookBinding == "none") {
			$("#bookBinding2").show();
		} else {
			$("#bookBinding2").hide();
		}
	});
});

function update() {
    alert(1111)
	var id = $("input[name='id']").val();
	var name = $("input[name='name']").val();
	var department = $("input[name='department']").val();
	var wage = $("input[name='wage']").val();

	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "wage/wageManage/save",
		data : {
			"id" : id,
			"name" : name,
			"department" : department,
			"wage" : wage
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