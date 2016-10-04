package sample;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;

/**
 * Tahle třída je užitečná asi jako sáňky v létě, nu což...
 *
 * Tahle třída zpřehledňuje celkem raznatním zpúsobem kod, představte si, že ke každému try/catch by se museolo
 * rozepisovat přesně to, co je v každé funkci, nikomu by se to nechtělo dělat, a navíc je tu zásada DRY =>
 * "Dont Repeat Yourself"
 *
 * popisky tu snád být nemusí, každému dojde o co jde
 */
class Dialogy {

    // zavedeni tridy Alert
    private final Alert alert = new Alert(Alert.AlertType.ERROR);

    // funkce na tisk alertu, nechtelo se mi psat dalsi 3 radky kodu ke kazdemu try/catch, navic tohle zprehlednuje kod
    public void Error(String nadpis, String popis) {
        alert.setTitle(nadpis);                // nastaveni nadpisu
        alert.setHeaderText(popis);                // nastaveni popisu chyby
        alert.setContentText(null);                   // nastavi prazdnou hodnotu pro ContentText
        alert.showAndWait();                        // zobrazeni dialogu s nasim textem
    }

    // funkce na tisk alertu, nechtelo se mi psat dalsi 3 radky kodu ke kazdemu try/catch, navic tohle zprehlednuje kod
    public void Info(String nadpis, String popis) {
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setTitle(nadpis);                     // nastaveni nadpisu
        alert.setHeaderText(popis);                 // nastaveni popisu chyby
        alert.setContentText(null);                 // nastavi prazdnou hodnotu pro ContentText
        alert.showAndWait();                        // zobrazeni dialogu s nasim textem
    }

    /* dalsi verze informacniho dialogu */
    public void Dialog(String popis, String obsah) {
        alert.setAlertType(Alert.AlertType.NONE);
        alert.setHeaderText(popis);
        alert.setContentText(obsah);
        alert.showAndWait();
    }

    // funkce na potvrzovaci dialog
    public Optional<ButtonType> Confirm(String nadpis, String dotaz) {
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.setTitle(nadpis);
        alert.setHeaderText(dotaz);
        alert.setContentText(null);
        //alert.getButtonTypes().add(new ButtonType("Ani za kokot vole"));
        return alert.showAndWait();
    }

    public void ErrorMessage(String nadpis, String popis, String vyjimka, Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(nadpis);
        alert.setHeaderText(popis);
        alert.setContentText(vyjimka);
        alert.initModality(Modality.WINDOW_MODAL);
        // Create expandable Exception.
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        ex.printStackTrace(printWriter);
        String exceptionText = stringWriter.toString();
        /**
         * automatické přizpůsobení okna
         */
        alert.initModality(Modality.WINDOW_MODAL);
        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(textArea, 0, 0);

        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }

    public void Message(Alert.AlertType alertType, String nadpis, String popis, String text) {
        /**
         * základní nstavení třídy Alert
         */
        Alert alert = new Alert(alertType);
        alert.setTitle(nadpis);
        alert.setHeaderText(popis);
        /**
         * automatické přizpůsobení okna
         */
        alert.initModality(Modality.WINDOW_MODAL);
        /**
         * zavedení TextArei
         */
        TextArea textArea = new TextArea(text);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        /**
         * základní nastavení textArei a GridPanu
         */
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);
        /**
         * Dosazení TextArei do GridPane
         */
        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(textArea, 0, 0);
        /**
         * Umožnění "rozathovací" nabídky
         */
        alert.getDialogPane().setExpandableContent(expContent);
        alert.showAndWait();
    }

    /**
     * Funkce pro vytvoření výberového dialogu
     * - volání se provadí String volba = dialogy.Vyber(---);
     * @param nadpis - String, obsahující celkový nadpis okna
     * @param popis - String, podrobnější popis výberu
     * @param choices List, výběr možností
     * @return - String, vrácení vybrané volby
     */
    public String Vyber(String nadpis, String popis, List<String> choices) {
        ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
        dialog.setTitle(nadpis);
        dialog.setContentText(popis);
        /**
         * Tradiční metoda získaní vybrané možnosti z  Listu
         */
        Optional<String> result = dialog.showAndWait();
        return result.get();
    }

    /**
     * rozpracovaný InputDialog, update v další verzi API
     * @return
     */
    public String InputDialog(String nadpis, String popis) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(nadpis);
        dialog.setContentText(popis);
        Optional<String> result = dialog.showAndWait();
        return result.get();
    }
}
