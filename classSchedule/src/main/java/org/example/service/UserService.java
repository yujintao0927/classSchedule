package org.example.service;

import org.example.pojo.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User findByUserName(String username);

    void register(String username, String password);

}
