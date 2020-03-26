package com.how2j.tmall.pojo;

/**
 * 属性
 * id 属性id
 * cid 分类id
 * name 属性名
 * @author chen
 */
public class Property {

    private Integer id;
    private Integer cid;
    private String name;
    private Category category;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", cid=" + cid +
                ", name='" + name + '\'' +
                ", category=" + category +
                '}';
    }
}
