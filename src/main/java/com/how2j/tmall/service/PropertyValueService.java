package com.how2j.tmall.service;

import com.how2j.tmall.pojo.PropertyValue;

import java.util.List;

/**
 * @author chen
 */
public interface PropertyValueService {
    /**
     *
     * @param pid
     * @return
     */
    List<PropertyValue> list(Integer pid);

    /**
     *
     * @param pv
     */
    void add(PropertyValue pv);

    /**
     * 根据 属性id property.id ptid 删除所有ptid属性的属性值
     * @param ptid
     */
    void delete(Integer ptid);
}
