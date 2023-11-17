package Task;

public final class Customer {

    private String name;
    private String surname;

    public Customer(final String name, final String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "Customer{" + "name='" + name + '\'' + ", surname='" + surname + '\'' + '}';
    }
}
