package Task;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileService {
    public void generateReceiptFile(final String baseFileName, final Receipt receipt) throws ReceiptGenerationException {
        final String fileName = baseFileName + "_" + receipt.getCustomer().getName() + "_" + receipt.getCustomer().getSurname() + ".txt";

        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writeLineToFile(writer, "Receipt: " + receipt.getDate());
            writeLineToFile(writer, "Customer: " + receipt.getCustomer().getName() + " " + receipt.getCustomer().getSurname());
            writeLineToFile(writer, "Products:");

            receipt.getProducts().stream()
                    .map(product -> product.getName() + ", quantity " + product.getQuantity())
                    .forEach(line -> {
                        try {
                            writeLineToFile(writer, line);
                        } catch (ReceiptGenerationException e) {
                            throw new RuntimeException(e);
                        }
                    });

            writeLineToFile(writer, "Total price: " + calculateTotalPrice(receipt.getProducts()));
            writeLineToFile(writer, "Paid: " + receipt.isPaid());
            writeLineToFile(writer, "------------------------------");
        } catch (final IOException e) {
            throw new ReceiptGenerationException();
        }
    }

    public List<Product> loadProductsFromFile(final String fileName) throws FileLoadException {
        final List<Product> loadedProducts = new ArrayList<>();

        try {
            final List<String> lines = Files.readAllLines(Paths.get(fileName));

            for (final String line : lines) {
                final String[] parts = line.split(",");

                if (parts.length == 3) {
                    final String name = parts[0].trim();
                    final double price = Double.parseDouble(parts[1].trim());
                    final int quantity = Integer.parseInt(parts[2].trim());

                    final Product product = new Product(name, price, quantity);
                    loadedProducts.add(product);
                } else {
                    throw new FileLoadException();
                }
            }
        } catch (final IOException | NumberFormatException e) {
            throw new FileLoadException();
        }

        return loadedProducts;
    }

    private void writeLineToFile(final BufferedWriter writer, final String line) throws ReceiptGenerationException {
        try {
            writer.write(line);
            writer.newLine();
        } catch (final IOException e) {
            throw new ReceiptGenerationException();
        }
    }

    private double calculateTotalPrice(final List<Product> products) {
        return products.stream()
                .mapToDouble(product -> product.getPrice() * product.getQuantity())
                .sum();
    }
}
