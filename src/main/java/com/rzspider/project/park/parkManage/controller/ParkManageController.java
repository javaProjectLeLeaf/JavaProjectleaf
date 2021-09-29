package com.rzspider.project.park.parkManage.controller;

import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.framework.web.domain.Message;
import com.rzspider.framework.web.page.TableDataInfo;
import com.rzspider.project.car.carManage.domain.Car;
import com.rzspider.project.car.carManage.mapper.CarMapper;
import com.rzspider.project.park.parkManage.domain.Person;
import com.rzspider.project.park.parkManage.mapper.PersonMapper;
import com.rzspider.project.park.parkManage.mapper.UserMakerMapper;
import com.rzspider.project.system.role.mapper.RoleMapper;
import com.rzspider.project.system.user.domain.User;
import com.rzspider.project.system.user.mapper.UserRoleMapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/park/parkManage")
public class ParkManageController extends BaseController {
    private String addr = "/park/parkManage";
    @Resource
    private PersonMapper personMapper;
    @Resource
    private CarMapper carMapper;
    @Resource
    private UserMakerMapper userMakerMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private UserRoleMapper userRoleMapper;


    @GetMapping()
    @RequiresPermissions("park:parkManage:view")
    public String showView() {
        return addr + "/parkManage";
    }

    @GetMapping("/list")
    @RequiresPermissions("park:parkManage:list")
    @ResponseBody
    public TableDataInfo list() {
        User user = getUser();
        if (userRoleMapper.selectIdByUserId(user.getUserId()) == 3) {
            startPage();
            List<Person> personList = personMapper.selectAll();
            return getDataTable(personList);
        } else {
            List<Person> personList = personMapper.selectAllById(user.getUserId());
            return getDataTable(personList);
        }

    }


    /**
     * 批量删除
     */

    @RequiresPermissions("park:parkManage:batchRemove")
    @PostMapping("/batchRemove")
    @ResponseBody
    public Message remove(@RequestParam("ids[]") Integer[] ids) {
        int rows = personMapper.batchDeletePersonManage(ids);
        if (rows > 0) {
            return Message.success();
        }
        return Message.error();
    }

    /**
     * 新增
     */

    @RequiresPermissions("park:parkManage:add")
    @GetMapping("/add")
    public String add() {
        return "/park/parkManage/add";
    }

    /**
     * 删除
     */
    @RequiresPermissions("park:parkManage:remove")
    @PostMapping("/remove/{id}")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Message remove(@PathVariable("id") Integer id) {
        User user = getUser();
        if (id == null) {
            return Message.error();
        }
        Person person = personMapper.selectByPrimaryKey(id);
        person.setStatus(0);
        String i = user.getUserId().toString();
        int userId = Integer.parseInt(i);
        if (personMapper.selectOneById().getMakeUserId() == userId || user.getUserId() == 3)
            if (personMapper.updateByPrimaryKey(person) > 0) {
                return Message.success();
            }
//            if (personMapper.deleteByPrimaryKey(id) > 0) {
//                return Message.success();
//            }
        return Message.error("删除此信息需要跟多权限");
    }

    /**
     * 修改
     */

    @RequiresPermissions("park:parkManage:alter")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Person person = personMapper.selectByPrimaryKey(id);
        model.addAttribute("person", person);
        return addr + "/edit";
    }
//

    /**
     * 车费详情
     */

    @RequiresPermissions("park:parkManage:detail")
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        int personId = id;
        List<Car> cars = carMapper.selectCarByPersonId(personId);
        model.addAttribute("cars", cars);
        return addr + "/detail";
    }

    /**
     * 保存
     */

    @RequiresPermissions("park:parkManage:save")
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/save")
    @ResponseBody
    public Message save(Person person) {
        if(person.getName().length()>10){
            return Message.error("用户名不符合规则");
        }
        if(person.getPhone().length()!=11){ //判断号码格式
            return Message.error("号码长度必须为11");
        }
        if(person.getAge()>150||person.getAge()<1){//判断年龄
            return Message.error("年龄不符合要求");
        }
        User user = getUser();//这个方法可以获取到当前登录对象，但是需要继承basecontroller
        //或用两个的，可以防止短路不是学过。
        if (person.getId() != null) {//修改
            //这个要放到修改里执行
            Person person1 = personMapper.selectByPrimaryKey(person.getId());
            if (person1 == null) {
                return Message.error();
            }
            int makeUserId = personMapper.selectByPrimaryKey(person.getId()).getMakeUserId();
            if (userRoleMapper.selectIdByUserId(user.getUserId()) == 3 || user.getUserId() == makeUserId) {//如果是超级管理员，或者创建人当前登录人就可以修改
                person.setMakeUserId(makeUserId);
                if (personMapper.updateByPrimaryKey(person) > 0) {
                    return Message.success();
                }
            } else {
                return Message.error("您无此数据的修改权限");
            }

        } else {
            if (person.getMakeUserId() == null) {
                int i = user.getUserId().intValue();
                person.setMakeUserId(i);
            }
            person.setStatus(1);//有效状态
            if (personMapper.insert(person) > 0) {//新增，那你新增被限制了啊？  这样就不会限制新增了，不然你是先满足超管，以及创建人是登录人才能新增了，是不是有问题？
                return Message.success();
            }
        }
        return Message.error();

    }


//        if(roleMapper.selectRolesByUserMakerLevel(userMakerMapper.selectByPrimaryKey(person.getMakeUserId()).getLevel())){
//
//        }


}
