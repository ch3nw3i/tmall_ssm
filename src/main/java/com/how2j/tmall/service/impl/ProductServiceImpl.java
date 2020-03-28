package com.how2j.tmall.service.impl;

import com.how2j.tmall.mapper.ProductMapper;
import com.how2j.tmall.pojo.Product;
import com.how2j.tmall.pojo.ProductImage;
import com.how2j.tmall.service.OrderItemService;
import com.how2j.tmall.service.ProductImageService;
import com.how2j.tmall.service.ProductService;
import com.how2j.tmall.service.ReviewService;
import com.how2j.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author chen
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private OrderItemService orderItemService;

    @Override
    public void add(Product product) {
        product.setCreateDate(new Date());
        productMapper.add(product);
    }

    @Override
    public void delete(Integer id) {
        productMapper.delete(id);
    }

    @Override
    public void update(Product product) {
        productMapper.update(product);
    }

    @Override
    public Product get(Integer id) {
        Product product = productMapper.get(id);
        ProductImage fpi = productImageService.getFirstProductImage(product.getId());
        product.setFirstProductImage(fpi);
        return product;
    }

//    @Override
//    public List<Product> list(Integer cid, Page page) {
//        return productMapper.list(cid, page);
//    }

    @Override
    public List<Product> list(Integer cid) {
        List<Product> productList = productMapper.list(cid);
        for (Product product: productList) {
            product.setFirstProductImage(productImageService.getFirstProductImage(product.getId()));
            product.setReviewCount(reviewService.total(product.getId()));
            product.setSaleCount(orderItemService.total(product.getId()));
        }
        return productList;
    }

//    @Override
//    public Integer total(Integer cid) {
//        return productMapper.total(cid);
//    }

    @Override
    public List<Product> search(String keyword) {
        List<Product> productList = productMapper.listByNameKeyword(keyword);
        for (Product p : productList) {
            p.setSaleCount(orderItemService.countSaleLastMonth(p.getId()));
            p.setReviewCount(reviewService.total(p.getId()));
            p.setFirstProductImage(productImageService.getFirstProductImage(p.getId()));
        }
        return productList;
    }

    @Override
    public Integer total(Integer cid) {
        return productMapper.total(cid);
    }
}
