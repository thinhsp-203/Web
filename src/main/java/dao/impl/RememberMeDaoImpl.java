package dao.impl;

import dao.RememberMeDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.RememberMeToken;
import util.JpaUtil;

public class RememberMeDaoImpl implements RememberMeDao {

    @Override
    public void save(RememberMeToken t) throws Exception {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (t.getUser() != null && t.getUser().getId() != null) {
                t.setUser(em.getReference(model.User.class, t.getUser().getId()));
            }
            if (t.getId() == null) {
                em.persist(t);
            } else {
                em.merge(t);
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
    public RememberMeToken findBySelector(String selector) throws Exception {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<RememberMeToken> query = em.createQuery(
                    "SELECT t FROM RememberMeToken t JOIN FETCH t.user WHERE t.selector = :selector",
                    RememberMeToken.class);
            query.setParameter("selector", selector);
            return query.getResultStream().findFirst().orElse(null);
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteBySelector(String selector) throws Exception {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            RememberMeToken token = em.createQuery(
                            "SELECT t FROM RememberMeToken t WHERE t.selector = :selector",
                            RememberMeToken.class)
                    .setParameter("selector", selector)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
            if (token != null) {
                em.remove(token);
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
    public void deleteAllForUser(Long userId) throws Exception {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM RememberMeToken t WHERE t.user.id = :userId")
                    .setParameter("userId", userId)
                    .executeUpdate();
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
}
