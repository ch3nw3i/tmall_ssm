package com.how2j.tmall.service.impl;

import com.how2j.tmall.mapper.ReviewMapper;
import com.how2j.tmall.pojo.Review;
import com.how2j.tmall.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chen
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    public List<Review> list(Integer pid) {
        return reviewMapper.list(pid);
    }

    @Override
    public Integer count(Integer pid) {
        return reviewMapper.count(pid);
    }
}
