package com.rzspider.project.car.carManage.controller;

import com.rzspider.common.utils.StringUtils;
import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.framework.web.domain.Message;
import com.rzspider.framework.web.page.TableDataInfo;
import com.rzspider.project.car.carManage.domain.Car;
import com.rzspider.project.car.carManage.mapper.CarMapper;
import com.rzspider.project.park.parkManage.domain.Person;
import com.rzspider.project.park.parkManage.mapper.PersonMapper;
import com.rzspider.project.system.user.domain.User;
import com.rzspider.project.system.user.mapper.UserRoleMapper;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/car/carManage")
public class CarManageController extends BaseController {
    @Resource
    private CarMapper carMapper;

    @Resource
    private PersonMapper personMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @RequiresPermissions("car:carManage:view")
    @GetMapping()
    public String view() {
        return "/car/carManage/carManage";
    }

    //新增车辆时下拉车主列表
    @PostMapping("/carUsers")
    @ResponseBody
    public List<Person> getCarUsers() {
        User user = getUser();
        if (userRoleMapper.selectIdByUserId(user.getUserId()) == 3) {
            startPage();
            List<Person> personList = personMapper.selectAll();
            return personList;
        } else {
            List<Person> personList = personMapper.selectAllById(user.getUserId());
            return personList;
        }
    }

    //修改车辆时下拉车主列表
    @PostMapping("edit/carUsers")
    @ResponseBody
    public List<Person> geteditCarUsers() {
        User user = getUser();
        if (userRoleMapper.selectIdByUserId(user.getUserId()) == 3) {
            startPage();
            List<Person> personList = personMapper.selectAll();
            return personList;
        } else {
            List<Person> personList = personMapper.selectAllById(user.getUserId());
            return personList;
        }

    }

    /**
     * 显示车辆列表
     * @return
     */
    @RequiresPermissions("car:carManage:list")
    @GetMapping("/list")
    @ResponseBody
    public TableDataInfo list(Car car) {
        startPage();
        List<Car> list = carMapper.selectAbout(car);
        return getDataTable(list);
    }


    /**
     * 点击新增按钮跳转新增页面
     */

    @RequiresPermissions("car:carManage:add")
    @GetMapping("/add")
    public String add() {
        return "/car/carManage/add";
    }

    /**
     * 点击删除按钮删除当前行
     */
    @RequiresPermissions("car:carManage:remove")
    @PostMapping("/remove/{id}")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Message remove(@PathVariable("id") Integer id) {
        if (carMapper.deleteByPrimaryKey(id) > 0) {
            return Message.success();
        }
        return Message.error();
    }

    /**
     * 点击修改显示车辆详情
     */

    @RequiresPermissions("car:carManage:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Car car = carMapper.selectByPrimaryKey(id);
        //判断查询车辆对象是否为空
        if(car==null){
            return "该对象已经被删除";
        }
        //日期格式化，页面传来的日期字符串中带有T
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
        //判断入场时间是否为空 防止空指针异常
        if(car.getIndate()!=null){
            car.setIndateStr(simpleDateFormat.format(car.getIndate()));
        }else {

            car.setIndateStr("");
        }
        //判断出场时间是否为空 防止空指针异常
        if(car.getOutdate()!=null){
            car.setOutdateStr(simpleDateFormat.format(car.getOutdate()));
        }else {
            car.setOutdateStr("");
        }
        model.addAttribute("outdateStr", car.getOutdateStr());
        model.addAttribute("car", car);
        return "/car/carManage/edit";
    }

    /**
     * 有id修改 数据 没有id新增数据
     */

    @RequiresPermissions("car:carManage:save")
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/save")
    @ResponseBody
    public Message save(Car car) {
        User user = getUser();
       int userId = user.getUserId().intValue();
       //前端传过来的日期格式2021-09-26T10：00 所有格式化的时候中间带了T
       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
        if (car.getId() != null) {//id不为空，代表进行修改
            if (carMapper.updateByPrimaryKey(car) > 0) {
                return Message.success();
            }
        } else {//id为空，进行新增
            try {
                //默认赋值创建者当前登录用户
                car.setCreateid(userId);
                //默认赋值创建时间，当前日期
                car.setCreatetime(new Date());
                //判断在场时间是否为空，如果是null没有经过判断会空指针！
                if (StringUtils.isNotEmpty(car.getIndateStr())) {
                    //将前端传过来的字符串类型日期转换成Date格式，保存到数据库
                    car.setIndate(simpleDateFormat.parse(car.getIndateStr()));
                } else {
                    return Message.error("入场时间不能为空");
                }
//            car.setOutdate(simpleDateFormat.parse(car.getOutdateStr()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (carMapper.insert(car) > 0) {//
                return Message.success();
            }
        }
        return Message.error();

    }

    /**
     * 点击列表离场按钮后请求，同时计算停车费，更新离场时间，以及状态变更为离场
     */
    @RequiresPermissions("car:carManage:leave")
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/leave/{id}")
    @ResponseBody
    public Message leave(@PathVariable("id") Integer id) {
        Date date = new Date();
        Car car = carMapper.selectByPrimaryKey(id);
        if (car.getOutdate() == null) {
            //设置对象离场时间为当前时间
            car.setOutdate(date);
            //设置对象状态为离场转态1
            car.setStatus(1);//1离场 0在场
            //得到停车时间
            long parkTime = car.getOutdate().getTime()-car.getIndate().getTime();
            //判断车类型分类计算车费
            if((parkTime/(1000*60*60)-1)<0){//不到一小时不收费
                car.setCost(new BigDecimal(0));
            }
             if("0".equals(car.getType())){
                Long cost = (parkTime/(1000*60*60)-1)*3;
                car.setCost(new BigDecimal(cost));
            }else if("1".equals(car.getType())){
                Long cost = (parkTime/(1000*60*60)-1)*4;
                car.setCost(new BigDecimal(cost));
            }else if("2".equals(car.getType())){
                Long cost = (parkTime/(1000*60*60)-1)*5;
                car.setCost(new BigDecimal(cost));
            }
            //修改数据库当前车辆信息
            int i = carMapper.updateByPrimaryKey(car);
            if (i > 0) {
                return Message.success();
            }
        }
        return Message.error("车辆已经离场，数据异常");
    }
}

