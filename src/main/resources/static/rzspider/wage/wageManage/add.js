$("#form-bookmanage-add").validate({
	rules : {
		name : {
			required : true,
			maxlength : 10
		},
	},
	messages : {
		"name" : {
			maxlength : '最长10字'
		},
	},
	submitHandler : function() {
		add();
	}
});

// // 绑定监听事件
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
// 			$("#bookBinding2").hide();ghua
// 		}
// 	});
// });

function add() {
	alert(1111);
	var name = $("input[name='name']").val();
	var department = $("input[name='department']").val();
	var wage = $("input[name='wage']").val();
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "wage/wageManage/save",
		data : {
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
				parent.layer.msg("新增成功,正在刷新数据请稍后……", {
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