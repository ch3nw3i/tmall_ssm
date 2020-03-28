package com.how2j.tmall.mapper;

import com.how2j.tmall.pojo.Product;
import com.how2j.tmall.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chen
 */
public interface ProductMapper {
    /**
     *
     * @param cid
     * @return
     */
    List<Product> list(Integer cid);
//    List<Product> list(@Param("cid") Integer cid, @Param("page") Page page);

//    /**
//     *
//     * @return
//     */
//    List<Product> listAll();

//    /**
//     *
//     * @param cid
//     * @return
//     */
//    List<Product> listByCid(Integer cid);

    /**
     *
     * @param cid
     * @return
     */
    Integer total(Integer cid);

    /**
     *
     * @param product
     */
    void add(Product product);

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
    Product get(Integer pid);

    /**
     *
     * @param product
     */
    void update(Product product);

    List<Product> listByNameKeyword(String keyword);



}
