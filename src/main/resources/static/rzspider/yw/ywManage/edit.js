$("#form-bookmanage-edit").validate({

	submitHandler : function(form) {
		update();
	}
});


function update() {
	var id = $("input[name='id']").val();
	var yw_title = $("input[name='yw_title']").val();
	var yw_content = $("input[name='yw_content']").val();
	var op_id = $("input[name='op_id']").val();
	var staff_name = $("input[name='staff_name']").val();
	var fee_file = $("input[name='fee_file']").val();
	var exfile = $("input[name='exfile']").val();
	var status = $("#status").val();
    var yw_type = $("#yw_type").val();
    if (yw_type == "none") {
        yw_type = $("#yw_type1").val();
    }
    var yw_dtl = $("#yw_dtl").val();
    if (yw_dtl == "none") {
        yw_dtl = $("#yw_dtl1").val();
    }

	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "yw/ywManage/save",
		data : {
			"id" : id,
			"yw_title" : yw_title,
			"yw_type" : yw_type,
			"yw_dtl" : yw_dtl,
			"yw_content" : yw_content,
			"op_id" : op_id,
			"staff_name" : staff_name,
			"fee_file" : fee_file,
			"exfile" : exfile,
            "status" : status,
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