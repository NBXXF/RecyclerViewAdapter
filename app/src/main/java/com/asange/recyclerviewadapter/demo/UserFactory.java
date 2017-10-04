package com.asange.recyclerviewadapter.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Description
 * Company Beijing icourt
 * author  youxuan  E-mail:xuanyouwu@163.com
 * date createTime：2017/10/4
 * version 2.1.0
 */
public class UserFactory {

    public static List<User> createUsers() {
        List<User> users = new ArrayList<>();
        int size = 5 + new Random().nextInt(10);
        for (int i = 0; i < size; i++) {
            users.add(new User(new Random().nextInt(100), "" + new Random().nextFloat()));
        }
        return users;
    }

    public static User createUser() {
        return new User(new Random().nextInt(100), "" + new Random().nextFloat());
    }
}
