import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataManager {

    private static final String SAVE_FILE = "studymate_data.txt";

    public static void save(List<Assignment> assignments) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(SAVE_FILE))) {
            for (Assignment a : assignments) {
                pw.println(
                        a.getSubject()  + "|" +
                                a.getTitle()    + "|" +
                                a.getDueDate()  + "|" +
                                a.getPriority() + "|" +
                                a.isDone()
                );
            }
        } catch (IOException e) {
            System.out.println("Could not save data: " + e.getMessage());
        }
    }

    public static List<Assignment> load() {
        List<Assignment> list = new ArrayList<>();
        File file = new File(SAVE_FILE);
        if (!file.exists()) return list;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    Assignment a = new Assignment(
                            parts[0],
                            parts[1],
                            LocalDate.parse(parts[2]),
                            parts[3]
                    );
                    a.setDone(Boolean.parseBoolean(parts[4]));
                    list.add(a);
                }
            }
        } catch (IOException e) {
            System.out.println("Could not load data: " + e.getMessage());
        }

        return list;
    }
}