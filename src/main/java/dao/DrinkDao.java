package dao;

import model.Drink;

import java.util.List;

public interface DrinkDao {
    List<Drink> findAll();
    List<Drink> findFeatured(int limit);
    List<Drink> findByCategory(Long categoryId);
    void save(Drink drink);
}
