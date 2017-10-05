package com.asange.recyclerviewadapter.demo;

import com.asange.recyclerviewadapter.SelectableEntity;

/**
 * Description
 * Company Beijing icourt
 * author  youxuan  E-mail:xuanyouwu@163.com
 * date createTime：2017/10/4
 * version 2.1.0
 */
public class User implements SelectableEntity {
    private int age;
    private String name;
    private boolean isItemSelected;

    public User(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isItemSelected() {
        return isItemSelected;
    }

    @Override
    public void setItemSelect(boolean select) {
        isItemSelected = select;
    }

    @Override
    public void toggleItemSelect() {
        isItemSelected = !isItemSelected;
    }
}
