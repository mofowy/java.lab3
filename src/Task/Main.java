package Task;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, FileLoadException, ReceiptGenerationException {

        final Store store = new Store();

        store.loadProductsFromFile("text.txt");

        final List<Product> allProducts = store.getProducts();

        if (!allProducts.isEmpty()) {
            store.removeProduct(allProducts.get(0));
        } else {
            System.out.println("List is blank.");
        }

        store.removeProduct(allProducts.get(0));

        store.addProduct(new Product("Apple", 5.0, 15));

        store.saveOrdersHistory();

        final Customer customer = new Customer("John", "Doe");
        final List<Product> orderProducts = store.getProducts();
        store.makeOrder(customer, orderProducts);

        final List<Order> orders = new ArrayList<>();

        final Order order = new Order(LocalDate.now(), customer, orderProducts, false);
        orders.add(order);

        if (!orders.isEmpty()) {
            store.generateReceipt(orders.get(0));
        } else {
            System.out.println("Order list is empty.");
        }

        store.generateReceipt(customer, orderProducts);

        final List<Product> filteredProducts = store.loadProductsFromFile("products.txt");
        System.out.println("Filtered products:");
        for (final Product product : filteredProducts) {
            System.out.println(product);
        }

        final double averagePrice = store.getAveragePrice();
        System.out.println("Avg price: " + averagePrice);

        final List<Order> ordersByUser = store.getOrdersHistoryByUser("user1");
        System.out.println("User1's money spend:");
        for (final Order currentOrder : ordersByUser) {
            System.out.println(currentOrder);
        }

        final Map<String, Integer> productsQuantityByUser = store.getProductsQuantityByUser("user1");
        System.out.println("All products was bought by user1:");
        for (final String productName : productsQuantityByUser.keySet()) {
            System.out.println(productName + ": " + productsQuantityByUser.get(productName));
        }

        final Product mostPopularProduct = store.getMostPopularProduct();
        System.out.println("Most popular product: " + mostPopularProduct);

        final int highestIncomeForDay = store.getHighestIncomeForDay();
        System.out.println("Most income for day: " + highestIncomeForDay);
    }
}
