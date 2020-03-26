package com.how2j.tmall.service;

import com.how2j.tmall.pojo.Cart;

import java.util.List;

/**
 * @author chen
 */
public interface CartService {
    /**
     *
     * @param uid
     * @return
     */
    List<Cart> listByUid(Integer uid);

    /**
     *
     * @param cart
     * @return
     */
    void add(Cart cart);

    Integer getCartNumByUid(Integer uid);

    void delete(Integer id);

    void update(Cart cart);

}
