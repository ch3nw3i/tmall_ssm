package com.how2j.tmall.mapper;

import com.how2j.tmall.pojo.Property;
import com.how2j.tmall.pojo.PropertyValue;

import java.util.List;

/**
 * @author chen
 */
public interface PropertyValueMapper {
    /**
     *
     * @param pid
     * @return
     */
    List<PropertyValue> list(Integer pid);

    /**
     * 在创建新的属性后，将属性名写入propertyvalue表，属性值value留空
     * @param pv
     */
    void add(PropertyValue pv);

    /**
     *
     * @param pv
     */
    void update(PropertyValue pv);

    /**
     *
     * @param ptid
     */
    void delete(Integer ptid);
}
