/**
 * Tato tridaslouzi k zobrazovani SplashScreenu pri nacitani programu.
 * TODO vymyslet nacitani vzhledu z fxml, udelat jako nove api
 */

package sample;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class SplashScreen extends Preloader{

    private Stage splashStage = new Stage();

    @Override
    public void start(Stage stage) throws Exception {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("views/splash.fxml"));
                splashStage.initStyle(StageStyle.UNDECORATED);
                splashStage.setScene(new Scene(root));
                splashStage.show();
            } catch (IOException e){
                System.out.println("Nemohl byt nacten SplashScreen");
            } catch (IllegalStateException e){
                e.printStackTrace();
            }
        }

    public void close(){
        splashStage.close();
    }
}
