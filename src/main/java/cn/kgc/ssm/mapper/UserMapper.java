package cn.kgc.ssm.mapper;

        import cn.kgc.ssm.bean.User;
        import org.apache.ibatis.annotations.Param;

        import java.util.List;

public interface UserMapper {
    //登录
    public User getUser(@Param("userCode")String userCode) throws  Exception;
    //分页查询
    public  List<User> getUserList(@Param("userName") String userName,
                                   @Param("userRole") Integer userRole)throws  Exception;
    //添加用户
    public  Integer  addUser(User user) throws Exception;
    //根据ID查询用户信息
    public  User getUserById(Integer id) throws  Exception;
    //根据id更改用户信息
    public  Integer updataUserById(User user) throws  Exception;
}
