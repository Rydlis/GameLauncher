/**
 * Tato tridaslouzi k zobrazovani SplashScreenu pri nacitani programu.
 * TODO vymyslet nacitani vzhledu z fxml, udelat jako nove api
 */

package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashScreen extends Application implements Initializable{

    @FXML
    private ImageView splashImageView;
    @FXML
    private ProgressBar progressBar;

    private Stage splashStage = new Stage();

    @Override
    public void start(Stage stage) throws Exception {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("views/splash.fxml"));
                splashStage.initStyle(StageStyle.UNDECORATED);
                splashStage.setScene(new Scene(root));
                splashStage.show();
            } catch (IOException | IllegalStateException e){
                e.printStackTrace();
            }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            splashImageView.setImage(new Image(getClass().getResource("resources/textures/splash_screen_image.png").toExternalForm()));
            progressBar.setMinSize(0.0,1.0);
            progressBar.setProgress(0.1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setProgressBar(Double value){
        try {
            System.out.println(value);
            progressBar.setProgress(value);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void close(){
        splashStage.close();
    }
}
