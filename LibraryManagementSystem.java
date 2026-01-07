import java.io.*;
import java.util.*;

class Book {
    private String title;
    private String author;
    private boolean checkedOut;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.checkedOut = false;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }
}

class Library {
    private List<Book> books = new ArrayList<>();

    public void addBook(String title, String author) {
        books.add(new Book(title, author));
    }

    public void searchBook(String keyword) {
        boolean found = false;
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(keyword.toLowerCase())
                    || book.getAuthor().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(book.getTitle() + " by " + book.getAuthor());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No books found.");
        }
    }

    public void checkoutBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                if (!book.isCheckedOut()) {
                    book.setCheckedOut(true);
                    System.out.println("Book checked out successfully.");
                } else {
                    System.out.println("Book already checked out.");
                }
                return;
            }
        }
        System.out.println("Book not found.");
    }

    public void returnBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                if (book.isCheckedOut()) {
                    book.setCheckedOut(false);
                    System.out.println("Book returned successfully.");
                } else {
                    System.out.println("Book was not checked out.");
                }
                return;
            }
        }
        System.out.println("Book not found.");
    }

    public void saveBooksToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (Book book : books) {
                writer.println(book.getTitle() + "," + book.getAuthor() + "," + book.isCheckedOut());
            }
        } catch (Exception e) {
            System.out.println("Error saving file.");
        }
    }

    public void loadBooksFromFile(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                Book book = new Book(data[0], data[1]);
                book.setCheckedOut(Boolean.parseBoolean(data[2]));
                books.add(book);
            }
        } catch (Exception e) {
            System.out.println("No previous data found.");
        }
    }
}

public class LibraryManagementSystem {

    // ✅ ENTRY POINT
    public static void main(String[] args) {

        Library library = new Library();
        library.loadBooksFromFile("library.txt");

        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (choice != 0) {
            System.out.println("\n----- Library Management System -----");
            System.out.println("1. Add Book");
            System.out.println("2. Search Book");
            System.out.println("3. Check Out Book");
            System.out.println("4. Return Book");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Title: ");
                        String title = scanner.nextLine();
                        System.out.print("Author: ");
                        String author = scanner.nextLine();
                        library.addBook(title, author);
                        break;

                    case 2:
                        System.out.print("Keyword: ");
                        library.searchBook(scanner.nextLine());
                        break;

                    case 3:
                        System.out.print("Book title: ");
                        library.checkoutBook(scanner.nextLine());
                        break;

                    case 4:
                        System.out.print("Book title: ");
                        library.returnBook(scanner.nextLine());
                        break;

                    case 0:
                        library.saveBooksToFile("library.txt");
                        System.out.println("Exiting...");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }
            } else {
                System.out.println("Invalid input.");
                scanner.nextLine();
            }
        }
        scanner.close();
    }
}
