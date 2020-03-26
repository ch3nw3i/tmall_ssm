package com.how2j.tmall.service;

import com.how2j.tmall.pojo.Review;

import java.util.List;

/**
 * @author chen
 */
public interface ReviewService {
    /**
     *
     * @param pid
     * @return
     */
    List<Review> list(Integer pid);

    /**
     *
     * @param pid
     * @return
     */
    Integer count(Integer pid);
}
