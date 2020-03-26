package com.how2j.tmall.mapper;

import com.how2j.tmall.pojo.ProductImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chen
 */
public interface ProductImageMapper {
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
     */
    void add(ProductImage pi);

    /**
     *
     * @param id
     */
    void delete(Integer id);

    /**
     *
     * @param pid
     * @return
     */
    List<ProductImage> listProductImages(Integer pid);
}
