package listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import model.Category;
import model.Drink;
import service.DrinkService;
import service.impl.CategoryServiceImpl;
import service.impl.DrinkServiceImpl;

import java.math.BigDecimal;
@WebListener
public class AppInitializer implements ServletContextListener {
    private final CategoryServiceImpl categoryService = new CategoryServiceImpl();
    private final DrinkService drinkService = new DrinkServiceImpl();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        if (categoryService.getAll().isEmpty()) {
            Category classic = createCategory("Trà sữa truyền thống", "", "Hương vị quen thuộc");
            Category special = createCategory("Signature", "", "Công thức độc quyền");
            Category topping = createCategory("Topping", "", "Đa dạng lựa chọn");

            categoryService.insert(classic);
            categoryService.insert(special);
            categoryService.insert(topping);

            Category persistedClassic = categoryService.get("Trà sữa truyền thống");
            Category persistedSpecial = categoryService.get("Signature");
            Category persistedTopping = categoryService.get("Topping");

            if (persistedClassic != null && persistedSpecial != null && persistedTopping != null) {
                seedDrinks(persistedClassic, persistedSpecial, persistedTopping);
            }
        }
    }

    private Category createCategory(String name, String icon, String description) {
        Category category = new Category();
        category.setName(name);
        category.setIcon(icon);
        category.setDescription(description);
        return category;
    }

    private void seedDrinks(Category classic, Category special, Category topping) {
        Drink drink1 = createDrink("Trà sữa trân châu", new BigDecimal("45000"),
                "Trân châu đen dẻo thơm kết hợp trà đen đậm vị.",
                "https://images.unsplash.com/photo-1546171753-97d7676f45b9?w=800", classic, true);
        Drink drink2 = createDrink("Matcha kem cheese", new BigDecimal("52000"),
                "Lớp kem cheese béo mặn hòa quyện matcha Nhật Bản.",
                "https://images.unsplash.com/photo-1544281443-1541e4886875?w=800", special, true);
        Drink drink3 = createDrink("Trà đào cam sả", new BigDecimal("48000"),
                "Vị thanh mát từ đào, cam và sả tươi.",
                "https://images.unsplash.com/photo-1534423861386-85a16f5d13fd?w=800", special, false);
        Drink drink4 = createDrink("Bánh flan trứng", new BigDecimal("28000"),
                "Topping mềm mịn dành cho mọi ly trà sữa.",
                "https://images.unsplash.com/photo-1541599540903-216a46ca1dc0?w=800", topping, false);

        drinkService.save(drink1);
        drinkService.save(drink2);
        drinkService.save(drink3);
        drinkService.save(drink4);
    }

    private Drink createDrink(String name, BigDecimal price, String description, String thumbnail,
                               Category category, boolean featured) {
        Drink drink = new Drink();
        drink.setName(name);
        drink.setPrice(price);
        drink.setDescription(description);
        drink.setThumbnail(thumbnail);
        drink.setCategory(category);
        drink.setFeatured(featured);
        return drink;
    }
}
