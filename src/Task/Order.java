package Task;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
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
            e.printStackTrace(); // Обробка або логування помилок
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
}
