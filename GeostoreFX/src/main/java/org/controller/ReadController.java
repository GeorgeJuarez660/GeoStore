package org.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controller.items.*;
import org.models.*;
import org.services.LoadPage;
import org.services.Service;
import org.utility.PartialSceneDTO;

import java.net.URL;
import java.util.*;

public class ReadController {// Questo è il BorderPane di menu.fxml

    @FXML
    private Label title;

    @FXML
    private TextField search;

    private Cliente user;
    private String isAdmin;
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
            title.setText(resLang.getString("read.title.users"));
        }
        else if(itemScene != null && itemScene.contains("product")){
            String lastChar = itemScene.substring(itemScene.length() - 1);
            if(lastChar.equals("1")){
                title.setText(resLang.getString("read.title.products"));
            }
            else if(lastChar.equals("O")){ //per l'ordinazione prodotto
                title.setText(resLang.getString("read.title.productsToOrder"));
            }
            else{
                title.setText(resLang.getString("read.title.availableProducts"));
            }
        }
        else if(itemScene != null && itemScene.contains("order")){
            String lastChar = itemScene.substring(itemScene.length() - 1);
            if(lastChar.equals("1")){
                title.setText(resLang.getString("read.title.orders"));
            }
            else{
                title.setText(resLang.getString("read.title.yourOrders"));
            }
        }
        else{
            title.setText(resLang.getString("read.title.news"));
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

        List<News> notizie = new ArrayList<>();
        Map<Integer, Utente> utenti = new HashMap<>();
        Map<Integer, Prodotto> prodotti = new HashMap<>();
        Map<Integer, Ordine> ordini = new HashMap<>();

        if (IDkey != null && !IDkey.isEmpty() && !IDkey.isBlank()){
            if(this.itemScene != null && this.itemScene.equals("user")){ //vede prima quale item riferisce
                utenti = service.ottieniUtenteByKeyword(IDkey);
            }
            else if(this.itemScene != null && this.itemScene.contains("product")){
                String lastChar = this.itemScene.substring(this.itemScene.length() - 1);
                if(lastChar.equals("1")){
                    prodotti = service.ottieniProdottoByKeyword(IDkey);
                }
                else if(lastChar.equals("O")){ //per l'ordinazione prodotto
                    prodotti = service.ottieniProdottoByKeyword(IDkey);
                }
                else{
                    prodotti = service.ottieniProdottoDisponibileByKeyword(IDkey);
                }
            }
            else if(this.itemScene != null && this.itemScene.contains("order")){
                String lastChar = this.itemScene.substring(this.itemScene.length() - 1);
                if(lastChar.equals("1")){
                    ordini = service.elencoOrdiniByEmail(IDkey);
                }
                else{
                    ordini = service.elencoPropriOrdiniByKeyword(user.getId(), IDkey);
                }
            }
            else{
                notizie = service.ottieniNotizieByKeyword(IDkey);
            }

            search.setText(IDkey);
        }
        else{
            if(this.itemScene != null && this.itemScene.equals("user")) { //vede prima quale item riferisce
                utenti = service.elencoUtenti();
            }
            else if(this.itemScene != null && this.itemScene.contains("product")){
                String lastChar = this.itemScene.substring(this.itemScene.length() - 1);
                if(lastChar.equals("1")){
                    prodotti = service.elencoProdotti();
                }
                else if(lastChar.equals("O")){ //per l'ordinazione prodotto
                    prodotti = service.elencoProdotti();
                }
                else{
                    prodotti = service.elencoProdottiDisponibili();
                }
            }
            else if(this.itemScene != null && this.itemScene.contains("order")){
                String lastChar = this.itemScene.substring(this.itemScene.length() - 1);
                if(lastChar.equals("1")){
                    ordini = service.elencoOrdini();
                }
                else{
                    ordini = service.elencoPropriOrdini(user.getId());
                }
            }
            else{
                notizie = service.elencoNotizie();
            }
        }

        itemList.getChildren().clear(); //pulisce prima di aggiungere

        if(this.itemScene != null && this.itemScene.equals("user")){
            for (Utente utente : utenti.values()) {
                if(utente.getId() != null && utente.getId() != 0){
                    try {
                        // Costruisce il percorso completo del file FXML
                        URL fileUrl = getClass().getResource("/org/scenes/items/userItem.fxml"); //trova la scena user
                        if (fileUrl == null) {
                            throw new java.io.FileNotFoundException("FXML file can't be found");
                        }

                        FXMLLoader loader = new FXMLLoader(fileUrl, resLang);
                        HBox userItem = loader.load();
                        UserItemController userItemController = loader.getController();
                        userItemController.save(fxmlLoader, user);
                        userItemController.setValues(utente);
                        userItemController.enableButtons();
                        itemList.getChildren().add(userItem);

                        // Carica il file FXML
                        // Imposta la scena caricata come contenuto centrale del BorderPane

                    } catch (Exception e) {
                        System.out.println("No page found. Please check FXMLLoader.");
                        e.printStackTrace();
                    }
                }
            }
        }
        else if(this.itemScene != null && this.itemScene.contains("product")){
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
                        productItemController.setValues(prodotto);
                        if(this.itemScene.equals("product-O")){ //per l'ordinazione prodotto
                            productItemController.enableButtonsForOrder();
                        }
                        else{
                            productItemController.enableButtons();
                        }
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
        else if(this.itemScene != null && this.itemScene.contains("order")){
            for (Ordine ordine : ordini.values()) {
                if(ordine.getId() != null && ordine.getId() != 0){
                    try {
                        // Costruisce il percorso completo del file FXML
                        URL fileUrl = getClass().getResource("/org/scenes/items/orderItem.fxml"); //trova la scena order
                        if (fileUrl == null) {
                            throw new java.io.FileNotFoundException("FXML file can't be found");
                        }

                        FXMLLoader loader = new FXMLLoader(fileUrl, resLang);
                        HBox productItem = loader.load();
                        OrderItemController orderItemController = loader.getController();
                        orderItemController.save(fxmlLoader, user);
                        orderItemController.setValues(ordine);
                        orderItemController.enableButtons();
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
        else{
            for (News notizia : notizie) {
                if(notizia.getId() != null && notizia.getId() != 0){
                    try {
                        // Costruisce il percorso completo del file FXML
                        URL fileUrl = getClass().getResource("/org/scenes/items/newsItem.fxml"); //trova la scena news
                        if (fileUrl == null) {
                            throw new java.io.FileNotFoundException("FXML file can't be found");
                        }

                        FXMLLoader loader = new FXMLLoader(fileUrl, resLang);
                        HBox newsItem = loader.load();
                        NewsItemController newsItemController = loader.getController();
                        newsItemController.save(fxmlLoader, user);
                        newsItemController.setValues(notizia, resLang);
                        newsItemController.enableButtons();
                        itemList.getChildren().add(newsItem);

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
        if(this.itemScene != null && this.itemScene.equals("user")){
            LoadPage.getPartialScene(fxmlLoader, "chooseTUserAdmin", user, null);
        }
        else if(this.itemScene != null && this.itemScene.contains("product")){
            if(this.itemScene.equals("product-O")){
                if(isAdmin != null && (isAdmin.contains("A") || isAdmin.contains("Q") || isAdmin.contains("O"))){
                    LoadPage.getPartialScene(fxmlLoader, "chooseTOrderAdmin", user, null);
                }
                else{
                    LoadPage.getPartialScene(fxmlLoader, "chooseTOrderCliente", user, null);
                }
            }
            else{
                if(isAdmin != null && (isAdmin.contains("A") || isAdmin.contains("P") || isAdmin.contains("Q"))){
                    LoadPage.getPartialScene(fxmlLoader, "chooseTProductAdmin", user, null);
                }
                else{
                    LoadPage.getPartialScene(fxmlLoader, "chooseTProductCliente", user, null);
                }
            }

        }
        else if(this.itemScene != null && (this.itemScene.contains("order"))){
            if(isAdmin != null && (isAdmin.contains("A") || isAdmin.contains("Q") || isAdmin.contains("O"))){
                LoadPage.getPartialScene(fxmlLoader, "chooseTOrderAdmin", user, null);
            }
            else{
                LoadPage.getPartialScene(fxmlLoader, "chooseTOrderCliente", user, null);
            }
        }
        else{
            LoadPage.getPartialScene(fxmlLoader, "homepage", user, null);
        }
    }

    @FXML
    private void searching(){ //button per cercare
        System.out.println("Start searching");

        if(showSearch){
            if(this.itemScene != null && this.itemScene.equals("user")){
                String keyword = search.getText();
                loadItems("user", keyword);
            }
            else if(this.itemScene != null && this.itemScene.equals("product-1")){
                String keyword = search.getText();
                loadItems("product-1", keyword);
            }
            else if(this.itemScene != null && this.itemScene.equals("product-2")){
                String keyword = search.getText();
                loadItems("product-2", keyword);
            }
            else if(this.itemScene != null && this.itemScene.equals("product-O")){ //per l'ordinazione prodotto
                String keyword = search.getText();
                loadItems("product-O", keyword);
            }
            else if(this.itemScene != null && this.itemScene.equals("order-1")){
                String keyword = search.getText();
                loadItems("order-1", keyword);
            }
            else if(this.itemScene != null && this.itemScene.equals("order-2")){
                String keyword = search.getText();
                loadItems("order-2", keyword);
            }
            else{
                String keyword = search.getText();
                loadItems("news", keyword);
            }
        }
        else{
            showSearchBar(true);
        }
    }
}