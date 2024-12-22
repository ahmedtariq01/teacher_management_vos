package fx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.loadmanagementsystem;
import main.teacher;
import utils.functionsUtils;

import java.util.List;

public class DeleteTeacherScreen {
    @SuppressWarnings("unchecked")
    public static Scene getScene(Stage primaryStage) {
        Label idLabel = new Label("Enter teacher ID (or -1 to skip):");
        TextField idField = new TextField();
        idField.setMaxWidth(300);

        Label nameLabel = new Label("Enter teacher name (or press Enter to skip):");
        TextField nameField = new TextField();
        nameField.setMaxWidth(300);

        Button searchButton = new Button("Search");
        Button backButton = new Button("Back to Main Menu");

        Label foundLabel = new Label();
        foundLabel.setVisible(false);

        TableView<teacher> tableView = new TableView<>();
        tableView.setVisible(false);

        TableColumn<teacher, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setPrefWidth(70); // Set preferred width for ID column

        TableColumn<teacher, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setPrefWidth(120); // Set preferred width for Name column

        TableColumn<teacher, String> departmentColumn = new TableColumn<>("Department");
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        departmentColumn.setPrefWidth(150); // Set preferred width for Department column

        TableColumn<teacher, String> courseColumn = new TableColumn<>("Course");
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("course"));
        courseColumn.setPrefWidth(120); // Set preferred width for Course column


        TableColumn<teacher, Void> actionColumn = new TableColumn<>("Action");
        Callback<TableColumn<teacher, Void>, TableCell<teacher, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<teacher, Void> call(final TableColumn<teacher, Void> param) {
                final TableCell<teacher, Void> cell = new TableCell<>() {
                    private final Button deleteButton = new Button("Delete");

                    {
                        deleteButton.setOnAction(e -> {
                            teacher data = getTableView().getItems().get(getIndex());
                            showConfirmationDialog(data, primaryStage);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(deleteButton);
                        }
                    }
                };
                return cell;
            }
        };
        actionColumn.setCellFactory(cellFactory);

        tableView.getColumns().addAll(idColumn, nameColumn, departmentColumn, courseColumn, actionColumn);

        searchButton.setOnAction(e -> {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();

            List<teacher> searchResults = functionsUtils.searchTeacherFromCSV(id, name);

            if (searchResults.isEmpty()) {
                showErrorDialog("No teacher found with ID: " + id + " or Name: " + name);
                tableView.setVisible(false);
                foundLabel.setVisible(false);
            } else {
                ObservableList<teacher> data = FXCollections.observableArrayList(searchResults);
                tableView.setItems(data);
                tableView.setVisible(true);

                // Display a message indicating the teacher's name and ID found
                if (!id.equals("-1")) {
                    foundLabel.setText("Showing the data for teacher ID: " + id);
                } else {
                    foundLabel.setText("Showing the data for teacher: " + name);
                }
                foundLabel.setVisible(true);
            }

            // Clear the input fields
            idField.clear();
            nameField.clear();
        });

        backButton.setOnAction(e -> primaryStage.setScene(loadmanagementsystem.getMainMenuScene(primaryStage)));

        VBox layout = new VBox(10, idLabel, idField, nameLabel, nameField, searchButton, foundLabel, tableView, backButton);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        return new Scene(layout, 1050, 650);
    }

    private static void showErrorDialog(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Search Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static void showConfirmationDialog(teacher data, Stage primaryStage) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Do you really want to delete teacher " + data.getName() + " with ID " + data.getId() + "?");

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        alert.getButtonTypes().setAll(yesButton, noButton);

        alert.showAndWait().ifPresent(type -> {
            if (type == yesButton) {
                functionsUtils.deleteTeacherByIdOrName(data.getId(), data.getName());
                primaryStage.setScene(getScene(primaryStage));
            }
        });
    }
}// Commit 4
