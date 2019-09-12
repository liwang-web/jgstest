package cn.kgc.ssm.controller;

import cn.kgc.ssm.bean.User;
import cn.kgc.ssm.service.UserService;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sun.istack.internal.logging.Logger;
import org.apache.commons.io.FilenameUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;


@Controller
@RequestMapping("/user")
public class UserController {
    Logger logger = Logger.getLogger(UserController.class);
    @Resource
    private UserService userService;

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/dologin")
    public String dologin(@RequestParam("userCode") String userCode,
                          @RequestParam("userPassword") String userPassword,
                          Model model, HttpSession session) {
        User user = userService.getUser(userCode);
        if (user == null) {

            model.addAttribute("error", "用户名不存在");
            return "login";
        } else {
            if (!user.getUserPassword().equals(userPassword)) {
                model.addAttribute("error", "密码错误");
                return "login";
            }
            session.setAttribute("userSession", user);
            return "frame";
        }
    }

    //*************************局部异常测试**********
 /*   @RequestMapping(value = "/exlogin.html", method = RequestMethod.GET)
    public String exLogin(@RequestParam String userCode, @RequestParam String userPassword) {
        logger.debug("exLogin==========================");
        //调用service方法,进行用户匹配
        User user = userService.getUser(userCode);
        if (null == user) {//登录失败
            throw new RuntimeException("用户名或密码不正确!");
        }
        return  "redirect:/user/main.html";
    }


        @ExceptionHandler(value = {RuntimeException.class})
        public  String handlerException(RuntimeException e, HttpServletRequest req){
            req.setAttribute("e",e);
            return  "error";

    }*/
    @RequestMapping("/userList")
    public String userList(@RequestParam(value = "userName", required = false) String userName,
                           @RequestParam(value = "userRole", required = false) Integer userRole,
                           @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
                           Model model) {
        if (pageIndex == null) {
            pageIndex = 1;
        }
        Page<Object> pages = PageHelper.startPage(pageIndex, 4);
        /*pages.getPageNum();
        pages.getPages();
        pages.getTotal();*/
        List<User> userList = userService.getUserList(userName, userRole);
        if (userList != null) {
            model.addAttribute("userList", userList);
            model.addAttribute("pages", pages);
            model.addAttribute("userName", userName);
            model.addAttribute("userRole", userRole);
            return "userlist";
        }
        return "frame";
    }

    @RequestMapping("/useradd")
    public String useradd() {
        return "useradd";
    }


    @RequestMapping("/usermodify")
    public String usermodify(@RequestParam("uid") Integer uid, Model model) {
        User user = userService.getUserById(uid);
        model.addAttribute("user", user);
        return "usermodify";
    }

    @RequestMapping(value = "/usermodifysave", method = RequestMethod.POST)
    public String usermodifysave(User user, HttpSession session) {
        User user1 = (User) session.getAttribute("userSession");
        user.setModifyBy(user1.getId());
        user.setModifyDate(new Date());
        if (userService.updataUserById(user) == 1) {
            return "redirect:/user/userList";
        }
        return "usermodify";
    }

    @RequestMapping(value="/useraddsave",method = RequestMethod.POST)
    public String useraddsave(User user, HttpSession session,
                              HttpServletRequest request,
                              @RequestParam(value="attachs",required = false) MultipartFile[] files){
        String idPicPath = null;
        String workPicPath = null;
        String errorinfo = "";

        for(int i = 0 ; i < files.length ; i ++){

            if(i == 0){
                errorinfo = "uploadFileError";
            }else if(i == 1){
                errorinfo = "uploadWpError";
            }

            MultipartFile file = files[i];
            if(!file.isEmpty()){
                //获取文件的原文件名
                String oldFileName = file.getOriginalFilename();
                //获取文件的格式
                String suffix = FilenameUtils.getExtension(oldFileName);
                if(file.getSize() > 500000){
                    request.setAttribute(errorinfo,"上传失败，文件不能大于500kb");
                    return "useradd";
                }else if(suffix.equalsIgnoreCase("jpg") ||
                        suffix.equalsIgnoreCase("jpeg") ||
                        suffix.equalsIgnoreCase("png") ||
                        suffix.equalsIgnoreCase("pneg")){

                    String newFileName = System.currentTimeMillis() + new Random(10000).nextInt() +"Persional.jpg";
                    String realPath = session.getServletContext().getRealPath("statics/upload");
                    File filepath = new File(realPath);
                    if(!filepath.exists()){
                        filepath.mkdirs();
                    }

                    logger.info(">>>>>>>>>>>>>>>>>>>>>>"+realPath);

                    try {
                        file.transferTo(new File(realPath,newFileName));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if(i == 0){
                        idPicPath = realPath + File.separator + newFileName;
                    }else if(i == 1){
                        workPicPath = realPath + File.separator + newFileName;
                    }
                }else{
                    request.setAttribute(errorinfo,"上传的文件格式不正确");
                    return "useradd";
                }
            }
        }

        User u = (User)session.getAttribute("userSession");
        user.setCreatedBy(u.getId());
        user.setCreationDate(new Date());
        user.setIdPicPath(idPicPath);
        user.setWordPicPath(workPicPath);
        if(userService.addUser(user) == 1){
            return "redirect:/user/userList";
        }
        return "useradd";
    }
    @RequestMapping("/userview/{id}")
    public String userview(@PathVariable("id") Integer id, Model model){
        User user = userService.getUserById(id);
        model.addAttribute("user",user);
        return "userview";
    }
}