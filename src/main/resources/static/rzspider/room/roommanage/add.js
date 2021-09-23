$("#form-bookmanage-add").validate({
	rules : {
        roomID : {
			required : true,
			maxlength : 5
		},
        type : {
		},
        floor : {
			number : true,
			maxlength : 8,
		},
        status : {
			number : true,
			maxlength : 8,
		},
        user : {
			digits : true,
			maxlength : 5,
		},
        describe : {
            number : true,
            maxlength : 100,
        },
        more : {
            digits : true,
            maxlength : 5,
        },
    },
	messages : {
		"roomID" : {
			maxlength : '房间号不规范'
		},
		"type" : {
		},
		"floor" : {
			number : "必须是数字",
			maxlength : "最大8位数字",
		},
		"status" : {
			number : "必须是数字",
			maxlength : "最大8位数字",
		},
		"user" : {
			digits : "必须是数字",
			maxlength : "最大5位数字",
		},
        "describe" : {
            digits : "必须是数字",
            maxlength : "最大5位数字",
        },
        "more" : {
            digits : "必须是数字",
            maxlength : "最大5位数字",
        },
	},
	submitHandler : function(form) {
		add();
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

function add() {
	var roomID = $("input[name='roomID']").val();
	var type = $("input[name='type']").val();
	var floor = $("input[name='floor']").val();
	var status = $("input[name='status']").val();
	var user = $("input[name='user']").val();
	var describe = $("input[name='describe']").val();
	var more = $("input[name='more']").val();

	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "book/bookmanage/save",
		data : {
			"roomID" : roomID,
			"type" : type,
			"floor" : floor,
			"status" : status,
			"user" : user,
			"describe" : describe,
			"more" : more,

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