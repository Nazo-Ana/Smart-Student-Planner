import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.List;

public class Main extends Application {

    private List<Assignment> assignments;
    private VBox mainContent;

    @Override
    public void start(Stage stage) {
        assignments = DataManager.load();

        // Sidebar
        VBox sidebar = buildSidebar();

        // Main content area
        mainContent = new VBox();
        mainContent.setStyle("-fx-background-color: #F4F6FF;");
        HBox.setHgrow(mainContent, Priority.ALWAYS);

        // Start on dashboard
        showDashboard();

        HBox root = new HBox(sidebar, mainContent);
        Scene scene = new Scene(root, 980, 640);

        stage.setTitle("StudyMate — Smart Student Planner");
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> DataManager.save(assignments));
        stage.show();
    }

    private VBox buildSidebar() {
        VBox sidebar = new VBox(8);
        sidebar.setPrefWidth(220);
        sidebar.setPadding(new Insets(30, 20, 30, 20));
        sidebar.setStyle("-fx-background-color: #2D2B55;");

        Label appName = new Label("📚 StudyMate");
        appName.setStyle(
                "-fx-font-size: 22px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: white;");

        Label tagline = new Label("Smart Student Planner");
        tagline.setStyle("-fx-font-size: 11px; -fx-text-fill: #9B99CC;");

        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: #9B99CC;");
        VBox.setMargin(sep, new Insets(12, 0, 12, 0));

        javafx.scene.control.Button btnDash   = UIHelper.sidebarButton("🏠  Dashboard");
        javafx.scene.control.Button btnAssign = UIHelper.sidebarButton("📝  Assignments");
        javafx.scene.control.Button btnTime   = UIHelper.sidebarButton("📅  Timetable");

        btnDash.setOnAction(e   -> showDashboard());
        btnAssign.setOnAction(e -> showAssignments());
        btnTime.setOnAction(e   -> showTimetable());

        sidebar.getChildren().addAll(appName, tagline, sep, btnDash, btnAssign, btnTime);
        return sidebar;
    }

    private void showDashboard() {
        Dashboard dashboard = new Dashboard(assignments);
        mainContent.getChildren().setAll(dashboard.getView());
    }

    private void showAssignments() {
        AssignmentManager manager = new AssignmentManager(assignments);
        mainContent.getChildren().setAll(manager.getView());
    }

    private void showTimetable() {
        Timetable timetable = new Timetable();
        mainContent.getChildren().setAll(timetable.getView());
    }

    public static void main(String[] args) {
        launch(args);
    }
}