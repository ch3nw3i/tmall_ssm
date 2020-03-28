package com.how2j.tmall.mapper;

import com.how2j.tmall.pojo.Category;
import com.how2j.tmall.util.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chen
 */
public interface CategoryMapper {
//    /**
//     * 分页查询category
//     * @param page
//     * @return category列表
//     */
//    List<Category> list(Page page);

    List<Category> list();

//    /**
//     * 查询所有分类
//     * @return
//     */
//    List<Category> listAll();

//    /**
//     * 获得分类表的总记录条数
//     * @return
//     */
//    Integer total();

    /**
     * 新增商品分类
     * @param category
     */
    void add(Category category);

    /**
     * 根据id，删除category
     * @param id
     */
    void delete(Integer id);

    /**
     * 修改分类的name
     * @param category
     */
    void update(Category category);

    /**
     * 根据id，获取对应的category对象
     * @param id
     * @return
     */
    Category get(Integer id);
}
