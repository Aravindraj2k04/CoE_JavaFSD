public class Student {
    private int id;
    private String name;
    private String email;
    private String course;
    private double fee;
    private double paid;
    private double due;
    private String address;
    private String phone;

    public Student(int id, String name, String email, String course, double fee, double paid, double due, String address, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.course = course;
        this.fee = fee;
        this.paid = paid;
        this.due = due;
        this.address = address;
        this.phone = phone;
    }

    // Getters and setters for each field

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCourse() {
        return course;
    }

    public double getFee() {
        return fee;
    }

    public double getPaid() {
        return paid;
    }

    public double getDue() {
        return due;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    // Other setters as needed
}
