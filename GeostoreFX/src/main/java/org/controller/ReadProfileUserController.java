package org.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controller.items.UserItemController;
import org.models.Amministratore;
import org.models.Cliente;
import org.models.Utente;
import org.services.LoadPage;
import org.services.Service;
import org.utility.PartialSceneDTO;

import java.net.URL;

public class ReadProfileUserController {// Questo è il BorderPane di menu.fxml

    @FXML
    private Label title;

    @FXML
    private TextField search;
    @FXML
    private HBox backBtn;


    private Cliente user;
    private Boolean isAdmin;
    private BorderPane fxmlLoader;
    private Service service;

    @FXML
    private HBox item;

    //------------------INITIALIZE-----------------------

    public void save(BorderPane fxmlLoader, Cliente utente){

        if(utente instanceof Amministratore){
            Amministratore admin = (Amministratore) utente;
            user = admin;
            isAdmin = admin.getCodeAdmin() != null && !admin.getCodeAdmin().isEmpty() && !admin.getCodeAdmin().isBlank() &&
                    (admin.getCodeAdmin().contains("A")
                    || admin.getCodeAdmin().contains("U")
                    || admin.getCodeAdmin().contains("N"));
        }
        else{
            user = utente;
            isAdmin = false;
        }

        this.fxmlLoader = fxmlLoader;
    }

    public void setTitle(String value) {
        title.setText(value);
    }

    public void loadItem(String itemScene){
        service = new Service();
        Utente utente;

        utente = service.ottieniProfiloUtente(user.getId());

        try {
            // Costruisce il percorso completo del file FXML
            URL fileUrl = getClass().getResource("/org/scenes/items/" + itemScene + ".fxml"); //trova la scena news
            if (fileUrl == null) {
                throw new java.io.FileNotFoundException("FXML file can't be found");
            }

            FXMLLoader loader = new FXMLLoader(fileUrl);
            VBox userProfileItem = loader.load();
            UserItemController userItemController = loader.getController();
            userItemController.save(fxmlLoader, user);
            userItemController.setValues(utente);
            item.getChildren().add(userProfileItem);

            // Carica il file FXML
            // Imposta la scena caricata come contenuto centrale del BorderPane

        } catch (Exception e) {
            System.out.println("No page found. Please check FXMLLoader.");
            e.printStackTrace();
        }

    }

    public void enableBackBtn(){
        backBtn.setVisible(isAdmin);
        backBtn.setManaged(isAdmin);
    }

    //------------------BUTTONS-----------------------

    @FXML
    private void back() { //button per tornare indietro
        System.out.println("Going back");

        LoadPage.getPartialScene(fxmlLoader, "chooseTUserAdmin", user, null);
    }

    @FXML
    private void updating(){ //button per andare alla pagina di modifica utente
        System.out.println("goes to update user");
        PartialSceneDTO partialSceneDTO = new PartialSceneDTO();
        partialSceneDTO.setFxmlLoader(fxmlLoader);
        partialSceneDTO.setInnerScene("update");
        partialSceneDTO.setItemScene("user-P");
        partialSceneDTO.setUser(user);
        String idKey = user.getId().toString();
        LoadPage.getPartialSceneCRU(partialSceneDTO, idKey, null);
    }
}