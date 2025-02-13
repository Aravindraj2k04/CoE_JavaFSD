import java.util.List;
import java.util.Scanner;

public class Main {

    private static StudentService studentService;

    public static void main(String[] args) {
        studentService = new StudentService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.println("Enter student name:");
                    String name = scanner.nextLine();
                    System.out.println("Enter student email:");
                    String email = scanner.nextLine();
                    System.out.println("Enter course:");
                    String course = scanner.nextLine();
                    System.out.println("Enter fee:");
                    double fee = scanner.nextDouble();
                    System.out.println("Enter paid:");
                    double paid = scanner.nextDouble();
                    System.out.println("Enter due:");
                    double due = scanner.nextDouble();
                    scanner.nextLine();  // Consume the newline character
                    System.out.println("Enter address:");
                    String address = scanner.nextLine();
                    System.out.println("Enter phone:");
                    String phone = scanner.nextLine();

                    studentService.addStudent(name, email, course, fee, paid, due, address, phone);
                    break;

                case 2:
                    List<Student> students = studentService.getAllStudents();
                    for (Student student : students) {
                        System.out.println("ID: " + student.getId() + ", Name: " + student.getName() + ", Email: " + student.getEmail());
                    }
                    break;

                case 3:
                    System.out.println("Exiting...");
                    return;
            }
        }
    }
}
