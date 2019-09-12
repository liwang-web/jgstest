package cn.kgc.ssm.service.impl;

import cn.kgc.ssm.bean.Provider;
import cn.kgc.ssm.mapper.ProviderMapper;
import cn.kgc.ssm.service.ProviderService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class ProviderServiceImpl implements ProviderService {
    @Resource
    private  ProviderMapper providerMapper;
    @Override
    public List<Provider> getProviderList(String proCode, String proName) {
        List<Provider> providerList=null;
        try {
            providerList= providerMapper.getProviderList(proCode,proName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return providerList;
    }

    @Override
    public Integer addprovider(Provider provider) {
        Integer result = null;
        try {
            result = providerMapper.addprovider(provider);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Provider getProviderById(Integer id) {
        Provider provider=null;
        try {
            provider = providerMapper.getProviderById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return provider;
    }


    @Override
    public Integer updateProviderById(Provider provider) {
        Integer result=0;
        try {
            result = providerMapper.updateProviderById(provider);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
