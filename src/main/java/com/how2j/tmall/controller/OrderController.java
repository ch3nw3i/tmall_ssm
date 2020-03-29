package com.how2j.tmall.controller;

import com.how2j.tmall.pojo.*;
import com.how2j.tmall.service.*;
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

    /**
     * 显示购物车
     * @param model
     * @param session
     * @return
     */
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
     * 商品页 立即购买
     * @param pid
     * @return
     */
    @RequestMapping("forebuyone")
    public String foreBuyOne(@Param("pid") Integer pid, @Param("num") Integer num, Model model, HttpSession session) {
        User u = (User) session.getAttribute("user");
        Order order = new Order();
        List<OrderItem> ois = new ArrayList<>();
        OrderItem oi = new OrderItem();
        Product product = productService.get(pid);
        oi.setNumber(num);
        oi.setProduct(product);
        Double total = 0.0d;
        total += oi.getNumber() * oi.getProduct().getPromotePrice();
        ois.add(oi);
        order.setOrderItems(ois);
        model.addAttribute("total", total);
        model.addAttribute("carts", ois);
        session.setAttribute("order", order);
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

    /**
     * 获取勾选的商品的购物车id，传递到订单结算页面
     * @param id
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("forebuy")
    public String foreBuy(@Param("id") String id, Model model, HttpSession session) {
        String[] split = id.split(",");
        List<Cart> cartList = new ArrayList<>();
        Float total = 0.0f;
        for (String s : split) {
            Cart cart = cartService.get(Integer.parseInt(s));
            total += cart.getProduct().getPromotePrice();
            cartList.add(cart);
        }
        session.setAttribute("cartList", cartList);
        model.addAttribute("carts", cartList);
        model.addAttribute("total", total);
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
     * 用户确认收货 界面
     */
    @RequestMapping("foreconfirmPay")
    public String foreConfirmPay(@Param("oid") Integer oid, Model model) {
        Order order = orderService.getById(oid);
        List<OrderItem> orderItemList = orderItemService.listByOid(oid);
        order.setOrderItems(orderItemList);
        model.addAttribute("o", order);
        return "/fore/confirmPay";
    }

    /**
     * 下订单，跳转到 支付页面
     * @param order
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("forecreateOrder")
    public String foreCreateOrder(Order order, Model model, HttpSession session) {
        List<Cart> cartList = (List<Cart>) session.getAttribute("cartList");
        Order o = (Order) session.getAttribute("order");
        User u = (User) session.getAttribute("user");
        // 商品页直接购买执行该流程
        if (o != null) {
            List<OrderItem> orderItemList = o.getOrderItems();
            for (OrderItem oi : orderItemList) {
                oi.setPid(oi.getProduct().getId());
                oi.setUid(u.getId());
            }
            order.setUid(u.getId());
            orderService.addOrder(order, o.getOrderItems());
            session.removeAttribute("order");
        }

        // 购物车购买执行该流程
        if (cartList != null) {
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
            for (Cart c : cartList) {
                cartService.delete(c.getId());
            }
            session.removeAttribute("orderList");
        }
        model.addAttribute("totalPrice", order.getTotalPrice());
        model.addAttribute("oid", order.getId());
        return "/fore/alipay";
    }

    /**
     * 跳转到支付成功
     * @param oid
     * @param total
     * @param model
     * @return
     */
    @RequestMapping("forealipay")
    public String foreAlipay(@Param("oid") Integer oid, @Param("total") Float total, Model model) {
        model.addAttribute("oid", oid);
        model.addAttribute("totalPrice", total);
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
    public String foreOrderConfirmed(Integer oid) {
        orderService.confirmPay(oid);
        return "/fore/orderConfirmed";
    }


    /**
     * 删除订单
     * @return
     */
    @RequestMapping("foredeleteOrder")
    public String foreDeleteOrder(Integer oid) {
        Integer result = orderService.delete(oid);
        if (result == 1) {
            return "success";
        } else {
            return "false";
        }
    }

    /**
     * 跳转 评论页面
     * @param oid
     * @param model
     * @return
     */
    @RequestMapping("forereview")
    public String foreReview(Integer oid, Model model) {
        Order order = orderService.getById(oid);
        List<OrderItem> orderItemList = orderItemService.listByOid(order.getId());
        order.setOrderItems(orderItemList);
        model.addAttribute("o", order);
        return "/fore/review";
    }
}
