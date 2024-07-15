package Roy_Lechtig_And_Zohar_Vardi;

import Roy_Lechtig_And_Zohar_Vardi.Models.*;
import Roy_Lechtig_And_Zohar_Vardi.Users.*;
import Roy_Lechtig_And_Zohar_Vardi.Exceptions.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    enum MenuOption {
        exit,
        addSeller,
        addBuyer,
        addProductToSeller,
        addProductToBuyer,
        payForBuyerOrder,
        printBuyersDetails,
        printSellersDetails,
        displayProductsByCategory,
        createNewCartFromHistory
    }

    static Scanner scanner = new Scanner(System.in);
    static Manager systemManager = new Manager();

    //method to handle integer input with retry
    static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int input = scanner.nextInt();
                if (input < 0) {
                    throw new IllegalArgumentException("Input cannot be negative. Please try again.");
                }
                return input;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, enter a valid integer. Please try again.");
                scanner.nextLine(); // Clear buffer
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    //method to handle double input with retry
    static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double input = scanner.nextDouble();
                scanner.nextLine(); // Clear buffer
                if (input < 0) {
                    throw new IllegalArgumentException("Input cannot be negative.");
                }
                return input;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number. Please try again.");
                scanner.nextLine(); // Clear buffer
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    static Seller[] accessAllSellers() {
        try {
            Seller[] allSellers = systemManager.getSellers();
            if (allSellers.length == 0) {
                throw new IllegalArgumentException("There are no sellers in the system.");
            }
            return allSellers;
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return null;
        }
    }

    static Buyer[] accessAllBuyers() {
        try {
            Buyer[] allBuyers = systemManager.getBuyers();
            if (allBuyers.length == 0) {
                throw new IllegalArgumentException("There are no buyers in the system.");
            }
            return allBuyers;
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return null;
        }
    }

    static Seller pickSellerFromList() {
        try {
            Seller[] allSellers = accessAllSellers();
            if (allSellers == null || allSellers.length == 0) {
                return null;
            }
            return (Seller) pickUserFromList(allSellers);
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return null;
        }
    }

    static Buyer pickBuyerFromList() {
        try {
            Buyer[] allBuyers = accessAllBuyers();
            if (allBuyers == null || allBuyers.length == 0) {
                return null;
            }
            return (Buyer) pickUserFromList(allBuyers);
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return null;
        }
    }

    static User pickUserFromList(User[] allUserType) {
        try {
            if (allUserType == null || allUserType.length == 0) {
                return null;
            }
            String userType = allUserType[0].getClass().getSimpleName();
            System.out.println("Available " + userType + "s:");
            for (int i = 0; i < allUserType.length; i++) {
                System.out.println((i + 1) + ". " + allUserType[i].getUserName());
            }
            int index;
            while (true) {

                index = getIntInput("Select a " + userType + " by number: ") - 1;
                if (index >= 0 && index < allUserType.length) {
                    break;
                } else {
                    System.out.println("Invalid " + userType + " selection. Please select again.");
                }
            }
            System.out.println("\n");
            return allUserType[index];
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return null;
        }
    }

    static Product pickProductFromList(Seller seller) {
        try {
            if (seller == null) {
                return null;
            }
            Product[] products = seller.getProducts();
            if (products.length == 0) {
                System.out.println("No products available.");
                return null;
            }
            System.out.println("Available products:");
            for (int i = 0; i < products.length; i++) {
                System.out.println((i + 1) + ". " + products[i].getName());
            }
            int productIndex;
            while (true) {
                productIndex = getIntInput("Select a product by number: ") - 1;
                if (productIndex >= 0 && productIndex < products.length) {
                    break;
                } else {
                    System.out.println("Invalid product selection. Please select again.");
                }
            }
            System.out.println("\n");
            return products[productIndex];
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return null;
        }
    }

    static void addSeller() {
        try {
            systemManager.addSeller((Seller) createUSer(new Seller()));
            System.out.println("The seller user is added.");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    static void addBuyer() {
        try {
            systemManager.addBuyer((Buyer)createUSer(new Buyer()));
            System.out.println("The buyer user is added.");

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
    static User createUSer(User user){
        try{
        if (askUsername(user) && askUserPassword(user)) {
            if (user instanceof Buyer) {
                ((Buyer) user).setAddress(askAddress());
            }
            return user;
        }
        else return null;
        } catch (Exception e) {
            System.out.println("An error occurred, can't add a "+ user.getClass().getSimpleName()+ " because:"+ e.getMessage());
            return null;
        }
    }

    static boolean askUsername(User user) {
        try {
            String userName;
            User existingUser;

            do {
                System.out.print("Enter a new " + user.getClass().getSimpleName() + " username: ");
                userName = scanner.next();
                scanner.nextLine();
                user.setUserName(userName);
                existingUser = systemManager.findUser(user);

                if (existingUser != null) {
                    System.out.println(user.getClass().getSimpleName() + " username already exists. Enter a different name: ");
                }

            } while (existingUser != null);

            user.setUserName(userName);
            return true;
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return false;
        }
    }

    static boolean askUserPassword(User user) {
        try {
        System.out.print("Enter a new " + user.getClass().getSimpleName() + " password: ");
        String Password = scanner.nextLine();
        user.setPassword(Password);
        return true;
    } catch (Exception e) {
        System.out.println("An error occurred: " + e.getMessage());
        return false;
    }
    }

    static  Address askAddress(){
        System.out.print("Enter the buyer country: ");
        String buyerCountry = scanner.nextLine();
        System.out.print("Enter the buyer city: ");
        String buyerCity = scanner.nextLine();
        System.out.print("Enter the buyer street: ");
        String buyerStreet = scanner.nextLine();
        int buyerBuildingNumber = getIntInput("Enter the buyer building number: ");
        return new Address(buyerCountry, buyerCity, buyerStreet, buyerBuildingNumber);
    }


    static void addProductToSeller() {
        try {
            Seller seller = pickSellerFromList();
            if (seller != null) {
                System.out.println("Enter the name of the product you wish to add: ");
                String productName = scanner.next();
                scanner.nextLine();
                double price = getDoubleInput("Enter the product price: ");
                System.out.println("Select the category of the product:");
                int index = 1;
                for (Category category : Category.values()) {
                    System.out.println(index + ". " + category.name());
                    index++;
                }
                int categoryChoice = getIntInput("Pick category by number: ");
                while (categoryChoice == 0 || categoryChoice > Category.values().length) {
                    categoryChoice = getIntInput("Invalid category selection. Please select again.");
                }
                Category category = Category.values()[categoryChoice - 1];
                boolean specialPackaging = getSpecialPackagingInput();
                double packagingPrice = 0.0;
                if (specialPackaging) {
                    packagingPrice = getDoubleInput("Enter the price for special packaging: ");
                }
                seller.addProduct(new Product(productName, price, category, specialPackaging, packagingPrice));
                System.out.println("Product added successfully.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while adding the product: " + e.getMessage());
        }
    }

    static boolean getSpecialPackagingInput() {
        while (true) {
            try {
                System.out.print("Does this product require special packaging?\n1. Yes\n2. No\nPick by number: ");
                int specialPackagingChoice = scanner.nextInt();
                if (specialPackagingChoice == 1) {
                    return true;
                } else if (specialPackagingChoice == 2) {
                    return false;
                } else {
                    System.out.println("Invalid choice. Please select again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear buffer
            }
        }
    }

    static void addProductToBuyer() {
        try {
            Buyer buyerForProduct = pickBuyerFromList();
            if (buyerForProduct == null) {
                return;
            }

            Seller seller;
            while (true) {
                seller = pickSellerFromList();
                if (seller != null) {
                    if (seller.getProducts() == null || seller.getProducts().length == 0) {
                        System.out.println("No products found for this seller. Please select a different seller.");
                    } else {
                        break;
                    }
                }
            }

            Product selectedProduct = pickProductFromList(seller);
            if (selectedProduct == null) {
                return;
            }
            buyerForProduct.addToCart(selectedProduct);
            System.out.println("Product added to cart successfully.");


        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    static void payForBuyerOrder() {
        try {
            Buyer payingBuyer = pickBuyerFromList();
            if (payingBuyer == null) {
                System.out.println("No buyer available.");
                return;
            }
            System.out.println("\n" + payingBuyer.getUserName() + "'s cart:\n" + payingBuyer.checkout());
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    static void printBuyersDetails() {
        try {
            Buyer[] allBuyers = accessAllBuyers();
            if (allBuyers != null) {
                for (Buyer buyer : allBuyers) {
                    try {
                        System.out.println("______________________________________");
                        System.out.println("Username: " + buyer.getUserName());
                        System.out.println("Address: " + buyer.getAddress());

                        System.out.println("Current Cart:");
                        Order currentCart;
                        try {
                            currentCart = buyer.getOrder();
                            if (currentCart.getCartLogSize() == 0) {
                                System.out.println("Cart is empty.");
                            } else {
                                System.out.println(currentCart.productsList());
                            }
                        } catch (NoItemInCartException e) {
                            System.out.println("Cart is empty.");
                        }

                        System.out.println("\nOrder History:");
                        try {
                            Order[] orders = buyer.getOrderHistory();
                            if (orders == null || orders.length == 0) {
                                System.out.println("No history.");
                            } else {
                                for (int j = 0; j < orders.length; j++) {
                                    System.out.println("Order " + (j + 1) + ":\n" + orders[j]);
                                }
                            }
                        } catch (UnsupportedOperationException e) {
                            System.out.println("No order history available.");
                        }

                    } catch (Exception e) {
                        System.out.println("Error printing details for buyer " + buyer.getUserName() + ": " + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while accessing buyers: " + e.getMessage());
        }
    }


    static void printSellersDetails() {
        try {
            Seller[] allSellers = accessAllSellers();
            if (allSellers == null || allSellers.length == 0) {
                System.out.println("No sellers found in the system.");
                return;
            }
            for (Seller seller : allSellers) {
                System.out.println("______________________________________");
                System.out.println("Username: " + seller.getUserName());
                System.out.println("Number of Products: " + seller.getProductLogsSize());

                Product[] sellerProducts = seller.getProducts();
                if (sellerProducts == null || sellerProducts.length == 0) {
                    System.out.println("No products listed for this seller.");
                } else {
                    System.out.println("Products:");
                    for (Product product : sellerProducts) {
                        System.out.println(product);
                    }
                }
                System.out.println();
            }

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }


    static void displayProductsByCategory() {
        try {
            System.out.println("Select a category to display products:");
            int index = 1;
            for (Category category : Category.values()) {
                System.out.println(index + ". " + category.name());
                index++;
            }
            int categoryChoice;
            while (true) {
                categoryChoice = getIntInput("Pick category by number: ");
                if (categoryChoice >= 1 && categoryChoice <= Category.values().length) {
                    break;
                } else {
                    System.out.println("Invalid category selection. Please select again.");
                }
            }
            Category selectedCategory = Category.values()[categoryChoice - 1];
            System.out.println("\n");
            Seller[] allSellers = accessAllSellers();
            boolean foundProducts = false;
            if (allSellers != null) {
                for (Seller seller : allSellers) {
                    Product[] sellerProducts = seller.getProducts();
                    for (Product product : sellerProducts) {
                        if (product.getCategory() == selectedCategory) {
                            System.out.println(product + "\n____________________________________");
                            foundProducts = true;
                        }
                    }
                }
            }

            if (!foundProducts) {
                System.out.println("No products found in the selected category.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void createNewCartFromHistory() {
        try {
            Buyer[] allBuyers = accessAllBuyers();
            if (allBuyers == null || allBuyers.length == 0) {
                System.out.println("No buyers found in the system.");
                return;
            }

            Buyer selectedBuyer = pickBuyerFromList();
            if (selectedBuyer == null) {
                System.out.println("No buyer selected or available.");
                return;
            }

            Order[] orderHistory = selectedBuyer.getOrderHistory();
            if (orderHistory.length == 0) {
                System.out.println("No order history found for this buyer.");
                return;
            }

            System.out.println("Select an order to create a new cart from:");
            for (int i = 0; i < orderHistory.length; i++) {
                System.out.println((i + 1) + ". Order Date: " + orderHistory[i].getDate());
            }

            int orderIndex;
            while (true) {
                orderIndex = getIntInput("Select an order by number: ") - 1;
                if (orderIndex >= 0 && orderIndex < orderHistory.length) {
                    break;
                } else {
                    System.out.println("Invalid order selection. Please select again.");
                }
            }

            Order selectedOrder = orderHistory[orderIndex];
            Order clonedOrder = selectedOrder.clone();
            selectedBuyer.createNewCartFromHistory(clonedOrder);

            System.out.println("New cart created successfully from selected order:");
            System.out.println(clonedOrder);

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid index access: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }


    static MenuOption selectOption() {
        System.out.println("\n--------------------------------------------------");
        System.out.println("Menu:");
        System.out.println("1. Add seller");
        System.out.println("2. Add buyer");
        System.out.println("3. Add product to seller");
        System.out.println("4. Add product to buyer's cart");
        System.out.println("5. Pay for buyer's order");
        System.out.println("6. Print buyers' details");
        System.out.println("7. Print sellers' details");
        System.out.println("8. Display products by category");
        System.out.println("9. Create a new cart from order history");
        System.out.println("0. Exit");

        int choice = getIntInput("Select an option: ");
        scanner.nextLine();
        while (choice > MenuOption.values().length - 1) {
            System.out.println("Invalid choice of number. Please try again.");
            choice = getIntInput("Enter your choice: ");
            scanner.nextLine();
        }
        System.out.println("\n");
        return MenuOption.values()[choice];
    }


    public static void main(String[] args) {
        try {
            boolean running = true;
            while (running) {
                switch (selectOption()) {
                    case addSeller:
                        addSeller();
                        break;
                    case addBuyer:
                        addBuyer();
                        break;
                    case addProductToSeller:
                        addProductToSeller();
                        break;
                    case addProductToBuyer:
                        addProductToBuyer();
                        break;
                    case payForBuyerOrder:
                        payForBuyerOrder();
                        break;
                    case printBuyersDetails:
                        printBuyersDetails();
                        break;
                    case printSellersDetails:
                        printSellersDetails();
                        break;
                    case displayProductsByCategory:
                        displayProductsByCategory();
                        break;
                    case createNewCartFromHistory:
                        createNewCartFromHistory();
                        break;
                    case exit:
                        running = false;
                        System.out.println("Thank you for using our program.!");
                        break;
                    default:
                        break;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid menu option. Please select a valid option from the menu.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
