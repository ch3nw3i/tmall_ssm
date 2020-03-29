package com.how2j.tmall.service.impl;

import com.how2j.tmall.mapper.OrderItemMapper;
import com.how2j.tmall.mapper.OrderMapper;
import com.how2j.tmall.pojo.Cart;
import com.how2j.tmall.pojo.Order;
import com.how2j.tmall.pojo.OrderItem;
import com.how2j.tmall.service.CartService;
import com.how2j.tmall.service.OrderItemService;
import com.how2j.tmall.service.OrderService;
import com.how2j.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.Inet4Address;
import java.util.Date;
import java.util.List;

/**
 * @author chen
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private CartService cartService;

    /**
     *
     * @return
     */
    @Override
    public List<Order> list() {
        List<Order> orderList = orderMapper.list();
        for (Order order : orderList) {
            List<OrderItem> orderItemList = orderItemService.listByOid(order.getId());
            order.setOrderItems(orderItemList);
        }
        return orderList;
    }

    @Override
    public void delivery(Integer id) {
        Order order = orderMapper.selectById(id);
        order.setDeliveryDate(new Date());
        orderMapper.delivery(order);
    }

    @Override
    public List<Order> listByUid(Integer uid) {
        List<Order> orderList = orderMapper.listByUid(uid);
        for (Order o : orderList) {
            List<OrderItem> orderItemList = orderItemService.listByOid(o.getId());
            o.setOrderItems(orderItemList);
        }
        return orderList;
    }

    @Override
    public String getLastOrderCode() {
        return orderMapper.getLastOrderCode();
    }

    @Override
    public void addOrder(Order order, List<OrderItem> orderItemList) {
        order.setOrderCode(String.valueOf(Integer.parseInt(orderMapper.getLastOrderCode()) + 1));
        order.setCreateDate(new Date());
        order.setStatus("waitPay");
        Double totalPrice = 0.0d;
        Integer totalNumber = 0;
        for (OrderItem oi : orderItemList) {
            totalPrice += oi.getProduct().getPromotePrice();
            totalNumber += oi.getNumber();
        }
        order.setTotalPrice(totalPrice);
        order.setTotalNumber(totalNumber);
        orderMapper.insert(order);;
        for (OrderItem oi : orderItemList) {
            oi.setOid(order.getId());
        }
        orderItemService.insert(orderItemList);
    }

    @Override
    public void confirmPay(Integer id) {
        Order order = orderMapper.selectById(id);
        order.setConfirmDate(new Date());
        orderMapper.confirmPay(order);
    }

//    @Override
//    public Integer getLastOid() {
//        return orderMapper.getLastOid();
//    }

    @Override
    public void payed(Integer id) {
        Order order = orderMapper.selectById(id);
//        order.setStatus("waitDelivery");
        order.setPayDate(new Date());
        orderMapper.payed(order);
    }

    @Override
    public Order getById(Integer id) {
        return orderMapper.selectById(id);
    }


    @Override
    public Integer delete(Integer id) {
        return orderMapper.delete(id);
    }
}
