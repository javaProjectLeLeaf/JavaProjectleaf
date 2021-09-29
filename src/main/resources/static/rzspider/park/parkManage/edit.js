$("#form-bookmanage-edit").validate({
	// rules : {
	// 	id : {
	// 		required : true,
	// 		maxlength : 30
	// 	},
	// 	name : {
	// 		digits : true,
	// 		maxlength : 13,
	// 		minlength : 10
	// 	},
	// 	sex : {
	// 		number : true,
	// 		maxlength : 8,
	// 	},
	// 	age : {
	// 		number : true,
	// 		maxlength : 8,
	// 	},
	// 	bookPages : {
	// 		digits : true,
	// 		maxlength : 5,
	// 	},
	//
	// },
	// messages : {
	// 	"bookName" : {
	// 		maxlength : '最长30字'
	// 	},
	// 	"bookIsbn" : {
	// 		digits : "必须是数字",
	// 		maxlength : "最大13位数字",
	// 		minlength : "最小10位数字"
	// 	},
	// 	"bookOriginalPrice" : {
	// 		number : "必须是数字",
	// 		maxlength : "最大8位数字",
	// 	},
	// 	"bookPurchasePrice" : {
	// 		number : "必须是数字",
	// 		maxlength : "最大8位数字",
	// 	},
	// 	"bookPages" : {
	// 		digits : "必须是数字",
	// 		maxlength : "最大5位数字",
	// 	},
	//  },
	submitHandler : function(form) {
		update();
	}
});

// 绑定监听事件
$(function() {
    $('#bookClassification').bind('change', function () {
        var sex = $(this).val();
        if (sex == "none") {
            $("#sex").show();
        } else {
            $("#sex").hide();
        }
    });
});

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
	var name = $("input[name='name']").val();
	var sex = $("#bookClassification").val();
	var age = $("input[name='age']").val();
	var phone = $("input[name='phone']").val();
    var makeUserId = $("input[name='makeUserId']").val();
	if(sex=="none"){
		sex=$("#sex").val();
	}

	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "park/parkManage/save",
		data : {
			"id" : id,
			"name" : name,
			"sex" : sex,
			"age" : age,
			"phone" : phone,
            "makeUserId":makeUserId
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
