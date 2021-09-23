package com.rzspider.project.room.roommanage.controller;

import com.rzspider.framework.aspectj.lang.annotation.Log;
import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.project.book.bookmanage.domain.Bookmanage;
import com.rzspider.project.room.roommanage.mapper.RoomMapper;
import com.rzspider.project.room.roommanage.mapper.RoomUserMapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/room/roommanage")
public class RoomController extends BaseController {
    private String prefix = "room/roommanage";
    public List<Bookmanage> bmList;
    private RoomMapper roomMapper;
    private RoomUserMapper roomUserMapper;

    //显示住宿房间信息
    @Log(title = "住宿管理", action = "查看住宿信息")
    @GetMapping()
    @RequiresPermissions("room:roommanage:view")
    public String roommanage() {
        return prefix + "/roommanage";
    }

    //添加宿舍信息
    @Log(title = "住宿管理", action = "添加房间信息")
    @RequiresPermissions("room:roommanage:add")
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

}
