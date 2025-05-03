package org.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.services.LoadPage;
import org.utility.Sounds;

public class PrepageController {

    //------------------BUTTONS-----------------------

    @FXML
    private void register(ActionEvent event) {
        System.out.println("signing up");
        Sounds.soundGo(); //parte la musica

        LoadPage.saveStage(event);

        LoadPage.access("register", null);
    }

    @FXML
    private void loginAdmin(ActionEvent event) {
        System.out.println("signing in admin");
        Sounds.soundGo(); //parte la musica

        LoadPage.saveStage(event);

        LoadPage.access("Admin", null);
    }

    @FXML
    private void loginCliente(ActionEvent event) {
        System.out.println("signing in cliente");
        Sounds.soundGo(); //parte la musica

        LoadPage.saveStage(event);

        LoadPage.access("User", null);
    }

    @FXML
    private void back(ActionEvent event) {
        System.out.println("Going back");
        Sounds.soundBack(); //parte la musica

        LoadPage.getFullScene("welcome", null);
    }

}