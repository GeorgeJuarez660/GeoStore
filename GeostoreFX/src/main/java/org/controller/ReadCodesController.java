package org.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controller.items.*;
import org.models.*;
import org.services.LoadPage;
import org.services.Service;
import org.utility.PartialSceneDTO;
import org.utility.Sounds;

import java.net.URL;
import java.util.*;

public class ReadCodesController {// Questo è il BorderPane di menu.fxml

    @FXML
    private Label title;

    @FXML
    private TextField search;

    @FXML
    private Button associateUserBtn;

    private Cliente user;
    private Boolean isAdmin;
    private BorderPane fxmlLoader;
    private Service service;
    private String itemScene;
    private Boolean showSearch;
    private ResourceBundle resLang;

    @FXML
    private VBox itemList;

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

    public void setTitle(String itemScene) {
        if(itemScene != null && itemScene.contains("code")){
            String lastChar = itemScene.substring(itemScene.length() - 1);
            if(lastChar.equals("A")){
                title.setText(resLang.getString("read.title.associatedCodes"));
            }
            else{
                title.setText(resLang.getString("read.title.codes"));
            }
        }
    }

    public void showSearchBar(Boolean show){
        search.setVisible(show);
        search.setManaged(show);
        showSearch = show;
    }

    public void enableAssociateCodici(String itemScene){
        if(itemScene != null && itemScene.contains("code")){
            String lastChar = itemScene.substring(itemScene.length() - 1);
            if(lastChar.equals("A")){
                associateUserBtn.setVisible(false);
                associateUserBtn.setManaged(false);

            }
            else{
                associateUserBtn.setVisible(true);
                associateUserBtn.setManaged(true);
            }
        }
    }

    public void loadItems(String itemScene, String IDkey){
        service = new Service();
        this.itemScene = itemScene;

        Map<Integer, Codice> codici = new HashMap<>();
        Map<Integer, CodiceAssociateDTO> codiciAssociati = new HashMap<>();

        if (IDkey != null && !IDkey.isEmpty() && !IDkey.isBlank()){
            if(this.itemScene != null && this.itemScene.contains("code")){ //vede prima quale item riferisce
                String lastChar = itemScene.substring(itemScene.length() - 1);
                if(lastChar.equals("A")){
                    codiciAssociati = service.ottieniCodiceAssociatoByKeyword(IDkey);
                }
                else{
                    codici = service.ottieniCodiceByKeyword(IDkey);
                }

            }

            search.setText(IDkey);
        }
        else{
            if(this.itemScene != null && this.itemScene.contains("code")) { //vede prima quale item riferisce
                String lastChar = itemScene.substring(itemScene.length() - 1);
                if(lastChar.equals("A")){
                    codiciAssociati = service.elencoCodiciAssociati();
                }
                else{
                    codici = service.elencoCodici();
                }
            }
        }

        itemList.getChildren().clear(); //pulisce prima di aggiungere

        if(this.itemScene != null && this.itemScene.equals("code-C")){
            for (Codice codice : codici.values()) {
                if(codice.getId() != null && codice.getId() != 0){
                    try {
                        // Costruisce il percorso completo del file FXML
                        URL fileUrl = getClass().getResource("/org/scenes/items/codeItem.fxml"); //trova la scena codice
                        if (fileUrl == null) {
                            throw new java.io.FileNotFoundException("FXML file can't be found");
                        }

                        FXMLLoader loader = new FXMLLoader(fileUrl, resLang);
                        HBox codeItem = loader.load();
                        CodeItemController codeItemController = loader.getController();
                        codeItemController.save(fxmlLoader, user);
                        codeItemController.setValues(codice);
                        codeItemController.enableButtons();
                        itemList.getChildren().add(codeItem);

                        // Carica il file FXML
                        // Imposta la scena caricata come contenuto centrale del BorderPane

                    } catch (Exception e) {
                        System.out.println("No page found. Please check FXMLLoader.");
                        e.printStackTrace();
                    }
                }
            }
        }
        else if(this.itemScene != null && this.itemScene.contains("code-A")){
            for (CodiceAssociateDTO associateDTO : codiciAssociati.values()) {
                if(associateDTO.getIdCodice() != null && associateDTO.getIdCodice() != 0){
                    try {
                        // Costruisce il percorso completo del file FXML
                        URL fileUrl = getClass().getResource("/org/scenes/items/codeAssociateItem.fxml"); //trova la scena codice associato
                        if (fileUrl == null) {
                            throw new java.io.FileNotFoundException("FXML file can't be found");
                        }

                        FXMLLoader loader = new FXMLLoader(fileUrl, resLang);
                        HBox codeAssociateItem = loader.load();
                        CodeAssociateItemController codeAssociateItemController = loader.getController();
                        codeAssociateItemController.save(fxmlLoader, user);
                        codeAssociateItemController.setValues(associateDTO);
                        codeAssociateItemController.enableButtons();
                        itemList.getChildren().add(codeAssociateItem);

                        // Carica il file FXML
                        // Imposta la scena caricata come contenuto centrale del BorderPane

                    } catch (Exception e) {
                        System.out.println("No page found. Please check FXMLLoader.");
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //------------------BUTTONS-----------------------

    @FXML
    private void back() { //button per tornare indietro
        System.out.println("Going back");
        Sounds.soundBack();

        if(this.itemScene != null && this.itemScene.contains("code")){
            LoadPage.getPartialScene(fxmlLoader, "chooseTCodeAdmin", user, null);
        }
    }

    @FXML
    private void associating() {
        System.out.println("goes to associate code to user");
        Sounds.soundGo();

        PartialSceneDTO partialSceneDTO = new PartialSceneDTO();
        partialSceneDTO.setFxmlLoader(fxmlLoader);
        partialSceneDTO.setInnerScene("createAssociateCode");
        partialSceneDTO.setUser(user);
        LoadPage.getPartialSceneCRU(partialSceneDTO, null, null);
    }

    @FXML
    private void searching(){ //button per cercare
        System.out.println("Start searching");
        Sounds.soundGo();

        if(showSearch){
            if(this.itemScene != null && this.itemScene.equals("code-C")){
                String keyword = search.getText();
                loadItems("code-C", keyword);
            }
            else if(this.itemScene != null && this.itemScene.equals("code-A")){
                String keyword = search.getText();
                loadItems("code-A", keyword);
            }
        }
        else{
            showSearchBar(true);
        }
    }
}