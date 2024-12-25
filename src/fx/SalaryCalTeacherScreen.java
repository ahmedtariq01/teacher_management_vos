package fx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.loadmanagementsystem;
import main.teacher;
import utils.functionsUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalaryCalTeacherScreen {
    @SuppressWarnings("unchecked")
    public static Scene getScene(Stage primaryStage) {
        TableView<teacher> table = new TableView<>();
        table.setEditable(false);

        Map<Integer, Float> extraHoursMap = new HashMap<>();

        // Define columns for the table view with explicit widths
        TableColumn<teacher, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setPrefWidth(70);

        TableColumn<teacher, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setPrefWidth(120);

        TableColumn<teacher, Float> payrateColumn = new TableColumn<>("Payrate");
        payrateColumn.setCellValueFactory(new PropertyValueFactory<>("payrate"));
        payrateColumn.setPrefWidth(100);

        TableColumn<teacher, Float> totalHoursMonthlyColumn = new TableColumn<>("Total Hours/Monthly");
        totalHoursMonthlyColumn.setCellValueFactory(new PropertyValueFactory<>("totalHoursPerMonth"));
        totalHoursMonthlyColumn.setPrefWidth(180);

        TableColumn<teacher, Float> grossSalaryColumn = new TableColumn<>("Gross Salary");
        grossSalaryColumn.setCellValueFactory(new PropertyValueFactory<>("grossSalary"));
        grossSalaryColumn.setPrefWidth(150);

        TableColumn<teacher, Float> netSalaryColumn = new TableColumn<>("Net Salary");
        netSalaryColumn.setCellValueFactory(new PropertyValueFactory<>("netSalary"));
        netSalaryColumn.setPrefWidth(150);

        // Extra Hours column functionality
        TableColumn<teacher, Void> extraHoursColumn = new TableColumn<>("Extra Hours");
        extraHoursColumn.setCellFactory(column -> new TableCell<>() {
            private final Button button = new Button("Add Hours");

            {
                button.setOnAction(event -> {
                    teacher teacherItem = (teacher) getTableRow().getItem();
                    if (teacherItem != null) {
                        TextInputDialog dialog = new TextInputDialog();
                        dialog.setTitle("Add Extra Hours");
                        dialog.setHeaderText("Enter extra hours worked:");
                        dialog.setContentText("Hours:");

                        dialog.showAndWait().ifPresent(hoursStr -> {
                            try {
                                float extraHours = Float.parseFloat(hoursStr);
                                extraHoursMap.put(teacherItem.getId(),
                                        extraHoursMap.getOrDefault(teacherItem.getId(), 0f) + extraHours);

                                // Update CSV values for total hours, gross and net salary
                                functionsUtils.updateTeacherExtraHoursInCSV(teacherItem, extraHoursMap);  // Corrected method signature

                                // Reload updated teacher list from CSV and refresh table
                                refreshTable(table);

                            } catch (NumberFormatException e) {
                                showAlert("Invalid Input for Extra Hours", "Please enter a valid number for extra hours.");
                            }
                        });
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        });
        extraHoursColumn.setPrefWidth(120);

        // Bonus column functionality
        TableColumn<teacher, Void> bonusColumn = new TableColumn<>("Bonus");
        bonusColumn.setCellFactory(column -> new TableCell<>() {
            private final Button button = new Button("Add Bonus");

            {
                button.setOnAction(event -> {
                    teacher teacherItem = (teacher) getTableRow().getItem();
                    if (teacherItem != null) {
                        TextInputDialog dialog = new TextInputDialog();
                        dialog.setTitle("Add Bonus");
                        dialog.setHeaderText("Enter bonus percentage of gross salary:");
                        dialog.setContentText("Bonus %:");

                        dialog.showAndWait().ifPresent(percentageStr -> {
                            try {
                                float percentage = Float.parseFloat(percentageStr);

                                // Update CSV values for gross and net salary with bonus
                                functionsUtils.updateTeacherBonusInCSV(teacherItem, percentage, extraHoursMap);  // Corrected method signature

                                // Reload updated teacher list from CSV and refresh table
                                refreshTable(table);

                            } catch (NumberFormatException e) {
                                showAlert("Invalid Input for Bonus", "Please enter a valid percentage for bonus.");
                            }
                        });
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        });
        bonusColumn.setPrefWidth(120);

        // Add columns to the table (all at once)
        table.getColumns().addAll(
            idColumn, nameColumn, payrateColumn, totalHoursMonthlyColumn,
            grossSalaryColumn, netSalaryColumn, extraHoursColumn, bonusColumn
        );

        // Load data from CSV file
        refreshTable(table);

        // Back button to navigate to main menu
        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(e -> primaryStage.setScene(loadmanagementsystem.getMainMenuScene(primaryStage)));

        // Layout setup
        VBox layout = new VBox(10, table, backButton);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        return new Scene(layout, 1050, 700);
    }

    private static void refreshTable(TableView<teacher> table) {
        // Reload data from CSV
        List<teacher> teacherList = functionsUtils.displayTeachersFromCSV("professors_timetable.csv");
        ObservableList<teacher> observableTeacherList = FXCollections.observableArrayList(teacherList);
        table.setItems(observableTeacherList);
    }

    private static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
// Commit 7
