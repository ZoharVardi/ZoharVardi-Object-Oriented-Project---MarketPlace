package Roy_Lechtig_And_Zohar_Vardi.Users;
import Roy_Lechtig_And_Zohar_Vardi.Exceptions.*;
import Roy_Lechtig_And_Zohar_Vardi.Models.*;


import java.util.Arrays;
import java.util.Date;

public class Buyer extends User {
    private Address address;
    private Order cart;
    private Order[] orderHistory;
    private int orderHistoryLogSize;

    public Buyer(String username, String password, Address address) {
        super(username, password);
        this.address = address;
        this.cart = new Order();
        this.orderHistory = new Order[2];
        this.orderHistoryLogSize = 0;
    }

    public Buyer() {
        super();
        this.cart = new Order();
        this.orderHistory = new Order[2];
        this.orderHistoryLogSize = 0;
    }

    public Address getAddress() {
        return address;
    }

    public Boolean setAddress(Address address) {
        this.address = address;
        return true;
    }

    public Order getOrder() throws NoItemInCartException{
        if (cart.getProducts() == null) {
            throw new NoItemInCartException();
        }
        return cart;
    }

    public Order[] getOrderHistory() {
        if (orderHistory == null) {
            throw new UnsupportedOperationException("No order history");
        }
        return Arrays.copyOf(orderHistory, orderHistoryLogSize);
    }

    public int getOrderHistoryLogSize() {
        return orderHistoryLogSize;
    }

    public void addToCart(Product product) {
        if (cart.getCartLogSize() == 0) {
            cart.setDate(new Date());
        }
        cart.addProduct(product);
    }

    public String checkout() throws CloneNotSupportedException, NoItemInCartException {
        if (cart.getCartLogSize() == 0) {
            throw new NoItemInCartException();
        }
        if (orderHistoryLogSize == orderHistory.length) {
            orderHistory = Arrays.copyOf(orderHistory, orderHistory.length * 2);
        }
        orderHistory[orderHistoryLogSize++] = cart.clone();
        cart.setDate(new Date());
        String receipt = cart.toString();
        cart = new Order();
        return receipt + "\nOrder placed successfully.\n";
    }


    public void createNewCartFromHistory(Order order) throws CloneNotSupportedException,NoItemInCartException {
        if (order.getCartLogSize() == 0) {
            throw new NoItemInCartException();
        }
        this.cart = order.clone();
    }
}
