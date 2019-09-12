package cn.kgc.ssm.controller;



import cn.kgc.ssm.bean.Provider;
import cn.kgc.ssm.bean.User;
import cn.kgc.ssm.service.ProviderService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/provider")
public class ProviderController {
   @Resource
    private ProviderService providerService;
    @RequestMapping("/providerList")
    public String userList(@RequestParam(value = "proCode",required = false)String proCode ,
                           @RequestParam(value = "proName",required = false) String proName,
                           @RequestParam(value = "pageIndex",required = false )Integer pageIndex,
                           Model model){
        if (pageIndex==null){
            pageIndex=1;
        }
        Page<Object> pages= PageHelper.startPage(pageIndex,4);
        /*pages.getPageNum();
        pages.getPages();
        pages.getTotal();*/
        List<Provider> providerList = providerService.getProviderList(proCode,proName);
        if (providerList!=null){
            model.addAttribute("providerList",providerList);
            model.addAttribute("pages",pages);
            model.addAttribute("proCode",proCode);
            model.addAttribute("proName",proName);
            return  "providerlist";
        }
        return "frame";
    }
    @RequestMapping("/provideradd")
    public String provideradd(){
        return "provideradd";
    }

    @RequestMapping(value = "/provideraddsave",method = RequestMethod.POST)
    public String provideraddsave(Provider provider, HttpSession session){
        User u = (User) session.getAttribute("userSession");
        provider.setCreatedBy(u.getId());
        provider.setCreationDate(new Date());
        if (providerService.addprovider(provider)==1){
            return "redirect:/provider/providerList";
        }
        return "provideradd";
    }

@RequestMapping("/modify")
    public  String modify(@RequestParam("proid") Integer proid,Model model){
        Provider provider=providerService.getProviderById(proid);
        model.addAttribute("provider",provider);
        return "providermodify";

}
@RequestMapping(value = "/modifysave",method = RequestMethod.POST)
    public  String modifysave(Provider provider, HttpSession session){
    User u = (User) session.getAttribute("userSession");
    provider.setCreatedBy(u.getId());
    provider.setCreationDate(new Date());
    if (providerService.updateProviderById(provider)==1){
        return "redirect:/provider/providerList";
    }
    return "providermodify";
}
@RequestMapping("/viewProvider/{id}")
    public String viewProvider (@PathVariable("id")Integer id,Model model){
        Provider provider=providerService.getProviderById(id);
        model.addAttribute("provider",provider);
        return "providerview";
}
}
