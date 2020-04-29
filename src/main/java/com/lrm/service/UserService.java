package com.lrm.service;

import com.lrm.po.User;

public interface UserService {
    public User checkUser(String username, String password);
}
