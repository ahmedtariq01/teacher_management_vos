package fx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.loadmanagementsystem;
import main.teacher;
import utils.functionsUtils;

import java.util.ArrayList;
import java.util.List;

public class AddTeacherScreen {
    public static Scene getScene(Stage primaryStage) {
        Label idLabel = new Label("Enter teacher ID:");
        TextField idField = new TextField();
        idField.setMaxWidth(300);

        Label nameLabel = new Label("Enter teacher name:");
        TextField nameField = new TextField();
        nameField.setMaxWidth(300);

        Label departmentLabel = new Label("Enter teacher department:");
        TextField departmentField = new TextField();
        departmentField.setMaxWidth(300);

        Label courseLabel = new Label("Enter teacher course:");
        TextField courseField = new TextField();
        courseField.setMaxWidth(300);

        Label daysLabel = new Label("Select teaching days:");
        ObservableList<CheckBox> dayCheckBoxes = FXCollections.observableArrayList(
                new CheckBox("Mon"), new CheckBox("Tue"), new CheckBox("Wed"),
                new CheckBox("Thu"), new CheckBox("Fri"), new CheckBox("Sat")
        );

        HBox daysBox = new HBox(10); // Display days horizontally with spacing of 10
        daysBox.setStyle("-fx-alignment: center;"); // Center align the days
        daysBox.getChildren().addAll(dayCheckBoxes);

        Label timeLabel = new Label("Enter teaching time (e.g., 10AM-12PM):");
        TextField timeField = new TextField();
        timeField.setMaxWidth(300);

        Label payrateLabel = new Label("Enter teacher pay rate per hour:");
        TextField payrateField = new TextField();
        payrateField.setMaxWidth(300);

        Button addButton = new Button("Add Teacher");
        addButton.setOnAction(e -> {
            boolean valid = true;
            idField.setStyle(""); // Reset style
            payrateField.setStyle(""); // Reset style

            int id = 0;
            float payrate = 0;

            try {
                id = functionsUtils.CheckValidatedIntegerInput(idField.getText());
            } catch (IllegalArgumentException ex) {
                showErrorDialog("Please enter a numeric value for ID.");
                idField.setStyle("-fx-border-color: red;");
                valid = false;
            }

            try {
                payrate = functionsUtils.getValidatedFloatInput(payrateField.getText());
            } catch (IllegalArgumentException ex) {
                showErrorDialog("Please enter a numeric value for pay rate.");
                payrateField.setStyle("-fx-border-color: red;");
                valid = false;
            }

            if (valid) {
                String name = nameField.getText();
                String department = departmentField.getText();
                String course = courseField.getText();
                String time = timeField.getText();

                // Get selected days
                List<String> selectedDays = new ArrayList<>();
                for (CheckBox checkBox : dayCheckBoxes) {
                    if (checkBox.isSelected()) {
                        selectedDays.add(checkBox.getText());
                    }
                }

                String days = String.join("/", selectedDays);

                // Create a new teacher object
                teacher newTeacher = new teacher(id, name, department, course, days, time, payrate, 0, 0, 0, 0,0);

                // Append to CSV (this function will calculate hours internally)
                functionsUtils.appendTeacherToCSV(newTeacher);

                // Show confirmation dialog
                showConfirmationDialog("Teacher added successfully!", "Teacher " + name + " has been added.");

                // Clear the input fields
                idField.clear();
                nameField.clear();
                departmentField.clear();
                courseField.clear();
                for (CheckBox checkBox : dayCheckBoxes) {
                    checkBox.setSelected(false);
                }
                timeField.clear();
                payrateField.clear();
            }
        });

        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(e -> primaryStage.setScene(loadmanagementsystem.getMainMenuScene(primaryStage)));

        VBox layout = new VBox(10, idLabel, idField, nameLabel, nameField, departmentLabel, departmentField, courseLabel, courseField, daysLabel, daysBox, timeLabel, timeField, payrateLabel, payrateField, addButton, backButton);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Label madeByLabel = new Label("Made by Ahmed Tariq - 24822542");
        madeByLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        return new Scene(layout, 1050, 650);
    }

    private static void showErrorDialog(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static void showConfirmationDialog(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
// Commit 3
// Commit 17
