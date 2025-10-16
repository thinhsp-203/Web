package dao.impl;

import dao.CategoryDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.Category;
import util.JpaUtil;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    @Override
    public void insert(Category category) {
        executeInTransaction(em -> em.persist(category));
    }

    @Override
    public void edit(Category category) {
        executeInTransaction(em -> em.merge(category));
    }

    @Override
    public void delete(Long id) {
        executeInTransaction(em -> {
            Category found = em.find(Category.class, id);
            if (found != null) {
                em.remove(found);
            }
        });
    }

    @Override
    public Category get(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(Category.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public Category get(String name) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Category> query = em.createQuery("SELECT c FROM Category c WHERE LOWER(c.name) = :name", Category.class);
            query.setParameter("name", name.toLowerCase());
            List<Category> result = query.getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Category> getAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Category c ORDER BY c.name", Category.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Category> search(String keyword) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            String pattern = "%" + keyword.toLowerCase() + "%";
            TypedQuery<Category> query = em.createQuery(
                    "SELECT c FROM Category c WHERE LOWER(c.name) LIKE :keyword ORDER BY c.name",
                    Category.class);
            query.setParameter("keyword", pattern);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    private interface EntityAction {
        void accept(EntityManager em);
    }

    private void executeInTransaction(EntityAction action) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            action.accept(em);
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
