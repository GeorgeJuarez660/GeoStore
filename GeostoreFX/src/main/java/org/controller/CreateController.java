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

import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;

public class CreateController {// Questo è il BorderPane di menu.fxml

    @FXML
    private Label title;

    @FXML
    private HBox createMask;

    private Cliente user;
    private String isAdmin;
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
            isAdmin = admin.getCodiceAdmin().getCodice();
        }
        else{
            user = utente;
            isAdmin = null;
        }

        this.fxmlLoader = fxmlLoader;
        this.resLang = resLang;
    }

    public void setTitle(String itemScene) {
        if(itemScene != null && itemScene.equals("user")){
            title.setText(resLang.getString("create.title.user"));
        }
        else if(itemScene != null && itemScene.equals("code")){
            title.setText(resLang.getString("create.title.code"));
        }
        else if(itemScene != null && itemScene.equals("product")){
            title.setText(resLang.getString("create.title.product"));
        }
        else if(itemScene != null && itemScene.equals("order")){
            title.setText(resLang.getString("create.title.order"));
        }
        else if(itemScene != null && itemScene.equals("category")){
            title.setText(resLang.getString("create.title.category"));
        }
        else if(itemScene != null && itemScene.equals("material")){
            title.setText(resLang.getString("create.title.material"));
        }
        else{
            title.setText(resLang.getString("create.title.news"));
        }
    }

    public void loadMask(String itemScene, String IDOrderKey){ //idOrderKey usato per l'ordinazione prodotto
        this.itemScene = itemScene;

        if(this.itemScene != null && this.itemScene.equals("user")){
            try {
                // Costruisce il percorso completo del file FXML
                URL fileUrl = getClass().getResource("/org/scenes/masks/userMask.fxml");
                if (fileUrl == null) {
                    throw new java.io.FileNotFoundException("FXML file can't be found");
                }

                FXMLLoader loader = new FXMLLoader(fileUrl, resLang);
                VBox mask = loader.load();
                UserMaskController userMaskController = loader.getController();// Ottieni il controller della scena caricata
                maskController = userMaskController;
                createMask.getChildren().add(mask);

                // Carica il file FXML
                // Imposta la scena caricata come contenuto centrale del BorderPane

            } catch (Exception e) {
                System.out.println("No page found. Please check FXMLLoader.");
                e.printStackTrace();
            }
        }
        else if(this.itemScene != null && this.itemScene.equals("code")){
            try {
                // Costruisce il percorso completo del file FXML
                URL fileUrl = getClass().getResource("/org/scenes/masks/codeMask.fxml");
                if (fileUrl == null) {
                    throw new java.io.FileNotFoundException("FXML file can't be found");
                }

                FXMLLoader loader = new FXMLLoader(fileUrl, resLang);
                VBox mask = loader.load();
                CodeMaskController codeMaskController = loader.getController();// Ottieni il controller della scena caricata
                maskController = codeMaskController;
                createMask.getChildren().add(mask);

                // Carica il file FXML
                // Imposta la scena caricata come contenuto centrale del BorderPane

            } catch (Exception e) {
                System.out.println("No page found. Please check FXMLLoader.");
                e.printStackTrace();
            }
        }
        else if(this.itemScene != null && this.itemScene.equals("product")){
            try {
                // Costruisce il percorso completo del file FXML
                URL fileUrl = getClass().getResource("/org/scenes/masks/productMask.fxml");
                if (fileUrl == null) {
                    throw new java.io.FileNotFoundException("FXML file can't be found");
                }

                FXMLLoader loader = new FXMLLoader(fileUrl, resLang);
                VBox mask = loader.load();
                ProductMaskController productMaskController = loader.getController();// Ottieni il controller della scena caricata
                productMaskController.setAvailable();
                productMaskController.setCategory();
                productMaskController.setMaterial();
                maskController = productMaskController;
                createMask.getChildren().add(mask);

                // Carica il file FXML
                // Imposta la scena caricata come contenuto centrale del BorderPane

            } catch (Exception e) {
                System.out.println("No page found. Please check FXMLLoader.");
                e.printStackTrace();
            }
        }
        else if(this.itemScene != null && this.itemScene.equals("order")){
            try {
                // Costruisce il percorso completo del file FXML
                URL fileUrl = getClass().getResource("/org/scenes/masks/orderMask.fxml");
                if (fileUrl == null) {
                    throw new java.io.FileNotFoundException("FXML file can't be found");
                }

                FXMLLoader loader = new FXMLLoader(fileUrl, resLang);
                VBox mask = loader.load();
                OrderMaskController orderMaskController = loader.getController();// Ottieni il controller della scena caricata
                orderMaskController.getValuesForOrder(IDOrderKey, user);
                maskController = orderMaskController;
                createMask.getChildren().add(mask);

                // Carica il file FXML
                // Imposta la scena caricata come contenuto centrale del BorderPane

            } catch (Exception e) {
                System.out.println("No page found. Please check FXMLLoader.");
                e.printStackTrace();
            }
        }
        else if(this.itemScene != null && this.itemScene.equals("category")){
            try {
                // Costruisce il percorso completo del file FXML
                URL fileUrl = getClass().getResource("/org/scenes/masks/categoryMask.fxml");
                if (fileUrl == null) {
                    throw new java.io.FileNotFoundException("FXML file can't be found");
                }

                FXMLLoader loader = new FXMLLoader(fileUrl, resLang);
                VBox mask = loader.load();
                CategoryMaskController categoryMaskController = loader.getController();// Ottieni il controller della scena caricata
                maskController = categoryMaskController;
                createMask.getChildren().add(mask);

                // Carica il file FXML
                // Imposta la scena caricata come contenuto centrale del BorderPane

            } catch (Exception e) {
                System.out.println("No page found. Please check FXMLLoader.");
                e.printStackTrace();
            }
        }
        else if(this.itemScene != null && this.itemScene.equals("material")){
            try {
                // Costruisce il percorso completo del file FXML
                URL fileUrl = getClass().getResource("/org/scenes/masks/materialMask.fxml");
                if (fileUrl == null) {
                    throw new java.io.FileNotFoundException("FXML file can't be found");
                }

                FXMLLoader loader = new FXMLLoader(fileUrl, resLang);
                VBox mask = loader.load();
                MaterialMaskController materialMaskController = loader.getController();// Ottieni il controller della scena caricata
                maskController = materialMaskController;
                createMask.getChildren().add(mask);

                // Carica il file FXML
                // Imposta la scena caricata come contenuto centrale del BorderPane

            } catch (Exception e) {
                System.out.println("No page found. Please check FXMLLoader.");
                e.printStackTrace();
            }
        }
        else{
            try {
                // Costruisce il percorso completo del file FXML
                URL fileUrl = getClass().getResource("/org/scenes/masks/newsMask.fxml");
                if (fileUrl == null) {
                    throw new java.io.FileNotFoundException("FXML file can't be found");
                }

                FXMLLoader loader = new FXMLLoader(fileUrl, resLang);
                VBox mask = loader.load();
                NewsMaskController newsMaskController = loader.getController();// Ottieni il controller della scena caricata
                newsMaskController.setDate();
                maskController = newsMaskController;
                createMask.getChildren().add(mask);

                // Carica il file FXML
                // Imposta la scena caricata come contenuto centrale del BorderPane

            } catch (Exception e) {
                System.out.println("No page found. Please check FXMLLoader.");
                e.printStackTrace();
            }
        }

    }

    //------------------BUTTONS-----------------------

    @FXML
    private void back() { //button per tornare indietro
        System.out.println("Going back");
        if(this.itemScene != null && this.itemScene.equals("user")){
            LoadPage.getPartialScene(fxmlLoader, "chooseTUserAdmin", user, null);
        }
        else if(this.itemScene != null && this.itemScene.equals("code")){
            LoadPage.getPartialScene(fxmlLoader, "chooseTCodeAdmin", user, null);
        }
        else if(this.itemScene != null && this.itemScene.equals("product")){
            LoadPage.getPartialScene(fxmlLoader, "chooseTProductAdmin", user, null);
        }
        else if(this.itemScene != null && this.itemScene.equals("order")){
            if(isAdmin != null && (isAdmin.contains("A") || isAdmin.contains("Q") || isAdmin.contains("O"))){
                LoadPage.getPartialScene(fxmlLoader, "chooseTOrderAdmin", user, null);
            }
            else{
                LoadPage.getPartialScene(fxmlLoader, "chooseTOrderCliente", user, null);
            }
        }
        else if(this.itemScene != null && this.itemScene.equals("category")){
            LoadPage.getPartialScene(fxmlLoader, "chooseTCategoryAdmin", user, null);
        }
        else if(this.itemScene != null && this.itemScene.equals("material")){
            LoadPage.getPartialScene(fxmlLoader, "chooseTMaterialAdmin", user, null);
        }
        else{
            LoadPage.getPartialScene(fxmlLoader, "homepage", user, null);
        }
    }

    @FXML
    private void create(ActionEvent event) throws ParseException { //button per creare
        System.out.println("Start creating");
        LoadPage.saveStage(event);
        LoadPage.loadingScene("LOAD-CRT", null);

        service = new Service();

        if(maskController instanceof UserMaskController){
            UserMaskController userMaskController = (UserMaskController) maskController;
            Cliente u = userMaskController.setValues();

            service.creazioneUtente(u, user);
        }
        else if(maskController instanceof CodeMaskController){
            CodeMaskController codeMaskController = (CodeMaskController) maskController;
            Codice c = codeMaskController.setValues();

            service.creazioneCodice(c, user);
        }
        else if(maskController instanceof ProductMaskController){
            ProductMaskController productMaskController = (ProductMaskController) maskController;
            Prodotto p = productMaskController.setValues();

            //crea prodotto
            service.creazioneProdotto(p, user);
        }
        else if(maskController instanceof OrderMaskController){
            OrderMaskController orderMaskController = (OrderMaskController) maskController;
            Ordine o = orderMaskController.setValues();

            //ordina prodotto
            service.ordinazioneProdotto(o, user);
        }
        else if(maskController instanceof CategoryMaskController){
            CategoryMaskController categoryMaskController = (CategoryMaskController) maskController;
            Categoria c = categoryMaskController.setValues();

            service.creazioneCategoria(c, user);
        }
        else if(maskController instanceof MaterialMaskController){
            MaterialMaskController materialMaskController = (MaterialMaskController) maskController;
            Materiale m = materialMaskController.setValues();

            service.creazioneMateriale(m, user);
        }
        else{
            NewsMaskController newsMaskController = (NewsMaskController) maskController;
            News n = newsMaskController.setValues();

            service.creazioneNotizia(n, user);
        }

    }
}