package org.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.models.Amministratore;
import org.models.Cliente;
import org.services.LoadPage;
import org.utility.Sounds;

public class InfoController {

    @FXML
    private BorderPane fxmlLoader;

    @FXML
    private Label version;

    @FXML
    private Cliente user;

    @FXML
    private Boolean isAdmin;

    //------------------INITIALIZE-----------------------

    public void save(BorderPane fxmlLoader, Cliente utente){
        if(utente instanceof Amministratore){
            Amministratore admin = (Amministratore) utente;
            user = admin;
            isAdmin = admin.getCodiceAdmin().getCodice() != null && !admin.getCodiceAdmin().getCodice().isEmpty() && !admin.getCodiceAdmin().getCodice().isBlank();
        }
        else{
            user = utente;
            isAdmin = false;
        }

        this.fxmlLoader = fxmlLoader;
    }

    public void setVersion(String value) {
        System.out.println("info");

        version.setText(value);
    }

    //------------------BUTTONS-----------------------

    @FXML
    private void back() { //button per andare alla homepage
        System.out.println("Going back");
        Sounds.soundBack();

        LoadPage.getPartialScene(fxmlLoader, "homepage", user, null);
    }
}