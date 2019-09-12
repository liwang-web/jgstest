package cn.kgc.ssm.service;

import cn.kgc.ssm.bean.User;
import cn.kgc.ssm.mapper.UserMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public interface UserService {

    public User getUser(String userCode);
    public List<User> getUserList(String username, Integer userRole);
    //添加用户
    public  Integer  addUser(User user) ;
    //根据ID查询用户信息
    public  User getUserById(Integer id) ;
    //根据id更改用户信息
    public  Integer updataUserById(User user) ;
}
