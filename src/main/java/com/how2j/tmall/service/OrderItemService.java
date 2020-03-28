package com.how2j.tmall.service;

import com.how2j.tmall.pojo.Order;
import com.how2j.tmall.pojo.OrderItem;

import java.util.List;

/**
 * @author chen
 */
public interface OrderItemService {
    /**
     *
     * @param order
     * @return
     */
    List<OrderItem> list(Order order);

    /**
     *
     * @param uid
     * @return
     */
    List<OrderItem> cart(Integer uid);

    /**
     *
     * @param pid
     * @return
     */
    Integer total(Integer pid);

    /**
     *
     * @param oid
     * @return
     */
    List<OrderItem> listByOid(Integer oid);

    void insert(OrderItem orderItem);

    void insert(List<OrderItem> orderItemList);

    Integer countSaleLastMonth(Integer pid);
}
