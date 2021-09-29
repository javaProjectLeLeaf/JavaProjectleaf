package com.rzspider.project.yw.ywManage.controller;

import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.FileMessageConstant;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.framework.web.domain.Message;
import com.rzspider.framework.web.page.TableDataInfo;
import com.rzspider.project.book.bookmanage.utils.ExcelUtils;
import com.rzspider.project.common.file.utilt.FileUtils;
import com.rzspider.project.system.user.domain.User;
import com.rzspider.project.yw.ywManage.domain.YwInfoList;
import com.rzspider.project.yw.ywManage.domain.YwModule;
import com.rzspider.project.yw.ywManage.mapper.YwInfoListMapper;
import com.rzspider.project.yw.ywManage.mapper.YwModuleMapper;
import org.apache.commons.io.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/yw/ywManage")
public class YwController extends BaseController {
    @Resource
    private YwInfoListMapper ywInfoListMapper;
    @Resource
    private YwModuleMapper ywModuleMapper;

    /**
     * 跳转主菜单页面
     *
     * @return
     */
    @RequiresPermissions("yw:ywManage:view")
    @GetMapping()
    public String ywManageList() {
        return "/yw/ywManage/ywManage";
    }

    /**
     * 获取业务详情列表
     */
    @RequiresPermissions("yw:ywManage:list")
    @GetMapping("/list")
    @ResponseBody
    public TableDataInfo list(YwInfoList ywInfoList1) {
        //实体类必须继承BaseEntity 获取其属性searchValue 可在sql中查询次属性 做模糊查询
        startPage();
        List<YwInfoList> ywInfoLists = ywInfoListMapper.selectAboutType(ywInfoList1);
        return getDataTable(ywInfoLists);
    }

    /**
     * 返回新增业务的页面
     */
    @RequiresPermissions("yw:ywManage:add")
    @GetMapping("/add")
    public String add() {
        return "yw/ywManage/add";
    }

    /**
     * id为空保存业务信息
     */
    @RequiresPermissions("yw:ywManage:save")
    @PostMapping("/save")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public Message save(YwInfoList ywInfoList) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm");
        User user = getUser();
        if (user.getUserId() != null && StringUtils.isNotEmpty(user.getUserName())) {
            ywInfoList.setOp_id(user.getUserId().intValue());
            ywInfoList.setStaff_name(user.getUserName());
            ywInfoList.setCreate_date(new Date());
            ywInfoList.setUpdate_date(new Date());
        }
        if (ywInfoList.getId() == null) {
            int i = ywInfoListMapper.insert(ywInfoList);
            if (i > 0) {
                return Message.success();
            }
            return Message.error();
        }
        int i = ywInfoListMapper.updateByPrimaryKey(ywInfoList);
        if (i > 0) {
            return Message.success();
        }
        return Message.error();
    }

    /**
     * 业务类型下拉框
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/yw_type")
    public List<YwModule> type() {
        User user = getUser();
        List<YwModule> lists = ywModuleMapper.selectType();
        return lists;
    }

    @ResponseBody
    @PostMapping("/edit/yw_type")
    public List<YwModule> type3() {
        User user = getUser();
        List<YwModule> lists = ywModuleMapper.selectType();
        return lists;
    }


    /**
     * 详细业务下拉框
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/yw_dtl/{yw_type}")
    public List<YwModule> del(@PathVariable("yw_type") String yw_type) {
        User user = getUser();
        List<YwModule> lists = ywModuleMapper.selectByType(yw_type);
        return lists;
    }

    @ResponseBody
    @PostMapping("/edit/yw_dtl/{yw_type}")
    public List<YwModule> del3(@PathVariable("yw_type") String yw_type) {
        User user = getUser();
        List<YwModule> lists = ywModuleMapper.selectByType(yw_type);
        return lists;
    }

    /**
     * 返回业务修改页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("yw:ywManage:edit")
    public String edit(@PathVariable("id") Integer id, Model model) {
        YwInfoList ywInfoList = ywInfoListMapper.selectByPrimaryKey(id);
        model.addAttribute(ywInfoList);
        return "/yw/ywManage/edit";
    }

    /**
     * 返回业务详情页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("yw:ywManage:more")
    public String detail(@PathVariable("id") Integer id, Model model) {
        YwInfoList ywInfoList = ywInfoListMapper.selectByPrimaryKey(id);
        model.addAttribute(ywInfoList);
        return "/yw/ywManage/detail";
    }

    /**
     * 修改业务状态  业务上下线
     */
    @PostMapping("/remove/{id}")
    @RequiresPermissions("yw:ywManage:status")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public Message status(@PathVariable("id") Integer id) {
        YwInfoList ywInfoList = ywInfoListMapper.selectByPrimaryKey(id);
        if (ywInfoList.getStatus() == 0) {
            ywInfoList.setStatus(1);//上线状态
            ywInfoList.setRestore_date(new Date());
            int i = ywInfoListMapper.updateByPrimaryKey(ywInfoList);
            if (i > 0) {
                return Message.success("成功修改状态为正常");
            }
        } else {
            ywInfoList.setStatus(0);//下线状态
            ywInfoList.setDelete_date(new Date());
            int i = ywInfoListMapper.updateByPrimaryKey(ywInfoList);
            if (i > 0) {
                return Message.success("成功修改状态为下线");
            }
        }
        return Message.error("状态修改失败");
    }

    /**
     * 业务内容和业务明细联动显示
     *
     * @param yw_dtl
     * @return
     */
    @ResponseBody
    @PostMapping("/yw_content/{yw_dtl}")
    public YwModule content(@PathVariable("yw_dtl") String yw_dtl) {
        YwModule ywModule = ywModuleMapper.selectYwContent(yw_dtl);
        return ywModule;
    }

    /**
     * 返回上传附件的输入页面
     */
    @RequiresPermissions("yw:ywManage:upFuJian")
    @RequestMapping("upFuJian")
    public String upFuJian() {
        return "/yw/ywManage/upFuJian";
    }


    /**
     * 批量导出excel
     */
    @RequiresPermissions("yw:ywManage:batchExcel")
//    @Log(title = "图书管理", action = "个人图书管理-批量导出excel")
    @GetMapping("/batchExport")
    public void batchExport(HttpServletResponse response, YwInfoList ywInfoList) {
        try {
            response.reset();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            // 数据库读取数据
            List<YwInfoList> bmList = ywInfoListMapper.selectAll();
            if (bmList == null || bmList.size() < 1) {
                return;
            }
            // 生成xls
            XSSFWorkbook workbook = ExcelUtils.createExcelFile1(bmList);
            // workbook写入输出流
            ExcelUtils.writeWBToStream(workbook, outputStream);
            byte[] data = outputStream.toByteArray();

            String excelName = "yw.xlsx";
            // 处理中文乱码
            try {
                excelName = FileUtils.getNewString(excelName);
            } catch (Exception e) {
                e.printStackTrace();
                excelName = "ywerro.xlsx";
            }
            response.setHeader(FileMessageConstant.FILE_CONTENT_DISPOSITION,
                    FileMessageConstant.FILE_ATTACHMENT_FILENAME + excelName);
            response.addHeader(FileMessageConstant.FILE_CONTENT_LENGTH,
                    CommonSymbolicConstant.EMPTY_STRING + data.length);
            response.setContentType(FileMessageConstant.FILE_CONTENT_TYPE);
            IOUtils.write(data, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//public byte[] batchExport(YwInfoList ywInfoList) {
//    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//    // 数据库读取数据
//    List<YwInfoList> bmList = ywInfoListMapper.selectAll(YwInfoList);
//    if (bmList == null || bmList.size() < 1) {
//        return null;
//    }
//    // 生成xls
//    XSSFWorkbook workbook = ExcelUtils.createExcelFile(bmList);
//    // workbook写入输出流
//    ExcelUtils.writeWBToStream(workbook, outputStream);
//    return outputStream.toByteArray();
//}


}
