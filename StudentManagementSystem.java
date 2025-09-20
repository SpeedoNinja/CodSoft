import java.io.*;
import java.util.*;

class Student {
    private String name;
    private String rollNumber;
    private String grade;

    public Student(String name, String rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public String getGrade() {
        return grade;
    }

    public void setName(String name) {
        if (!name.isEmpty()) this.name = name;
    }

    public void setGrade(String grade) {
        if (!grade.isEmpty()) this.grade = grade;
    }

    public String toString() {
        return name + "," + rollNumber + "," + grade;
    }

    public static Student fromString(String data) {
        String[] parts = data.split(",");
        return new Student(parts[0], parts[1], parts[2]);
    }
}

class StudentManagementSystem {
    private List<Student> students;
    private final String FILE_NAME = "students.txt";

    public StudentManagementSystem() {
        students = new ArrayList<>();
        loadFromFile();
    }

    public void addStudent(Student s) {
        if (!s.getName().isEmpty() && !s.getRollNumber().isEmpty() && !s.getGrade().isEmpty()) {
            students.add(s);
            saveToFile();
        }
    }

    public void removeStudent(String rollNumber) {
        students.removeIf(s -> s.getRollNumber().equalsIgnoreCase(rollNumber));
        saveToFile();
    }

    public Student searchStudent(String rollNumber) {
        for (Student s : students) {
            if (s.getRollNumber().equalsIgnoreCase(rollNumber)) return s;
        }
        return null;
    }

    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        for (Student s : students) {
            System.out.println("Name: " + s.getName());
            System.out.println("Roll No: " + s.getRollNumber());
            System.out.println("Grade: " + s.getGrade());
            System.out.println("--------------------");
        }
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Student s : students) {
                writer.write(s.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Student s = Student.fromString(line);
                students.add(s);
            }
        } catch (IOException e) {
            System.out.println("Error loading data.");
        }
    }
}

public class StudentApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentManagementSystem sms = new StudentManagementSystem();

        while (true) {
            System.out.println("\n=== Student Management System ===");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Search Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = sc.nextLine().trim();
                    System.out.print("Enter roll number: ");
                    String roll = sc.nextLine().trim();
                    System.out.print("Enter grade: ");
                    String grade = sc.nextLine().trim();
                    sms.addStudent(new Student(name, roll, grade));
                    System.out.println("Student added.");
                    break;

                case 2:
                    System.out.print("Enter roll number to remove: ");
                    String rollToRemove = sc.nextLine().trim();
                    sms.removeStudent(rollToRemove);
                    System.out.println("Student removed if exists.");
                    break;

                case 3:
                    System.out.print("Enter roll number to search: ");
                    String rollToSearch = sc.nextLine().trim();
                    Student found = sms.searchStudent(rollToSearch);
                    if (found != null) {
                        System.out.println("Name: " + found.getName());
                        System.out.println("Roll No: " + found.getRollNumber());
                        System.out.println("Grade: " + found.getGrade());
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 4:
                    sms.displayAllStudents();
                    break;

                case 5:
                    System.out.println("Exiting system.");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
