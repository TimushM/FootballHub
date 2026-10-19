package com.footballfan;

import com.footballfan.db.Database;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        Label title = new Label("Football Fan App");
        title.setStyle("-fx-font-size: 26px; -fx-font-weight: bold;");

        Label status = new Label(checkDatabase());

        VBox root = new VBox(15, title, status);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        stage.setTitle("Football Fan App");
        stage.setScene(new Scene(root, 800, 500));
        stage.show();
    }

    private String checkDatabase() {
        try {
            Integer one = Database.jdbc().queryForObject("SELECT 1", Integer.class);
            return (one != null && one == 1)
                    ? "✅ Подключение к базе данных успешно"
                    : "❌ Неожиданный ответ от БД";
        } catch (Exception e) {
            return "❌ Нет подключения к БД. Запущен ли docker compose up?";
        }
    }

    @Override
    public void stop() {
        Database.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
