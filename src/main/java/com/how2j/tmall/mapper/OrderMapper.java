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

    void insert(Order order);

    String getLastOrderCode();

    void confirmPay(Integer id);

    Integer getLastOid();

    void payed(Order order);

    Order selectById(Integer id);

}
