package Task;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private LocalDate date;
    private Customer customer;
    private List<Product> products;
    private boolean paid;
    public Order(LocalDate now, Customer customer, List<Product> products, boolean b) {
        this.date = date;
        this.customer = customer;
        this.products = products;
        this.paid = paid;
    }

    public static void makeOrder(Store store, Customer customer, List<Product> products) throws ReceiptGenerationException {
        store.addOrder(new Order(LocalDate.now(), customer, products, false));
    }

    public static void saveOrdersHistory(Store store) {
        List<Order> orders = store.getOrders();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("orders_history.txt"))) {
            for (Order order : orders) {
                writeLineToFile(writer, "Order Date: " + order.getDate());
                writeLineToFile(writer, "Customer: " + order.getCustomer().getName() + " " + order.getCustomer().getSurname());
                writeLineToFile(writer, "Products:");
                for (Product product : order.getProducts()) {
                    writeLineToFile(writer, product.getName() + ", quantity " + product.getQuantity());
                }
                writeLineToFile(writer, "Total price: " + calculateTotalPrice(order.getProducts()));
                writeLineToFile(writer, "Paid: " + order.isPaid());
                writeLineToFile(writer, "------------------------------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeLineToFile(final BufferedWriter writer, final String line) throws IOException {
        writer.write(line);
        writer.newLine();
    }

    public LocalDate getDate() {
        return date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public boolean isPaid() {
        return paid;
    }

    private static double calculateTotalPrice(final List<Product> products) {
        return products.stream()
                .mapToDouble(product -> product.getPrice() * product.getQuantity())
                .sum();
    }
    private List<Product> products;
    private boolean paid;

    public Order() {
        this.products = new ArrayList<>();
        this.paid = false;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public void addPackagesForFruitsAndVegetables() {
        List<String> packages = new ArrayList<>();

        for (Product product : products) {
            String productName = product.getName().toLowerCase();

            if (isFruit(productName) || isVegetable(productName)) {
                packages.add("Package for " + productName);
            }
        }

        products.addAll(createPackageProducts(packages));
    }

    public String generateCommentForMeatAndFish() {
        List<String> meatAndFishProducts = new ArrayList<>();

        for (Product product : products) {
            String productName = product.getName().toLowerCase();

            if (isMeat(productName) || isFish(productName)) {
                meatAndFishProducts.add(productName);
            }
        }

        if (!meatAndFishProducts.isEmpty()) {
            return "Don't forget to store products " + String.join(", ", meatAndFishProducts) +
                    " in the refrigerator.";
        } else {
            return null;
        }
    }

    private boolean isVegetable(String productName) {
        return false;
    }

    private boolean isFruit(String productName) {
        return false;
    }

    private boolean isMeat(String productName) {
        return false;
    }

    private boolean isFish(String productName) {
        return false;
    }

    private List<Product> createPackageProducts(List<String> packages) {
        List<Product> packageProducts = new ArrayList<>();

        for (String packageName : packages) {
            packageProducts.add(new Product(packageName, 0.0, 1));
        }

        return packageProducts;
    }
}
