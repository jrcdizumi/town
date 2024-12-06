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
        if (metaObject.hasSetter("udate")) {
            metaObject.setValue("udate", LocalDateTime.now());
        }
        if (metaObject.hasSetter("pupdatedate")) {
            metaObject.setValue("pupdatedate", LocalDateTime.now());
        }
        if (metaObject.hasSetter("updateDate")) {
            metaObject.setValue("updateDate", LocalDateTime.now());
        }
    }

    private void setRegisterTime(Object entity) {
        MetaObject metaObject = SystemMetaObject.forObject(entity);
        if (metaObject.hasSetter("rdate")) {
            metaObject.setValue("rdate", LocalDateTime.now());
        }
        if (metaObject.hasSetter("createdate")) {
            metaObject.setValue("createdate", LocalDateTime.now());
        }
        if (metaObject.hasSetter("pbegindate")) {
            metaObject.setValue("pbegindate", LocalDateTime.now());
        }
        if (metaObject.hasSetter("supportDate")) {
            metaObject.setValue("supportDate", LocalDateTime.now());
        }
    }
}
