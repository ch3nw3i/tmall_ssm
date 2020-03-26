package com.how2j.tmall.controller;

import com.how2j.tmall.pojo.*;
import com.how2j.tmall.service.CartService;
import com.how2j.tmall.service.OrderItemService;
import com.how2j.tmall.service.OrderService;
import com.how2j.tmall.service.ProductService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("")
public class OrderController {

    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private ProductService productService;

    @RequestMapping("forecart")
    public String foreCart(Model model, HttpSession session) {
        User u = (User) session.getAttribute("user");
        if (u != null) {
            List<Cart> cartList = cartService.listByUid(u.getId());
            model.addAttribute("cartList", cartList);
            return "/fore/cart";
        } else {
            return "/fore/login";
        }
    }

    @RequestMapping("forebought")
    public String foreBought(Model model, HttpSession session) {
        User u = (User) session.getAttribute("user");
        if (u != null) {
            List<Order> orderList = orderService.listByUid(u.getId());
            model.addAttribute("os", orderList);
            return "/fore/bought";
        } else {
            return "/fore/login";
        }
    }

    /**
     * 立即购买
     * @param pid
     * @return
     */
    @RequestMapping("forebuyone")
    public String foreBuyOne(@Param("pid") Integer pid, @Param("num") Integer num, Model model, HttpSession session) {
        User u = (User) session.getAttribute("user");
        Cart cart = new Cart();
        Product product = productService.get(pid);
        cart.setNumber(num);
        cart.setProduct(product);
        Double total = 0.0d;
        total += cart.getNumber() * cart.getProduct().getPromotePrice();
        List<Cart> cartList = new ArrayList<>();
        cartList.add(cart);
        model.addAttribute("total", total);
        model.addAttribute("carts", cartList);
        session.setAttribute("carts", cartList);
        return "/fore/buy";
    }

    @ResponseBody
    @RequestMapping("foreaddCart")
    public String foreAddCart(@Param("pid") Integer pid, @Param("num") Integer num, Model model, HttpSession session) {
        User u = (User) session.getAttribute("user");
        if (u != null) {
            Cart cart = new Cart();
            cart.setPid(pid);
            cart.setNumber(num);
            cart.setUid(u.getId());
            cartService.add(cart);
            return "success";
        } else {
            return "false";
        }
    }

    @RequestMapping("foredeleteCart")
    public String foreDeleteCart(@Param("id") Integer id) {
        cartService.delete(id);
        return "success";
    }

    @RequestMapping("forebuy")
    public String foreBuy(@Param("id") List<Integer> idList, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Cart> cartList = cartService.listByUid(user.getId());
        model.addAttribute("carts", cartList);
        return "/fore/buy";
    }

    @ResponseBody
    @RequestMapping("forechangeCartItem")
    public String foreChangeCartItem(@Param("pid") Integer pid, @Param("number") Integer number, HttpSession session) {
        User u = (User) session.getAttribute("user");
        Cart c = new Cart();
        c.setUid(u.getId());
        c.setPid(pid);
        c.setNumber(number);
        cartService.update(c);
        return "success";
    }

    /**
     * 用户确认收货
     */
    @RequestMapping("foreconfirmPay")
    public String foreConfirmPay(@Param("oid") Integer oid) {
        orderService.confirmPay(oid);
        return "/fore/payed";
    }

    /**
     * 下订单
     * @param order
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("forecreateOrder")
    public String foreCreateOrder(Order order, Model model, HttpSession session) {
        List<Cart> cartList = (List<Cart>) session.getAttribute("carts");
        User u = (User) session.getAttribute("user");
        List<OrderItem> orderItemList = new ArrayList<>();
        order.setUid(u.getId());
        for (Cart c : cartList) {
            OrderItem oi = new OrderItem();
            oi.setNumber(c.getNumber());
            oi.setProduct(c.getProduct());
            oi.setPid(oi.getProduct().getId());
            oi.setUid(u.getId());
            orderItemList.add(oi);
        }
        orderService.addOrder(order, orderItemList);
        // !!! 可能出现问题的代码 !!!
        Integer lastOid = orderService.getLastOid();
        model.addAttribute("totalPrice", order.getTotalPrice());
        model.addAttribute("oid", lastOid);
        return "/fore/alipay";
    }

    /**
     * 支付宝支付确认 status：waitPay -> waitDelivery
     * @param oid
     * @param total
     * @param model
     * @return
     */
    @RequestMapping("forepayed")
    public String forePayed(@Param("oid") Integer oid, @Param("total") Double total, Model model) {
        orderService.payed(oid);
        Order order = orderService.getById(oid);
        model.addAttribute("o", order);
        return "/fore/payed";
    }

    /**
     * 确认收货 status：waitConfirm -> waitReview
     * @return
     */
    @RequestMapping("foreorderConfirmed")
    public String foreOrderConfirmed(Model model) {

        return "/fore/confirmPay";
    }
}
