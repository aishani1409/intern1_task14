package project14;
import java.io.*;
import java.util.*;

class Product implements Serializable 
{
    private int id;
    private String name;
    private int quantity;
    private double price;

    public Product(int id, String name, int quantity, double price) 
    {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() { 
        return id; 
    }
    public String getName() 
    { return name; 

    }
    public int getQuantity() 
    { return quantity; 

    }
    public double getPrice() 
    { return price; 

    }
    public void setName(String name) 
    { this.name = name; 

    }
    public void setQuantity(int quantity) 
    { this.quantity = quantity; 

    }
    public void setPrice(double price) 
    { this.price = price; 

    }
    @Override
    public String toString() 
    {
        return "ID: " + id +
                ", Name: " + name +
                ", Quantity: " + quantity +
                ", Price: " + price;
    }
}

public class task14 
{
    private static HashMap<Integer, Product> inventory = new HashMap<>();
    private static Scanner sc = new Scanner(System.in);
    private static final String FILE_NAME = "inventory.dat";
    public static void main(String[] args) 
    {
        loadFromFile();
        while (true) {
            System.out.println("\n--- Inventory Menu ---");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("4. View Products");
            System.out.println("5. Inventory Summary");
            System.out.println("6. Exit");
            System.out.print("Choose option: ");
            try {
                int choice = sc.nextInt();
                switch (choice) 
                {
                    case 1: addProduct(); 
                    break;
                    case 2: updateProduct(); 
                    break;
                    case 3: deleteProduct(); 
                    break;
                    case 4: viewProducts(); 
                    break;
                    case 5: displaySummary(); 
                    break;
                    case 6:
                        saveToFile();
                        System.out.println("Data saved. Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter correct data type.");
                sc.nextLine(); 
            }
        }
    }

    private static void addProduct() 
    {
        System.out.print("Enter Product ID: ");
        int id = sc.nextInt();
        if (inventory.containsKey(id)) {
            System.out.println("Product ID already exists! Must be unique.");
            return;
        }
        sc.nextLine();
        System.out.print("Enter Product Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Quantity: ");
        int quantity = sc.nextInt();
        System.out.print("Enter Price: ");
        double price = sc.nextDouble();
        Product product = new Product(id, name, quantity, price);
        inventory.put(id, product);
        System.out.println("Product added successfully!");
    }

    private static void updateProduct() 
    {
        System.out.print("Enter Product ID to update: ");
        int id = sc.nextInt();
        if (!inventory.containsKey(id)) 
            {
            System.out.println("Product not found!");
            return;
        }
        sc.nextLine();
        System.out.print("Enter new Name: ");
        String name = sc.nextLine();
        System.out.print("Enter new Quantity: ");
        int quantity = sc.nextInt();
        System.out.print("Enter new Price: ");
        double price = sc.nextDouble();
        Product product = inventory.get(id);
        product.setName(name);
        product.setQuantity(quantity);
        product.setPrice(price);
        System.out.println("Product updated successfully!");
    }

    private static void deleteProduct() 
    {
        System.out.print("Enter Product ID to delete: ");
        int id = sc.nextInt();
        if (inventory.remove(id) != null) 
            {
            System.out.println("Product deleted successfully!");
        } 
        else 
            {
            System.out.println("Product not found!");
        }
    }

    private static void viewProducts() 
    {
        if (inventory.isEmpty()) 
            {
            System.out.println("No products available.");
            return;
        }

        for (Product p : inventory.values()) 
            {
            System.out.println(p);
        }
    }

    private static void displaySummary() 
    {
        int totalItems = 0;
        double totalValue = 0;
        for (Product p : inventory.values()) 
            {
            totalItems += p.getQuantity();
            totalValue += p.getQuantity() * p.getPrice();
        }
        System.out.println("Total Products: " + inventory.size());
        System.out.println("Total Quantity: " + totalItems);
        System.out.println("Total Inventory Value: " + totalValue);
    }

    private static void saveToFile() 
    {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(inventory);
        } catch (IOException e) {
            System.out.println("Error saving data!");
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadFromFile() 
    {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            inventory = (HashMap<Integer, Product>) ois.readObject();
        } catch (Exception e) {
            System.out.println("No previous data found. Starting fresh.");
        }
    }
}
