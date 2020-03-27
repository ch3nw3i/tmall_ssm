package com.how2j.tmall.controller;

import com.how2j.tmall.pojo.*;
import com.how2j.tmall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("")
public class ProductController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private PropertyValueService propertyValueService;

    @RequestMapping("forehome")
    public String foreHome(Model model, HttpSession session) {
        List<Category> categoryList = categoryService.listAll();
        for (Category c : categoryList) {
            List<Product> products = productService.list(c.getId());
            c.setProducts(products);
        }
        if (!session.isNew()) {
            User user = (User) session.getAttribute("user");
            Integer cartNum = (Integer) session.getAttribute("cartTotalItemNumber");

            model.addAttribute("cartTotalItemNumber", cartNum);
            model.addAttribute("user", user);
        }
        model.addAttribute("cs", categoryList);
        return "/fore/home";
    }

    @RequestMapping("foreproduct")
    public String foreProduct(Integer pid, Model model) {
        Product product = productService.get(pid);
        List<Review> reviewList = reviewService.list(pid);
        List<PropertyValue> propertyValueList = propertyValueService.list(pid);
        List<ProductImage> productImageList = productImageService.list(pid);
        List<ProductImage> productSingleImages = new ArrayList<>();
        List<ProductImage> productDetailImages = new ArrayList<>();
        for (ProductImage pi : productImageList) {
            if ("type_single".equals(pi.getType())) {
                productSingleImages.add(pi);
            }
            if ("type_detail".equals(pi.getType())) {
                productDetailImages.add(pi);
            }
        }
        product.setProductSingleImages(productSingleImages);
        product.setProductDetailImages(productDetailImages);

        model.addAttribute("p", product);
        model.addAttribute("reviews", reviewList);
        model.addAttribute("pvs", propertyValueList);
        return "/fore/product";
    }

    @RequestMapping("foresearch")
    public String foreSearch(String keyword, Model model) {
        List<Product> searchResults = productService.search(keyword);
        model.addAttribute("ps", searchResults);
        return "/fore/searchResult";
    }
}
