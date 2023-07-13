package com.parser.enums;

import com.mvc.DAO.impl.*;
import com.mvc.DAO.inter.LogisticsInfoDAO;
import com.mvc.bean.*;

/**
 * 表枚举类，枚举了数据源涉及到的所有表
 * 每个表枚举对象都存储了其对应的DAO实现类对象和Bean类对象，便于执行反射操作
 */
public enum TableEnum {
    CONTAINER_STATUS(ContainerStatusBean.class, ContainerStatusDAOImpl.class),
    CUSTOMER(CustomerBean.class, CustomerDAOImpl.class),
    LOAD(LoadBean.class, LoadDAOImpl.class),
    UNLOAD(UnloadBean.class, UnloadDAOImpl.class),
    LOGISTICS_COMPANY(LogisticsCompanyBean.class, LogisticsCompanyDAOImpl.class),
    LOGISTICS_INFO(LogisticsInfoBean.class, LogisticsInfoDAOImpl.class);

    private final Class<?> beanClass;
    private final Class<?> DAOImplClass;

    TableEnum(Class<?> beanClass, Class<?> DAOImplClass) {
        this.beanClass = beanClass;
        this.DAOImplClass = DAOImplClass;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public Class<?> getDAOImplClass() {
        return DAOImplClass;
    }
}
