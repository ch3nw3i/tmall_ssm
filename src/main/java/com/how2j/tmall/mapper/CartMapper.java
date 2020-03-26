package com.how2j.tmall.mapper;

import com.how2j.tmall.pojo.Cart;

import java.util.List;

/**
 * @author chen
 */
public interface CartMapper {

    /**
     *
     * @param uid
     * @return
     */
    List<Cart> selectByUid(Integer uid);

    /**
     *
     * @param cart
     * @return
     */
    Integer insert(Cart cart);

    Integer getCartTotalItemNumberByUid(Integer uid);

    void update(Cart cart);

    void deleteById(Integer id);
}
