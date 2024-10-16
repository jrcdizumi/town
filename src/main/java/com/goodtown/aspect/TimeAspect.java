package com.goodtown.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.time.LocalDateTime;

@Aspect
@Component
public class TimeAspect {

    @Before("execution(* com.baomidou.mybatisplus.core.mapper.BaseMapper.updateById(..)) && args(entity)")
    public void beforeUpdate(Object entity) {
        setUpdateTime(entity);
    }
    @Before("execution(* com.baomidou.mybatisplus.core.mapper.BaseMapper.insert(..)) && args(entity)")
    public void beforeInsert(Object entity) {
        setUpdateTime(entity);
        setRegisterTime(entity);
    }

    private void setUpdateTime(Object entity) {
        MetaObject metaObject = SystemMetaObject.forObject(entity);
        if (metaObject.hasSetter("updateTime")) {
            metaObject.setValue("updateTime", LocalDateTime.now());
        }
    }

    private void setRegisterTime(Object entity) {
        MetaObject metaObject = SystemMetaObject.forObject(entity);
        if (metaObject.hasSetter("registerTime")) {
            metaObject.setValue("registerTime", LocalDateTime.now());
        }
    }
}
