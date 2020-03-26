package com.how2j.tmall.service;

import com.how2j.tmall.pojo.ProductImage;
import com.how2j.tmall.util.UploadedImageFile;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author chen
 */
public interface ProductImageService {
    /**
     *
     * @param pid
     * @return
     */
    List<ProductImage> listProductImages(Integer pid);

    /**
     *
     * @param pid
     * @return
     */
    ProductImage getFirstProductImage(Integer pid);

    /**
     *
     * @param pid
     * @return
     */
    List<ProductImage> list(Integer pid);

    /**
     *
     * @param id
     * @return
     */
    ProductImage get(Integer id);

    /**
     *
     * @param pi
     * @param session
     * @param uploadedImageFile
     * @throws IOException
     */
    void add(ProductImage pi, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException;

    /**
     *
     * @param id
     * @param session
     */
    void delete(Integer id, HttpSession session);
}
