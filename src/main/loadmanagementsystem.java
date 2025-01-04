package main;

import utils.functionsUtils;
import fx.AddTeacherScreen;
import fx.DeleteTeacherScreen;
import fx.DisplayTeachersScreen;
import fx.LoginScreen;
import fx.SalaryCalTeacherScreen;
import fx.SearchTeacherScreen;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class loadmanagementsystem extends Application {
    public static ArrayList<teacher> teacherList = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Application is initializing...");
        teacherList.addAll(functionsUtils.readTeachersFromCSV("professors_timetable.csv"));
        System.out.println("Loading teacher data from file...");
        System.out.println("Teacher data loaded successfully.\n");
        System.out.println("Application has started...\n");
        System.out.println("Application Logs:\n");

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Teacher Management System");

        // Create the loading screen
        Label titleLabel = new Label("Teacher Management System");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label loadingLabel = new Label("Loading system...");
        ProgressBar loadingBar = new ProgressBar();
        Label madeByLabel = new Label("Made by Ahmed Tariq - 24822542");
        madeByLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        VBox loadingLayout = new VBox(10, titleLabel, loadingLabel, loadingBar);
        loadingLayout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-spacing: 15;");

        BorderPane loadingPane = new BorderPane();
        loadingPane.setCenter(loadingLayout);
        loadingPane.setBottom(madeByLabel);
        BorderPane.setAlignment(madeByLabel, Pos.BOTTOM_RIGHT);

        Scene loadingScene = new Scene(loadingPane, 1050, 650);

        primaryStage.setScene(loadingScene);
        primaryStage.show();

        // Simulate loading with a pause
        PauseTransition pause = new PauseTransition(Duration.seconds(10));
        pause.setOnFinished(event -> primaryStage.setScene(LoginScreen.getScene(primaryStage)));
        pause.play();
    }

    public static Scene getMainMenuScene(Stage primaryStage) {
        Label titleLabel = new Label("Teacher Management System");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button addTeacherButton = new Button("Add Teacher");
        Button searchTeacherButton = new Button("Search Teacher");
        Button deleteTeacherButton = new Button("Delete Teacher");
        Button displayTeachersButton = new Button("Display All Teachers");
        Button salaryTeachersButton = new Button("Calculate Monthly Teachers Pay");
        Button exitButton = new Button("Exit");

        addTeacherButton.setOnAction(e -> primaryStage.setScene(AddTeacherScreen.getScene(primaryStage)));
        searchTeacherButton.setOnAction(e -> primaryStage.setScene(SearchTeacherScreen.getScene(primaryStage)));
        deleteTeacherButton.setOnAction(e -> primaryStage.setScene(DeleteTeacherScreen.getScene(primaryStage)));
        displayTeachersButton.setOnAction(e -> primaryStage.setScene(DisplayTeachersScreen.getScene(primaryStage)));
        salaryTeachersButton.setOnAction(e -> primaryStage.setScene(SalaryCalTeacherScreen.getScene(primaryStage)));

        exitButton.setOnAction(e -> primaryStage.close());

        VBox layout = new VBox(10, titleLabel, addTeacherButton, searchTeacherButton, deleteTeacherButton, displayTeachersButton, salaryTeachersButton, exitButton);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-spacing: 15;");

        BorderPane mainMenuPane = new BorderPane();
        mainMenuPane.setCenter(layout);
        Label madeByLabel = new Label("Made by Ahmed Tariq - 24822542");
        madeByLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        mainMenuPane.setBottom(madeByLabel);
        BorderPane.setAlignment(madeByLabel, Pos.BOTTOM_RIGHT);

        return new Scene(mainMenuPane, 1050, 650);
    }
}// Commit 10
// Commit 24
