package com.how2j.tmall.service;

import com.how2j.tmall.pojo.Category;
import com.how2j.tmall.util.Page;
import com.how2j.tmall.util.UploadedImageFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chen
 */
public interface CategoryService {

    List<Category> list();

    /**
     * 新增商品分类
     * @param category
     * @param session
     * @param uploadedImageFile
     */
    void add(Category category, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException ;

    /**
     * 根据id，删除category
     * @param id
     * @param session
     *
     */
    void delete(Integer id, HttpSession session) throws IOException;

    /**
     * 修改分类的name
     * @param category
     */
    void update(Category category, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException;

    /**
     * 根据id，获取对应的category对象
     * @param id
     * @return
     */
    Category get(Integer id);
}
