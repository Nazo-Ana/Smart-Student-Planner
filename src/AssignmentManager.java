import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class AssignmentManager {

    private final List<Assignment> assignments;
    private VBox assignmentList;

    public AssignmentManager(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public VBox getView() {
        VBox box = new VBox(16);
        box.setPadding(new Insets(40));

        Label heading = new Label("📝 Assignments");
        heading.setStyle(
                "-fx-font-size: 28px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #2D2B55;");

        HBox form = buildForm();

        assignmentList = new VBox(10);
        refreshList();

        ScrollPane scroll = new ScrollPane(assignmentList);
        scroll.setFitToWidth(true);
        scroll.setStyle(
                "-fx-background: #F4F6FF;" +
                        "-fx-background-color: #F4F6FF;");
        VBox.setVgrow(scroll, Priority.ALWAYS);

        box.getChildren().addAll(heading, form, scroll);
        return box;
    }

    private HBox buildForm() {
        TextField subjectField = new TextField();
        subjectField.setPromptText("Subject");
        subjectField.setPrefWidth(140);
        subjectField.setStyle(UIHelper.fieldStyle());

        TextField titleField = new TextField();
        titleField.setPromptText("Assignment title");
        titleField.setPrefWidth(200);
        titleField.setStyle(UIHelper.fieldStyle());

        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Due date");

        ComboBox<String> priority = new ComboBox<>();
        priority.getItems().addAll("🔴 High", "🟡 Medium", "🟢 Low");
        priority.setPromptText("Priority");

        Button addBtn = new Button("+ Add");
        addBtn.setStyle(
                "-fx-background-color: #6C63FF;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 13px;" +
                        "-fx-padding: 9 18;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-cursor: hand;");

        addBtn.setOnAction(e -> {
            String subject = subjectField.getText().trim();
            String title   = titleField.getText().trim();
            LocalDate due  = datePicker.getValue();
            String prio    = priority.getValue();

            if (subject.isEmpty() || title.isEmpty() || due == null || prio == null) {
                UIHelper.showAlert("Please fill in all fields!");
                return;
            }

            assignments.add(new Assignment(subject, title, due, prio));
            DataManager.save(assignments);
            refreshList();

            subjectField.clear();
            titleField.clear();
            datePicker.setValue(null);
            priority.setValue(null);
        });

        HBox form = new HBox(10,
                subjectField, titleField, datePicker, priority, addBtn);
        form.setAlignment(Pos.CENTER_LEFT);
        form.setPadding(new Insets(16));
        form.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 12px;");
        return form;
    }

    private void refreshList() {
        assignmentList.getChildren().clear();

        if (assignments.isEmpty()) {
            Label empty = new Label("No assignments yet. Add one above! 🎉");
            empty.setStyle("-fx-font-size: 14px; -fx-text-fill: #aaa;");
            assignmentList.getChildren().add(empty);
            return;
        }

        for (Assignment a : assignments) {
            assignmentList.getChildren().add(buildCard(a));
        }
    }

    private HBox buildCard(Assignment a) {
        HBox card = new HBox(16);
        card.setPadding(new Insets(16));
        card.setAlignment(Pos.CENTER_LEFT);
        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 10px;");

        long days = ChronoUnit.DAYS.between(LocalDate.now(), a.getDueDate());
        String countdown =
                days < 0  ? "⚠ OVERDUE!"       :
                        days == 0 ? "⚠ Due TODAY!"      :
                                days == 1 ? "📅 Due tomorrow"   :
                                        "📅 Due in " + days + " days";
        String countColor =
                days < 0  ? "#FF6B6B" :
                        days <= 1 ? "#FFD93D" : "#6BCB77";

        VBox info = new VBox(4);
        HBox.setHgrow(info, Priority.ALWAYS);

        Label titleLbl = new Label(a.getTitle());
        titleLbl.setStyle(a.isDone()
                ? "-fx-font-size: 15px; -fx-font-weight: bold; " +
                "-fx-text-fill: #bbb; -fx-strikethrough: true;"
                : "-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #2D2B55;");

        Label subLbl = new Label("📘 " + a.getSubject() + "   " + a.getPriority());
        subLbl.setStyle("-fx-font-size: 12px; -fx-text-fill: #888;");

        Label dueLbl = new Label(countdown);
        dueLbl.setStyle(
                "-fx-font-size: 12px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: " + countColor + ";");

        info.getChildren().addAll(titleLbl, subLbl, dueLbl);

        Button doneBtn = new Button(a.isDone() ? "↩ Undo" : "✅ Done");
        doneBtn.setStyle(
                "-fx-background-color: " + (a.isDone() ? "#aaa" : "#6BCB77") + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 6px;" +
                        "-fx-cursor: hand;");
        doneBtn.setOnAction(e -> {
            a.setDone(!a.isDone());
            DataManager.save(assignments);
            refreshList();
        });

        Button deleteBtn = new Button("🗑");
        deleteBtn.setStyle(
                "-fx-background-color: #FF6B6B;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 6px;" +
                        "-fx-cursor: hand;");
        deleteBtn.setOnAction(e -> {
            assignments.remove(a);
            DataManager.save(assignments);
            refreshList();
        });

        card.getChildren().addAll(info, doneBtn, deleteBtn);
        return card;
    }
}