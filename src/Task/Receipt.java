package Task;

import java.time.LocalDate;
import java.util.List;

public class Receipt {

    private LocalDate date;
    private Customer customer;
    private List<Product> products;
    private boolean paid;

    public Receipt(final LocalDate date, final Customer customer, final List<Product> products, final boolean paid) {
        this.date = date;
        this.customer = customer;
        this.products = products;
        this.paid = paid;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(final Customer customer) {
        this.customer = customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(final List<Product> products) {
        this.products = products;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(final boolean paid) {
        this.paid = paid;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "date=" + date +
                ", customer=" + customer +
                ", products=" + products +
                ", paid=" + paid +
                '}';
    }
}
