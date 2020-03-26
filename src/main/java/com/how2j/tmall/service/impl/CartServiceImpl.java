package com.how2j.tmall.service.impl;

import com.how2j.tmall.mapper.CartMapper;
import com.how2j.tmall.pojo.Cart;
import com.how2j.tmall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chen
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;
    @Override
    public List<Cart> listByUid(Integer uid) {
        return cartMapper.selectByUid(uid);
    }

    @Override
    public void add(Cart cart) {
        List<Cart> cartList = cartMapper.selectByUid(cart.getUid());
        // flag 标记 查询结果中是否有同用户、同产品的字段，如果有，就设置为true，如果没有则设置成false
        Boolean flag = false;
        if (!cartList.isEmpty()) {
            for (Cart c : cartList) {
                if (c.getUid().equals(cart.getUid()) && c.getPid().equals(cart.getPid())) {
                    c.setNumber(c.getNumber() + cart.getNumber());
                    cartMapper.update(c);
                    flag = true;
                }
            }
        }
        // 若查询结果中没有同一用户和同一商品的字段，则执行insert操作
        if (flag == false) {
            cartMapper.insert(cart);
        }
    }

    @Override
    public Integer getCartNumByUid(Integer uid) {
        return cartMapper.getCartTotalItemNumberByUid(uid);
    }

    @Override
    public void delete(Integer id) {
        cartMapper.deleteById(id);
    }

    @Override
    public void update(Cart cart) {
        List<Cart> cartList = cartMapper.selectByUid(cart.getUid());
        for (Cart c : cartList) {
            if (c.getPid().equals(cart.getPid())) {
                cart.setId(c.getId());
                cartMapper.update(cart);
            }
        }
    }
}
