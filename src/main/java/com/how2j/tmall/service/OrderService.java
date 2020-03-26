package com.how2j.tmall.service;

import com.how2j.tmall.pojo.Order;
import com.how2j.tmall.pojo.OrderItem;
import com.how2j.tmall.util.Page;

import java.util.List;

/**
 * @author chen
 */
public interface OrderService {
    /**
     *
     * @param page
     * @return
     */
    List<Order> list(Page page);

    /**
     *
     * @return
     */
    Integer total();

    /**
     *
     * @param id
     */
    void delivery(Integer id);

    List<Order> listByUid(Integer uid);

    String getLastOrderCode();

    void addOrder(Order order, List<OrderItem> orderItemList);

    void confirmPay(Integer id);

    Integer getLastOid();

    void payed(Integer id);

    Order getById(Integer id);
}
