package com.how2j.tmall.mapper;

import com.how2j.tmall.pojo.User;
import com.how2j.tmall.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chen
 */
public interface UserMapper {
    /**
     *
     * @param page
     * @return
     */
    List<User> list(@Param("page") Page page);

//    /**
//     *
//     * @return
//     */
//    Integer total();

    /**
     *
     * @param name
     * @return
     */
    User getUser(String name);

    /**
     * @param user
     * @return 1 成功  -1 失败
     */
    Integer add(User user);
}
