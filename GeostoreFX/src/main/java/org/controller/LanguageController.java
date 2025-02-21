package org.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.services.LoadPage;

import java.util.Locale;

public class LanguageController {

    private Locale locale;

    //------------------BUTTONS-----------------------
    @FXML
    private void back(ActionEvent event) {
        System.out.println("Going back");
        LoadPage.getFullScene("welcome", null);
    }

    @FXML
    private void chooseItalian(ActionEvent event) {
        System.out.println("Changing language in italian");
        LoadPage.saveStage(event);
        LoadPage.loadingSceneWithLang("LOAD-CHL", null);

        LoadPage.getFullScene("welcome", "it");
    }

    @FXML
    private void chooseEnglish(ActionEvent event) {
        System.out.println("Changing language in english");
        LoadPage.saveStage(event);
        LoadPage.loadingSceneWithLang("LOAD-CHL", null);

        LoadPage.getFullScene("welcome", "en");
    }

    @FXML
    private void chooseJapanese(ActionEvent event) {
        System.out.println("Changing language in japanese");
        LoadPage.saveStage(event);
        LoadPage.loadingSceneWithLang("LOAD-CHL", null);

        LoadPage.getFullScene("welcome", "ja");
    }


    @FXML
    public void initialize() {
        System.out.println("Language");
    }
}