package Roy_Lechtig_And_Zohar_Vardi.Models;

public class Product implements Cloneable {
    private static int nextSerialNumber = 1;
    private int serialNumber;
    private String name;
    private double price;
    private Category category;
    private boolean specialPackaging;
    private double packagingPrice;

    public Product(String name, double price, Category category, boolean specialPackaging, double packagingPrice)  {

        this.serialNumber = nextSerialNumber++;
        this.name = name;
        this.price = price;
        this.category = category;
        this.specialPackaging = specialPackaging;
        this.packagingPrice = packagingPrice;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isSpecialPackaging() {
        return specialPackaging;
    }

    public double getPackagingPrice() {
        return packagingPrice;
    }

    public double getTotalPrice() {
        if (specialPackaging) {
            return price + packagingPrice;
        } else {
            return price;
        }
    }
    public Product clone() throws CloneNotSupportedException {
        return (Product) super.clone();
    }

    @Override
    public String toString() {
        String result = "Serial Number: " + serialNumber + "\n";
        result += "Product Name: " + name + "\n";
        result += "Price: " + price + "\n";
        result += "Category: " + category + "\n";
        if (specialPackaging) {
            result += "Has special packaging, packaging price: " + packagingPrice + "\n";
        }
        result += "Product total Price: " + getTotalPrice() + "\n";
        return result;
    }
}