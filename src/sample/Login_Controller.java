package sample;

/* WOW O.O */
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller pro login screen, popis funkcí je u každé jednotlivé funkce,
 * pardon že tu není velký popis, nechtělo se mi asi nic psát
 *
 */
public class Login_Controller implements Initializable{

    /*
     * FXML prvky aplikace, zaváděné při kompilaci, holt jsem byl tak naučený
     */
    @FXML
    private MediaView Video_background;
    @FXML
    private Button btn_Login;
    @FXML
    private TextField txtField_nick;
    @FXML
    private TextField txtField_password;
    @FXML
    private CheckMenuItem chk_Database;
    @FXML
    private ImageView imgView_wheel1;
    @FXML
    private ImageView imgView_wheel2;
    @FXML
    private CheckMenuItem chkButton_PlayVideo;
    @FXML
    private CheckMenuItem chkButton_PlaySound;
    @FXML
    private AnchorPane anchPane_main;

    /*
     * Zavedení všech potřebných tříd
     */
    private ServerCommunicationMechanics serverCommunicationMechanics = new ServerCommunicationMechanics();
    private Client_Controller client_controller = new Client_Controller();
    private Dialogy dialogs = new Dialogy();
    private RotateTransition rotateTransition1 = new RotateTransition(new Duration(500));
    private RotateTransition rotateTransition2 = new RotateTransition(new Duration(500));
    private ParallelTransition parallelTransition;

    /*
     * Zavedení globálních proměnných
     */
    private Boolean isLogged;
    private Boolean isPlayingLoadingAnimation = true;
    private Boolean isPlayingSound = false;
    private Boolean isPlayingVideo = false;
    private Boolean playVideo;
    private Boolean playSound;
    //jenom na testování
    protected static String Player_Nick_Vole;

    /**
     * Funkce při zavedení třídy
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /* nastavení pozadí tlačítka na login */
        btn_Login.setStyle("-fx-background-image: url('/sample/resources/textures/btn_Login.jpg');" +
                "-fx-background-color: transparent;");

        /* nudné zavedení obrázků do framu na loading animaci */
        imgView_wheel1.setImage(new Image(getClass().getResource("resources/textures/loading_wheel.png").toExternalForm()));
        imgView_wheel1.setDisable(true);
        imgView_wheel1.setOpacity(0);
        imgView_wheel2.setImage(new Image(getClass().getResource("resources/textures/loading_wheel.png").toExternalForm()));
        imgView_wheel2.setDisable(true);
        imgView_wheel2.setOpacity(0);

        /* zavolaní funkce pro spuštění videa v pozadí */
        playVideo = chkButton_PlayVideo.isSelected();
        playSound = chkButton_PlaySound.isSelected();
        videoBackground();
        musicBackground();
    }

    /**
     * Ovládaní loggování, zde se vyhodnocuje zda-li je uživatel registrovaný a taktéž se zde ovládá GUI
     * @param nick - zadaný nick uživatele získaný z funkce @handleLoginButton
     * @param password - zadané heslo získané z funkce @handleLoginButton
     */
    private void loginProcces(String nick, String password){
        // využití pro testovací účely
        if (nick.equals("Rydlis")){
            try {
                musicBackground();
                client_controller.start(Main.stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // konec testování, pokračuj ve čtení
            isLogged = serverCommunicationMechanics.loginUser(nick, password, chk_Database.isSelected()); // funkce vrací true/falce o tom, zda li je vůbec uživatel v databázi
            if (isLogged){
                try {
                    musicBackground();
                    client_controller.start(Main.stage);          // pokud je vše úspěšné a uživatel ani já nic nezesrali, přepně se okno do klienta
                }  catch (Exception e) {
                    e.printStackTrace();                          // stát se může vše, a v Javě dvakrát pravděpodobněji
                }
            } else {
                dialogs.Error("Ooops!","Bad nick or password, try again");  // uživatel jak se zdá zadal špatný nick nebo heslo
            }
        }
    }

    /**
     * Handlery pro tlačítka  login obrazovce
     * @param event
     */
    public void handleLoginButton(ActionEvent event){
        loadingAnimation();
        setPlayer_Nick_Vole(txtField_nick.getText());
        loginProcces(txtField_nick.getText(), txtField_password.getText());
    }

    /* -------------------------- Just GUI Thing -------------------------- */

    /**
     * Vnitřní metody pro Login screen, pokud zde něco není zkus i jiné Controlelry, tu není vše!
     *
     * konkrétně k této funkci -> funkce jako první zavede Media soubor, čili video, kterí je
     * uložené v resource složce přímo v projektu, což je fakt mega užitečné
     * dále se zavede MediaPlayer s tímto souborem, pak se MediaPlayer dosadí do MediaView, což je celkové pozadá
     * login screenu, a pak se akorát spustí
     * !! funkce je volána z funkce initialize, a neměla by být volána z nikada jinama
     */
    private void videoBackground(){
        if (playVideo) {
            Media video = new Media(getClass().getResource("resources/textures/part1.mp4").toExternalForm());
            MediaPlayer player = new MediaPlayer(video);
            Video_background.setMediaPlayer(player);
            player.play();
        } else {
            // TODO prostě obrázek z videa, LOL
            System.out.println("ať to neřve že není video");
        }
    }

    /**
     * Obdoba funkce playVideoBackground(), akorát že pouští *intro mňůzik*
     */
    private void musicBackground(){
        Media song = new Media(getClass().getResource("resources/sounds/intro.mp3").toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(song);
        if (playSound && isPlayingSound){
            System.out.println("Hudba hraje");
            mediaPlayer.play();
            isPlayingSound = true;
        } else if (isPlayingSound){
            System.out.println("hudba nehraje, došlo k přehození View");
            mediaPlayer.stop();
            isPlayingSound = false;
        } else System.out.println("Tlačítko není aktivováno a hudba nehraje, nebo se něco zase posralo, a co jako");
    }

    /**
     * Animace, yay!!!
     *
     * Ale niní k popisu, prvně se zviditelní prvky animace, pak se nastaví atributy prvního kolečka a následně druhého,
     * následně se inicializuje ParalelAnimation Class, což slouží k souběžnému spuštění všech animací do konstruktoru
     * poslaných, následně se spustí a měla by se kolečka začít točit
     *
     * jakmile je přihlašování u konce, animace se díky první větvy IF vypne
     *
     * Easy, ne?
     *
     * TODO ty vole, kdyby to fungovalo tak lehce, jsem zase opět něco pojebkal
     */
    private void loadingAnimation(){
        if (isPlayingLoadingAnimation) {
            /* nastavení viditelnosti */
            imgView_wheel1.setDisable(false);
            imgView_wheel1.setOpacity(100);
            imgView_wheel2.setDisable(false);
            imgView_wheel2.setOpacity(100);

            /* nastavení prvniho kolečka */
            rotateTransition1.setNode(imgView_wheel1);
            rotateTransition1.setFromAngle(imgView_wheel1.getRotate());
            rotateTransition1.setToAngle(imgView_wheel1.getRotate() + 360);
            rotateTransition1.setByAngle(imgView_wheel1.getRotate() + 180);

            /* nastavení druheho kolecka */
            rotateTransition2.setNode(imgView_wheel2);
            rotateTransition2.setFromAngle(imgView_wheel2.getRotate());
            rotateTransition2.setToAngle(imgView_wheel2.getRotate() - 360);
            rotateTransition2.setByAngle(imgView_wheel2.getRotate() - 180);

            /* inicializace animací */
            parallelTransition = new ParallelTransition();
            parallelTransition.getChildren().addAll(rotateTransition1, rotateTransition2);

            /* spusteni animace */
            parallelTransition.play();

            /* nastavení booleanu z false na true */
            isPlayingLoadingAnimation = !isPlayingLoadingAnimation;
        } else {
            /* nastavení neviditelnosti koleček */
            imgView_wheel1.setDisable(true);
            imgView_wheel1.setOpacity(0);
            imgView_wheel2.setDisable(true);
            imgView_wheel2.setOpacity(0);

            /* zastaveni animace */
            parallelTransition.stop();
            isPlayingLoadingAnimation = !isPlayingLoadingAnimation;
        }
    }

    public String getPlayer_Nick_Vole() {
        return Player_Nick_Vole;
    }

    public void setPlayer_Nick_Vole(String player_Nick_Vole) {
        Player_Nick_Vole = player_Nick_Vole;
    }
}
