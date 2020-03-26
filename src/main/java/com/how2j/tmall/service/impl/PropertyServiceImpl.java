package com.how2j.tmall.service.impl;

import com.how2j.tmall.mapper.PropertyMapper;
import com.how2j.tmall.pojo.Property;
import com.how2j.tmall.service.PropertyService;
import com.how2j.tmall.service.PropertyValueService;
import com.how2j.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chen
 */
@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyMapper propertyMapper;

    @Autowired
    private PropertyValueService propertyValueService;

    @Override
    public List<Property> list(Integer cid, Page page) {
        return propertyMapper.list(cid, page);
    }

    @Override
    public List<Property> list(Integer cid) {
        return propertyMapper.listByCid(cid);
    }

    @Override
    public Integer total(Integer cid) {
        return propertyMapper.total(cid);
    }

    @Override
    public Property get(Integer id) {
        return propertyMapper.get(id);
    }

    @Override
    public void add(Property property) {
        propertyMapper.add(property);
    }

    @Override
    public void delete(Integer id) {
        propertyValueService.delete(id);
        propertyMapper.delete(id);
    }

    @Override
    public void update(Property property) {
        propertyMapper.update(property);
    }
}
