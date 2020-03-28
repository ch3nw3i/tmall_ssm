package com.how2j.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2j.tmall.pojo.*;
import com.how2j.tmall.service.*;
import com.how2j.tmall.util.ImageUtil;
import com.how2j.tmall.util.Page;
import com.how2j.tmall.util.UploadedImageFile;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chen
 */
@Controller
@RequestMapping("")
public class AdminController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private PropertyValueService propertyValueService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    // ----------------分类---------------------------------------

    @RequestMapping("admin_category_list")
    public String adminCategoryList(Model model, Page page) {
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Category> cs = categoryService.list();
        int total = (int) new PageInfo<>(cs).getTotal();
        page.setTotal(total);
        model.addAttribute("cs", cs);
        model.addAttribute("page", page);
        return "admin/listCategory";
    }

    @RequestMapping("admin_category_add")
    public String adminCategoryAdd(Category category, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException {
        categoryService.add(category, session, uploadedImageFile);
        return "redirect:/admin_category_list";
    }

    @RequestMapping("admin_category_edit")
    public String adminCategoryEdit(@Param("id") Integer id, Model model) {
        Category category = categoryService.get(id);
        model.addAttribute("c", category);
        return "admin/editCategory";
    }

    @RequestMapping("admin_category_update")
    public String adminCategoryUpdate(Category category, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException {
        categoryService.update(category, session, uploadedImageFile);
        return "redirect:/admin_category_list";
    }

    @RequestMapping("admin_category_delete")
    public String adminCategoryDelete(@Param("id") Integer id, HttpSession session) throws IOException {
        categoryService.delete(id, session);
        return "redirect:/admin_category_list";
    }

    // ----------------属性--------------------------------------

    @RequestMapping("admin_property_list")
    public String adminPropertyList(@Param("cid") Integer cid, Model model, Page page) {
        PageHelper.offsetPage(page.getStart(), page.getCount());
        Category category = categoryService.get(cid);
        List<Property> propertyList = propertyService.list(cid);
        int total = (int) new PageInfo<>(propertyList).getTotal();
        page.setTotal(total);
        model.addAttribute("c", category);
        model.addAttribute("ps", propertyList);
        model.addAttribute("page", page);
        return "admin/listProperty";
    }

    @RequestMapping("admin_property_add")
    public String adminPropertyAdd(Property property) {
        propertyService.add(property);
        return "redirect:/admin_property_list?cid=" + property.getCid();
    }

    @RequestMapping("admin_property_edit")
    public String adminPropertyEdit(@Param("id") Integer id, Model model) {
        Property property = propertyService.get(id);
        model.addAttribute("c", property.getCategory());
        model.addAttribute("p", property);
        return "admin/editProperty";
    }

    @RequestMapping("admin_property_update")
    public String adminPropertyUpdate(Property property) {
        propertyService.update(property);
        return "redirect:/admin_property_list?cid=" + property.getCid();
    }

    @RequestMapping("admin_property_delete")
    public String adminPropertyDelete(@Param("id") Integer id) {
        Property property = propertyService.get(id);
        propertyService.delete(id);
        return "redirect:/admin_property_list?cid=" + property.getCid();
    }

    // ----------------商品---------------------------------------

    @RequestMapping("admin_product_list")
    public String adminProductList(@Param("cid") Integer cid, Model model, Page page) {
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Product> productList = productService.list(cid);
        int total = (int) new PageInfo<>(productList).getTotal();
        page.setTotal(total);
        for (Product product : productList) {
            ProductImage fpi = productImageService.getFirstProductImage(product.getId());
            product.setFirstProductImage(fpi);
        }
        Category category = categoryService.get(cid);
        model.addAttribute("c", category);
        model.addAttribute("ps", productList);
        model.addAttribute("page", page);
        return "admin/listProduct";
    }

    @RequestMapping("admin_product_add")
    public String adminProductAdd(Product product) {
        productService.add(product);
        List<Product> productList = productService.list(product.getCid());
//        int total = (int) new PageInfo<>(productList).getTotal();
//        page.setTotal(total);

        return "redirect:/admin_product_list?cid=" + product.getCid();
    }

    @RequestMapping("admin_product_edit")
    public String adminProductEdit(@Param("id") Integer id, Model model) {
        Product product = productService.get(id);
        Category category = categoryService.get(product.getCid());
        model.addAttribute("c", category);
        model.addAttribute("p", product);
        return "admin/editProduct";
    }

    @RequestMapping("admin_product_update")
    public String adminProductUpdate(Product product) {
        productService.update(product);

        return "redirect:/admin_product_list?cid=" + product.getCid();
    }

    @RequestMapping("admin_product_delete")
    public String adminProductDelete(@Param("id") Integer id) {
        Product product = productService.get(id);
        productService.delete(id);
        return "redirect:/admin_product_list?cid=" + product.getCid();
    }

    // ----------------属性值--------------------------------------
    @RequestMapping("admin_propertyValue_edit")
    public String adminPropertyValueEdit(@Param("pid") Integer pid, Model model) {
        List<PropertyValue> propertyValueList =  propertyValueService.list(pid);
        Product product = productService.get(pid);
        if (propertyValueList.isEmpty()) {
            List<Property> propertyList = propertyService.list(product.getCid());
            for (Property p : propertyList) {
                PropertyValue pv = new PropertyValue();
                pv.setProperty(p);
                propertyValueList.add(pv);
            }
            for (PropertyValue pv : propertyValueList) {
                if (pv.getId() == null) {
                    pv.setPid(pid);
                    pv.setPtid(pv.getProperty().getId());
                    propertyValueService.add(pv);
                }
            }
            propertyValueList =  propertyValueService.list(pid);
        }
        model.addAttribute("pvs", propertyValueList);
        model.addAttribute("p", product);
        return "redirect:/admin_propertyValue_edit?pid=" + pid;
    }

    @RequestMapping("admin_propertyValue_update")
    public String adminPropertyValueUpdate(PropertyValue propertyValue) {
        propertyValueService.add(propertyValue);
        return "redirect:/admin_propertyValue_edit?pid=" + propertyValue.getPid();
    }

    // ----------------商品图片---------------------------------------

    @RequestMapping("admin_productImage_list")
    public String adminProductImageList(@Param("pid") Integer pid, Model model) {
        List<ProductImage> productImageList = productImageService.list(pid);
        List<ProductImage> pisSingle = new ArrayList<>();
        List<ProductImage> pisDetail = new ArrayList<>();
        for (ProductImage productImage : productImageList) {
            if ("type_single".equals(productImage.getType())) {
                pisSingle.add(productImage);
            }
            if ("type_detail".equals(productImage.getType())) {
                pisDetail.add(productImage);
            }
        }
        Product product = productService.get(pid);
        model.addAttribute("p", product);
        model.addAttribute("pisSingle", pisSingle);
        model.addAttribute("pisDetail", pisDetail);
        return "admin/listProductImage";
    }

    @RequestMapping("admin_productImage_edit")
    public String adminProductImageEdit() {
        return "admin/listProduct";
    }


    @RequestMapping("admin_productImage_add")
    public String adminProductImageAdd(ProductImage productImage, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException {
        productImageService.add(productImage, session, uploadedImageFile);
        return "redirect:/admin_productImage_list?pid=" + productImage.getPid();
    }

    @RequestMapping("admin_productImage_delete")
    public String adminProductImageDelete(@Param("id") Integer piid, HttpSession session) {
        productImageService.delete(piid, session);
        return "admin/listProductImage";
    }


    // ----------------用户---------------------------------------
    @RequestMapping("admin_user_list")
    public String adminUserList(Model model, Page page) {
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<User> userList = userService.list();
        int total = (int) new PageInfo<>(userList).getTotal();
        page.setTotal(total);
        model.addAttribute("page", page);
        model.addAttribute("us", userList);
        return "admin/listUser";
    }


    // ----------------订单---------------------------------------
    @RequestMapping("admin_order_list")
    public String adminOrderList(Model model, Page page) {
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Order> orderList = orderService.list();
        int total = (int) new PageInfo<>(orderList).getTotal();
        page.setTotal(total);
        model.addAttribute("page", page);
        model.addAttribute("os", orderList);
        return "admin/listOrder";
    }

    @RequestMapping("admin_order_delivery")
    public String adminOrderDelivery(Integer id, Model model) {
        orderService.delivery(id);
        return "redirect:/admin_order_list";
    }

}
