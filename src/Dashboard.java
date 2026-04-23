import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Dashboard {

    private final List<Assignment> assignments;

    public Dashboard(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public VBox getView() {
        VBox box = new VBox(20);
        box.setPadding(new Insets(40));

        String today = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy"));

        Label heading = new Label("Welcome back! 👋");
        heading.setStyle(
                "-fx-font-size: 30px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #2D2B55;");

        Label dateLabel = new Label("📅  " + today);
        dateLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #888;");

        Label quote = new Label(
                "\" The secret of getting ahead is getting started. \"\n— Mark Twain");
        quote.setWrapText(true);
        quote.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-text-fill: #6C63FF;" +
                        "-fx-background-color: white;" +
                        "-fx-padding: 20px;" +
                        "-fx-background-radius: 12px;");
        quote.setMaxWidth(Double.MAX_VALUE);

        HBox cards = buildStatCards();

        Label upcomingTitle = new Label("⚡ Upcoming Deadlines");
        upcomingTitle.setStyle(
                "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #2D2B55;");

        VBox upcomingList = buildUpcomingList();

        box.getChildren().addAll(
                heading, dateLabel, quote, cards, upcomingTitle, upcomingList);
        return box;
    }

    private HBox buildStatCards() {
        long pending   = assignments.stream().filter(a -> !a.isDone()).count();
        long completed = assignments.stream().filter(a ->  a.isDone()).count();
        long dueToday  = assignments.stream()
                .filter(a -> !a.isDone() && a.getDueDate().equals(LocalDate.now()))
                .count();

        HBox cards = new HBox(16);
        cards.getChildren().addAll(
                makeCard("📝", "Assignments", pending   + " pending",  "#FF6B6B"),
                makeCard("✅", "Completed",   completed + " done",     "#6BCB77"),
                makeCard("⏰", "Due Today",   dueToday  + " tasks",    "#FFD93D")
        );
        return cards;
    }

    private VBox makeCard(String icon, String title, String value, String color) {
        VBox card = new VBox(6);
        card.setPadding(new Insets(20));
        card.setPrefWidth(190);
        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 12px;");

        Label ico = new Label(icon);
        ico.setStyle("-fx-font-size: 24px;");

        Label ttl = new Label(title);
        ttl.setStyle("-fx-font-size: 13px; -fx-text-fill: #888;");

        Label val = new Label(value);
        val.setStyle(
                "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: " + color + ";");

        card.getChildren().addAll(ico, ttl, val);
        return card;
    }

    private VBox buildUpcomingList() {
        VBox list = new VBox(8);

        assignments.stream()
                .filter(a -> !a.isDone())
                .sorted((a, b) -> a.getDueDate().compareTo(b.getDueDate()))
                .limit(4)
                .forEach(a -> list.getChildren().add(makeUpcomingRow(a)));

        if (list.getChildren().isEmpty()) {
            Label none = new Label("No upcoming assignments! Enjoy your free time 🎉");
            none.setStyle("-fx-font-size: 13px; -fx-text-fill: #aaa;");
            list.getChildren().add(none);
        }

        return list;
    }

    private HBox makeUpcomingRow(Assignment a) {
        HBox row = new HBox(16);
        row.setPadding(new Insets(12, 16, 12, 16));
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 10px;");

        long days = ChronoUnit.DAYS.between(LocalDate.now(), a.getDueDate());
        String countdown =
                days < 0  ? "OVERDUE!"          :
                        days == 0 ? "Due TODAY!"         :
                                days == 1 ? "Due tomorrow"       :
                                        "Due in " + days + " days";
        String countColor =
                days < 0      ? "#FF6B6B" :
                        days <= 1     ? "#FFD93D" : "#6BCB77";

        Label titleLbl = new Label(a.getTitle());
        titleLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #2D2B55;");
        HBox.setHgrow(titleLbl, Priority.ALWAYS);

        Label subjectLbl = new Label("📘 " + a.getSubject());
        subjectLbl.setStyle("-fx-font-size: 12px; -fx-text-fill: #888;");

        Label countLbl = new Label(countdown);
        countLbl.setStyle(
                "-fx-font-size: 12px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: " + countColor + ";");

        row.getChildren().addAll(titleLbl, subjectLbl, countLbl);
        return row;
    }
}