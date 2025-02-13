import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public void addStudent(Student student) {
        String sql = "INSERT INTO student (name, email, course, fee, paid, due, address, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getName());
            stmt.setString(2, student.getEmail());
            stmt.setString(3, student.getCourse());
            stmt.setDouble(4, student.getFee());
            stmt.setDouble(5, student.getPaid());
            stmt.setDouble(6, student.getDue());
            stmt.setString(7, student.getAddress());
            stmt.setString(8, student.getPhone());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM student";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("course"),
                        rs.getDouble("fee"),
                        rs.getDouble("paid"),
                        rs.getDouble("due"),
                        rs.getString("address"),
                        rs.getString("phone")
                );
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // Add other CRUD operations as needed (edit, delete)
}
