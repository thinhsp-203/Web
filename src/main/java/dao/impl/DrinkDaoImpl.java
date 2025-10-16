package dao.impl;

import dao.DrinkDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.Drink;
import util.JpaUtil;

import java.util.List;

public class DrinkDaoImpl implements DrinkDao {
    @Override
    public List<Drink> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT d FROM Drink d ORDER BY d.name", Drink.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Drink> findFeatured(int limit) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Drink> query = em.createQuery(
                    "SELECT d FROM Drink d WHERE d.featured = true ORDER BY d.id DESC",
                    Drink.class);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Drink> findByCategory(Long categoryId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Drink> query = em.createQuery(
                    "SELECT d FROM Drink d WHERE d.category.id = :cate ORDER BY d.name",
                    Drink.class);
            query.setParameter("cate", categoryId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public void save(Drink drink) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (drink.getCategory() != null && drink.getCategory().getId() != null) {
                drink.setCategory(em.getReference(model.Category.class, drink.getCategory().getId()));
            }
            if (drink.getId() == null) {
                em.persist(drink);
            } else {
                em.merge(drink);
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
}
