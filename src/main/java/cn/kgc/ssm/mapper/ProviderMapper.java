package cn.kgc.ssm.mapper;

import cn.kgc.ssm.bean.Provider;
import cn.kgc.ssm.bean.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProviderMapper {
    //分页查询
    public List<Provider> getProviderList(@Param("proCode") String proCode,
                                          @Param("proName") String proName)throws  Exception;
    //添加供应商信息
       public  Integer  addprovider(Provider provider) throws Exception;
    //    根据ID查询供应商信息
       public  Provider getProviderById(Integer id) throws  Exception;
    //    //根据id更改供应商信息
       public  Integer updateProviderById(Provider provider) throws  Exception;
}
