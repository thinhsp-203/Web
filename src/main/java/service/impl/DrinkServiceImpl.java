package service.impl;

import dao.DrinkDao;
import dao.impl.DrinkDaoImpl;
import model.Drink;
import service.DrinkService;

import java.util.List;

public class DrinkServiceImpl implements DrinkService {
    private final DrinkDao drinkDao = new DrinkDaoImpl();

    @Override
    public List<Drink> getFeaturedDrinks(int limit) {
        return drinkDao.findFeatured(limit);
    }

    @Override
    public List<Drink> getDrinksByCategory(Long categoryId) {
        return drinkDao.findByCategory(categoryId);
    }

    @Override
    public List<Drink> getAll() {
        return drinkDao.findAll();
    }

    @Override
    public void save(Drink drink) {
        drinkDao.save(drink);
    }
}
