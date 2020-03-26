package com.how2j.tmall.pojo;

/**
 * 商品图片
 * id 图片id
 * pid 商品id
 * type 图片类型（独立、详情）
 * @author chen
 */
public class ProductImage {

    private Integer id;
    private Integer pid;
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ProductImage{" +
                "id=" + id +
                ", pid=" + pid +
                ", type='" + type + '\'' +
                '}';
    }
}
