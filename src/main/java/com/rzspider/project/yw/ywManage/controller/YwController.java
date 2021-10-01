package com.rzspider.project.yw.ywManage.controller;

import com.rzspider.common.constant.CommonSymbolicConstant;
import com.rzspider.common.constant.FileExtensionConstant;
import com.rzspider.common.constant.FileMessageConstant;
import com.rzspider.common.constant.project.BookConstant;
import com.rzspider.common.utils.FileUploadUtils;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.framework.aspectj.lang.annotation.Log;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.framework.web.domain.Message;
import com.rzspider.framework.web.page.TableDataInfo;
import com.rzspider.project.book.bookmanage.domain.Bookmanage;
import com.rzspider.project.book.bookmanage.utils.BookmanageUtils;
import com.rzspider.project.book.bookmanage.utils.ExcelUtils;
import com.rzspider.project.common.file.controller.FileController;
import com.rzspider.project.common.file.utilt.FileUtils;
import com.rzspider.project.system.user.domain.User;
import com.rzspider.project.yw.ywManage.domain.YwInfoList;
import com.rzspider.project.yw.ywManage.domain.YwModule;
import com.rzspider.project.yw.ywManage.mapper.YwInfoListMapper;
import com.rzspider.project.yw.ywManage.mapper.YwModuleMapper;
import org.apache.commons.io.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/yw/ywManage")
public class YwController extends BaseController {
    public List<YwInfoList> ywInfoLists;
    @Resource
    private YwInfoListMapper ywInfoListMapper;
    @Resource
    private YwModuleMapper ywModuleMapper;
    @Autowired
    private FilePathConfig filePathConfig;

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

    /**
     * 返回批量上传模板页面
     */
    @RequiresPermissions("yw:ywManage:addExcel")
    @RequestMapping("/addExport")
    public String addExport() {
        return "/yw/ywManage/batchAdd";
    }

    /**
     * 下载模板
     */
    @RequiresPermissions("yw:ywManage:downExcelTemplate")
    @GetMapping("/downExcelTemplate")
    public void downExcelTemplate(HttpServletResponse response) throws IOException {
        response.reset();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // 生成xls
        XSSFWorkbook workbook = ExcelUtils.createYwExcelFile();
        // workbook写入输出流
        ExcelUtils.writeWBToStream(workbook, outputStream);
        byte[] data = outputStream.toByteArray();

        String excelName = "业务模板.xlsx";
        // 处理中文乱码
        try {
            excelName = FileUtils.getNewString(excelName);
        } catch (Exception e) {
            e.printStackTrace();
            excelName = BookConstant.BOOK_DEFAULT_EXCELTEMPLATE_NAME;
        }
        response.setHeader(FileMessageConstant.FILE_CONTENT_DISPOSITION,
                FileMessageConstant.FILE_ATTACHMENT_FILENAME + excelName);
        response.addHeader(FileMessageConstant.FILE_CONTENT_LENGTH, CommonSymbolicConstant.EMPTY_STRING + data.length);
        response.setContentType(FileMessageConstant.FILE_CONTENT_TYPE);

        IOUtils.write(data, response.getOutputStream());
        response.getOutputStream().close();
    }

    /**
     * 批量上传Excel解析返回数据并显示
     */
    @RequiresPermissions("yw:ywManage:batchAnalyzeList")
    @ResponseBody
    @PostMapping("/batchAnalyzeList")
    public Message upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        ywInfoLists = null;
        // 判断文件大小，格式等等
        try {
            FileUploadUtils.assertAllowed(file);
        } catch (FileUploadBase.FileSizeLimitExceededException e1) {
            e1.printStackTrace();

            ywInfoLists.clear();
            return Message.error(FileMessageConstant.FILE_MESSAGE_SIZE_GREATER_TEN_M);
        }
        // 原始名字
        String fileName = file.getOriginalFilename();
        if (!fileName.toLowerCase().endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_EXCEL_XLS)
                && !fileName.toLowerCase().endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_EXCEL_XLSX)) {
            ywInfoLists.clear();
            return Message.error(FileMessageConstant.FILE_MESSAGE_FORMAT_INCORRENT);
        }
        // 重命名
        fileName = FileUploadUtils.renameToUUID(fileName);
        // 先上传
        try {
            FileUploadUtils.uploadFile(file.getBytes(), filePathConfig.getUploadCachePath(), fileName);
        } catch (Exception e) {
            ywInfoLists.clear();
            return Message.error();
        }
        // 解析并返回数据
        File f = new File(filePathConfig.getUploadCachePath() + File.separator + fileName);
        if (!f.exists()) {
            ywInfoLists = null;
        }
        List<YwInfoList> ywInfoLists1 = ExcelUtils.readYwExcel(f);
        // 删除复制的文件
        if (f.exists()) {
            f.delete();
        }
        ywInfoLists = ywInfoLists1;
        if (ywInfoLists == null || ywInfoLists.size() < 1) {
            return Message.error(BookConstant.BOOK_MESSAGE_NO_DATA);
        }
        return Message.success();
    }

    /**
     * 显示上传excel表格
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/batchAnalyzeList2")
    public TableDataInfo batchAnalyzeList2() {
        TableDataInfo tdi = getDataTable(ywInfoLists);
        return tdi;
    }

    /**
     * 保存上传的excel数据
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/batchSave")
    @RequiresPermissions("yw:ywManage:batchSave")
    public Message batchSave() {
        if (ywInfoLists == null || ywInfoLists.size() < 1) {
            ywInfoLists.clear();
            return Message.error("文件中没有数据或者数据不全");
        }
        for (int i = 0; i < ywInfoLists.size(); i++) {
            if (ywInfoListMapper.insert(ywInfoLists.get(i)) == 0) {
                ywInfoLists.clear();
                return Message.error("数据格式有误 无法上传");
            }
        }
        return Message.success();
    }

/**
 *返回业务管理界面
 */
    @RequiresPermissions("yw:ywManage:module")
    @RequestMapping("/module")
    public String module(){
        return "/yw/ywManage/module";
    }


    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    //文件上传相关代码
    @RequiresPermissions("yw:ywManage:upFuJian")
    @RequestMapping(value = "upFuJian")
    @ResponseBody
    public String upload(@RequestParam("test") MultipartFile file) {
        if (file.isEmpty()) {
            return "文件为空";
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        logger.info("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        logger.info("上传的后缀名为：" + suffixName);
        // 文件上传后的路径
        String filePath = "E://test//";
        // 解决中文问题，liunx下中文路径，图片显示问题
        // fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            return "上传成功";
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }

    //文件下载相关代码
    @RequiresPermissions("yw:ywManage:downFuJian")
    @RequestMapping("/downFuJian")
    public String downloadFile(org.apache.catalina.servlet4preview.http.HttpServletRequest request, HttpServletResponse response){
        String fileName = "FileUploadTests.java";
        if (fileName != null) {
            //当前是从该工程的WEB-INF//File//下获取文件(该目录可以在下面一行代码配置)然后下载到C:\\users\\downloads即本机的默认下载的目录
            String realPath = request.getServletContext().getRealPath(
                    "C://Users//Administrator//IdeaProjects//JavaProjectleaf//src//main//resources//static//");
            File file = new File(realPath, fileName);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition",
                        "attachment;fileName=" +  fileName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("success");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }
    //多文件上传
    @RequestMapping(value = "/batch/upload", method = RequestMethod.POST)
    @ResponseBody
    public String handleFileUpload(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request)
                .getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(new FileOutputStream(
                            new File(file.getOriginalFilename())));
                    stream.write(bytes);
                    stream.close();

                } catch (Exception e) {
                    stream = null;
                    return "You failed to upload " + i + " => "
                            + e.getMessage();
                }
            } else {
                return "You failed to upload " + i
                        + " because the file was empty.";
            }
        }
        return "upload successful";
    }
}
