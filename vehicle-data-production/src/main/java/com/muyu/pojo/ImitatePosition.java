package com.muyu.pojo;

/**
 * @author 牧鱼
 * @Classname ImitatePosition
 * @Description TODO
 * @Date 2021/9/15
 */
public class ImitatePosition {

    private Integer id;

    private String name;

    private String position;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "ImitatePosition{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
