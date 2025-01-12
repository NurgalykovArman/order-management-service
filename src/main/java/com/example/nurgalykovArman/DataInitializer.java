package com.example.nurgalykovArman;

import com.example.nurgalykovArman.enums.Role;
import com.example.nurgalykovArman.models.Order;
import com.example.nurgalykovArman.models.Product;
import com.example.nurgalykovArman.models.User;
import com.example.nurgalykovArman.repositories.OrderRepository;
import com.example.nurgalykovArman.repositories.ProductRepository;
import com.example.nurgalykovArman.enums.OrderStatus;
import com.example.nurgalykovArman.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        // Создаем несколько заказов и продуктов
        Order order1 = new Order();
        order1.setCustomerName("Nurgalykov Arman");
        order1.setStatus(OrderStatus.PENDING);
        order1.setTotalPrice(150.0);

        Product product1 = new Product();
        product1.setName("Milk");
        product1.setPrice(50.0);
        product1.setQuantity(2);
        product1.setOrder(order1);  // Связываем продукт с заказом

        Product product2 = new Product();
        product2.setName("Bread");
        product2.setPrice(50.0);
        product2.setQuantity(1);
        product2.setOrder(order1);

        order1.getProducts().add(product1);
        order1.getProducts().add(product2);

        // Сохраняем заказ и продукты
        orderRepository.save(order1);

        // Создаем второй заказ
        Order order2 = new Order();
        order2.setCustomerName("Nurgalykov Aruzhan");
        order2.setStatus(OrderStatus.CONFIRMED);
        order2.setTotalPrice(200.0);

        Product product3 = new Product();
        product3.setName("Chocolate");
        product3.setPrice(100.0);
        product3.setQuantity(2);
        product3.setOrder(order2);

        order2.getProducts().add(product3);

        orderRepository.save(order2);

        logger.info("The database has been created, the initial data has been uploaded");
    }

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin")); // Пароль "admin"
            admin.setRoles(Set.of(Role.ADMIN));

            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user")); // Пароль "user"
            user.setRoles(Set.of(Role.USER));

            userRepository.save(admin);
            userRepository.save(user);
        }
    }
}
