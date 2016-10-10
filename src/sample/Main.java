package sample;

/* WOW O.O */
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class Main extends Application {

    // podívej, splashscreen, ať to taky trochu vypadá
    private SplashScreen splashScreen = new SplashScreen();

    // to abych měl přítup odkudkoliv
    protected static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{

        /**
         * uložení objektu Stage do "static" proměné, aby bylo možné k ní přistupovat odkukoliv
         */
        splashScreen.start(new Stage());
        Main.stage = primaryStage;
        System.out.println("načítání layoutu okna");
        //just progress bar
        splashScreen.setProgressBar(0.2);
        Parent root = FXMLLoader.load(getClass().getResource("views/Login_Screen.fxml"));
        primaryStage.setTitle("Game Launcher");
        Scene scene = new Scene(root);
        //just progress bar
        splashScreen.setProgressBar(0.4);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        //just progress bar
        splashScreen.setProgressBar(0.8);

        /**
         * hele, na tohle nejsem hrdý, a co nejdříve se té zrůdnosti zbavím, fakt se omlouvám komukoliv kdo to dostane do ruky
         *
         * k tomuto zvěrstvu jsem byl donucen po tom, co jsem zjistil že parametr setResizable(false) mi dojebal, vyloženě dojebal
         * celý design přihlašovací obrazovky klienta, prostě WTF, mělo se to zbavit jenom tlačítka nahře, né dokurvit úplně všchno na co
         * to mělo právo
         */
        primaryStage.setMinHeight(720);
        primaryStage.setMinWidth(1280);
        primaryStage.setMaxHeight(720);
        primaryStage.setMaxWidth(1280);

        //nastavení finále progress baru
        splashScreen.setProgressBar(1.0);

        /**
         * už otevři oči, je to za tebou
         */
        // zobrazení login screenu
        primaryStage.show();

        // a schování SplashScreenu
        splashScreen.close();


        /**
         * handler pro zachytávání žádosti o uzavření aplikace přes křížek
         * - při zachycení této žádosti se objeví potvrzovací dialogové okno
         * - stiskne li uživatel OK, aplikace se ukončí
         * - pokud uživatel zruší žádost, aplikace jede dál
         */
        primaryStage.setOnCloseRequest(event -> {
            ButtonType buttonType = new Dialogy().Confirm("Log out", "Do you want to log out and get a real life?").get();
            if (buttonType == ButtonType.OK) {
                Platform.exit();
            } else event.consume();
        });

    }


    public static void main(String[] args) {
        launch(args);
    }
}
