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
    $('#pay').bind('change', function () {
        var pay = $(this).val();
        if (pay == "none") {
            $("#pay1").show();
        } else {
            $("#pay1").hide();
        }
    });

    $('#carUser2').bind('change', function () {
        var carUser = $(this).val();
        if (carUser == "none") {
            $("#carUser").show();
        } else {
            $("#carUser").hide();
        }
    });
});


function save() {
    var code = $("input[name='code']").val();
    var indate = $("input[name='indate']").val();
    var outdate = $("input[name='outdate']").val();
    // var personid = $("input[name='personid']").val();
    var cost = $("input[name='cost']").val();
    var createid = $("input[name='createid']").val();
    // var createtime = $("input[name='createtime']").val();
    var pay = $("#pay").val();
    if (pay == "none") {
        pay = $("#pay1").val();
    }
    var status = $("#status").val();
    if (status == "none") {
        status = $("#status1").val();
    }
    var personid = $("#carUser").val();
    if (personid == "none") {
        personid = $("#carUser2").val();
    }

    var type = $("#radio1").is(':checked') == true ? 0
        : ($("#radio2").is(':checked') == true ? 1 : 2);

    $.ajax({
        cache: true,
        type: "POST",
        url: ctx + "car/carManage/save",
        data: {
            "code": code,
            "type": type,
            "indateStr": indate,
            "outdateStr": outdate,
            "personid": personid,
            "cost": cost,
            "status": status,
            "pay": pay,
            // "createtime" : createtime,
            "createid": createid,

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