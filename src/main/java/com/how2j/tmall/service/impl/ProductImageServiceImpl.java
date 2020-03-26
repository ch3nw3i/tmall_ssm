package com.how2j.tmall.service.impl;

import com.how2j.tmall.mapper.ProductImageMapper;
import com.how2j.tmall.mapper.ProductMapper;
import com.how2j.tmall.pojo.ProductImage;
import com.how2j.tmall.service.ProductImageService;
import com.how2j.tmall.util.ImageUtil;
import com.how2j.tmall.util.UploadedImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author chen
 */
@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    private ProductImageMapper productImageMapper;

    @Override
    public List<ProductImage> listProductImages(Integer pid) {
        return productImageMapper.listProductImages(pid);
    }

    @Override
    public ProductImage getFirstProductImage(Integer pid) {
        ProductImage firstProductImage = productImageMapper.getFirstProductImage(pid);
        return firstProductImage;
    }

    @Override
    public List<ProductImage> list(Integer pid) {
        return productImageMapper.list(pid);
    }

    @Override
    public ProductImage get(Integer id) {
        return productImageMapper.get(id);
    }

    @Override
    public void add(ProductImage pi, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException {
        productImageMapper.add(pi);
        // 上传分类图片
        File imageFolder = new File(session.getServletContext().getRealPath("img/" + pi.getType()));
        File file = new File(imageFolder, pi.getId() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        uploadedImageFile.getImage().transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img, "jpg", file);
    }

    @Override
    public void delete(Integer id, HttpSession session) {
        // 删除数据库记录
        productImageMapper.delete(id);
        // 删除图标文件
        ProductImage productImage = productImageMapper.get(id);
        File imageFolder = new File(session.getServletContext().getRealPath("img/" + productImage.getType()));
        File file = new File(imageFolder, productImage.getId() + ".jpg");
        file.delete();
    }
}
