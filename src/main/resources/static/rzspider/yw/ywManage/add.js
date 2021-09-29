$("#form-bookmanage-add").validate({
	submitHandler : function(form) {
		add();
	}
});



function add() {
	var yw_title = $("input[name='yw_title']").val();
	var yw_content = $("input[name='yw_content']").val();
    var fee_file = $("input[name='fee_file']").val();
    var exfile = $("input[name='exfile']").val();
    var yw_type = $("#yw_type").val();
    if (yw_type == "none") {
        yw_type = $("#yw_type1").val();
    }
    var yw_dtl = $("#yw_dtl").val();
    if (yw_dtl == "none") {
        yw_dtl = $("#yw_dtl1").val();
    }
    alert(yw_dtl)

	$.ajax({
		cache : true,
		type : "POST",
		url : ctx + "yw/ywManage/save",
		data : {
			"yw_title" : yw_title,
			"yw_type" : yw_type,
			"yw_dtl" : yw_dtl,
			"yw_content" : yw_content,
			"fee_file" : fee_file,
			"exfile" : exfile,


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