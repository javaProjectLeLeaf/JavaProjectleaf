package com.rzspider.project.scheduletask.task.mapper;

import org.apache.ibatis.annotations.Select;


public interface CronMapper {
    @Select("select cron from cron where cron_id=1")
    public String getCron();
}

