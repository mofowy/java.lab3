package Task;

import java.io.FileNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, FileLoadException, ReceiptGenerationException {

        Store store = new Store();

        store.loadProductsFromFile("text.txt");

        List<Product> allProducts = store.getProducts();

        if (!allProducts.isEmpty()) {
            store.removeProduct(allProducts.get(0));
        } else {
            System.out.println("List is blank.");
        }

        store.removeProduct(allProducts.get(0));

        store.addProduct(new Product("Apple", 5.0, 15));

        store.saveOrdersHistory();

        Customer customer = new Customer("John", "Doe");
        List<Product> orderProducts = store.getProducts();
        store.makeOrder(customer, orderProducts);

        List<Order> orders = new ArrayList<>();

        Order order = new Order(LocalDate.now(), customer, orderProducts, false);
        orders.add(order);

        if (!orders.isEmpty()) {
            store.generateReceipt(orders.get(0));
        } else {
            System.out.println("Order list is empty.");
        }

        store.generateReceipt(customer, orderProducts);

        List<Product> filteredProducts = store.loadProductsFromFile("products.txt");
        System.out.println("Filtered products:");
        for (Product product : filteredProducts) {
            System.out.println(product);
        }

        double averagePrice = store.getAveragePrice();
        System.out.println("Avg price: " + averagePrice);

        List<Order> ordersByUser = store.getOrdersHistoryByUser("user1");
        System.out.println("User1's money spend:");
        for (Order currentorder : ordersByUser) {
            System.out.println(order);
        }

        Map<String, Integer> productsQuantityByUser = store.getProductsQuantityByUser("user1");
        System.out.println("All products was buyed by user1:");
        for (String productName : productsQuantityByUser.keySet()) {
            System.out.println(productName + ": " + productsQuantityByUser.get(productName));
        }

        Product mostPopularProduct = store.getMostPopularProduct();
        System.out.println("Most popular product: " + mostPopularProduct);

        int highestIncomeForDay = store.getHighestIncomeForDay();
        System.out.println("Most income for day: " + highestIncomeForDay);
    }
}
