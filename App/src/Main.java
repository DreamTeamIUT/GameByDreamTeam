import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {


    public static Stage primaryStage;
    public static Parent root;

    @Override
    public void start(Stage primaryStage1) throws Exception {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        root = loader.load();
        Controller c = loader.getController();
        c.setStage(primaryStage1);

        primaryStage1.setTitle("PackageCreator");
        primaryStage1.setScene(new Scene(root, 600, 500, Color.BLACK));
        primaryStage1.show();

        primaryStage = primaryStage1;



    }


    public static void main(String[] args) {
        launch(args);
    }



}


