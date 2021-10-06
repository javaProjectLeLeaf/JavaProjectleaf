package com.rzspider.project.village.villageManage.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.rzspider.common.utils.StringUtils;
import com.rzspider.project.village.villageManage.domain.Village;
import com.rzspider.project.village.villageManage.domain.VillagePCList;
import com.rzspider.project.village.villageManage.mapper.VillageDetailMapper;
import com.rzspider.project.village.villageManage.mapper.VillageMapper;
import com.rzspider.project.village.villageManage.mapper.VillagePCListMapper;
import com.rzspider.project.yd.ydManage.domain.YwInfo;
import com.rzspider.project.yd.ydManage.domain.YwMod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/village/villageManage")
public class villageManage {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Resource
    private VillagePCListMapper villagePCListMapper;
    @Resource
    private VillageDetailMapper villageDetailMapper;
    @Resource
    private VillageMapper villageMapper;


    /**
     * 首页
     * @return
     */
    @GetMapping("/view")
    public String view(){
        return "village/villageManage/villageList";
    }

    /**
     * 首页列表查询
     * @param villagePCList
     * @return
     */
    @PostMapping("/list")
    @ResponseBody
    public JSONObject list(VillagePCList villagePCList){
        JSONObject jsonObject = new JSONObject();
         if(StringUtils.isNotEmpty(villagePCList.getVillageName())){
             villagePCList.setVillageName("%"+villagePCList.getVillageName()+"%");
         }
         if(StringUtils.isNotEmpty(villagePCList.getContractorName())){
             villagePCList.setContractorName("%"+villagePCList.getContractorName()+"%");
         }
        PageHelper.startPage(villagePCList.getPage() + 1, villagePCList.getRows());
        List<VillagePCList> villagePCListList =villagePCListMapper.selectAll(villagePCList);
        Integer i = villagePCListMapper.selectCount(villagePCList);
        jsonObject.put("total", i);
        jsonObject.put("data", villagePCListList);
        return jsonObject;

    }

    /**
     * 修改保存
     */
    @PostMapping("/editVillageInfo")
    @ResponseBody
    public JSONObject editVillageInfo(VillagePCList villagePCList){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        JSONObject jsonObject = new JSONObject();
        if(StringUtils.isNotEmpty(villagePCList.getBuildDateStr())) {
            try {
                villagePCList.setBuildDate(simpleDateFormat.parse(villagePCList.getBuildDateStr()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        int i = villagePCListMapper.updateByPrimaryKeySelective(villagePCList);
        if(i>0){
            jsonObject.put("msg","success");
            return jsonObject;
        }
        jsonObject.put("msg","error");
        return jsonObject;
    }

    /**
     * 区县下拉框
     */
    @PostMapping("/countyCode")
    @ResponseBody
    public JSONObject countyCode(){
        List<VillagePCList> villagePCLists = villagePCListMapper.selectCountyCode();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data",villagePCLists);
        return jsonObject;
    }

    /**
     * 区域下拉框
     */
    @ResponseBody
    @PostMapping("/regionId/{countyCode}")
    public JSONObject ywDtl(@PathVariable("countyCode") String countyCode){
        JSONObject jsonObject = new JSONObject();
        List<VillagePCList> villagePCLists = villagePCListMapper.selectRegionIdByCountyCode(countyCode);
        jsonObject.put("data",villagePCLists);
        return jsonObject;
    }


}
