package dao.impl;

import dao.UserDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.User;
import util.JpaUtil;

public class UserDaoImpl implements UserDao {
    @Override
    public User findByUsername(String username) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE LOWER(u.userName) = :username", User.class);
            query.setParameter("username", username.toLowerCase());
            return query.getResultStream().findFirst().orElse(null);
        } finally {
            em.close();
        }
    }

    @Override
    public User findById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public void save(User user) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (user.getId() == null) {
                em.persist(user);
            } else {
                em.merge(user);
            }
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        return exists("SELECT COUNT(u) FROM User u WHERE LOWER(u.userName) = :value", username, true);
    }

    @Override
    public boolean existsByEmail(String email) {
        return exists("SELECT COUNT(u) FROM User u WHERE LOWER(u.email) = :value", email, true);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return exists("SELECT COUNT(u) FROM User u WHERE u.phone = :value", phone, false);
    }

    private boolean exists(String jpql, String value, boolean toLower) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("value", toLower ? value.toLowerCase() : value);
            Long count = query.getSingleResult();
            return count != null && count > 0;
        } finally {
            em.close();
        }
    }
}
