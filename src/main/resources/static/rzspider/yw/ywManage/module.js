var prefix = ctx + "yw/ywManage"
//一堆对字段的条件限制
$(function() {
	var columns = [
			{
				checkbox : true
			},
			{

				field : 'id',
				title : '序列'
			},
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
                visible : false,
				field : 'op_id',
				title : '创建人编号'
			},
			{

				field : 'staff_name',
				title : '创建人姓名'
			},
			{
                visible : false,
				field : 'bill_id',
				title : '创建人联系方式',
				align : 'center',

			},
			{
                visible : false,
				field : 'create_date',
				title : '创建时间'
			},
			{
                visible : false,
				field : 'update_date',
				title : '更新时间'
			},
        {
            visible : false,
            field: 'delete_date',
            title: '下线时间'
        },
        {
            visible : false,
            field: 'restore_date',
            title: '恢复时间'
        },
        {
            visible : false,
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
			{
				title : '操作',
				align : 'center',
				formatter : function(value, row, index) {
					var actions = [];
                    actions.push('<a class="btn btn-primary btn-xs '
                        + detailFlag
                        + '" href="#" title="详情" mce_href="#" onclick="detail(\''
                        + row.id
                        + '\')"><i class="fa fa-search"></i>详情</a> ');
					actions.push('<a class="btn btn-warning btn-xs '

							+ '" href="#" title="资费表" onclick="tariff(\''
							+ row.id
							+ '\')"><i class="fa fa-remove"></i>资费表</a>');
                    if(row.status==0){//row是一条数据 下面按钮凭借到这条数据上
                        actions.push('<a class="btn btn-success btn-xs '
                            + removeFlag
                            + '" href="#" id="up" title="上线" mce_href="#" onclick="up(\''
                            + row.id
                            + '\')"><i class="fa fa-search"></i>上线</a> ');
                    } else{
                        actions.push('<a class="btn btn-warning btn-xs '
                            + removeFlag
                            + '" href="#" id="down" title="下线" onclick="down(\'' + row.id
                            + '\')"><i class="fa fa-remove"></i>下线</a>');
                    }

                    actions.push('<a class="btn btn-success btn-xs '
                        + editFlag
                        + '" href="#" title="修改" mce_href="#" onclick="edit(\''
                        + row.id
                        + '\')"><i class="fa fa-edit"></i>修改</a> ');

                    return actions.join('');
				}
			} ];
	var url = prefix + "/list";
	$.initTable(columns, url,'请选择业务大类');
});

/* 详情-详细 */
function detail(id) {
	var url = prefix + '/detail/' + id;
	layer_showAuto("业务详情", url);
}

//上传附件
function upFuJian() {
    var url = prefix + '/upFuJian';
    // 固定大小更宽
    layer_showAuto2("上传附件内容", url);
}

/* -新增业务 */
function add() {
	var url = prefix + '/add';
	layer_showAuto("新增业务", url);
}

/* 批量上传图书详情-新增 */
function addExport() {
	var url = prefix + '/addExport';
	// 固定大小更宽
	layer_showAuto2("批量上传图书", url);
}

/* 全部导出excel */
function batchExport() {
	var table1Length = document.getElementById("table1").rows[1].cells.length;
	alert(table1Length)
	if (table1Length == 1) {
		// 没有数据
		$.modalMsg("没有图书可供导出", "warning");
		return;
	} else {
		location.href = prefix + "/batchExport";
		layer.msg("正在导出图书,请稍后……", {
			icon : 1,
			shade : [ 0.1, '#fff' ]
		});

	}
}
// function batchExport(){
// 	var table1Length = $("#table1").rows[1].cells.length;
// 	if(table1Length==1){
//
// 	}
//
// }

/* 图书详情-修改 */
function edit(id) {
	var url = prefix + '/edit/' + id;
	layer_showAuto("业务修改", url);
}

// 修改业务状态
function down(id) {
	$.modalConfirm("确定下线业务？", function() {
		_ajax(prefix + "/remove/" + id, "", "post");
	})
}
function up(id) {
    $.modalConfirm("确定上线业务？", function() {
        _ajax(prefix + "/remove/" + id, "", "post");
    })
}

// 批量删除
function batchRemove() {
	var rows = $.getSelections("bookId");
	if (rows.length == 0) {
		$.modalMsg("请选择要删除的数据", "warning");
		return;
	}
	$.modalConfirm("确认要删除选中的" + rows.length + "条数据吗?", function() {
		_ajax(prefix + '/batchRemove', {
			"ids" : rows
		}, "post");
	});

    function module() {
        var url =prefix + '/module';
        layer_showAuto("业务明细管理", url);
    }


}
