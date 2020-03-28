package com.how2j.tmall.service;

import com.how2j.tmall.pojo.Product;
import com.how2j.tmall.util.Page;

import java.util.List;

/**
 * @author chen
 */
public interface ProductService {
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
     * @param product
     */
    void update(Product product);

    /**
     *
     * @param id
     * @return
     */
    Product get(Integer id);

//    /**
//     *
//     * @param cid
//     * @param page
//     * @return
//     */
//    List<Product> list(Integer cid, Page page);

    /**
     *
     * @param cid
     * @return
     */
    List<Product> list(Integer cid);

    /**
     *
     * @param cid
     * @return
     */
    Integer total(Integer cid);

    List<Product> search(String keyword);

}
