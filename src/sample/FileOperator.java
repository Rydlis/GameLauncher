package sample;

import com.sun.istack.internal.Nullable;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Třída pro správu otevírání souborů v klientovi, ve hře samotné se používá mechanismu OpenGL
 *
 * slouží třeba k načítání Cache souborů
 *
 * využívá jednoduchého mechanismu přes FileOpener v Javě samotné, ale chtěl jsem to na jednom místě,
 * něco jako třída Dialody
 */
public class FileOperator {
    private final FileChooser fileChooser = new FileChooser();                // zavedeni tridy FileChooseru

    /**
     * funkce pro otevírání souborů s filtrem, který si individuálně nastavuji
     */
    public File open(String nazev_filtru, String soubory_filtru){

        @Nullable String nazev = nazev_filtru;
        @Nullable String soubory = soubory_filtru;

        fileChooser.setTitle("Otevři soubory");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(nazev, soubory)
        );
        return fileChooser.showOpenDialog(Main.stage.getScene().getWindow());
    }

    /**
     * Funkce podobná funkci výše, akorát že nepoužívá žádný filtr pro výběr souboru
     * @return
     */
    public File open(){
        fileChooser.setTitle("Otevři soubory");
        return fileChooser.showOpenDialog(Main.stage.getScene().getWindow());
    }

    /**
     * Funkce pro ukládání souborů
     * @return
     */
    public File save(){
        fileChooser.setTitle("Uložit soubor jako");
        return fileChooser.showSaveDialog(Main.stage.getScene().getWindow());
    }
}
