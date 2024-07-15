package Roy_Lechtig_And_Zohar_Vardi;
import Roy_Lechtig_And_Zohar_Vardi.Comperators.*;
import Roy_Lechtig_And_Zohar_Vardi.Users.*;


import java.util.Arrays;

class Manager {
    private Buyer[] buyers;
    private Seller[] sellers;
    private int buyersLogSize;
    private int sellersLogSize;


    public Manager() {
        this.buyers = new Buyer[2];
        this.sellers = new Seller[2];
        this.buyersLogSize = 0;
        this.sellersLogSize = 0;
    }

    public Buyer[] getBuyers() {
        if (buyersLogSize > 1) {
            Arrays.sort(buyers, new compareBuyersName());
        }
        return Arrays.copyOf(buyers, buyersLogSize);
    }

    public Seller[] getSellers() {
        if (sellersLogSize > 1) {
            Arrays.sort(sellers, new compareSellersByNumberOfProducts());
        }
        return Arrays.copyOf(sellers, sellersLogSize);
    }

    public int getBuyersLogSize() {
        return buyersLogSize;
    }

    public int getSellersLogSize() {
        return sellersLogSize;
    }

    public void addBuyer(Buyer buyer) {
        if (buyersLogSize == buyers.length) {
            buyers = Arrays.copyOf(buyers, buyers.length * 2);
        }
        buyers[buyersLogSize++] = buyer;
    }

    public void addSeller(Seller seller) {
        if (sellersLogSize == sellers.length) {
            sellers = Arrays.copyOf(sellers, sellers.length * 2);
        }
        sellers[sellersLogSize++] = seller;
    }
    public void addUser(User user) {
        if (user instanceof Buyer) {
            addBuyer((Buyer)user);
        } else {
            addSeller((Seller) user);
        }
    }

    public Buyer findBuyer(String username) {
        for (int i = 0; i < buyersLogSize; i++) {
            if (buyers[i].getUserName().equals(username)) {
                return buyers[i];
            }
        }
        return null;
    }

    public Seller findSeller(String username) {
        for (int i = 0; i < sellersLogSize; i++) {
            if (sellers[i].getUserName().equals(username)) {
                return sellers[i];
            }
        }
        return null;
    }

    public User findUser(User user) {
        if (user instanceof Buyer) {
            return findBuyer(user.getUserName());
        } else {
            return findSeller(user.getUserName());
        }
    }
}



