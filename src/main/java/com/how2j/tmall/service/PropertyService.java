package com.how2j.tmall.service;

import com.how2j.tmall.pojo.Property;
import com.how2j.tmall.util.Page;

import java.util.List;

/**
 * @author chen
 */
public interface PropertyService {
//    /**
//     *
//     * @param cid
//     * @param page
//     * @return
//     */
//    List<Property> list(Integer cid, Page page);

    /**
     *
     * @param cid
     * @return
     */
    List<Property> list(Integer cid);

//    /**
//     *
//     * @param cid
//     * @return
//     */
//    Integer total(Integer cid);

    /**
     *
     * @param id
     * @return
     */
    Property get(Integer id);

    /**
     *
     * @param property
     */
    void add(Property property);

    /**
     *
     * @param id
     */
    void delete(Integer id);

    /**
     *
     * @param property
     */
    void update(Property property);
}
