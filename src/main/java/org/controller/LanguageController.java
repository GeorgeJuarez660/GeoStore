package org.controller;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.services.LoadPage;
import org.utility.Sounds;

import java.util.Locale;

public class LanguageController {

    private Locale locale;

    //------------------BUTTONS-----------------------
    @FXML
    private void back(ActionEvent event) {
        System.out.println("Going back");
        Platform.runLater(Sounds::soundBack); //parte la musica

        LoadPage.getFullScene("welcome", null);
    }

    @FXML
    private void chooseItalian(ActionEvent event) {
        System.out.println("Changing language in italian");
        Sounds.soundGo(); //parte la musica

        LoadPage.saveStage(event);
        LoadPage.loadingScene("LOAD-CHL", null);

        LoadPage.getFullScene("welcome", "it");
    }

    @FXML
    private void chooseEnglish(ActionEvent event) {
        System.out.println("Changing language in english");
        Sounds.soundGo();; //parte la musica

        LoadPage.saveStage(event);
        LoadPage.loadingScene("LOAD-CHL", null);

        LoadPage.getFullScene("welcome", "en");
    }

    @FXML
    private void chooseJapanese(ActionEvent event) {
        System.out.println("Changing language in japanese");
        Sounds.soundGo(); //parte la musica

        LoadPage.saveStage(event);
        LoadPage.loadingScene("LOAD-CHL", null);

        LoadPage.getFullScene("welcome", "ja");
    }


    @FXML
    public void initialize() {
        System.out.println("Language");
    }
}