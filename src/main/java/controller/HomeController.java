package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Category;
import model.Drink;
import service.DrinkService;
import service.impl.CategoryServiceImpl;
import service.impl.DrinkServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/home")
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final CategoryServiceImpl categoryService = new CategoryServiceImpl();
    private final DrinkService drinkService = new DrinkServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Category> categories = categoryService.getAll();
        req.setAttribute("categories", categories);

        String cateParam = req.getParameter("category");
        List<Drink> drinks;
        if (cateParam != null && !cateParam.isBlank()) {
            try {
                Long cateId = Long.valueOf(cateParam);
                drinks = drinkService.getDrinksByCategory(cateId);
                req.setAttribute("activeCategory", cateId);
            } catch (NumberFormatException e) {
                drinks = drinkService.getFeaturedDrinks(12);
            }
        } else {
            drinks = drinkService.getFeaturedDrinks(12);
        }
        req.setAttribute("drinks", drinks);
        req.getRequestDispatcher("/views/home.jsp").forward(req, resp);
    }
}
