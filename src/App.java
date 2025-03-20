import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

public class App extends Application {

    // {1, 2, 3},
    // {5, 6, 0},
    // {7, 8, 4}

    // {2, 5, 3},
    // {0, 6, 8},
    // {1, 4, 7}

    // {8, 4, 5},
    // {0, 2, 6},
    // {3, 7, 1}

    // {5, 1, 2},
    // {7, 6, 3},
    // {8, 4, 0}

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene.fxml"));
            Parent root = loader.load();

            Controller controller = loader.getController();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);

            primaryStage.setTitle("Puzzle 8");

            controller.scene = scene;
            controller.primaryStage = primaryStage;

            primaryStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Skill issue");
        }
    }
}
