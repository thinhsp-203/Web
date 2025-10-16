package dao;

import model.User;

public interface UserDao {
    User findByUsername(String username);
    User findById(Long id);
    void save(User user);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
