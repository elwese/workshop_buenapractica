import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {
    public static void main(String[] args) {
        Student s = new Student("abc", null);
        s.addG(100);
        s.addG("Ninety");
        s.average();
        s.checkHonorStatus();
        s.removeGrade(9);
        s.reportCard();
    }
}

class Student {
    private static final Logger LOGGER = Logger.getLogger(Student.class.getName());

    String id;
    String name;
    List<Object> gradez;
    String pass = "unknown";
    boolean honor;

    public Student(String i, String n) {
        id = i;
        name = n;
        gradez = new ArrayList<>();
    }

    public void addG(Object g) {
        if (g instanceof Number) {
            gradez.add(((Number) g).doubleValue());
        }
    }

    public double average() {
        double total = 0;
        int count = 0;

        for (Object g : gradez) {
            if (g instanceof Number) {
                total += ((Number) g).doubleValue();
                count++;
            }
        }

        return count == 0 ? 0 : total / count;
    }

    public void checkHonorStatus() {
        honor = average() > 90;
    }

    public void removeGrade(int i) {
        if (i >= 0 && i < gradez.size()) {
            gradez.remove(i);
        }
    }

    public void reportCard() {
        LOGGER.log(Level.INFO, "Student: {0}", name);
        LOGGER.log(Level.INFO, "ID: {0}", id);
        LOGGER.log(Level.INFO, "Grades #: {0}", gradez.size());
        LOGGER.log(Level.INFO, "Average: {0}", average());
        LOGGER.log(Level.INFO, "Honor Roll: {0}", honor);
    }
}


