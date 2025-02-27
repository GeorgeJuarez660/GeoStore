package org.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import org.controller.items.*;
import org.models.*;
import org.services.LoadPage;
import org.services.Service;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ReadProductTypeController {// Questo è il BorderPane di menu.fxml

    @FXML
    private Label title;

    private Cliente user;
    private Boolean isAdmin;
    private BorderPane fxmlLoader;
    private Service service;
    private String itemScene;

    @FXML
    private FlowPane buttonList; //per categorie/materie
    @FXML
    private HBox backBtn;

    //------------------INITIALIZE-----------------------

    public void save(BorderPane fxmlLoader, Cliente utente){

        if(utente instanceof Amministratore){
            Amministratore admin = (Amministratore) utente;
            user = admin;
            isAdmin = admin.getCodeAdmin() != null && !admin.getCodeAdmin().isEmpty() && !admin.getCodeAdmin().isBlank();
        }
        else{
            user = utente;
            isAdmin = false;
        }

        this.fxmlLoader = fxmlLoader;
    }

    public void setTitle(String itemScene, ResourceBundle resLang) {
        if(itemScene != null && itemScene.equals("category")){
            title.setText(resLang.getString("read.title.categories"));
        }
        else{
            title.setText(resLang.getString("read.title.materials"));
        }
    }

    public void enableBackBtn() {
        backBtn.setVisible(isAdmin);
        backBtn.setManaged(isAdmin);
    }


    public void loadButtons(String itemScene){
        service = new Service();
        this.itemScene = itemScene;

        Map<Integer, Categoria> categorie = new HashMap<>();
        Map<Integer, Materia> materie = new HashMap<>();

        if(this.itemScene != null && this.itemScene.equals("category")) {//per vedere quale button riferisce
            categorie = service.ottieniCategorie();
        }
        else{
            materie = service.ottieniMaterie();
        }

        buttonList.getChildren().clear(); //pulisce prima di aggiungere

        if(this.itemScene != null && this.itemScene.equals("category")){
            for (Categoria categoria : categorie.values()) {
                if(categoria.getId() != null && categoria.getId() != 0){
                    try {
                        // Costruisce il percorso completo del file FXML
                        URL fileUrl = getClass().getResource("/org/scenes/items/categoryButton.fxml"); //trova la scena pulsante category
                        if (fileUrl == null) {
                            throw new java.io.FileNotFoundException("FXML file can't be found");
                        }

                        FXMLLoader loader = new FXMLLoader(fileUrl);
                        Button categoryButton = loader.load();
                        ProductTypeButtonController productTypeButtonController = loader.getController();
                        productTypeButtonController.save(fxmlLoader, user);
                        productTypeButtonController.setCategoryValues(categoria);
                        productTypeButtonController.enableButtons();
                        buttonList.getChildren().add(categoryButton);

                        // Carica il file FXML
                        // Imposta la scena caricata come contenuto centrale del BorderPane

                    } catch (Exception e) {
                        System.out.println("No page found. Please check FXMLLoader.");
                        e.printStackTrace();
                    }
                }
            }
        }
        else{
            for (Materia materia : materie.values()) {
                if(materia.getId() != null && materia.getId() != 0){
                    try {
                        // Costruisce il percorso completo del file FXML
                        URL fileUrl = getClass().getResource("/org/scenes/items/materialButton.fxml"); //trova la scena pulsante material
                        if (fileUrl == null) {
                            throw new java.io.FileNotFoundException("FXML file can't be found");
                        }

                        FXMLLoader loader = new FXMLLoader(fileUrl);
                        Button categoryButton = loader.load();
                        ProductTypeButtonController productTypeButtonController = loader.getController();
                        productTypeButtonController.save(fxmlLoader, user);
                        productTypeButtonController.setMaterialValues(materia);
                        productTypeButtonController.enableButtons();
                        buttonList.getChildren().add(categoryButton);

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
        if(this.itemScene != null && this.itemScene.equals("category")){
            LoadPage.getPartialScene(fxmlLoader, "chooseTCategoryAdmin", user, null);
        }
        else{
            LoadPage.getPartialScene(fxmlLoader, "chooseTMaterialAdmin", user, null);
        }
    }
}