package com.how2j.tmall.service.impl;

import com.how2j.tmall.mapper.UserMapper;
import com.how2j.tmall.pojo.User;
import com.how2j.tmall.service.UserService;
import com.how2j.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chen
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> list(Page page) {
        return userMapper.list(page);
    }

    @Override
    public Integer total() {
        return userMapper.total();
    }

    @Override
    public User login(User user) {
        User resultUser = userMapper.getUser(user.getName());
        if (resultUser != null) {
            if (resultUser.getPassword().equals(user.getPassword())) {
                return resultUser;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

        @Override
        public Integer add (User user){
            return userMapper.add(user);
        }
    }
