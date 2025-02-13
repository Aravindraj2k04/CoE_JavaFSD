import java.util.List;

public class StudentService {

    private StudentDAO studentDAO;

    public StudentService() {
        this.studentDAO = new StudentDAO();
    }

    public void addStudent(String name, String email, String course, double fee, double paid, double due, String address, String phone) {
        // Basic input validation
        if (name == null || email == null || email.isEmpty()) {
            System.out.println("Invalid input.");
            return;
        }

        Student student = new Student(0, name, email, course, fee, paid, due, address, phone);
        studentDAO.addStudent(student);
    }

    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }

    // Other business logic as required
}
