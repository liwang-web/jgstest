package cn.kgc.ssm.service;

import cn.kgc.ssm.bean.Provider;

import java.util.List;

public interface ProviderService {
    public List<Provider> getProviderList(String proCode, String proName);
    //添加供应商信息
    public  Integer  addprovider(Provider provider) ;
    //    根据ID查询供应商信息
    public  Provider getProviderById(Integer id);
    //    //根据id更改供应商信息
    public  Integer updateProviderById(Provider provider);
}
