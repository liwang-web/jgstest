package cn.kgc.ssm.service.impl;

import cn.kgc.ssm.bean.User;
import cn.kgc.ssm.mapper.UserMapper;
import cn.kgc.ssm.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    User user=new User();
    @Override
    public User getUser(String userCode) {
        try {
            user=userMapper.getUser(userCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> getUserList(String userName, Integer userRole) {
        List<User> userList=null;
        try {
           userList = userMapper.getUserList(userName, userRole);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userList;
    }

    @Override
    public Integer addUser(User user) {
        Integer result=0;
        try {
            result = userMapper.addUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public User getUserById(Integer id) {
        User user=new User();
        try {
             user = userMapper.getUserById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public Integer updataUserById(User user) {
        Integer result=0;
        try {
             result = userMapper.updataUserById(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
