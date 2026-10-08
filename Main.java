import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String name = readRequiredValue(scanner, "Enter student name: ");
            String id = readRequiredValue(scanner, "Enter student ID: ");
            Student student = new Student(id, name);

            LOGGER.info("Enter grades from 0 to 100. Submit a blank line when finished.");
            while (true) {
                LOGGER.info("Grade: ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    break;
                }

                try {
                    student.addGrade(input);
                } catch (IllegalArgumentException exception) {
                    LOGGER.log(Level.WARNING, () -> "Invalid grade: " + exception.getMessage());
                }
            }

            student.reportCard();
        }
    }

    private static String readRequiredValue(Scanner scanner, String prompt) {
        while (true) {
            LOGGER.log(Level.INFO, prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            LOGGER.info("This value cannot be empty. Please try again.");
        }
    }
}

class Student {
    private static final Logger LOGGER = Logger.getLogger(Student.class.getName());

    private final String id;
    private final String name;
    private final List<Double> grades = new ArrayList<>();

    public Student(String id, String name) {
        this.id = requireNonEmpty(id, "ID");
        this.name = requireNonEmpty(name, "Name");
    }

    private static String requireNonEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }
        return value.trim();
    }

    public void addGrade(String value) {
        if (value == null) {
            throw new IllegalArgumentException("grade must be numeric.");
        }
        final double grade;
        try {
            grade = Double.parseDouble(value.trim());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("grade must be numeric.", exception);
        }
        addGrade(grade);
    }

    public void addGrade(double grade) {
        if (grade < 0 || grade > 100 || Double.isNaN(grade)) {
            throw new IllegalArgumentException("grade must be between 0 and 100.");
        }
        grades.add(grade);
    }

    public double average() {
        if (grades.isEmpty()) {
            return 0;
        }

        double total = 0;
        for (double grade : grades) {
            total += grade;
        }
        return total / grades.size();
    }

    public String letterGrade() {
        double average = average();
        if (average >= 90) {
            return "A";
        } else if (average >= 80) {
            return "B";
        } else if (average >= 70) {
            return "C";
        } else if (average >= 60) {
            return "D";
        }
        return "F";
    }

    public String passStatus() {
        return average() >= 60 ? "Passed" : "Failed";
    }

    public void reportCard() {
        LOGGER.log(Level.INFO, () -> "Student: " + name);
        LOGGER.log(Level.INFO, () -> "ID: " + id);
        LOGGER.log(Level.INFO, () -> "Grades: " + grades);
        LOGGER.log(Level.INFO, () -> "Average: " + average());
        LOGGER.log(Level.INFO, () -> "Letter grade: " + letterGrade());
        LOGGER.log(Level.INFO, () -> "Status: " + passStatus());
    }
}
