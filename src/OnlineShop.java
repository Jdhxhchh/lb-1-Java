// OnlineShop.java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OnlineShop {
    private static List<Product> products = new ArrayList<>();
    private static Cart cart = new Cart();
    private static List<Order> orderHistory = new ArrayList<>();

    public static void main(String[] args) {
        initializeProducts();

        Scanner scanner = new Scanner(System.in);

        boolean exit = false;
        while (!exit) {
            System.out.println("1. View Products");
            System.out.println("2. Add Product to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Remove Product from Cart");
            System.out.println("5. Place Order");
            System.out.println("6. Search Products");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewProducts();
                    break;
                case 2:
                    addProductToCart(scanner);
                    break;
                case 3:
                    viewCart();
                    break;
                case 4:
                    removeProductFromCart(scanner);
                    break;
                case 5:
                    placeOrder();
                    break;
                case 6:
                    searchProducts(scanner);
                    break;
                case 7:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void initializeProducts() {
        Category electronics = new Category("Electronics");
        Category clothing = new Category("Clothing");

        products.add(new Product(1, "Laptop", 999.99, "High-performance laptop", electronics));
        products.add(new Product(2, "Smartphone", 699.99, "Latest smartphone model", electronics));
        products.add(new Product(3, "T-shirt", 19.99, "Comfortable cotton t-shirt", clothing));
        products.add(new Product(4, "Jeans", 39.99, "Slim-fit jeans", clothing));
    }

    private static void viewProducts() {
        System.out.println("Available Products:");
        for (Product product : products) {
            System.out.println("ID: " + product.getId() + ", Name: " + product.getName() + ", Price: " + product.getPrice() +
                    ", Category: " + product.getCategory().getName());
        }
    }

    private static void addProductToCart(Scanner scanner) {
        System.out.print("Enter product ID to add to cart: ");
        int productId = scanner.nextInt();
        Product product = findProductById(productId);
        if (product != null) {
            cart.addProduct(product);
            System.out.println("Product added to cart!");
        } else {
            System.out.println("Product not found!");
        }
    }

    private static void viewCart() {
        List<Product> cartProducts = cart.getProducts();
        if (cartProducts.isEmpty()) {
            System.out.println("Cart is empty");
        } else {
            System.out.println("Cart Contents:");
            for (Product product : cartProducts) {
                System.out.println("Name: " + product.getName() + ", Price: " + product.getPrice());
            }
        }
    }

    private static void removeProductFromCart(Scanner scanner) {
        System.out.print("Enter product ID to remove from cart: ");
        int productId = scanner.nextInt();
        Product product = findProductById(productId);
        if (product != null) {
            cart.removeProduct(product);
            System.out.println("Product removed from cart!");
        } else {
            System.out.println("Product not found in cart!");
        }
    }

    private static Product findProductById(int productId) {
        for (Product product : products) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    private static void placeOrder() {
        if (cart.getProducts().isEmpty()) {
            System.out.println("Cart is empty. Cannot place order.");
            return;
        }
        Order order = new Order(cart);
        orderHistory.add(order);
        order.displayOrderDetails();
        cart = new Cart(); // Clear the cart after placing order
    }

    private static void searchProducts(Scanner scanner) {
        System.out.print("Enter search query: ");
        String query = scanner.nextLine().toLowerCase();
        List<Product> searchResults = new ArrayList<>();
        for (Product product : products) {
            if (product.getName().toLowerCase().contains(query) || product.getCategory().getName().toLowerCase().contains(query)) {
                searchResults.add(product);
            }
        }
        if (searchResults.isEmpty()) {
            System.out.println("No matching products found.");
        } else {
            System.out.println("Search Results:");
            for (Product product : searchResults) {
                System.out.println("ID: " + product.getId() + ", Name: " + product.getName() + ", Price: " + product.getPrice() +
                        ", Category: " + product.getCategory().getName());
            }
        }
    }
}

class Product {
    private int id;
    private String name;
    private double price;
    private String description;
    private Category category;

    public Product(int id, String name, double price, String description, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }
}

class Category {
    private String name;

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Cart {
    private List<Product> products;

    public Cart() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public List<Product> getProducts() {
        return products;
    }
}

class Order {
    private Cart cart;

    public Order(Cart cart) {
        this.cart = cart;
    }

    public double calculateTotal() {
        double total = 0;
        for (Product product : cart.getProducts()) {
            total += product.getPrice();
        }
        return total;
    }

    public void displayOrderDetails() {
        System.out.println("Order Details:");
        for (Product product : cart.getProducts()) {
            System.out.println("Name: " + product.getName() + ", Price: " + product.getPrice());
        }
        System.out.println("Total: " + calculateTotal());
    }
}

