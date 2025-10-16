package service;

import model.User;

public interface UserService {
    User authenticate(String username, String password);
    boolean register(String username, String password, String email, String fullname, String phone);
    User findByUsername(String username);
    User findById(long id);
}
