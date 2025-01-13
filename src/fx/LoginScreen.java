package fx;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.loadmanagementsystem;

public class LoginScreen {
    public static Scene getScene(Stage primaryStage) {
        Label emailLabel = new Label("Enter your email:");
        TextField emailField = new TextField();
        emailField.setMaxWidth(300);

        Label passwordLabel = new Label("Enter your password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setMaxWidth(300);

        TextField passwordTextField = new TextField();
        passwordTextField.setMaxWidth(300);
        passwordTextField.setManaged(false);
        passwordTextField.setVisible(false);

        CheckBox showPasswordCheckBox = new CheckBox("Show Password");
        showPasswordCheckBox.setOnAction(e -> {
            if (showPasswordCheckBox.isSelected()) {
                passwordTextField.setText(passwordField.getText());
                passwordTextField.setManaged(true);
                passwordTextField.setVisible(true);
                passwordField.setManaged(false);
                passwordField.setVisible(false);
            } else {
                passwordField.setText(passwordTextField.getText());
                passwordField.setManaged(true);
                passwordField.setVisible(true);
                passwordTextField.setManaged(false);
                passwordTextField.setVisible(false);
            }
        });

        Button loginButton = new Button("Login");
        Button forgetPasswordButton = new Button("Forget Password");

        loginButton.setOnAction(e -> {
            String email = emailField.getText();
            String password = showPasswordCheckBox.isSelected() ? passwordTextField.getText() : passwordField.getText();

            if (authenticate(email, password)) {
                primaryStage.setScene(loadmanagementsystem.getMainMenuScene(primaryStage));
            } else {
                showErrorDialog("Incorrect email or password.");
                emailField.clear();
                passwordField.clear();
                passwordTextField.clear();
            }
        });

        forgetPasswordButton.setOnAction(e -> showInfoDialog("Contact the administrator at ahmed.tariq24@my.northampton.ac.uk"));

        VBox centerLayout = new VBox(10, emailLabel, emailField, passwordLabel, passwordField, passwordTextField, showPasswordCheckBox, loginButton, forgetPasswordButton);
        centerLayout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Label madeByLabel = new Label("Made by Ahmed Tariq - 24822542");
        madeByLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        BorderPane layout = new BorderPane();
        layout.setCenter(centerLayout);
        layout.setBottom(madeByLabel);
        BorderPane.setAlignment(madeByLabel, javafx.geometry.Pos.BOTTOM_RIGHT);
        BorderPane.setMargin(madeByLabel, new javafx.geometry.Insets(10));

        return new Scene(layout, 1050, 650);
    }

    private static boolean authenticate(String email, String password) {
        // Replace with actual authentication logic
        return "admin@example.com".equals(email) && "password123".equals(password);
    }

    private static void showErrorDialog(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static void showInfoDialog(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}// Commit 6
// Commit 20
// Commit 34
