package com.how2j.tmall.mapper;

import com.how2j.tmall.pojo.Order;
import com.how2j.tmall.util.Page;

import java.util.List;

/**
 * @author chen
 */
public interface OrderMapper {
    /**
     *
     * @return
     */
    List<Order> list();

    /**
     *
     * @param order
     */
    void delivery(Order order);

    List<Order> listByUid(Integer uid);

    Integer insert(Order order);

    String getLastOrderCode();

    /**
     * 确认收货后，订单 status waitConfirm -> waitReview
     * @param order
     */
    void confirmPay(Order order);

//    Integer getLastOid();

    /**
     * 支付成功后，订单status waitPay -> waitDelivery
     * @param order
     */
    void payed(Order order);

    Order selectById(Integer id);

    Integer delete(Integer id);

}
