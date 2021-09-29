$("#form-bookmanage-add").validate({
	rules : {
		name : {

		},
		sex : {

		},
		age : {

		},
		phone : {
		},
        makeUserId : {
        },

	},
	messages : {
		"name" : {
		},
		"sex" : {
		},
		"age" : {
		},
		"phone" : {
		},
        "makeUserId" : {
        },

	},
	submitHandler : function() {
		add();
	}
});

// 绑定监听事件
$(function() {
	$('#bookClassification').bind('change', function() {
		var sex = $(this).val();
		if (sex == "none") {
			$("#sex").show();
		} else {
			$("#sex").hide();
		}
	});
});


function add() {

	var name = $("input[name='name']").val();
	var sex = $("#bookClassification").val();
	var age = $("input[name='age']").val();
	var phone = $("input[name='phone']").val();
    var makeUserId = null;
    if (sex == "none") {
        sex = $("#sex").val();
    }
	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "park/parkManage/save",
		data : {
			"name" : name,
			"sex" : sex,
			"age" : age,
			"phone" : phone,
            "makeUserId":makeUserId,
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