package org.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controller.items.ProductItemController;
import org.models.*;
import org.services.LoadPage;
import org.services.Service;
import org.utility.PartialSceneDTO;

import java.net.URL;
import java.util.*;

public class ReadProductsByCMController {// Questo è il BorderPane di menu.fxml

    @FXML
    private Label title;

    @FXML
    private TextField search;

    private Cliente user;
    private Boolean isAdmin;
    private BorderPane fxmlLoader;
    private Service service;
    private String itemScene, typeKey;
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
        if(itemScene != null && itemScene.contains("product")){
            String lastChar = itemScene.substring(itemScene.length() - 1);
            if(lastChar.equals("C")){ //per via categoria
                title.setText(resLang.getString("read.title.productsByCategories"));
            }
            else{ //per via materia
                title.setText(resLang.getString("read.title.productsByMaterials"));
            }
        }
    }

    public void showSearchBar(Boolean show){
        search.setVisible(show);
        search.setManaged(show);
        showSearch = show;
    }

    public void loadItems(String itemScene, String IDkey){
        service = new Service();
        this.itemScene = itemScene;

        Map<Integer, Prodotto> prodotti = new HashMap<>();

        String check = "^[0-9]*$";

        if (IDkey != null && !IDkey.isEmpty() && !IDkey.isBlank() && !IDkey.matches(check)){
            if(this.itemScene != null && this.itemScene.contains("product")){ //vede prima quale item riferisce
                String lastChar = this.itemScene.substring(this.itemScene.length() - 1);
                if(lastChar.equals("C")){ //per prodotti via categoria
                    prodotti = service.prodottiViaCategoriaByKeyword(typeKey, IDkey);
                }
                else{ //per prodotti via materia
                    prodotti = service.prodottiViaMaterialeByKeyword(typeKey, IDkey);
                }
            }

            search.setText(IDkey);
        }
        else{
            if(this.itemScene != null && this.itemScene.contains("product")){ //vede prima quale item riferisce
                String lastChar = this.itemScene.substring(this.itemScene.length() - 1);
                if(lastChar.equals("C")){ //per prodotti via categoria
                    prodotti = service.prodottiViaCategoria(IDkey);
                    if(IDkey.matches(check)){
                        this.typeKey = IDkey;
                    }
                }
                else{ //per prodotti via materia
                    prodotti = service.prodottiViaMateriale(IDkey);
                    if(IDkey.matches(check)){
                        this.typeKey = IDkey;
                    }
                }
            }
        }

        itemList.getChildren().clear(); //pulisce prima di aggiungere

        if(this.itemScene != null && this.itemScene.contains("product")){
            for (Prodotto prodotto : prodotti.values()) {
                if(prodotto.getId() != null && prodotto.getId() != 0){
                    try {
                        // Costruisce il percorso completo del file FXML
                        URL fileUrl = getClass().getResource("/org/scenes/items/productItem.fxml"); //trova la scena product
                        if (fileUrl == null) {
                            throw new java.io.FileNotFoundException("FXML file can't be found");
                        }

                        FXMLLoader loader = new FXMLLoader(fileUrl, resLang);
                        HBox productItem = loader.load();
                        ProductItemController productItemController = loader.getController();
                        productItemController.save(fxmlLoader, user);
                        productItemController.setValues(prodotto, resLang);
                        productItemController.enableButtons();
                        itemList.getChildren().add(productItem);

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
        if(this.itemScene != null && this.itemScene.contains("product")){
            if(this.itemScene.equals("product-C")){
                System.out.println("goes to category");
                PartialSceneDTO partialSceneDTO = new PartialSceneDTO();
                partialSceneDTO.setFxmlLoader(fxmlLoader);
                partialSceneDTO.setInnerScene("readProductType");
                partialSceneDTO.setItemScene("category");
                partialSceneDTO.setUser(user);
                LoadPage.getPartialSceneCRU(partialSceneDTO, null, null);
            }
            else if(this.itemScene.equals("product-M")){
                System.out.println("goes to material");
                PartialSceneDTO partialSceneDTO = new PartialSceneDTO();
                partialSceneDTO.setFxmlLoader(fxmlLoader);
                partialSceneDTO.setInnerScene("readProductType");
                partialSceneDTO.setItemScene("material");
                partialSceneDTO.setUser(user);
                LoadPage.getPartialSceneCRU(partialSceneDTO, null, null);
            }
        }
    }

    @FXML
    private void searching(){ //button per cercare
        System.out.println("Start searching");

        if(showSearch){
            if(this.itemScene != null && this.itemScene.equals("product-C")){ //per i prodotti di via categoria
                String keyword = search.getText();
                loadItems("product-C", keyword);
            }
            else if(this.itemScene != null && this.itemScene.equals("product-M")){ //per i prodotti di via materia
                String keyword = search.getText();
                loadItems("product-M", keyword);
            }
        }
        else{
            showSearchBar(true);
        }
    }
}