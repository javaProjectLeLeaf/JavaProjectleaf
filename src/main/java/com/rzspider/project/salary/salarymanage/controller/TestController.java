package com.rzspider.project.salary.salarymanage.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.rzspider.common.constant.*;
import com.rzspider.common.utils.FileUploadUtils;
import com.rzspider.framework.aspectj.lang.annotation.Log;
import com.rzspider.framework.config.FilePathConfig;
import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.framework.web.domain.Message;
import com.rzspider.project.salary.salarymanage.domain.Salary;
import com.rzspider.project.salary.salarymanage.mapper.SalaryMapper;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 测试
 *
 * @author Leaf
 */
@Controller
@RequestMapping("/test/testManage")
public class TestController extends BaseController {

    @Resource
    private SalaryMapper salaryMapper;

    @GetMapping("test")
    public String testManage() {
        return "salary/test";
    }

    /**
     * 上传文件，直接获取内容存储到数据库
     */
    @ResponseBody
    @PostMapping("/uploadImgFile")
    public Message uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request, String blogFileName)
            throws IOException {
        String basePath = FilePathConfig.getUploadBlogPath();
        // 判断文件大小，格式等等
        try {
            FileUploadUtils.assertAllowedSetSize(file, 2 * 1024 * 1024);
        } catch (FileUploadBase.FileSizeLimitExceededException e1) {
            e1.printStackTrace();
            return Message.error(FileMessageConstant.FILE_MESSAGE_SIZE_GREATER_SIZE);
        }
        // 原始名字
        String fileName = file.getOriginalFilename();

        // 重命名
        fileName = FileUploadUtils.renameToUUID(fileName);
        if (blogFileName == null || CommonSymbolicConstant.EMPTY_STRING.equals(blogFileName)
                || CommonConstant.UNDEFINED.equals(blogFileName)) {
            blogFileName = "blogset";
        } else {
            // 验证 blogFileName

        }
        // 先上传
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
        // 如果直接返回路径
        String imgbase64String = FileOtherConstant.FILE_JUMP_PATH_PREFIX3 + blogFileName + File.separator + fileName;
        Message message = new Message();
        message = message.success();
        message.put(ReturnMessageConstant.RETURN_MESSAGE_KEY_1, imgbase64String);
        // message.put(ReturnMessageConstant.RETURN_MESSAGE_KEY_2,
        // fileName);
        return message;

    }

    @PostMapping("testList")
    @ResponseBody
    public JSONObject testList(Salary salary) {
        PageHelper.startPage(salary.getPage() + 1, salary.getRows());
        List<Salary> salaryList = salaryMapper.selectAll();
        int total = salaryMapper.selectCount();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", total);
        jsonObject.put("data", salaryList);
        return jsonObject;
    }

    @PostMapping("addSalary")
    @ResponseBody
    public JSONObject addSalary(Salary salary) {
        int i = salaryMapper.insertSelective(salary);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "success");
        return jsonObject;
    }


}
