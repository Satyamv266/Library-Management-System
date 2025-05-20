import java.util.*;

// Student class to hold student details
class Student {
    private String name;
    private int idNo;
    private String stream;
    private List<String> issuedBooks;

    public Student(String name, int idNo, String stream) {
        this.name = name;
        this.idNo = idNo;
        this.stream = stream;
        this.issuedBooks = new ArrayList<>();
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getIdNo() { return idNo; }
    public void setIdNo(int idNo) { this.idNo = idNo; }

    public String getStream() { return stream; }
    public void setStream(String stream) { this.stream = stream; }

    public List<String> getIssuedBooks() { return issuedBooks; }

    public void issueBook(String book) {
        if (issuedBooks.size() < 2) {
            issuedBooks.add(book);
        } else {
            System.out.println("Cannot issue more than 2 books.");
        }
    }

    public void returnBook(String book) {
        issuedBooks.remove(book);
    }

    public void display() {
        System.out.println("\nName of Student: " + name);
        System.out.println("Id of Student: " + idNo);
        System.out.println("Stream of Student: " + stream);
        System.out.println("Issued Books: " + (issuedBooks.isEmpty() ? "None" : issuedBooks));
    }
}

// Binary Search Tree (BST) class for managing books
class BookBST {
    private Node root;

    private static class Node {
        String key;
        Node left, right;

        public Node(String item) {
            key = item;
            left = null;
            right = null;
        }
    }

    public BookBST() {
        root = null;
    }

    public void insert(String key) {
        root = insertRec(root, key);
    }

    private Node insertRec(Node root, String key) {
        if (root == null) {
            root = new Node(key);
            return root;
        }

        if (key.compareTo(root.key) < 0)
            root.left = insertRec(root.left, key);
        else if (key.compareTo(root.key) > 0)
            root.right = insertRec(root.right, key);
        else
            System.out.println("Error: Duplicate key.");

        return root;
    }

    public boolean containsNode(String value) {
        return containsNodeRecursive(root, value);
    }

    private boolean containsNodeRecursive(Node current, String key) {
        if (current == null) {
            return false;
        }
        if (key.equalsIgnoreCase(current.key)) {
            return true;
        }
        return key.compareTo(current.key) < 0 
            ? containsNodeRecursive(current.left, key)
            : containsNodeRecursive(current.right, key);
    }

    public void printInorder() {
        printInorderRec(root);
    }

    private void printInorderRec(Node node) {
        if (node == null)
            return;

        printInorderRec(node.left);
        System.out.print(node.key + "    ");
        printInorderRec(node.right);
    }

    public void printTree() {
        printTreeRec(root, 0);
    }

    private void printTreeRec(Node t, int space) {
        if (t == null)
            return;

        space += 5;
        printTreeRec(t.right, space);
        System.out.println();
        for (int i = 5; i < space; i++)
            System.out.print(" ");
        System.out.print("[" + t.key + "]");
        printTreeRec(t.left, space);
    }

    public void deleteKey(String key) {
        root = deleteRec(root, key);
    }

    private Node deleteRec(Node root, String key) {
        if (root == null)
            return root;

        if (key.compareTo(root.key) < 0)
            root.left = deleteRec(root.left, key);
        else if (key.compareTo(root.key) > 0)
            root.right = deleteRec(root.right, key);
        else {
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;

            root.key = minValue(root.right);
            root.right = deleteRec(root.right, root.key);
        }
        return root;
    }

    private String minValue(Node root) {
        String minv = root.key;
        while (root.left != null) {
            minv = root.left.key;
            root = root.left;
        }
        return minv;
    }
}

public class LibraryManagement {
    private static final String LIBRARIAN_USER_ID = "dsa@1";
    private static final String LIBRARIAN_PASSWORD = "abc123";
    private static BookBST bookTree = new BookBST();
    private static HashMap<String, Integer> bookInventory = new HashMap<>();
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        
        bookTree.insert("Data Structures");
        bookTree.insert("Algorithms");
        bookTree.insert("Java Programming");
        bookInventory.put("Data Structures", 5); 
        bookInventory.put("Algorithms", 3);     
        bookInventory.put("Java Programming", 2);

        
        Student[] students = new Student[3];
        students[0] = new Student("Satyam", 12322276, "B.Tech-CSE");
        students[1] = new Student("Jay", 12322266, "B.Tech-CSE");
        students[2] = new Student("Abhinandan", 12322256, "B.Tech-CSE");

        // Issue books to satyam
        students[0].issueBook("Data Structures");
        students[0].issueBook("Algorithms");
        bookInventory.put("Data Structures", bookInventory.get("Data Structures") - 1);
        bookInventory.put("Algorithms", bookInventory.get("Algorithms") - 1);

        // Issue books to Jay
        students[1].issueBook("Java Programming");
        students[1].issueBook("Data Structures");
        bookInventory.put("Java Programming", bookInventory.get("Java Programming") - 1);
        bookInventory.put("Data Structures", bookInventory.get("Data Structures") - 1);

        // Issue books to Abhinandan
        students[2].issueBook("Algorithms");
        students[2].issueBook("Java Programming");
        bookInventory.put("Algorithms", bookInventory.get("Algorithms") - 1);
        bookInventory.put("Java Programming", bookInventory.get("Java Programming") - 1);

        runLibrarySystem(students);
    }

    private static void runLibrarySystem(Student[] students) {
        boolean exit = false;

        while (!exit) {
            displayMainMenuOptions();
            int choice = input.nextInt();

            switch (choice) {
                case 1:
                    handleLibrarianLogin();
                    break;
                case 2:
                    handleUserLogin(students);
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMainMenuOptions() {
        System.out.println("\n.....................................");
        System.out.println("1. Librarian Login. ");
        System.out.println("2. User Login. ");
        System.out.println("3. Exit. ");
        System.out.println("\n.....................................");
        System.out.print("Enter Your choice: ");
    }

    private static void handleLibrarianLogin() {
        System.out.print("\nEnter UserId: ");
        String userId = input.next();

        System.out.print("\nEnter Password: ");
        String password = input.next();

        if (!LIBRARIAN_USER_ID.equals(userId)) {
            System.out.println("Invalid User ID.");
        } else if (!LIBRARIAN_PASSWORD.equals(password)) {
            System.out.println("Invalid Password.");
        } else {
            System.out.println("Login successfully.");
            librarianMenu();
        }
    }

    private static void librarianMenu() {
        boolean exit = false;
        while (!exit) {
            displayLibrarianMenuOptions();
            int choice = input.nextInt();

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    deleteBook();
                    break;
                case 3:
                    updateBook();
                    break;
                case 4:
                    printBookDetails();
                    break;
                case 5:
                    bookTree.printInorder();
                    break;
                case 6:
                    bookTree.printTree();
                    break;
                case 7:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayLibrarianMenuOptions() {
        System.out.println("\n.....................................");
        System.out.println("1. Add book. ");
        System.out.println("2. Delete book. ");
        System.out.println("3. Update book. ");
        System.out.println("4. Print book details. ");
        System.out.println("5. Print books in-order. ");
        System.out.println("6. Print binary search tree. ");
        System.out.println("7. Exit. ");
        System.out.println("\n.....................................");
        System.out.print("Enter Your choice: ");
    }

    private static void addBook() {
        System.out.print("Enter the name of the book to add: ");
        String newBook = input.next();

        if (bookTree.containsNode(newBook)) {
            System.out.println("Book already exists in the library.");
        } else {
            bookTree.insert(newBook);
            System.out.println("Book added successfully.");
            bookInventory.put(newBook, 1); // Initial count of 1 copy
        }
    }

    private static void deleteBook() {
        System.out.print("Enter the name of the book to delete: ");
        String deleteBook = input.next();

        if (!bookTree.containsNode(deleteBook)) {
            System.out.println("Book does not exist in the library.");
        } else {
            bookTree.deleteKey(deleteBook);
            bookInventory.remove(deleteBook);
            System.out.println("Book deleted successfully.");
        }
    }

    private static void updateBook() {
        System.out.print("Enter the name of the book to update: ");
        String updateBook = input.next();

        if (!bookTree.containsNode(updateBook)) {
            System.out.println("Book does not exist in the library.");
        } else {
            System.out.print("Enter the new count of the book: ");
            int count = input.nextInt();
            bookInventory.put(updateBook, count);
            System.out.println("Book count updated successfully.");
        }
    }

    private static void printBookDetails() {
        System.out.println("\nBooks currently available in the library:");
        for (Map.Entry<String, Integer> entry : bookInventory.entrySet()) {
            System.out.println(entry.getKey() + " - Copies available: " + entry.getValue());
        }
    }

    private static void handleUserLogin(Student[] students) {
        boolean validLogin = false;

        while (!validLogin) {
            System.out.print("\nEnter Student Id: ");
            int studentId = input.nextInt();

            Student currentStudent = null;
            for (Student student : students) {
                if (student.getIdNo() == studentId) {
                    currentStudent = student;
                    break;
                }
            }

            if (currentStudent == null) {
                System.out.println("Student not found. Please try again.");
            } else {
                validLogin = true;
                userMenu(currentStudent);
            }
        }
    }

    private static void userMenu(Student student) {
        boolean exit = false;
        while (!exit) {
            displayUserMenuOptions(student);
            int choice = input.nextInt();

            switch (choice) {
                case 1:
                    displayStudentDetails(student);
                    break;
                case 2:
                    issueBook(student);
                    break;
                case 3:
                    returnBook(student);
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayUserMenuOptions(Student student) {
        System.out.println("\n.....................................");
        System.out.println("1. Display student details. ");
        System.out.println("2. Issue book. ");
        System.out.println("3. Return book. ");
        System.out.println("4. Exit. ");
        System.out.println("\n.....................................");
        System.out.print("Enter Your choice: ");
    }

    private static void displayStudentDetails(Student student) {
        student.display();
    }

    private static void issueBook(Student student) {
        System.out.print("Enter the name of the book to issue: ");
        String issueBook = input.next();

        if (!bookTree.containsNode(issueBook)) {
            System.out.println("Book does not exist in the library.");
        } else if (bookInventory.get(issueBook) == 0) {
            System.out.println("Sorry, no copies of this book are currently available.");
        } else {
            student.issueBook(issueBook);
            bookInventory.put(issueBook, bookInventory.get(issueBook) - 1);
            System.out.println("Book issued successfully.");
        }
    }

    private static void returnBook(Student student) {
        System.out.print("Enter the name of the book to return: ");
        String returnBook = input.next();

        if (!student.getIssuedBooks().contains(returnBook)) {
            System.out.println("You have not issued this book.");
        } else {
            student.returnBook(returnBook);
            bookInventory.put(returnBook, bookInventory.get(returnBook) + 1);
            System.out.println("Book returned successfully.");
        }
    }
}
