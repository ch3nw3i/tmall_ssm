package com.how2j.tmall.service.impl;

import com.how2j.tmall.mapper.OrderItemMapper;
import com.how2j.tmall.pojo.Order;
import com.how2j.tmall.pojo.OrderItem;
import com.how2j.tmall.pojo.ProductImage;
import com.how2j.tmall.service.OrderItemService;
import com.how2j.tmall.service.OrderService;
import com.how2j.tmall.service.ProductImageService;
import com.how2j.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chen
 */
@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private ProductImageService productImageService;

    @Override
    public List<OrderItem> list(Order order) {
        return orderItemMapper.list(order);
    }

    @Override
    public List<OrderItem> cart(Integer uid) {
        return orderItemMapper.listByUid(uid);
    }

    @Override
    public Integer total(Integer pid) {
        return orderItemMapper.total(pid);
    }

    @Override
    public List<OrderItem> listByOid(Integer oid) {
        List<OrderItem> orderItemList = orderItemMapper.listByOid(oid);
        for (OrderItem oi : orderItemList) {
            ProductImage fpi = productImageService.getFirstProductImage(oi.getPid());
            oi.getProduct().setFirstProductImage(fpi);
        }
        return  orderItemList;
    }

    @Override
    public void insert(OrderItem orderItem) {
        orderItemMapper.insert(orderItem);
    }

    @Override
    public void insert(List<OrderItem> orderItemList) {
        for (OrderItem oi : orderItemList) {
            orderItemMapper.insert(oi);
        }
    }

    @Override
    public Integer countSaleLastMonth(Integer pid) {
        return orderItemMapper.countSaleLastMonth(pid);
    }
}
