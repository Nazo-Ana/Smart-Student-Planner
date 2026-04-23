import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

public class Timetable {

    public VBox getView() {
        VBox box = new VBox(20);
        box.setPadding(new Insets(40));

        Label heading = new Label("📅 Weekly Timetable");
        heading.setStyle(
                "-fx-font-size: 28px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #2D2B55;");

        GridPane grid = buildGrid();

        ScrollPane scroll = new ScrollPane(grid);
        scroll.setFitToWidth(true);
        scroll.setStyle(
                "-fx-background: #F4F6FF;" +
                        "-fx-background-color: #F4F6FF;");

        box.getChildren().addAll(heading, scroll);
        return box;
    }

    private GridPane buildGrid() {
        String[] days    = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
        String[] periods = {"8:00","9:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00"};
        String[] colors  = {"#6C63FF","#FF6B6B","#FFD93D","#6BCB77","#4ECDC4","#45B7D1","#96CEB4"};

        GridPane grid = new GridPane();
        grid.setHgap(4);
        grid.setVgap(4);
        grid.setStyle(
                "-fx-background-color: white;" +
                        "-fx-padding: 16;" +
                        "-fx-background-radius: 12;");

        // Day headers
        for (int d = 0; d < days.length; d++) {
            Label lbl = new Label(days[d]);
            lbl.setMinWidth(110);
            lbl.setAlignment(Pos.CENTER);
            lbl.setStyle(
                    "-fx-background-color: " + colors[d] + ";" +
                            "-fx-text-fill: white;" +
                            "-fx-font-weight: bold;" +
                            "-fx-padding: 8;" +
                            "-fx-background-radius: 6;");
            grid.add(lbl, d + 1, 0);
        }

        // Time slots
        for (int p = 0; p < periods.length; p++) {
            Label timeLbl = new Label(periods[p]);
            timeLbl.setMinWidth(55);
            timeLbl.setAlignment(Pos.CENTER_RIGHT);
            timeLbl.setStyle(
                    "-fx-font-size: 12px;" +
                            "-fx-text-fill: #888;" +
                            "-fx-padding: 0 8 0 0;");
            grid.add(timeLbl, 0, p + 1);

            for (int d = 0; d < days.length; d++) {
                Label cell = new Label();
                cell.setMinSize(110, 44);
                cell.setAlignment(Pos.CENTER);
                cell.setStyle(
                        "-fx-background-color: #F4F6FF;" +
                                "-fx-background-radius: 6;" +
                                "-fx-font-size: 11px;" +
                                "-fx-text-fill: #555;");
                grid.add(cell, d + 1, p + 1);
            }
        }

        // Sample classes
        addEntry(grid, 1, 1, "Programming II", colors[0]);
        addEntry(grid, 3, 1, "Programming II", colors[0]);
        addEntry(grid, 2, 2, "Mathematics",    colors[1]);
        addEntry(grid, 4, 2, "Mathematics",    colors[1]);
        addEntry(grid, 1, 3, "Database",       colors[2]);
        addEntry(grid, 5, 3, "Database",       colors[2]);
        addEntry(grid, 3, 4, "English",        colors[3]);
        addEntry(grid, 2, 5, "Networks",       colors[4]);
        addEntry(grid, 4, 5, "Networks",       colors[4]);

        return grid;
    }

    private void addEntry(GridPane grid, int col, int row, String text, String color) {
        Label lbl = new Label(text);
        lbl.setMinSize(110, 44);
        lbl.setAlignment(Pos.CENTER);
        lbl.setWrapText(true);
        lbl.setStyle(
                "-fx-background-color: " + color + "22;" +
                        "-fx-border-color: " + color + ";" +
                        "-fx-border-radius: 6;" +
                        "-fx-background-radius: 6;" +
                        "-fx-font-size: 11px;" +
                        "-fx-text-fill: " + color + ";" +
                        "-fx-font-weight: bold;");
        grid.add(lbl, col, row);
    }
}