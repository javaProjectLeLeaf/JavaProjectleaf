var prefix = ctx + "deal/dealManage";//ctx和分页有关，不然就会报错
//一堆对字段的条件限制
$(function() {
	var columns = [
			{
				checkbox : true
			},
			{
                visible : false,
				field : 'id',
				title : 'id'
			},
			{
				field : 'userName',
				title : '用户名'
			},
        {
            field : 'balance',
            title : '余额'
        },
        {
            field: 'status',
            title: '状态',
            formatter : function(value, row, index) {
                if (value == 0) {
                    return '<span class="label label-success">有效</span>';
                } else if (value == 1) {
                    return '<span class="label label-warning">失效</span>';
                }
            }
        },
			{
				field : 'record',
				title : '交易记录'
			},

			{
				title : '操作',
				align : 'center',
				formatter : function(value, row, index) {
					var actions = [];
					actions.push('<a class="btn btn-success btn-xs '
									+ editFlag
									+ '" href="#" title="编辑" mce_href="#" onclick="edit(\''
									+ row.id
									+ '\')"><i class="fa fa-edit"></i>编辑</a> ');
					// if(row.status==0){
                    //     actions.push('<a class="btn btn-primary btn-xs '
                    //         + leaveFlag
                    //         + '" href="#" id="lc" title="离场" mce_href="#" onclick="leave(\''
                    //         + row.id
                    //         + '\')"><i class="fa fa-search"></i>离场</a> ');
					// }
					// actions.push('<a class="btn btn-primary btn-xs '
					// 				+ detailFlag
					// 				+ '" href="#" title="详情" mce_href="#" onclick="detail(\''
					// 				+ row.id
					// 				+ '\')"><i class="fa fa-search"></i>详情</a> ');
					actions.push('<a class="btn btn-warning btn-xs '
							+ removeFlag
							+ '" href="#" title="删除" onclick="remove(\''
							+ row.id
							+ '\')"><i class="fa fa-remove"></i>删除</a>');
					return actions.join('');
				}
			} ];
	var url = prefix + "/list";
	$.initTable(columns, url,'请输入车牌号');
});
//吊添加接口返回添加页面
function add() {
    var url = prefix + '/add';
    layer_showAuto("添加用户信息", url);
}
//调用修改接口返回修改页面
function edit(id) {
    var url = prefix + '/edit/'+id;
    layer_showAuto("修改用户信息", url);
}
//调用删除接口
function remove(id) {
    $.modalConfirm("确定要删除次信息吗？", function() {
        _ajax(prefix + "/remove/" + id, "", "post");
    })
}