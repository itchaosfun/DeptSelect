package com.cn.chaos.pcddemo;

/**
 * Created by xx on 2016/8/21.
 */
public class DeptBean {
    public String  code;
    public String name;
    public String pcode;

    public boolean isCheck = false;//用来记录当前条目是否被选中

    public DeptBean(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
