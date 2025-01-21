import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

public class App extends Application {

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

            primaryStage.setMinWidth(572);
            primaryStage.setMinHeight(340);

            primaryStage.setWidth(572);
            primaryStage.setHeight(340);

            primaryStage.setMaxWidth(1920);
            primaryStage.setMaxHeight(1080);

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
