package com.feealan.wheelview.bean;

import java.util.List;

/**
 * 解析城市数据实体类
 *
 * @author fee https://github.com/FeeAlan QQ：752115651 费松柏
 * @created 2016/05/16
 */
public class CityBean {

    /**
     * area : 010
     * code : 110111
     * level : 2
     * name : 房山区
     * prefix : 区
     */

    private String         area;
    private String         code;
    private int            level;
    private String         name;
    private String         prefix;
    private List<CityBean> cities;

    public List<CityBean> getCities() {
        return cities;
    }

    public void setCities(List<CityBean> cities) {
        this.cities = cities;
    }

    @Override
    public String toString() {
        return "CityBean{" +
                "area='" + area + '\'' +
                ", code='" + code + '\'' +
                ", level=" + level +
                ", name='" + name + '\'' +
                ", prefix='" + prefix + '\'' +
                ", cities=" + cities +
                '}';
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getArea() {
        return area;
    }

    public String getCode() {
        return code;
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }
}
