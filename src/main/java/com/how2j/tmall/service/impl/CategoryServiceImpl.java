package com.how2j.tmall.service.impl;

import com.how2j.tmall.mapper.ProductMapper;
import com.how2j.tmall.pojo.Category;
import com.how2j.tmall.pojo.Product;
import com.how2j.tmall.pojo.Property;
import com.how2j.tmall.service.*;
import com.how2j.tmall.mapper.CategoryMapper;
import com.how2j.tmall.util.ImageUtil;
import com.how2j.tmall.util.Page;
import com.how2j.tmall.util.UploadedImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chen
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductService productService;

//    @Override
//    public List<Category> list(Page page) {
//        List<Category> list = categoryMapper.list(page);
//        return list;
//    }

//    @Override
//    public List<Category> list() {
//        return categoryMapper.list();
//    }



    @Override
    public List<Category> list() {
        List<Category> list = categoryMapper.list();
        for (Category category : list) {
            List<Product> productList = productService.list(category.getId());

            List<List<Product>> rows = new ArrayList<List<Product>>();
            List<Product> row = new ArrayList<Product>();
            //定义批次分组提交量
            int groupItemCount = 5;
            //被分隔集合的总数量
            int totalCount = productList.size() - 1;
            //一共被分几组
            int count = totalCount % groupItemCount == 0 ? totalCount / groupItemCount : totalCount / groupItemCount + 1;
            for (int i = 0; i <= count; i += 5) {
                if (count * groupItemCount <= totalCount) {
                    row = productList.subList(i, count * groupItemCount);
                } else {
                    row = productList.subList(i, totalCount);
                }
                rows.add(row);
            }
            category.setProductsByRow(rows);
        }
        return list;
    }

//    @Override
//    public Integer total() {
//        return categoryMapper.total();
//    }

    @Override
    public void add(Category category, HttpSession session, UploadedImageFile uploadedImageFile)  throws IOException {
        // 新增数据库记录
        categoryMapper.add(category);
        // 上传分类图片
        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, category.getId() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        uploadedImageFile.getImage().transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img, "jpg", file);
    }

    @Override
    public void delete(Integer id, HttpSession session) {

//        // 删除分类下的所有商品
//        List<Product> productList = productService.list(id);
//        for (Product product : productList) {
//            productService.delete(product.getId());
//        }
//        // 删除分类下的所有属性
//        List<Property> propertyList = propertyService.list(id);
//        for (Property property : propertyList) {
//            propertyService.delete(property.getId());
//        }

        // 删除数据库中的记录
        categoryMapper.delete(id);
        // 删除图片文件
        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, id + ".jpg");
        file.delete();
    }

    @Override
    public void update(Category category, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException {
        categoryMapper.update(category);
        // 上传分类图片
        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, category.getId() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        uploadedImageFile.getImage().transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img, "jpg", file);
    }

    @Override
    public Category get(Integer id) {
        Category category = categoryMapper.get(id);
        return category;
    }
}
