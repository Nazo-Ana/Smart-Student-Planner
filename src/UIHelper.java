import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

public class UIHelper {

    public static String fieldStyle() {
        return "-fx-background-radius: 8px; " +
                "-fx-padding: 8px; " +
                "-fx-font-size: 13px;";
    }

    public static Button sidebarButton(String text) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);

        String normal =
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: #CDCBE8;" +
                        "-fx-font-size: 14px;" +
                        "-fx-alignment: CENTER-LEFT;" +
                        "-fx-padding: 10 16;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-cursor: hand;";

        String hover =
                "-fx-background-color: #3D3B6E;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-alignment: CENTER-LEFT;" +
                        "-fx-padding: 10 16;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-cursor: hand;";

        btn.setStyle(normal);
        btn.setOnMouseEntered(e -> btn.setStyle(hover));
        btn.setOnMouseExited(e  -> btn.setStyle(normal));
        return btn;
    }

    public static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}