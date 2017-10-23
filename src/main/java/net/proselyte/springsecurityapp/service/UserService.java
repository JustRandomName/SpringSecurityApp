package net.proselyte.springsecurityapp.service;

import net.proselyte.springsecurityapp.model.User;

import java.util.List;


public interface UserService {

    void save(User user);

    List<User> findByUsername(String username);
}
