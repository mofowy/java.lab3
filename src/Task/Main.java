package Task;

import java.io.FileNotFoundException;
import Task.FileLoadException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, FileLoadException, ReceiptGenerationException {

        Store store = new Store();

        // Завантаження даних з файлу
        store.loadProductsFromFile("D:\\products.txt");

        // Отримання всіх продуктів
        List<Product> allProducts = store.getProducts();

        // Продаж продукту
        store.removeProduct(allProducts.get(0));

        // Редагування продукту
        store.addProduct(new Product("New Apple", 5.0, 15));

        // Збереження історії покупок
        store.saveOrdersHistory();

        // Створення замовлення
        Customer customer = new Customer("John", "Doe");
        List<Product> orderProducts = store.getProducts();
        store.makeOrder(customer, orderProducts);

        List<Order> orders = new ArrayList<>();
        // Додаємо замовлення до списку
        Order order = new Order(LocalDate.now(), customer, orderProducts, false);
        orders.add(order);

        // Перевіряємо наявність замовлень перед викликом generateReceipt
        if (!orders.isEmpty()) {
            store.generateReceipt(orders.get(0));
        } else {
            System.out.println("Список замовлень порожній. Немає замовлень для створення чека.");
        }

        // Генерація чека
        store.generateReceipt(customer, orderProducts);

        // Фільтрування продуктів за ціною
        List<Product> filteredProducts = store.loadProductsFromFile("your_file_name.txt");
        System.out.println("Фільтровані продукти:");
        for (Product product : filteredProducts) {
            System.out.println(product);
        }

        // Визначення середньої ціни
        double averagePrice = store.getAveragePrice();
        System.out.println("Середня ціна: " + averagePrice);

        // Визначення витрат заданого користувача за заданий період часу
        List<Order> ordersByUser = store.getOrdersHistoryByUser("user1");
        System.out.println("Витрати користувача user1:");
        for (Order currentorder : ordersByUser) {
            System.out.println(order);
        }

        // Отримання даних про сумарну кількість кожного купленого продукту заданого користувача
        Map<String, Integer> productsQuantityByUser = store.getProductsQuantityByUser("user1");
        System.out.println("Дані про сумарні кількості продуктів, куплених користувачем user1:");
        for (String productName : productsQuantityByUser.keySet()) {
            System.out.println(productName + ": " + productsQuantityByUser.get(productName));
        }

        // Знаходження найпопулярнішого продукту
        Product mostPopularProduct = store.getMostPopularProduct();
        System.out.println("Найпопулярніший продукт: " + mostPopularProduct);

        // Знаходження найбільшого доходу за день
        int highestIncomeForDay = store.getHighestIncomeForDay();
        System.out.println("Найбільший дохід за день: " + highestIncomeForDay);
    }
}
