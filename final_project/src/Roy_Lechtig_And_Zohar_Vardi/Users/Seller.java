package Roy_Lechtig_And_Zohar_Vardi.Users;
import Roy_Lechtig_And_Zohar_Vardi.Models.Product;


import java.util.Arrays;

public class Seller extends User {
    private Product[] products;
    private int productLogsSize;


    public Seller(String username, String password) {
        super(username, password);
        this.products = new Product[2];
        this.productLogsSize = 0;
    }

    public Seller() {
        super();
        this.products = new Product[2];
        this.productLogsSize = 0;
    }


    public Product[] getProducts() {
        if (productLogsSize == 0) {
            return null;
        }
        return Arrays.copyOf(products, productLogsSize);
    }

    public int getProductLogsSize() {
        return productLogsSize;
    }

    public void addProduct(Product product) {
        if (productLogsSize == products.length) {
            products = Arrays.copyOf(products, products.length * 2);
        }
        products[productLogsSize++] = product;
    }
}
