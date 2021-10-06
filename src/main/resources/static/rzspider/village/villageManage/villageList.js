define(function (require, exports, module) {
    var Utils = require("global/utils.js");
    var Page = Utils.initPage('villageListHtml');
    var exportCmd = null;
    var exportDoorCmd = null;
    var isAdmin = false;
    var isManager=false;
    var isContractor = false;
    var village = {
        init: function () {
            this.staffAllRole();
            this.isContractor();
            var _form = Page.findId('queryVillageListForm');
            this.getAllCountNames(_form);//页面县区列表
            this.getAllCardList();
        },

        staffAllRole: function () {
            var cmd = 'opId=' + LOGINUSER.operatorId;
            Rose.ajax.postJson(srvMap.get('selectRoleList'), cmd, function (json) {
                if (json.rows) {
                    for (var i = 0; i < json.rows.length; i++) {
                        if (json.rows[i].ROLE_ID == 14001) {
                            isAdmin = true;
                            break;
                        }else if (json.rows[i].ROLE_ID == 5001){
                            isManager=true;
                            break;
                        }
                    }
                }
            });
        },

        isContractor: function () {
            var cmd = 'opId=' + LOGINUSER.operatorId;
            Rose.ajax.postJson(srvMap.get('isContractor'), cmd, function (json) {
                if (json.rows) {
                    if (json.rows.length > 0) {
                        isContractor = true;
                    }
                }
            });
        },

        //县区列表 == 查询
        getAllCountNames: function (_form) {
            var self = this;
            var cmd = 'countyType=' + '2';
            Rose.ajax.postJson(srvMap.get('allCounty'), cmd, function (json) {
                if (!json) return;
                var html = '<option value=""' + '>' + '请选择' + '</option>';
                for (var i in json.rows) {
                    html += '<option value=' + json.rows[i].countyCode + '>' + json.rows[i].countyName + '</option>';
                }
                $(".countyCode").html(html);
                $(".countyCode").unbind('change');
                $(".countyCode").bind('change', function () {
                    self.getRegion($(this).val(), _form);
                });

            });
        },
        getRegion: function (name, _form) {
            var regionName = _form.find("[name='regionId']");
            var cmd = 'name=' + name;
            var html = '<option value=""' + '>' + '请选择' + '</option>';
            Rose.ajax.postJson(srvMap.get('partOfRegion'), cmd, function (json) {
                for (var e in json.rows) {
                    html += '<option value=' + json.rows[e].regionId + '>' + json.rows[e].regionName + '</option>';
                }
                regionName.html(html);
            });
        },

        getAllCardList: function () {
            var self = this;
            var _dom = Page.findId('getVillageList');
            var _form = Page.findId('queryVillageListForm');
            var _domPagination = _dom.find("[name='pagination']");
            Utils.setSelectData(_form);
            var cmd = _form.serialize();
            exportCmd = cmd;
            Utils.getServerPage(srvMap.get('allVillageList'), cmd, function (json) {
                var template = Handlebars.compile(Page.findTpl('getVillageList'));
                _dom.find("[name='content']").html(template(json.rows));
                if (isAdmin || isContractor) {
                    Page.findId('getVillageList').find("[name='export1']").removeClass('hide');
                    Page.findId('getVillageList').find("[name='update']").removeClass('hide');
                    Page.findId('getVillageList').find("[name='selectUser']").removeClass('hide');
                }else if (isManager){
                    Page.findId('getVillageList').find("[name='export1']").removeClass('hide');
                    Page.findId('getVillageList').find("[name='update']").addClass('hide');
                    Page.findId('getVillageList').find("[name='selectUser']").addClass('hide');
                    Page.findId('queryVillageListForm').find("[id ='countyCodeId']").addClass('hide');
                    Page.findId('queryVillageListForm').find("[id ='regionIds']").addClass('hide');
                }
                self.queryAllCardList();
                self.exportCards();
                self.updatePlanConfigBtn();
                self.detailPlanConfigBtn();
                self.selectBuilding();
                self.stockDetail();
                self.selectUserclick();
            }, _domPagination, 10, 6);
        },
        //小区导出
        exportCards: function () {
            Page.findId('export').bind('click', function () {
                $(this).attr("href", srvMap.get('exportVillageList') + "?" + exportCmd);
                Utils.downCover();
            })
        },
        queryAllCardList: function () {
            var self = this;
            var dom = Page.findId('getVillageList');
            var form = Page.findId('queryVillageListForm');
            var query = form.find("[name='query']");
            var pagination = dom.find("[name='pagination']");
            Utils.setSelectData(form);
            query.unbind('click');
            query.bind('click', function () {
                var cmd = form.serialize();
                cmd = cmd + '&opId=' + LOGINUSER.operatorId;
                exportCmd = cmd;
                Utils.getServerPage(srvMap.get('allVillageList'), cmd, function (json) {
                    var template = Handlebars.compile(Page.findTpl('getVillageList'));
                    dom.find("[name='content']").html(template(json.rows));
                    if (isAdmin || isContractor) {
                        Page.findId('getVillageList').find("[name='export1']").removeClass('hide');
                        Page.findId('getVillageList').find("[name='update']").removeClass('hide');
                        Page.findId('getVillageList').find("[name='selectUser']").removeClass('hide');
                    }else if (isManager){
                        Page.findId('getVillageList').find("[name='export1']").removeClass('hide');
                        Page.findId('getVillageList').find("[name='update']").addClass('hide');
                        Page.findId('getVillageList').find("[name='selectUser']").addClass('hide');
                    }
                    self.exportCards();
                    self.updatePlanConfigBtn();
                    self.detailPlanConfigBtn();
                    self.selectBuilding();
                    self.stockDetail();
                    self.selectUserclick();
                }, pagination, 10, 6);
            })
        },
        /**
         * 详情点击
         * @param _this
         */
        detailPlanConfigBtn: function () {
            var _dom = Page.findId('getVillageList');
            var _update = _dom.find("[name='detail']");
            var _modal = Page.findModal('detailVillageListModal');
            var _form = Page.findId('detailVillageListForm');
            _update.unbind('click');
            _update.bind('click', function () {
                var _this = $(this);
                var _data = Utils.getTableOneRow(_this);
                if (_data) {
                    _form.find("[name='id']").val(_data.id);
                    _form.find("[name='property']").val(_data.property);
                    _form.find("[name='buildDate']").val(_data.buildDate);
                    _form.find("[name='phone']").val(_data.phone);
                    _form.find("[name='address']").val(_data.address);
                    _form.find("[name='countyName']").val(_data.countyName);
                    _form.find("[name='regionName']").val(_data.regionName);
                    _form.find("[name='villageName']").val(_data.villageName);
                    _form.find("[name='contractorName']").val(_data.contractorName);
                    _form.find("[name='scene']").val(_data.scene);
                    _modal.modal('show');
                }
            })
        },
        /**
         * 修改点击
         * @param _this
         */
        updatePlanConfigBtn: function () {
            $("#idNo").text("小区信息");
            var self = this;
            var _dom = Page.findId('getVillageList');
            var _update = _dom.find("[name='update']");
            _update.unbind('click');
            _update.bind('click', function () {
                var _this = $(this);
                var _data = Utils.getTableOneRow(_this);
                if (_data) {
                    var _modal = Page.findModal('updateVillageListModal');
                    var _form = Page.findId('updateVillageListForm');
                    if (isContractor) {
                        $("#contractorName").addClass('hide');
                    }
                    _modal.modal('show');
                    Utils.setSelectData(_form, '', function () {
                        _form.find("[name='contractorName']").val(_data.contractorId);
                    });
                    _form.find("[name='id']").val(_data.id);
                    _form.find("[name='property']").val(_data.property);
                    _form.find("[name='buildDate']").val(_data.buildDate);
                    _form.find("[name='phone']").val(_data.phone);
                    _form.find("[name='address']").val(_data.address);
                    _form.find("[name='countyName']").val(_data.countyName);
                    _form.find("[name='regionName']").val(_data.regionName);
                    _form.find("[name='villageName']").val(_data.villageName);
                    self.saveUpdatePlanConfigInfo();
                }
            })
        },

        //小区修改保存
        saveUpdatePlanConfigInfo: function () {
            var self = this;
            var _form = Page.findId('updateVillageListForm');
            var _modal = Page.findModal('updateVillageListModal');
            var _saves = _modal.find("[name='saves']");
            _saves.unbind('click');
            _saves.bind('click', function () {
                if (!_form.find("[name='contractorName']").val()) {
                    window.XMS.msgbox.show('承包人不能为空', 'error', 1000);
                    return;
                }
                var cmd = _form.serialize();
                _saves.attr("disable", true);
                Rose.ajax.postJson(srvMap.get('updateVillageDetail'), cmd, function (status) {
                    _saves.attr("disable", false);
                    if (status.retMessage == "success") {
                        window.XMS.msgbox.show('修改成功', 'success', 2000);
                        $("#JS_updateVillageListForm")[0].reset();
                        _modal.modal('hide');
                        setTimeout(function () {
                            $('#up1').selectpicker('refresh');
                            self.getAllCardList();
                        }, 1000)
                    } else {
                        $("#JS_updateVillageListForm")[0].reset();
                        _modal.modal('hide');
                        window.XMS.msgbox.show('修改失败,' + status.retMessage, 'error', 3000);
                    }
                })
            });
        },
        /**
         * 幢弹窗
         * @param _this
         */
        selectBuilding: function () {
            var self = this;
            var _dom = Page.findId('getVillageList');
            var _update = _dom.find("[name='building']");
            _update.unbind('click');
            _update.bind('click', function () {
                var _this = $(this);
                var _data = Utils.getTableOneRow(_this);
                if (_data) {
                    $("#idUserNo").text(_data.villageName);
                    var _modal = Page.findModal('selectBuildingModal');
                    _modal.modal('show');
                    self.allSoxUserList(_data);
                }
            });
        },
        /**
         *幢列表页查询
         * @param cmd
         */
        allSoxUserList: function (_data) {
            var self = this;
            var _dom = Page.findId('getBuildingList');
            var _form = Page.findId('selectBuildingForm');
            var _domPagination = _dom.find("[name='pagination']");
            var cmd = _form.serialize();
            cmd = cmd + '&villageId=' + _data.id;
            Utils.getServerPage(srvMap.get('allBuildingList'), cmd, function (json) {
                var template = Handlebars.compile(Page.findTpl('getAllBuildingList'));
                _dom.find("[name='content']").html(template(json.rows));
            }, _domPagination, 10, 6);
            self.queryAllSoxUserList(_data);
        },

        //按条件查询幢
        queryAllSoxUserList: function (_data) {
            var _dom = Page.findId('getBuildingList');
            var _form = Page.findId('selectBuildingForm');
            var _domPagination = _dom.find("[name='pagination']");
            var _queryUser = _form.find("[name='queryUser']");
            _queryUser.unbind("click");
            _queryUser.bind('click', function () {
                var cmd = _form.serialize();
                cmd = cmd + '&villageId=' + _data.id;
                Utils.getServerPage(srvMap.get('allBuildingList'), cmd, function (status) {
                    if (status.retCode != 404) {
                        var template = Handlebars.compile(Page.findTpl('getAllBuildingList'));
                        _dom.find("[name='content']").html(template(status.rows));
                    } else {
                        window.XMS.msgbox.show('查询失败,' + status.retMessage, 'error', 3000);
                    }
                }, _domPagination, 10, 6);
            })
        },
        /**
         * 户信息
         */
        stockDetail: function () {
            var self = this;
            var dom = Page.findId('getVillageList');
            var stock = dom.find("[name='stock']");
            var modal = Page.findModal('stockModal');
            stock.unbind('click');
            stock.bind('click', function () {
                modal.modal('show');
                var _this = $(this);
                var _data = Utils.getTableOneRow(_this);
                if (_data) {
                    $("#idUserNo3").text(_data.villageName);
                    self.allDoorList(_data);
                }
            })
        },
        /**
         *户列表页查询
         * @param cmd
         */
        allDoorList: function (_data) {
            var self = this;
            var _dom = Page.findId('getDoorList');
            var _form = Page.findId('selectDoorForm');
            var _domPagination = _dom.find("[name='pagination']");
            var cmd = _form.serialize();
            cmd = cmd + '&villageId=' + _data.id;
            exportDoorCmd = cmd;
            Utils.getServerPage(srvMap.get('allDoorList'), cmd, function (json) {
                var template = Handlebars.compile(Page.findTpl('getStockList'));
                _dom.find("[name='content']").html(template(json.rows));
                if (isAdmin || isContractor) {
                    Page.findId('getDoorList').find("[name='export2']").removeClass('hide');
                }else if (isManager){
                    Page.findId('getDoorList').find("[name='export2']").removeClass('hide');
                    Page.findId('getDoorList').find("[name ='update']").addClass('hide')
                }
                self.updateDoorPlanConfigBtn(_data);
                self.detailDoorPlanConfigBtn();
                self.exportDoors();
                self.getAllDetailList(_data);
            }, _domPagination, 10, 6);
            self.queryDoorAllSoxUserList(_data);
        },

        //户导出
        exportDoors: function () {
            Page.findId('export1').bind('click', function () {
                $(this).attr("href", srvMap.get('exportDoorList') + "?" + exportDoorCmd);
                Utils.downCover();
            })
        },
        //按条件查询户
        queryDoorAllSoxUserList: function (_data) {
            var self = this;
            var _dom = Page.findId('getDoorList');
            var _form = Page.findId('selectDoorForm');
            var _domPagination = _dom.find("[name='pagination']");
            var _queryUser = _form.find("[name='queryUser']");
            _queryUser.unbind("click");
            _queryUser.bind('click', function () {
                var cmd = _form.serialize();
                cmd = cmd + '&villageId=' + _data.id;
                exportDoorCmd = cmd;
                Utils.getServerPage(srvMap.get('allDoorList'), cmd, function (status) {
                    if (status.retCode != 404) {
                        var template = Handlebars.compile(Page.findTpl('getStockList'));
                        _dom.find("[name='content']").html(template(status.data.rows));
                    } else {
                        window.XMS.msgbox.show('查询失败,' + status.retMessage, 'error', 3000);
                    }
                    self.updateDoorPlanConfigBtn(_data);
                    self.detailDoorPlanConfigBtn();
                    self.exportDoors();
                }, _domPagination, 10, 6);
            })
        },
        /**
         * 户详情点击
         * @param _this
         */
        detailDoorPlanConfigBtn: function () {
            var _dom = Page.findId('getDoorList');
            var _update = _dom.find("[name='detail']");
            var _modal = Page.findModal('detailDoorListModal');
            var _form = Page.findId('detailDoorListForm');
            _update.unbind('click');
            _update.bind('click', function () {
                var _this = $(this);
                var _data = Utils.getTableOneRow(_this);
                if (_data) {
                    _form.find("[name='id']").val(_data.id);
                    _form.find("[name='building']").val(_data.building);
                    _form.find("[name='unit']").val(_data.unit);
                    _form.find("[name='door']").val(_data.door);
                    _form.find("[name='phone']").val(_data.phone);
                    _form.find("[name='ifinstallWb']").val(_data.ifinstallWb == 1 ? '安装' : '未安装');
                    _form.find("[name='wbTypes']").val(_data.wbTypes);
                    _form.find("[name='wbNumber']").val(_data.wbNumber);
                    _form.find("[name='expirationTime']").val(_data.expirationTime);

                    _form.find("[name='threemonthAvgarpu']").val(_data.threemonthAvgarpu);
                    _form.find("[name='surname']").val(_data.surname);
                    _form.find("[name='cost']").val(_data.cost);
                    _form.find("[name='mixWb']").val(_data.mixWb);
                    _form.find("[name='wbTv']").val(_data.wbTv);
                    _form.find("[name='remarks']").val(_data.remarks);
                    _form.find("[name='rangerTime']").val(_data.rangerTime);
                    _form.find("[name='nakedwbTime']").val(_data.nakedwbTime);
                    _form.find("[name='ifrenewable']").val(_data.ifrenewable);
                    _form.find("[name='mixHost']").val(_data.mixHost);
                    _form.find("[name='mixMin']").val(_data.mixMin);
                    _form.find("[name='ifwbSilent']").val(_data.ifwbSilent);
                    _form.find("[name='iftvSilent']").val(_data.iftvSilent);
                    _form.find("[name='ex1']").val(_data.ex1);
                    _form.find("[name='ex2']").val(_data.ex2);
                    _form.find("[name='cfFlag']").val(_data.cfFlag);
                    _modal.modal('show');
                }
            })
        },
        /**
         * 户修改点击
         * @param _this
         */
        updateDoorPlanConfigBtn: function (_datas) {
            var self = this;
            var _dom = Page.findId('getDoorList');
            var _update = _dom.find("[name='update']");
            _update.unbind('click');
            _update.bind('click', function () {
                var _this = $(this);
                var _data = Utils.getTableOneRow(_this);
                if (_data) {
                    var _modal = Page.findModal('updateDoorListModal');
                    var _form = Page.findId('updateDoorListForm');
                    _datas.id = _data.villageId;
                    _form.find("[name='id']").val(_data.id);
                    _form.find("[name='building']").val(_data.building);
                    _form.find("[name='unit']").val(_data.unit);
                    _form.find("[name='door']").val(_data.door);
                    _form.find("[name='phone']").val(_data.phone);
                    _form.find("[name='wbTypes']").val(_data.wbTypes);
                    _form.find("[name='wbNumber']").val(_data.wbNumber);
                    _form.find("[name='expirationTime']").val(_data.expirationTime);
                    _form.find("[name='ifinstallWb']").val(_data.ifinstallWb);

                    _form.find("[name='threemonthAvgarpu']").val(_data.threemonthAvgarpu);
                    _form.find("[name='surname']").val(_data.surname);
                    _form.find("[name='cost']").val(_data.cost);
                    _form.find("[name='mixWb']").val(_data.mixWb);
                    _form.find("[name='wbTv']").val(_data.wbTv);
                    _form.find("[name='remarks']").val(_data.remarks);
                    _form.find("[name='rangerTime']").val(_data.rangerTime);
                    _form.find("[name='nakedwbTime']").val(_data.nakedwbTime);
                    _form.find("[name='ifrenewable']").val(_data.ifrenewable);
                    _form.find("[name='mixHost']").val(_data.mixHost);
                    _form.find("[name='mixMin']").val(_data.mixMin);
                    _form.find("[name='ifwbSilent']").val(_data.ifwbSilent);
                    _form.find("[name='iftvSilent']").val(_data.iftvSilent);
                    _form.find("[name='ex1']").val(_data.ex1);
                    _modal.modal('show');
                    self.saveUpdateDoorPlanConfigInfo(_datas);
                }
            })
        },

        //户修改保存
        saveUpdateDoorPlanConfigInfo: function (_datas) {
            var self = this;
            var _form = Page.findId('updateDoorListForm');
            var _modal = Page.findModal('updateDoorListModal');
            var _saves = _modal.find("[name='saves']");
            _saves.unbind('click');
            _saves.bind('click', function () {
                var cmd = _form.serialize();
                _saves.attr("disable", true);
                Rose.ajax.postJson(srvMap.get('updateDoorDetail'), cmd, function (status) {
                    _saves.attr("disable", false);
                    if (status.retMessage == "success") {
                        window.XMS.msgbox.show('修改成功', 'success', 2000);
                        $("#JS_updateDoorListForm")[0].reset();
                        _modal.modal('hide');
                        setTimeout(function () {
                            $('#up1').selectpicker('refresh');
                            self.allDoorList(_datas);
                        }, 1000)
                    } else {
                        $("#JS_updateDoorListForm")[0].reset();
                        _modal.modal('hide');
                        window.XMS.msgbox.show('修改失败,' + status.retMessage, 'error', 3000);
                    }
                })
            });
        },
        getAllDetailList: function (_data) {
            var self = this;
            var dom = Page.findId('getDoorList');
            var add = dom.find("[name='addAllCard']");
            var modal = Page.findModal('detailModal');
            self.saveBuildings(_data);
            add.unbind('click');
            add.bind('click', function () {
                self.select1(_data);
                self.selectedVillage();
                self.getAllBuildings(_data);
                modal.modal('show');
            });
        },
        select1: function (_data) {
            var self = this;
            var form = Page.findId('queryDetailForm');
            //排摸人模态框
            var select5 = form.find("[name='select1']");
            select5.unbind('click');
            select5.bind('click', function () {
                var modal1 = Page.findModal('selectProjectNameUpdateModal');
                modal1.modal('show');
                var select5 = modal1.find("[name='select5']");
                var pagination = modal1.find("[name='pagination']");
                var _form = Page.findId('selectProjectManagerUpdateForm');
                var _dom = Page.findId('getProjectNameUpdateList');
                select5.unbind('click');
                select5.bind('click', function () {
                    var cmd = _form.serialize();
                    if (!modal1.find("[name='opName']").val() && !modal1.find("[name='code']").val() && !modal1.find("[name='orgName']").val()) {
                        window.XMS.msgbox.show('请进行一项或者多项条件筛选', 'error', 1000);
                        return;
                    }
                    Utils.getServerPage(srvMap.get('getAllNoOpListSelect'), cmd, function (json) {
                        var template = Handlebars.compile(Page.findTpl('getProjectNameUpdateList'));
                        _dom.find("[name='content']").html(template(json.rows));
                        Utils.eventTrClickCallback(Page.findId('selectProjectManagerUpdateForm'));
                        Utils.selectAll(Page.findId('selectProjectManagerUpdateForm').find('table'));
                        Page.findId('selectProjectManagerUpdateForm').show();
                        self.selectProjectNameUpdate(form, _data);
                    }, pagination, 10, 6);
                })
            });
        },
        selectProjectNameUpdate: function (form, _data) {
            var self = this;
            var dom = Page.findId('getProjectNameUpdateList');
            var select7 = dom.find("[name='select7']");
            var modal1 = Page.findModal('selectProjectNameUpdateModal');
            select7.unbind('click');
            select7.bind('click', function () {
                var data = Utils.getTableOneRow($(this));
                if (data) {
                    form.find("[name='rangerName']").val(data.stallStaffName);
                    form.find("[name='rangerId']").val(data.stallOpId);
                    window.XMS.msgbox.show('选择成功', 'success', 1000);
                    modal1.modal('hide');
                    self.getAllSelectedBuildings(_data, data.stallOpId);
                }
            })
        },
        //已选择幢列表 查询
        getAllSelectedBuildings: function (_data, opId) {
            var cmd = 'villageId=' + _data.id + '&rangerId=' + opId;
            Rose.ajax.postJson(srvMap.get('getAllSelectedBuildings'), cmd, function (json) {
                var arr = json.rows.buildings.split(',');
                $('#up').selectpicker('val', arr);
                $('#up').selectpicker('refresh');
            });
        },
        //幢列表 查询
        getAllBuildings: function (_data) {
            var cmd = 'villageId=' + _data.id;
            Rose.ajax.postJson(srvMap.get('getBuildings'), cmd, function (json) {
                if (!json) return;
                var html = '';
                for (var i in json.rows) {
                    html += '<option value=' + json.rows[i].building + '>' + json.rows[i].building + '</option>';
                }
                $(".selectPicker").html(html);
                $('.selectPicker').selectpicker('refresh');
            });
        },
        selectedVillage: function () {
            $(".selectPicker").selectpicker({
                noneSelectedText: '请选择'//默认显示内容
            });
            //初始化刷新数据
            $(window).on('load', function () {
                $('.selectPicker').selectpicker('refresh');
            });
        },
        //排摸人保存
        saveBuildings: function (_datas) {
            var self = this;
            var _form = Page.findId('queryDetailForm');
            var _modal = Page.findModal('detailModal');
            var _saves = _modal.find("[name='save']");
            _saves.unbind('click');
            _saves.bind('click', function () {
                if (!_form.find("[name='building']").val()) {
                    window.XMS.msgbox.show('幢不能为空', 'error', 1000);
                    return;
                }
                var buildings = [];
                _form.find("[name='building']").each(function () {
                    buildings.push($(this).val());
                });
                var buildingsStr = buildings.join(',');
                var cmd = _form.serialize();
                cmd = cmd + '&buildingsStr=' + buildingsStr + '&villageId=' + _datas.id;
                _saves.attr("disable", true);
                Rose.ajax.postJson(srvMap.get('saveBuildings'), cmd, function (status) {
                    _saves.attr("disable", false);
                    if (status.retMessage == "success") {
                        window.XMS.msgbox.show('保存成功', 'success', 2000);
                        $("#JS_queryDetailForm")[0].reset();
                        _modal.modal('hide');
                        setTimeout(function () {
                            $('#up').selectpicker('refresh');
                            self.allDoorList(_datas);
                        }, 1000)
                    } else {
                        $("#JS_queryDetailForm")[0].reset();
                        _modal.modal('hide');
                        window.XMS.msgbox.show('保存失败,' + status.retMessage, 'error', 3000);
                    }
                })
            });
        },

        /**
         * 选择排摸人弹窗
         * @param _this
         */
        selectUserclick: function () {
            $("#idUserNo").text("");
            var self = this;
            var _dom = Page.findId('getVillageList');
            var _update = _dom.find("[name='selectUser']");
            _update.unbind('click');
            _update.bind('click', function () {
                var _this = $(this);
                var _data = Utils.getTableOneRow(_this);
                if (_data) {
                    var _modal = Page.findModal('selectSoxPointUserModal');
                    _modal.modal('show');
                    self.allVillageUserList(_data);
                }
            });
        },

        /**
         *排摸人列表页查询
         * @param cmd
         */
        allVillageUserList: function (_data) {
            var self = this;
            var _dom = Page.findId('getUserList');
            var _form = Page.findId('selectSoxPointUserForm');
            var _domPagination = _dom.find("[name='pagination']");
            var cmd = _form.serialize();
            cmd = cmd + '&villageId=' + _data.id;
            Utils.getServerPage(srvMap.get('allVillageRangerList'), cmd, function (json) {
                var template = Handlebars.compile(Page.findTpl('getAllSoxPointUserList'));
                _dom.find("[name='content']").html(template(json.rows));
                //排摸人保存
                self.selectUserInsert(_data);
                //排摸人删除
                self.delSoxPonitUser(_data);
            }, _domPagination, 10, 6);
            self.queryAllSoxUserList1(_data);
        },

        //按条件查询排摸人
        queryAllSoxUserList1: function (_data) {
            var self = this;
            var _dom = Page.findId('getUserList');
            var _form = Page.findId('selectSoxPointUserForm');
            var _domPagination = _dom.find("[name='pagination']");
            var _queryUser = _form.find("[name='queryUser']");
            _queryUser.unbind("click");
            _queryUser.bind('click', function () {
                var cmd = _form.serialize();
                cmd = cmd + '&villageId=' + _data.id;
                Utils.getServerPage(srvMap.get('allVillageRangerList'), cmd, function (status) {
                    if (status.retCode != 404) {
                        var template = Handlebars.compile(Page.findTpl('getAllSoxPointUserList'));
                        _dom.find("[name='content']").html(template(status.rows));
                        self.selectUserInsert(_data)
                        //排摸人删除
                        self.delSoxPonitUser(_data);
                    } else {
                        window.XMS.msgbox.show('查询失败,' + status.retMessage, 'error', 3000);
                    }
                }, _domPagination, 10, 6);
            })
        },
        //排摸人保存
        selectUserInsert: function (_data) {
            var self = this;
            var _dom = Page.findId('getUserList');
            var _select = _dom.find("[name='select']");
            _select.unbind('click');
            _select.bind('click', function () {
                var _this = $(this);
                var _dataUser = Utils.getTableOneRow(_this);
                var cmd = 'villageId=' + _data.id + '&userName=' + _dataUser.userName + '&opId=' + _dataUser.opId;
                Rose.ajax.postJson(srvMap.get('saveVillageRanger'), cmd, function (status) {
                    if (status.retMessage == "success") {
                        window.XMS.msgbox.show('保存成功', 'success', 2000);
                        setTimeout(function () {
                            self.allVillageUserList(_data);
                        }, 1000)
                    } else if (status.data.retCode == 2019) {
                        window.XMS.msgbox.show('保存失败', status.retMessage, 'error', 3000);
                        setTimeout(function () {
                            self.allVillageUserList(_data);
                        }, 1000)
                    } else {
                        window.XMS.msgbox.show('保存失败,' + status.retMessage, 'error', 3000);
                    }
                })
            })
        },
        //排摸人删除
        delSoxPonitUser: function (_data) {
            var self = this;
            var _dom = Page.findId('getUserList');
            var del = _dom.find("[name='del']");
            del.unbind('click');
            del.bind('click', function () {
                var _this = $(this);
                var _dataUser = Utils.getTableOneRow(_this);
                var cmd = 'id=' + _dataUser.id;
                Rose.ajax.postJson(srvMap.get('deleteVillageRanger'), cmd, function (status) {
                    if (status.retMessage == "success") {
                        window.XMS.msgbox.show('取消成功', 'success', 2000);
                        setTimeout(function () {
                            self.allVillageUserList(_data);
                        }, 1000)
                    } else {
                        window.XMS.msgbox.show('取消失败,' + status.retMessage, 'error', 3000);
                    }
                })
            })
        }
    };

    module.exports = village;
});