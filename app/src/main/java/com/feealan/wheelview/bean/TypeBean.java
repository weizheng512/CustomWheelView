package com.feealan.wheelview.bean;

import java.util.List;

/**
 * 活动类型解析实体类
 *
 * @author fee https://github.com/FeeAlan QQ：752115651 费松柏
 * @created 2016/05/16
 */
public class TypeBean {
    private String      p;
    private int         n;
    private List<Cbean> c;

    public TypeBean(String p, int n, List<Cbean> c) {
        this.p = p;
        this.n = n;
        this.c = c;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public List<Cbean> getC() {
        return c;
    }

    public void setC(List<Cbean> c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return "TypeBean{" +
                "p='" + p + '\'' +
                ", n=" + n +
                ", c=" + c +
                '}';
    }

    public static class Cbean {
        private String content;
        private int    num;

        @Override
        public String toString() {
            return "Cbean{" +
                    "content='" + content + '\'' +
                    ", num=" + num +
                    '}';
        }

        public Cbean(int num, String content) {
            this.num = num;
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
