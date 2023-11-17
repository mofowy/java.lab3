package Task;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.time.LocalDate;
import java.util.stream.Collectors;


public class Store {

    private List<Product> products;
    private List<Order> orders;
    private FileService fileService;

    public Store() {
        this.products = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.fileService = new FileService();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(final List<Product> products) {
        this.products = products;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(final List<Order> orders) {
        this.orders = orders;
    }

    public void addProduct(final Product product) {
        this.products.add(product);
    }

    public void removeProduct(final Product product) {
        this.products.remove(product);
    }

    public void addOrder(final Order order) {
        orders.add(order);
    }

    public void generateReceipt(final Customer customer, final List<Product> products) throws ReceiptGenerationException {
        final Receipt receipt = new Receipt(LocalDate.now(), customer, products, false);
        this.fileService.generateReceiptFile("receipt", receipt);
    }

    public List<Product> loadProductsFromFile(final String fileName) throws FileLoadException {
        return this.fileService.loadProductsFromFile(fileName);
    }

    public void makeOrder(Customer customer, List<Product> products) throws ReceiptGenerationException {
        Order.makeOrder(this, customer, products);
    }

    public void saveOrdersHistory() {
        Order.saveOrdersHistory(this);
    }

    public double getAveragePrice() {
        return getAveragePrice();
    }
    public List<Order> getOrdersHistoryByUser(String userName) {
        return orders.stream()
                .filter(order -> order.getCustomer().getName().equals(userName))
                .collect(Collectors.toList());
    }

    // Метод для отримання даних про сумарну кількість кожного купленого продукту користувача
    public Map<String, Integer> getProductsQuantityByUser(String userName) {
        Map<String, Integer> productsQuantityByUser = new HashMap<>();

        orders.stream()
                .filter(order -> order.getCustomer().getName().equals(userName))
                .flatMap(order -> order.getProducts().stream())
                .forEach(product -> productsQuantityByUser.merge(product.getName(), product.getQuantity(), Integer::sum));

        return productsQuantityByUser;
    }

    // Метод для отримання найпопулярнішого продукту
    public Product getMostPopularProduct() {
        Map<String, Integer> productQuantityMap = new HashMap<>();

        orders.stream()
                .flatMap(order -> order.getProducts().stream())
                .forEach(product -> productQuantityMap.merge(product.getName(), product.getQuantity(), Integer::sum));

        return productQuantityMap.entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .map(entry -> products.stream().filter(product -> product.getName().equals(entry.getKey())).findFirst().orElse(null))
                .orElse(null);
    }

    // Метод для отримання найбільшого доходу за день
    public int getHighestIncomeForDay() {
        Map<LocalDate, Double> incomeByDay = orders.stream()
                .collect(Collectors.groupingBy(Order::getDate, Collectors.summingDouble(order -> calculateTotalPrice(order.getProducts()))));

        return incomeByDay.entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getValue)
                .orElse(0.0)
                .intValue();
    }

    private static void writeLineToFile(BufferedWriter writer, String line) throws IOException {
        writer.write(line);
        writer.newLine();
    }
    public void generateReceipt(Order order) throws ReceiptGenerationException {
        final Receipt receipt = new Receipt(LocalDate.now(), order.getCustomer(), order.getProducts(), false);

        // Створення ім'я файлу на основі імені та прізвища клієнта
        final String fileName = "receipt_" + order.getCustomer().getName() + "_" + order.getCustomer().getSurname() + ".txt";

        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writeLineToFile(writer, "Receipt: " + receipt.getDate());
            writeLineToFile(writer, "Customer: " + receipt.getCustomer().getName() + " " + receipt.getCustomer().getSurname());
            writeLineToFile(writer, "Products:");

            receipt.getProducts().forEach(product -> {
                String line = product.getName() + ", quantity " + product.getQuantity();
                try {
                    writeLineToFile(writer, line);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            writeLineToFile(writer, "Total price: " + calculateTotalPrice(receipt.getProducts()));
            writeLineToFile(writer, "Paid: " + receipt.isPaid());
            writeLineToFile(writer, "------------------------------");

        } catch (IOException e) {
            throw new ReceiptGenerationException("Error generating receipt.");
        }
    }
    // Допоміжний метод для розрахунку загальної ціни продуктів у замовленні
    private double calculateTotalPrice(List<Product> products) {
        return products.stream().mapToDouble(product -> product.getPrice() * product.getQuantity()).sum();
    }
}
