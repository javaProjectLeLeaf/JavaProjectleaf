package com.rzspider.project.car.carMan.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.rzspider.project.car.carManage.domain.Car;
import com.rzspider.project.car.carManage.mapper.CarMapper;
import com.rzspider.project.park.parkManage.domain.Person;
import com.rzspider.project.park.parkManage.mapper.PersonMapper;
import com.rzspider.project.salary.salarymanage.domain.Salary;
import com.rzspider.project.system.user.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.Resources;
import java.beans.SimpleBeanInfo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("car/carMan")
public class NewCarController {
    @Resource
    private CarMapper carMapper;
    @Resource
    private PersonMapper personMapper;

    @GetMapping("carList")
    public String carList(){
        return "car/carManage/carTest";
    }
    @ResponseBody
    @RequestMapping("/list")
    public JSONObject list(Car car){
        PageHelper.startPage(car.getPage() + 1, car.getRows());
        List<Car> cars = carMapper.selectNewAll();
        int total = carMapper.selectCount();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", total);
        jsonObject.put("data", cars);
        return jsonObject;

    }


    @PostMapping("/addCar")
    @ResponseBody
    public JSONObject addSalary(Car car) {
        JSONObject jsonObject = new JSONObject();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (car.getId() != null) {
            try {
                car.setIndate(simpleDateFormat.parse(car.getIndateStr()));
                car.setOutdate(simpleDateFormat.parse(car.getOutdateStr()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (carMapper.updateByPrimaryKey(car) > 0) {
                jsonObject.put("msg", "success");
                return jsonObject;
            }
            jsonObject.put("msg", "error");
            return jsonObject;
        } else {
            car.setIndate(new Date());
            car.setCreatetime(new Date());
            car.setStatus(0);
            car.setPay(0);
            int i = carMapper.insertSelective(car);
            if (i > 0) {
                jsonObject.put("msg", "success");
                return jsonObject;
            }
            jsonObject.put("msg", "error");
            return jsonObject;
        }
    }

    @PostMapping("/deleteCar")
    @ResponseBody
    public JSONObject deleteCar(Integer id){
        JSONObject jsonObject = new JSONObject();
        if(carMapper.deleteByPrimaryKey(id)>0){
            jsonObject.put("msg","success");
            return jsonObject;
        }
        jsonObject.put("msg","error");
        return jsonObject;
    }

    /**
     * 修改车辆信息
     * @param car
     * @return
     */

    @PostMapping("/updateCar/{car}")
    @ResponseBody
    public JSONObject updateCar(@PathVariable("car")Car car){
        JSONObject jsonObject = new JSONObject();
        if(carMapper.updateByPrimaryKey(car)>0){
            jsonObject.put("msg","success");
            return jsonObject;
        }
        jsonObject.put("msg","error");
        return jsonObject;
    }

    /**
     * 查询用户列表
     * @return
     */
    @GetMapping("/personList")
    @ResponseBody
    public JSONObject personList(Person person){
        PageHelper.startPage(person.getPage() + 1, person.getRows());
        int total = personMapper.selectCount();
        JSONObject jsonObject = new JSONObject();
        List<Person> personList = personMapper.selectAll();
        jsonObject.put("total",total );
        jsonObject.put("data" ,personList);
        return jsonObject;
    }

}
