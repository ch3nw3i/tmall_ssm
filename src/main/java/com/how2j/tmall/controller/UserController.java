package com.how2j.tmall.controller;

import com.how2j.tmall.pojo.User;
import com.how2j.tmall.service.CartService;
import com.how2j.tmall.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;

    @RequestMapping("loginPage")
    public String loginPage() {

        return "/fore/login";
    }

    @RequestMapping("forelogin")
    public String foreLogin(User user, Model model, HttpSession session) {
        User u = userService.login(user);
        if (u != null) {
            Integer cartNum = cartService.getCartNumByUid(u.getId());
            model.addAttribute("user", u);

            session.setAttribute("user", u);
            session.setAttribute("cartTotalItemNumber", cartNum);
            return "redirect:/forehome";
        } else {
            session.setAttribute("msg", "用户名或密码错误");
            return "/fore/login";
        }
    }

    @RequestMapping("forelogout")
    public String foreLogout(Model model, HttpSession session) {
        model.addAttribute("user", null);
        session.removeAttribute("user");
        session.removeAttribute("cartTotalItemNumber");
        return "redirect:/forehome";
    }

    @RequestMapping("registerPage")
    public String registerPage() {
        return "/fore/register";
    }

    @RequestMapping("foreregister")
    public String foreRegister(User user, Model model, HttpSession session) {
        Integer result = userService.add(user);
        User u = userService.login(user);
        if (!u.equals(null)) {
            return "/fore/registerSuccess";
        } else {
            return "/fore/register";
        }
    }

    @ResponseBody
    @RequestMapping("forecheckLogin")
    public String foreCheckLogin(Model model, HttpSession session) {
        User u = (User) session.getAttribute("user");
        if (u != null) {
            return "success";
        } else {
            return "false";
        }
    }

    @ResponseBody
    @RequestMapping("foreloginAjax")
    public String foreLoginAjax(@Param("name") String name, @Param("password") String password, HttpSession session) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        User u = userService.login(user);
        if (u != null) {
            Integer cartNum = cartService.getCartNumByUid(u.getId());
            session.setAttribute("user", u);
            session.setAttribute("cartTotalItemNumber", cartNum);
            return "success";
        } else {
            return "false";
        }
    }
}
