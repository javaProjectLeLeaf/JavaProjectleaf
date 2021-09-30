/**
 * [description]demo-用户性别
 * @param  {[type]} value)}
 * @return {[type]} [description]
 */
Handlebars.registerHelper("transUserSex", function (value) {
    if (value === "1") {
        return "男";
    } else if (value === "2") {
        return "女";
    }
});