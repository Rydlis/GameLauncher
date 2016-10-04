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

    protected static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{

        /**
         * uložení objektu Stage do "static" proměné, aby bylo možné k ní přistupovat odkukoliv
         */
        Main.stage = primaryStage;
        System.out.println("načítání layoutu okna");
        System.out.println(getClass().getResource("views/Login_Screen.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("views/Login_Screen.fxml"));
        primaryStage.setTitle("Game Launcher");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();

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

        /**
         * už otevři oči, je to za tebou
         */
        primaryStage.show();

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
