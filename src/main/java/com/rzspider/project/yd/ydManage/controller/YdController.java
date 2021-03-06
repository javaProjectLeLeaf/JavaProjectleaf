package com.rzspider.project.yd.ydManage.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.rzspider.common.constant.*;
import com.rzspider.common.constant.project.BookConstant;
import com.rzspider.common.utils.FileUploadUtils;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.framework.web.domain.Message;
import com.rzspider.framework.web.page.TableDataInfo;
import com.rzspider.project.book.bookmanage.utils.ExcelUtils;
import com.rzspider.project.common.file.utilt.FileUtils;
import com.rzspider.project.monitor.job.domain.Job;
import com.rzspider.project.system.role.domain.Role;
import com.rzspider.project.system.role.mapper.RoleMapper;
import com.rzspider.project.system.user.domain.User;
import com.rzspider.project.system.user.mapper.UserMapper;
import com.rzspider.project.system.user.mapper.UserRoleMapper;
import com.rzspider.project.yd.ydManage.domain.YwInfo;
import com.rzspider.project.yd.ydManage.domain.YwMod;
import com.rzspider.project.yd.ydManage.mapper.YwInfoMapper;
import com.rzspider.project.yd.ydManage.mapper.YwModMapper;
import com.rzspider.project.yw.ywManage.domain.YwInfoList;
import org.apache.commons.io.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.util.JAXBSource;
import java.beans.SimpleBeanInfo;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/yd/ydManage")
public class YdController extends BaseController{
    private List<YwInfo> ywInfos;
    @Resource
    private YwInfoMapper ywInfoMapper;
    @Resource
    private YwModMapper ywModMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private FilePathConfig filePathConfig;

    /**
     * ????????????????????????
     */
    @GetMapping("/view")
    public String view(){
        return "yd/ydManage/ydManage";
    }

    /**
     * ??????????????????
     * @return
     */
    @PostMapping("/list")
    @ResponseBody
    public JSONObject list(YwInfo ywInfo) {
        JSONObject jsonObject = new JSONObject();
        Long userid=getUserId();
        List<Long> roles = userRoleMapper.selectRoleIdByUserId(userid);
        if(roles.contains(6L)){//6????????????????????????????????????????????????????????????????????????6????????????ID?????????????????????
            jsonObject.put("role","isAdmin");
        }else {
            jsonObject.put("role","noAdmin");
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (StringUtils.isNotEmpty(ywInfo.getStartStr())) {
            try {
                ywInfo.setStart(simpleDateFormat.parse(ywInfo.getStartStr()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if( StringUtils.isNotEmpty(ywInfo.getEndStr())){
            try {
                ywInfo.setEnd(simpleDateFormat.parse(ywInfo.getEndStr()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(StringUtils.isNotEmpty(ywInfo.getYwTitle())){
            ywInfo.setYwTitle("%"+ywInfo.getYwTitle()+"%");
        }
        PageHelper.startPage(ywInfo.getPage() + 1, ywInfo.getRows());
        List<YwInfo> ywInfos = ywInfoMapper.selectAll(ywInfo);
        int i = ywInfoMapper.selectPage(ywInfo);
        jsonObject.put("total", i);
        jsonObject.put("data", ywInfos);
        return jsonObject;
    }

    /**
     * ????????????
     * @param ywInfo
     * @return
     */
    @PostMapping("/addYw")
    @ResponseBody
    public JSONObject addYw(YwInfo ywInfo){
        JSONObject jsonObject = new JSONObject();
        ywInfo.setOpId(103);
        ywInfo.setStaffName("?????????");
        ywInfo.setBillId("18888888888");
        ywInfo.setCreateDate(new Date());
        ywInfo.setStatus(1);
        int i = ywInfoMapper.insert(ywInfo);
        if(i>0){
            jsonObject.put("msg","success");
            return jsonObject;
        }
        jsonObject.put("msg","error");
        return jsonObject;
    }

    /**
     * ???????????????????????????
     */
    @PostMapping("/yw_type")
    @ResponseBody
    public JSONObject yw_type(){
        JSONObject jsonObject = new JSONObject();
        List<YwMod> ywMods = ywModMapper.selectAllByType();
        jsonObject.put("data",ywMods);
        return jsonObject;
    }

    /**
     * ???????????????????????????
     */
    @ResponseBody
    @PostMapping("/ywDtl/{ytype}")
    public JSONObject ywDtl(@PathVariable("ytype") String ywType){
        JSONObject jsonObject = new JSONObject();
        List<YwMod> ywMods = ywModMapper.selectDtlByType(ywType);
        jsonObject.put("data",ywMods);
        return jsonObject;
    }

    /**
     * ????????????
     */
    @PostMapping("/editYw")
    @ResponseBody
    public JSONObject editYw(YwInfo ywInfo){
        JSONObject jsonObject = new JSONObject();
        ywInfo.setUpdateDate(new Date());
        int i = ywInfoMapper.updateByPrimaryKeySelective(ywInfo);
        if(i>0){
            jsonObject.put("msg","success");
            return jsonObject;
        }
        jsonObject.put("msg","error");
        return jsonObject;
    }

    /**
     * ????????????
     */
    @ResponseBody
    @PostMapping("/ywOffLine")
    public JSONObject ywOffLine(YwInfo ywInfo){
        JSONObject jsonObject = new JSONObject();
        ywInfo.setDeleteDate(new Date());
        ywInfo.setStatus(2);
        int i = ywInfoMapper.updateByPrimaryKeySelective(ywInfo);
        if(i>0){
            jsonObject.put("msg","success");
            return jsonObject;
        }
        jsonObject.put("msg","error");
        return jsonObject;
    }

    /**
     * ????????????
     */
    @ResponseBody
    @PostMapping("/ywOnLine")
    public JSONObject ywOnLine(YwInfo ywInfo){
        JSONObject jsonObject = new JSONObject();
        ywInfo.setRestoreDate(new Date());
        ywInfo.setStatus(1);
        int i = ywInfoMapper.updateByPrimaryKeySelective(ywInfo);
        if(i>0){
            jsonObject.put("msg","success");
            return jsonObject;
        }
        jsonObject.put("msg","error");
        return jsonObject;
    }

    /**
     * id??????????????????
     */
    @ResponseBody
    @PostMapping("/ywMore")
    public JSONObject ywMore(YwInfo ywInfo){
        JSONObject jsonObject = new JSONObject();
        YwInfo ywInfo1 = ywInfoMapper.selectByPrimaryKey(ywInfo.getId());
        jsonObject.put("data",ywInfo1);
        return jsonObject;
    }





    /**
     * ????????????????????????
     */
    @PostMapping("/whList")
    @ResponseBody
    public JSONObject whList(YwMod ywMod) {
        PageHelper.startPage(ywMod.getPage() + 1, ywMod.getRows(),"id desc");
        if(StringUtils.isNotEmpty(ywMod.getYwType())){
            ywMod.setYwType("%"+ywMod.getYwType()+"%");
        }
        if(StringUtils.isNotEmpty(ywMod.getYwDtl())){
            ywMod.setYwDtl("%"+ywMod.getYwDtl()+"%");
        }
        JSONObject jsonObject = new JSONObject();
        List<YwMod> ywMods = ywModMapper.selectAll(ywMod);
        int total = ywModMapper.selectPage(ywMod);
        jsonObject.put("data", ywMods);
        jsonObject.put("total",total);
        return jsonObject;
    }

    /**
     * ??????????????????
     */
    @PostMapping("/addYwWh")
    @ResponseBody
    public JSONObject addYwWh(YwMod ywMod){
        JSONObject jsonObject = new JSONObject();
        if(ywMod.getId()==null){
            int s = ywModMapper.insertSelective(ywMod);
            if(s>0){
                jsonObject.put("msg","success");
                return jsonObject;
            }
            jsonObject.put("msg","error");
            return jsonObject;
        }else{
            int s = ywModMapper.updateByPrimaryKeySelective(ywMod);
            if(s>0){
                jsonObject.put("msg","success");
                return jsonObject;
            }
            jsonObject.put("msg","error");
            return jsonObject;
        }

    }

    /**
     * ????????????????????????
     */
    @PostMapping("/deleteWh")
    @ResponseBody
    public JSONObject deleteWh(YwMod ywMod){
        JSONObject jsonObject = new JSONObject();
        int i = ywModMapper.deleteByPrimaryKey(ywMod.getId());
        if(i>0){
            jsonObject.put("msg","success");
            return jsonObject;
        }
        jsonObject.put("msg","error");
        return jsonObject;
    }

    /**
     * ????????????
     * @param file
     * @param request
     * @param blogFileName
     * @return
     * @throws IOException
     */
    @ResponseBody
    @PostMapping("/uploadImgFile")
    public Message uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request, String blogFileName)
            throws IOException {
        String basePath = FilePathConfig.getUploadBlogPath();
        // ?????????????????????????????????
        try {
            FileUploadUtils.assertAllowedSetSize(file, 2 * 1024 * 1024);
        } catch (FileUploadBase.FileSizeLimitExceededException e1) {
            e1.printStackTrace();
            return Message.error(FileMessageConstant.FILE_MESSAGE_SIZE_GREATER_SIZE);
        }
        // ????????????
        String fileName = file.getOriginalFilename();

        // ?????????
        fileName = FileUploadUtils.renameToUUID(fileName);
        if (blogFileName == null || CommonSymbolicConstant.EMPTY_STRING.equals(blogFileName)
                || CommonConstant.UNDEFINED.equals(blogFileName)) {
            blogFileName = "blogset";
        } else {
            // ?????? blogFileName

        }
        // ?????????
        try {
            FileUploadUtils.uploadFile(file.getBytes(), basePath + File.separator + blogFileName, fileName);
        } catch (Exception e) {
            return Message.error();
        }
        // String imgbase64String = OtherConstant.OTHER_DATAIMAGE
        // +
        // fileName.substring(fileName.lastIndexOf(CommonSymbolicConstant.POINT)
        // + 1)
        // + OtherConstant.OTHER_BASE64 + new
        // String(Base64.encodeBase64(file.getBytes()));
        // ????????????????????????
        String imgbase64String = FileOtherConstant.FILE_JUMP_PATH_PREFIX3 + blogFileName + File.separator + fileName;
        Message message = new Message();
        message = message.success();
        message.put(ReturnMessageConstant.RETURN_MESSAGE_KEY_1, imgbase64String);
        // message.put(ReturnMessageConstant.RETURN_MESSAGE_KEY_2,
        // fileName);
        return message;

    }

    /**
     * ??????excel
     * @param response
     * @param ywInfo
     */
    @GetMapping("/batchExport")
    public void batchExport(HttpServletResponse response, YwInfo ywInfo) {
        try {
            response.reset();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            // ?????????????????????
            List<YwInfo> bmList = ywInfoMapper.selectAll(ywInfo);
            if (bmList == null || bmList.size() < 1) {
                return;
            }
            // ??????xls
            XSSFWorkbook workbook = ExcelUtils.createExcelFileYwInfo(bmList);
            // workbook???????????????
            ExcelUtils.writeWBToStream(workbook, outputStream);
            byte[] data = outputStream.toByteArray();

            String excelName = "yw.xlsx";
            // ??????????????????
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
     * ????????????
     * @param response
     * @throws IOException
     */
    @GetMapping("/downExcelTemplate")
    public void downExcelTemplate(HttpServletResponse response) throws IOException {
        response.reset();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // ??????xls
        XSSFWorkbook workbook = ExcelUtils.createYdExcelFile();
        // workbook???????????????
        ExcelUtils.writeWBToStream(workbook, outputStream);
        byte[] data = outputStream.toByteArray();

        String excelName = "????????????.xlsx";
        // ??????????????????
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
     * ????????????Excel???????????????????????????
     */
    @ResponseBody
    @PostMapping("/batchAnalyzeList")
    public Message upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        ywInfos = null;
        // ?????????????????????????????????
        try {
            FileUploadUtils.assertAllowed(file);
        } catch (FileUploadBase.FileSizeLimitExceededException e1) {
            e1.printStackTrace();

            ywInfos.clear();
            return Message.error(FileMessageConstant.FILE_MESSAGE_SIZE_GREATER_TEN_M);
        }
        // ????????????
        String fileName = file.getOriginalFilename();
        if (!fileName.toLowerCase().endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_EXCEL_XLS)
                && !fileName.toLowerCase().endsWith(FileExtensionConstant.FILE_EXTENSION_POINT_EXCEL_XLSX)) {
            ywInfos.clear();
            return Message.error(FileMessageConstant.FILE_MESSAGE_FORMAT_INCORRENT);
        }
        // ?????????
        fileName = FileUploadUtils.renameToUUID(fileName);
        // ?????????
        try {
            FileUploadUtils.uploadFile(file.getBytes(), filePathConfig.getUploadCachePath(), fileName);
        } catch (Exception e) {
            ywInfos.clear();
            return Message.error();
        }
        // ?????????????????????
        File f = new File(filePathConfig.getUploadCachePath() + File.separator + fileName);
        if (!f.exists()) {
            ywInfos = null;
        }
        List<YwInfo> ywInfoLists1 = ExcelUtils.readYdExcel(f);
        // ?????????????????????
        if (f.exists()) {
            f.delete();
        }
        ywInfos = ywInfoLists1;
        if (ywInfos == null || ywInfos.size() < 1) {
            return Message.error(BookConstant.BOOK_MESSAGE_NO_DATA);
        }
        return Message.success();
    }

    /**
     * ????????????excel??????
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/batchAnalyzeList2")
    public TableDataInfo batchAnalyzeList2() {
        TableDataInfo tdi = getDataTable(ywInfos);
        return tdi;
    }
}
