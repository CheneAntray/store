package com.acorus.store.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * @author CheneAntray
 * @create 2020-05-19 9:01
 */
@Component
public class AutoInsertHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("is_sale",0,metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
