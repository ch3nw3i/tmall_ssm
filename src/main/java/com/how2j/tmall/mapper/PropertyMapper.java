package com.how2j.tmall.mapper;

import com.how2j.tmall.pojo.Property;
import com.how2j.tmall.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chen
 */
public interface PropertyMapper {
    /**
     *
     * @param cid
     * @return
     */
    List<Property> list(@Param("cid") Integer cid);

//    /**
//     *
//     * @param cid
//     * @return
//     */
//    List<Property> listByCid(Integer cid);

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
     * @param property
     */
    void update(Property property);

    /**
     *
     * @param id
     */
    void delete(Integer id);
}
