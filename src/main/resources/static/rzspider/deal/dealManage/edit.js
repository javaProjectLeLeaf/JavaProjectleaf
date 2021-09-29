$("#form-bookmanage-edit").validate({
    submitHandler : function(form) {
        update();
    }
});
// 绑定监听事件
$(function() {
        $('#status').bind('change', function () {
            var status = $(this).val();
            if (status == "none") {
                $("#status1").show();
            } else {
                $("#status1").hide();
            }
        });
    });

function update() {
	var id = $("input[name='id']").val();
    var userName = $("input[name='userName']").val();
    var balance = $("input[name='balance']").val();
    var record = $("input[name='record']").val();
    var status = $("#status").val();
    if (status == "none") {
        status = $("#status1").val();
    }

    $.ajax({
		cache : true,
		type : "POST",
		url : ctx + "deal/dealManage/save",
		data : {
			"id" : id,
            "userName" : userName,
            "balance" : balance,
            "status" : status,
            "record" : record,

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
