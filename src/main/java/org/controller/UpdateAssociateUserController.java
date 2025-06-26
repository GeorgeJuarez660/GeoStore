package org.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controller.masks.*;
import org.models.*;
import org.services.LoadPage;
import org.services.Service;
import org.utility.Sounds;

import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;

public class UpdateAssociateUserController {// Questo è il BorderPane di menu.fxml

    @FXML
    private Label title;

    @FXML
    private HBox createMask;

    private Cliente user;
    private Boolean isAdmin;
    private BorderPane fxmlLoader;
    private Service service;
    private String itemScene;
    private ResourceBundle resLang;

    private Object maskController;

    //------------------INITIALIZE-----------------------

    public void save(BorderPane fxmlLoader, Cliente utente, ResourceBundle resLang){

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
        this.resLang = resLang;
    }

    public void loadMask(String itemScene, String emailKey){
        service = new Service();

        try {
            // Costruisce il percorso completo del file FXML
            URL fileUrl = getClass().getResource("/org/scenes/masks/" + itemScene + ".fxml"); //trova la scena associazione utente
            if (fileUrl == null) {
                throw new java.io.FileNotFoundException("FXML file can't be found");
            }

            FXMLLoader loader = new FXMLLoader(fileUrl, resLang);
            VBox mask = loader.load();
            CodeAssociateMaskController codeAssociateMaskController = loader.getController();
            codeAssociateMaskController.setAdminCode();
            codeAssociateMaskController.setUserEmail();
            codeAssociateMaskController.getValues(emailKey);
            maskController = codeAssociateMaskController;
            createMask.getChildren().add(mask);

            // Carica il file FXML
            // Imposta la scena caricata come contenuto centrale del BorderPane

        } catch (Exception e) {
            System.out.println("No page found. Please check FXMLLoader.");
            e.printStackTrace();
        }

    }

    //------------------BUTTONS-----------------------

    @FXML
    private void back() { //button per tornare indietro
        System.out.println("Going back");
        Sounds.soundBack();

        LoadPage.getPartialScene(fxmlLoader, "chooseTCodeAdmin", user, null);
    }

    @FXML
    private void updateAssociation(ActionEvent event) throws ParseException { //button per modificare
        System.out.println("question");
        Sounds.soundGo();

        LoadPage.saveStage(event);

        LoadPage.questionScene("Q-US", null, user, maskController, null, false);
    }

    public void startUpdatingAssociation(ActionEvent event, Cliente user, Object maskController) throws ParseException {
        System.out.println("Start updating");
        LoadPage.saveStage(event);
        LoadPage.loadingScene("LOAD-USC", null);

        service = new Service();

        if(maskController instanceof CodeAssociateMaskController){
            CodeAssociateMaskController codeAssociateMaskController = (CodeAssociateMaskController) maskController;
            CodiceAssociateDTO ca = codeAssociateMaskController.setValuesWithID();

            service.modificaAssociazioneCodice(ca, user);
        }
    }
}