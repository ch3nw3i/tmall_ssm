package com.how2j.tmall.service.impl;

import com.how2j.tmall.mapper.PropertyValueMapper;
import com.how2j.tmall.pojo.PropertyValue;
import com.how2j.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chen
 */
@Service
public class PropertyValueServiceImpl implements PropertyValueService {

    @Autowired
    private PropertyValueMapper propertyValueMapper;

    @Override
    public List<PropertyValue> list(Integer pid) {
        return propertyValueMapper.list(pid);
    }

    @Override
    public void add(PropertyValue pv) {
        propertyValueMapper.add(pv);
    }

    @Override
    public void delete(Integer ptid) {
        propertyValueMapper.delete(ptid);
    }
}
