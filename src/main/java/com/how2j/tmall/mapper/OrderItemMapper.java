package com.how2j.tmall.mapper;

import com.how2j.tmall.pojo.Order;
import com.how2j.tmall.pojo.OrderItem;

import java.util.List;

/**
 * @author chen
 */
public interface OrderItemMapper {
    /**\
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
    List<OrderItem> listByUid(Integer uid);

    /**
     *
     * @param pid
     * @return
     */
    Integer count(Integer pid);

    /**
     *
     * @param id
     * @return
     */
    OrderItem get(Integer id);

    /**
     *
     * @param oid
     * @return
     */
    List<OrderItem> listByOid(Integer oid);

    void insert(OrderItem orderItem);
}
