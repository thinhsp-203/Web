package service;

import model.Drink;

import java.util.List;

public interface DrinkService {
    List<Drink> getFeaturedDrinks(int limit);
    List<Drink> getDrinksByCategory(Long categoryId);
    List<Drink> getAll();
    void save(Drink drink);
}
