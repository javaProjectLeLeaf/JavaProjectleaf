$("#form-bookmanage-add").validate({
    submitHandler: function (form) {
        save();
    }
});



// 绑定监听事件
$(function () {
    $('#status').bind('change', function () {

        var status = $(this).val();
        if (status == "none") {
            $("#status1").show();
        } else {
            $("#status1").hide();
        }
    });

});


function save() {
    var userName = $("input[name='userName']").val();
    var balance = $("input[name='balance']").val();
    var record = $("input[name='record']").val();
    var status = $("#status").val();
    if (status == "none") {
        status = $("#status1").val();
    }

    alert(balance)
    alert(status)
    $.ajax({
        cache: true,
        type: "POST",
        url: ctx + "deal/dealManage/save",
        data: {
            "userName": userName,
            "balance": balance,
            "status": status,
            "record": record,

        },
        async: false,
        error: function (request) {
            $.modalAlert("系统错误", modal_status.FAIL);
        },
        success: function (data) {
            if (data.code == 0) {
                parent.layer.msg("新增成功,正在刷新数据请稍后……", {
                    icon: 1,
                    time: 500,
                    shade: [0.1, '#fff']
                }, function () {
                    $.parentReload();
                });
            } else {
                $.modalAlert(data.msg, modal_status.FAIL);
            }

        }
    });
}