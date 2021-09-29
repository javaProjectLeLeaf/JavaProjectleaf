$("#form-bookmanage-add").validate({
	rules : {
        roomID : {
		},
        type : {
		},
        floor : {
		},
        status : {
		},
        user : {
		},
        bz : {
        },
        more : {
        },
    },
	messages : {
		"roomID" : {
		},

	},
	submitHandler : function(form) {
		add();
	}
});

// 绑定监听事件

function add() {
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
			"roomID" : roomID,
			"type" : type,
			"floor" : floor,
			"status" : status,
			"user" : user,
			"bz" : bz,
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