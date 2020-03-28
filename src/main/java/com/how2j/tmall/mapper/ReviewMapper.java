package com.how2j.tmall.mapper;

import com.how2j.tmall.pojo.Review;

import java.util.List;

/**
 * @author chen
 */
public interface ReviewMapper {
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
    Integer total(Integer pid);
}
