package org.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.services.LoadPage;

public class PrepageController {

    //------------------BUTTONS-----------------------

    @FXML
    private void register(ActionEvent event) {
        System.out.println("signing up");
        LoadPage.saveStage(event);

        LoadPage.access("register", null);
    }

    @FXML
    private void loginAdmin(ActionEvent event) {
        System.out.println("signing in admin");
        LoadPage.saveStage(event);

        LoadPage.access("Admin", null);
    }

    @FXML
    private void loginCliente(ActionEvent event) {
        System.out.println("signing in cliente");
        LoadPage.saveStage(event);

        LoadPage.access("User", null);
    }

    @FXML
    private void back(ActionEvent event) {
        System.out.println("Going back");

        LoadPage.getFullScene("welcome", null);
    }

}