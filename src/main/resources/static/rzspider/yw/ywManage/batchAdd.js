var prefix = ctx + "yw/ywManage";

// $(function() {
var columns = [
    {
        field : 'yw_title',
        title : '业务标题'
    },
    {
        field : 'yw_type',
        title : '业务大类'
    },
    {
        field : 'yw_dtl',
        title : '业务详细'
    },
    {
        field : 'yw_content',
        title : '业务内容'
    },
    {
        field : 'op_id',
        title : '创建人编号'
    },
    {

        field : 'staff_name',
        title : '创建人姓名'
    },
    {

        field : 'bill_id',
        title : '创建人联系方式',
        align : 'center',

    },
    {

        field : 'create_date',
        title : '创建时间'
    },
    {

        field : 'update_date',
        title : '更新时间'
    },
    {

        field: 'delete_date',
        title: '下线时间'
    },
    {

        field: 'restore_date',
        title: '恢复时间'
    },
    {

        field: 'status',
        title: '状态',
        formatter : function(value, row, index) {
            if (value == 0) {
                return '<span class="label label-success">下线</span>';
            } else if (value == 1) {
                return '<span class="label label-warning">正常</span>';
            }
        }

    },
    {
        visible : false,
        field: 'fee_file',
        title: '资费一览表附件'
    },
    {
        visible : false,
        field: 'exfile',
        title: '其他附件'
    },

];
$(function() {
	layui.use('upload', function() {
		var upload = layui.upload;
		// 执行实例
		var uploadInst = upload.render({
			elem : '#uploadExcel', // 绑定元素
			url : '/yw/ywManage/batchAnalyzeList', // 上传接口
			size : 10000,// 不超过10m
			accept : 'file',
			exts: 'xls|xlsx|XLS|XLSX',
			done : function(data) {
				if (data.code == 0) {
					document.getElementById("uploadExcel").setAttribute(
							"disabled", true);
					layer.msg("批量上传成功,正在刷新数据请稍后……", {
						icon : 1,
						time : 500,
						shade : [ 0.1, '#fff' ]
					}, function() {
					    alert(111)
						var url = prefix + "/batchAnalyzeList2";
						$.initTable2(columns, url);
						// 保存按钮可用
						document.getElementById("batchSave").removeAttribute(
								"disabled");
					});
				} else {
					$.modalAlert(data.msg, modal_status.FAIL);
				}

			},
		});
	});
});

/* 下载模板 */
function downExcelTemplate() {
	location.href = prefix + "/downExcelTemplate";
	layer.msg('正在下载模板,请稍后…', {
		icon : 1
	});
}

/* 批量保存 */
function batchSave() {
	$
			.ajax({
				cache : true,
				type : "POST",
				url : ctx + "yw/ywManage/batchSave",
				async : true,
				error : function(request) {
					$.modalAlert("系统错误", modal_status.FAIL);
				},
				beforeSend : function() {
					// 禁用按钮防止重复提交
					document.getElementById("batchSave").setAttribute(
							"disabled", true);
					parent.layer.msg("正在批量保存,请稍后……", {
						icon : 1,
						time : 0,
						shade : [ 0.1, '#fff' ]
					});
				},

				success : function(data) {
					if (data.code == 0) {
						parent.layer.msg("批量保存成功,正在刷新数据请稍后……", {
							icon : 1,
							time : 500,
							shade : [ 0.1, '#fff' ]
						}, function() {
							$.parentReload();
						});
					} else {
						$.modalAlert(data.msg, modal_status.FAIL);
					}

				},
				// 完成后取消禁用
				complete : function() {
					document.getElementById("batchSave").removeAttribute(
							"disabled");
					// 关闭提示窗
					layer.closeAll('dialog');
				},
			});
}
