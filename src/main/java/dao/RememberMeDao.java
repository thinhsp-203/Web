package dao;

import model.RememberMeToken;

public interface RememberMeDao {
    void save(RememberMeToken t) throws Exception;
    RememberMeToken findBySelector(String selector) throws Exception;
    void deleteBySelector(String selector) throws Exception;
    void deleteAllForUser(Long userId) throws Exception;
}
