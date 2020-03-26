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
     * @param page
     * @return
     */
    List<User> list(Page page);

    /**
     *
     * @return
     */
    Integer total();

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
