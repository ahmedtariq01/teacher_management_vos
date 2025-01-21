package fx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.loadmanagementsystem;
import main.teacher;
import utils.functionsUtils;

import java.util.List;

public class DisplayTeachersScreen {
    @SuppressWarnings("unchecked")
    public static Scene getScene(Stage primaryStage) {
        TableView<teacher> table = new TableView<>();
        table.setEditable(false);

        // Define columns for the table view with explicit widths
        TableColumn<teacher, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setPrefWidth(70);

        TableColumn<teacher, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setPrefWidth(120);

        TableColumn<teacher, String> departmentColumn = new TableColumn<>("Department");
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        departmentColumn.setPrefWidth(150);

        TableColumn<teacher, String> courseColumn = new TableColumn<>("Course");
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("course"));
        courseColumn.setPrefWidth(120);

        TableColumn<teacher, String> dayColumn = new TableColumn<>("Day");
        dayColumn.setCellValueFactory(new PropertyValueFactory<>("day"));
        dayColumn.setPrefWidth(80);

        TableColumn<teacher, String> timeColumn = new TableColumn<>("Time");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        timeColumn.setPrefWidth(100);

        TableColumn<teacher, Float> payrateColumn = new TableColumn<>("Payrate");
        payrateColumn.setCellValueFactory(new PropertyValueFactory<>("payrate"));
        payrateColumn.setPrefWidth(100);

        TableColumn<teacher, Float> hoursPerDayColumn = new TableColumn<>("Hours/Day");
        hoursPerDayColumn.setCellValueFactory(new PropertyValueFactory<>("hoursPerDay"));
        hoursPerDayColumn.setPrefWidth(120);

        TableColumn<teacher, Float> totalHoursWeekColumn = new TableColumn<>("Total Hours/Week");
        totalHoursWeekColumn.setCellValueFactory(new PropertyValueFactory<>("totalHoursPerWeek"));
        totalHoursWeekColumn.setPrefWidth(150);

        // Column for Total Hours/Month
        TableColumn<teacher, Float> totalHoursMonthColumn = new TableColumn<>("Total Hours/Month");
        totalHoursMonthColumn.setCellValueFactory(new PropertyValueFactory<>("totalHoursPerMonth"));
        totalHoursMonthColumn.setPrefWidth(150);

        // Column for Gross Salary
        TableColumn<teacher, Float> grossSalaryColumn = new TableColumn<>("Gross Salary");
        grossSalaryColumn.setCellValueFactory(new PropertyValueFactory<>("grossSalary"));
        grossSalaryColumn.setPrefWidth(150);

        // Column for Net Salary
        TableColumn<teacher, Float> netSalaryColumn = new TableColumn<>("Net Salary");
        netSalaryColumn.setCellValueFactory(new PropertyValueFactory<>("netSalary"));
        netSalaryColumn.setPrefWidth(150);

        // Add columns to the table
        table.getColumns().addAll(
            idColumn, nameColumn, departmentColumn, courseColumn,
            dayColumn, timeColumn, payrateColumn,
            hoursPerDayColumn, totalHoursWeekColumn, totalHoursMonthColumn, grossSalaryColumn, netSalaryColumn
        );

        // Load and display teacher data from CSV
        List<teacher> teacherList = functionsUtils.readTeachersFromCSV("professors_timetable.csv");
        ObservableList<teacher> observableTeacherList = FXCollections.observableArrayList(teacherList);
        table.setItems(observableTeacherList);

        // Back button to navigate to main menu
        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(e -> primaryStage.setScene(loadmanagementsystem.getMainMenuScene(primaryStage)));

        // Layout setup
        VBox layout = new VBox(10, table, backButton);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        return new Scene(layout, 1150, 650);
    }
}
// Commit 5
// Commit 19
// Commit 33
// Commit 47
