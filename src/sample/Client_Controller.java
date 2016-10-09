package sample;

/* WOW O.O */
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Client_Controller extends Application implements Initializable{

    /*
     * Zavedení potřebných komponent GUI
     *
     * prostě jsem byl takhle naučený, co už
     */
    @FXML
    private ImageView img_Avatar;
    @FXML
    private AnchorPane main_frame;
    @FXML
    private Button btn_Account;
    @FXML
    private Button btn_Settings;
    @FXML
    private Button btn_About;
    @FXML
    private Button btn_chat;
    @FXML
    private Label label_Nick;
    @FXML
    private GridPane gridPane_Statistics;
    @FXML
    private ListView feed_View;
    @FXML
    private Label label_XP;
    @FXML
    private Label label_Level;
    @FXML
    private Label label_Kills;
    @FXML
    private Label label_Deaths;
    @FXML
    private Label label_Money;
    @FXML
    private Label label_PlayedHours;
    @FXML
    private Label label_LastPlayed;
    @FXML
    private TextArea txtArea_about;

    /*
     * Zavedení potřebných tříd
     *
     */
    private FadeTransition fadeTransition = new FadeTransition();
    private Dialogy dialogs = new Dialogy();
    private ServerCommunicationMechanics serverCommunicationMechanics = new ServerCommunicationMechanics();
    private ChatClient_Controller chatClient_controller = new ChatClient_Controller();

    /*
     * zavedení globálních proměnných
     */
    private String player_nick;
    private Boolean isStatisticsVisible = false;
    private Boolean WebDatabase = false;
    private Boolean isChacheFile = false;

    /**
     * Funkce které běží při samotném vytváření dané třídy
     * probíhá zde spoustu nastavení a zavádění užitečných listenerů a event handlerů
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("načítání layoutu okna");
        Parent root = FXMLLoader.load(getClass().getResource("views/Main_view.fxml"));
        primaryStage.setTitle("Game Launcher");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        /*
         * handler, který se zavolá při pokusu ukončit aplikaci, stejně jako v Main, tak naco to popisovat dvakrát
         *
         * -navíc, asi to budu tady celé dělat sám *sad face* :(
         */
        primaryStage.setOnCloseRequest(event -> {
            ButtonType type = dialogs.Confirm("Log out", "Do you want to log out and get a real life?").get();
            /**
             * Such Code, Much WOW
             * a ne, nejde to switchem
             */
            if (type == ButtonType.OK){
                Platform.exit();
            } if (type == ButtonType.CANCEL){
                event.consume();
            } else event.consume();
        });
    }

    /*
     * funkce které se spouší až po vytvoření třídy, jako třeba nastavení a dosazení textur -> nechce se mi to dělat přes css
     * CSS pořádně neumí ani prohlížeče, natož tak JavaFX
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        img_Avatar.setImage(new Image(getClass().getResource("resources/avatar.png").toExternalForm()));
        /*
         * Základní inicializace GUIčka, jako setování nicku, gettování statistik
         */
        label_Nick.setText(Login_Controller.Player_Nick_Vole);
    }

    /**
     * Základní funkce herního klienta, spíše GUI
     *
     *
     * Tahle funkce by šla vyřešit mnohem, ale mnohem elegantněji, bohužel, mě nic tak pozdě večer nenapadlo
     * @param event
     */
    public void handleShowStatistics(ActionEvent event){
        if (!isStatisticsVisible){
            isStatisticsVisible = !isStatisticsVisible;
            System.out.println(isStatisticsVisible);
            // zneviditelnění feed View
            feed_View.setOpacity(0);
            feed_View.setDisable(true);

            // nastavení všech Labelů, FML
            System.out.println("zavolána funkce v controlleru a nick: " + player_nick);
            ArrayList<Integer> results = serverCommunicationMechanics.getStatistics(player_nick, WebDatabase);
            label_XP.setText(String.valueOf(results.get(0)));
            label_Level.setText(String.valueOf(results.get(1)));
            label_Kills.setText(String.valueOf(results.get(2)));
            label_Deaths.setText(String.valueOf(results.get(3)));
            label_Money.setText(String.valueOf(results.get(4)));
            label_PlayedHours.setText(String.valueOf(results.get(5)));
            //label_LastPlayed.setText(String.valueOf(results.get(5)));

            // zapnutí Statistik
            fadeIn(gridPane_Statistics);

        } if (isStatisticsVisible){
            isStatisticsVisible = !isStatisticsVisible;
            // zneviditelnění statistik
            gridPane_Statistics.setDisable(true);
            gridPane_Statistics.setOpacity(0);

            // zviditelnění feed view
            feed_View.setDisable(false);
            feed_View.setOpacity(100);
        }

    }

    /**
     * Handler pro ukázání statistik hráče
     * @param event
     */
    public void handleButtonAbout(ActionEvent event){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(getClass().getResource("resources/about.txt").getFile()));            // zavedení BufferedReaderu a připsání souboru
            String text = "";                                                                                   // zavedení pomocné proměnné text
            for(String line; (line = bufferedReader.readLine()) != null; ){                                     // začátek cyklu na čtení souboru
                text += line + "\n";
            }
            bufferedReader.close();// uazvření BufferedReaderu
            txtArea_about.setDisable(false);
            txtArea_about.setOpacity(100);
            txtArea_about.setText(text);       // zavolání funkce Message ze třídy Dialogy a předání text, který se přečetl ze souboru
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handler pro otevření chatovacího okna, pošle se pár stringů jako nick
     * a zbytek si obstarává ChatClient_Controller sám
     *
     * TODO dodělat posílání nicku
     * @param event
     */
    public void handleButtonChat(ActionEvent event){
        try {
            chatClient_controller.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Animace, WOWOWOWOWOWOW
     * @param node
     */
    private void fadeIn(Node node){
        fadeTransition.setNode(node);
        fadeTransition.setDuration(new Duration(500));
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(100);
        node.setDisable(false);
        fadeTransition.play();
    }

    /**
     * Gettery a Settery
     * @return
     */

    public Boolean getWebDatabase() {
        return WebDatabase;
    }

    public void setWebDatabase(Boolean webDatabase) {
        WebDatabase = webDatabase;
    }

    void setNick(String nick) {
        player_nick = nick;
        System.out.println(nick + " Ve třídě client_controller");
    }
}
