package util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public final class JpaUtil {
    private static final EntityManagerFactory EMF = buildEntityManagerFactory();

    private JpaUtil() {
    }

    private static EntityManagerFactory buildEntityManagerFactory() {
        Map<String, String> overrides = new HashMap<>();
        putIfPresent(overrides, "jakarta.persistence.jdbc.driver", System.getenv("JPA_JDBC_DRIVER"));
        putIfPresent(overrides, "jakarta.persistence.jdbc.url", System.getenv("JPA_JDBC_URL"));
        putIfPresent(overrides, "jakarta.persistence.jdbc.user", System.getenv("JPA_JDBC_USER"));
        putIfPresent(overrides, "jakarta.persistence.jdbc.password", System.getenv("JPA_JDBC_PASSWORD"));
        putIfPresent(overrides, "hibernate.dialect", System.getenv("HIBERNATE_DIALECT"));
        putIfPresent(overrides, "hibernate.hbm2ddl.auto", System.getenv("HIBERNATE_HBM2DDL"));
        if (Boolean.parseBoolean(System.getenv().getOrDefault("JPA_SHOW_SQL", "false"))) {
            overrides.put("hibernate.show_sql", "true");
            overrides.put("hibernate.format_sql", "true");
        }
        return Persistence.createEntityManagerFactory("alotraPU", overrides);
    }

    private static void putIfPresent(Map<String, String> overrides, String key, String value) {
        if (value != null && !value.isBlank()) {
            overrides.put(key, value);
        }
    }

    public static EntityManager getEntityManager() {
        return EMF.createEntityManager();
    }

    public static void shutdown() {
        if (EMF.isOpen()) {
            EMF.close();
        }
    }
}
