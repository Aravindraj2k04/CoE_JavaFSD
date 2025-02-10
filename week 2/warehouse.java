import java.io.*;
import java.util.*;
import java.util.concurrent.*;

// Location class representing warehouse locations
class Location {
    private int aisle, shelf, bin;

    public Location(int aisle, int shelf, int bin) {
        this.aisle = aisle;
        this.shelf = shelf;
        this.bin = bin;
    }

    @Override
    public String toString() {
        return "Aisle " + aisle + ", Shelf " + shelf + ", Bin " + bin;
    }
}

// Product class representing a product in the inventory
class Product {
    private String productID, name;
    private int quantity;
    private Location location;

    public Product(String productID, String name, int quantity, Location location) {
        this.productID = productID;
        this.name = name;
        this.quantity = quantity;
        this.location = location;
    }

    public synchronized boolean reduceStock(int amount) {
        if (quantity >= amount) {
            quantity -= amount;
            return true;
        }
        return false;
    }

    public String getProductID() { return productID; }
    public int getQuantity() { return quantity; }
    public Location getLocation() { return location; }
    @Override
    public String toString() { return name + " (" + productID + ") - Quantity: " + quantity; }
}

// Order class representing an order
class Order implements Comparable<Order> {
    private String orderID;
    private List<String> productIDs;
    private Priority priority;

    public enum Priority {
        STANDARD, EXPEDITED
    }

    public Order(String orderID, List<String> productIDs, Priority priority) {
        this.orderID = orderID;
        this.productIDs = productIDs;
        this.priority = priority;
    }

    public List<String> getProductIDs() { return productIDs; }
    public Priority getPriority() { return priority; }

    @Override
    public int compareTo(Order o) {
        return this.priority.compareTo(o.priority);
    }
}

// Custom Exceptions
class OutOfStockException extends Exception {
    public OutOfStockException(String message) { super(message); }
}

class InvalidLocationException extends Exception {
    public InvalidLocationException(String message) { super(message); }
}

// Inventory Manager class
class InventoryManager {
    private Map<String, Product> inventory = new ConcurrentHashMap<>();
    private PriorityBlockingQueue<Order> orderQueue = new PriorityBlockingQueue<>();

    public void addProduct(Product product) {
        inventory.put(product.getProductID(), product);
    }

    public void processOrders() {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        while (!orderQueue.isEmpty()) {
            executor.execute(() -> {
                try {
                    Order order = orderQueue.poll();
                    if (order != null) {
                        fulfillOrder(order);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
    }

    private void fulfillOrder(Order order) throws OutOfStockException {
        for (String productID : order.getProductIDs()) {
            Product product = inventory.get(productID);
            if (product != null && product.reduceStock(1)) {
                System.out.println("Order fulfilled for product: " + product);
            } else {
                throw new OutOfStockException("Product " + productID + " is out of stock.");
            }
        }
    }

    public void addOrder(Order order) {
        orderQueue.offer(order);
    }
}

// Main class
public class WarehouseManagement {
    public static void main(String[] args) {
        InventoryManager inventoryManager = new InventoryManager();

        // Initializing inventory
        inventoryManager.addProduct(new Product("P001", "Laptop", 10, new Location(1, 2, 3)));
        inventoryManager.addProduct(new Product("P002", "Phone", 5, new Location(2, 3, 1)));

        // Creating and adding orders
        Order order1 = new Order("O001", Arrays.asList("P001", "P002"), Order.Priority.EXPEDITED);
        Order order2 = new Order("O002", Arrays.asList("P002"), Order.Priority.STANDARD);

        inventoryManager.addOrder(order1);
        inventoryManager.addOrder(order2);

        // Processing orders
        inventoryManager.processOrders();
    }
}
