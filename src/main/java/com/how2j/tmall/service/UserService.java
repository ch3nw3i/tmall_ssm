package com.how2j.tmall.service;

import com.how2j.tmall.pojo.User;
import com.how2j.tmall.util.Page;

import java.util.List;

/**
 * @author chen
 */
public interface UserService {
    /**
     *
     * @return
     */
    List<User> list();

    /**
     *
     * @param user
     * @return
     */
    User login(User user);

    /**
     *
     * @param user
     * @return
     */
    Integer add(User user);
}
