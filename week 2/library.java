import java.io.*;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Custom Exceptions
class BookNotFoundException extends Exception {
    public BookNotFoundException(String message) {
        super(message);
    }
}

class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }
}

class MaxBooksAllowedException extends Exception {
    public MaxBooksAllowedException(String message) {
        super(message);
    }
}

// Book Class
class Book implements Serializable {
    private String title;
    private String author;
    private String ISBN;

    public Book(String title, String author, String ISBN) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    @Override
    public String toString() {
        return title + " by " + author + " (ISBN: " + ISBN + ")";
    }
}

// User Class
class User {
    private String name;
    private String userID;
    private List<Book> borrowedBooks;

    public User(String name, String userID) {
        this.name = name;
        this.userID = userID;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getUserID() {
        return userID;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void addBorrowedBook(Book book) {
        borrowedBooks.add(book);
    }

    public void removeBorrowedBook(Book book) {
        borrowedBooks.remove(book);
    }
}

// ILibrary Interface
interface ILibrary {
    void borrowBook(String ISBN, String userID) throws BookNotFoundException, UserNotFoundException, MaxBooksAllowedException;
    void returnBook(String ISBN, String userID) throws BookNotFoundException, UserNotFoundException;
    void reserveBook(String ISBN, String userID) throws BookNotFoundException, UserNotFoundException;
    Book searchBook(String title);
}

// LibrarySystem Abstract Class
abstract class LibrarySystem implements ILibrary {
    protected List<Book> books;
    protected List<User> users;

    public LibrarySystem() {
        books = new ArrayList<>();
        users = new ArrayList<>();
    }

    public abstract void addBook(Book book);
    public abstract void addUser(User user);

    public Book searchBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<User> getUsers() {
        return users;
    }
}

// LibraryManager Class with Multithreading
class LibraryManager extends LibrarySystem {
    private Lock lock = new ReentrantLock();

    @Override
    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public void borrowBook(String ISBN, String userID) throws BookNotFoundException, UserNotFoundException, MaxBooksAllowedException {
        lock.lock();
        try {
            Book book = searchBook(ISBN);
            User user = getUserByID(userID);

            if (book == null) throw new BookNotFoundException("Book not found!");
            if (user == null) throw new UserNotFoundException("User not found!");
            if (user.getBorrowedBooks().size() >= 3) throw new MaxBooksAllowedException("User has already borrowed 3 books!");

            user.addBorrowedBook(book);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void returnBook(String ISBN, String userID) throws BookNotFoundException, UserNotFoundException {
        lock.lock();
        try {
            Book book = searchBook(ISBN);
            User user = getUserByID(userID);

            if (book == null) throw new BookNotFoundException("Book not found!");
            if (user == null) throw new UserNotFoundException("User not found!");

            user.removeBorrowedBook(book);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void reserveBook(String ISBN, String userID) throws BookNotFoundException, UserNotFoundException {
        // Reserve book logic (similar to borrowBook but does not modify borrowedBooks)
    }

    private User getUserByID(String userID) {
        for (User user : users) {
            if (user.getUserID().equals(userID)) {
                return user;
            }
        }
        return null;
    }
}

// LibraryDataManager for Saving and Loading Data
class LibraryDataManager {
    public static void saveLibraryData(LibraryManager libManager, String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(libManager.getBooks());
            out.writeObject(libManager.getUsers());
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    public static void loadLibraryData(LibraryManager libManager, String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            libManager.getBooks().addAll((List<Book>) in.readObject());
            libManager.getUsers().addAll((List<User>) in.readObject());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
}

// Main Class to Demonstrate the System
public class Main {
    public static void main(String[] args) {
        LibraryManager libManager = new LibraryManager();

        // Add books
        libManager.addBook(new Book("Java Programming", "James Gosling", "123456"));
        libManager.addBook(new Book("Data Structures", "Mark Weiss", "654321"));
       
        // Add users
        libManager.addUser(new User("Alice", "U001"));
        libManager.addUser(new User("Bob", "U002"));

        // Borrow and return books
        try {
            libManager.borrowBook("123456", "U001");
            System.out.println("Alice borrowed 'Java Programming'");
            libManager.returnBook("123456", "U001");
            System.out.println("Alice returned 'Java Programming'");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Save data to file
        LibraryDataManager.saveLibraryData(libManager, "library_data.dat");

        // Load data from file
        LibraryManager newLibManager = new LibraryManager();
        LibraryDataManager.loadLibraryData(newLibManager, "library_data.dat");

        // Show loaded data
        System.out.println("Books in the loaded system:");
        for (Book book : newLibManager.getBooks()) {
            System.out.println(book);
        }
        System.out.println("Users in the loaded system:");
        for (User user : newLibManager.getUsers()) {
            System.out.println(user.getName());
        }
    }
}
