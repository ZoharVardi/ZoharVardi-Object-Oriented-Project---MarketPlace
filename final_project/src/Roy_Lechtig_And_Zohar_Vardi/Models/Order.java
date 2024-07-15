package Roy_Lechtig_And_Zohar_Vardi.Models;

import Roy_Lechtig_And_Zohar_Vardi.Exceptions.NoItemInCartException;

import java.util.Arrays;
import java.util.Date;

public class Order implements Cloneable{
    private Product[] products;
    private Date date;
    private int cartLogSize;

    public Order() {
        this.products = new Product[2];
        this.date = null;
        this.cartLogSize = 0;
    }

    public Product[] getProducts() {
        return Arrays.copyOf(products, cartLogSize);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCartLogSize() {
        return cartLogSize;
    }

    public String productsList() throws NoItemInCartException {
        String productsString = "";
        if (getCartLogSize() == 0) {
            throw new NoItemInCartException();
        }
        for (int i = 0; i < cartLogSize; i++) {
            productsString += products[i].toString() + "\n";
        }
        return productsString;
    }

    public void addProduct(Product product) {
        if (cartLogSize == products.length) {
            products = Arrays.copyOf(products, products.length * 2);
        }
        products[cartLogSize++] = product;
    }

    private double getTotalPrice() {
        double totalPrice = 0.0;
        for (int i = 0; i < cartLogSize; i++) {
            totalPrice += products[i].getTotalPrice();
        }
        return totalPrice;
    }

    @Override
    public Order clone() throws CloneNotSupportedException {
        Order clonedOrder = (Order) super.clone();
        clonedOrder.products = this.products.clone();
        for (int i = 0; i < this.products.length; i++) {
            if (this.products[i] != null) {
                clonedOrder.products[i] = (Product) this.products[i].clone();
            }
        }
        clonedOrder.date = new Date();
        return clonedOrder;
    }

    @Override
    public String toString() {
        try {
            return "Products:\n" + productsList() + "\nTotal Price: " + getTotalPrice() + "\nDate: " + date + "\n";
        } catch (NoItemInCartException e) {
            throw new RuntimeException(e);
        }
    }
}
