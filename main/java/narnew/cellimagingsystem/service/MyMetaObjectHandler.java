package narnew.cellimagingsystem.service;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * 填充默认的创建时间及修改时间字段
 */
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    private static final String CREATE_TIME = "createdTime";

    private static final String UPDATE_TIME = "updatedTime";

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, CREATE_TIME, Date.class, new Date());
        this.strictInsertFill(metaObject, UPDATE_TIME, Date.class, new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, CREATE_TIME, Date.class, new Date());
        // 默认提供的strictUpdateFill为有值不覆盖， gmtModified需要覆盖，利用通用塞值的方法填充
        this.setFieldValByName(UPDATE_TIME, new Date(), metaObject);
    }
}
