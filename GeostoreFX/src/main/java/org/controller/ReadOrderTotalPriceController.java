package org.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controller.items.OrderTotalPriceItemController;
import org.models.Amministratore;
import org.models.Cliente;
import org.models.Ordine;
import org.services.LoadPage;
import org.services.Service;

import java.net.URL;
import java.sql.Date;
import java.util.Calendar;
import java.util.ResourceBundle;

public class ReadOrderTotalPriceController {// Questo è il BorderPane di menu.fxml

    @FXML
    private Label title;

    @FXML
    private Button re_search;


    private Cliente user;
    private Boolean isAdmin;
    private BorderPane fxmlLoader;
    private Service service;
    private ResourceBundle resLang;

    @FXML
    private HBox item;

    //------------------INITIALIZE-----------------------

    public void save(BorderPane fxmlLoader, Cliente utente, ResourceBundle resLang){

        if(utente instanceof Amministratore){
            Amministratore admin = (Amministratore) utente;
            user = admin;
            isAdmin = admin.getCodeAdmin() != null && !admin.getCodeAdmin().isEmpty() && !admin.getCodeAdmin().isBlank() &&
                    (admin.getCodeAdmin().contains("A")
                    || admin.getCodeAdmin().contains("Q")
                    || admin.getCodeAdmin().contains("O"));
        }
        else{
            user = utente;
            isAdmin = false;
        }

        this.fxmlLoader = fxmlLoader;
        this.resLang = resLang;
    }

    public void setTitle(String choosedDate) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(Date.valueOf(choosedDate));
        int giorno = calendario.get(Calendar.DAY_OF_MONTH);
        int mese = calendario.get(Calendar.MONTH) + 1;
        int anno = calendario.get(Calendar.YEAR);

        String value = resLang.getString("read.title.totalPriceOrders");

        value = value + " " + giorno + "/" + mese + "/" + anno;

        title.setText(value);
    }

    public void loadItem(String itemScene, String chooseDate){
        service = new Service();
        Ordine ordine;

        ordine = service.ordiniTotaliGiornalieri(user, chooseDate);

        if(ordine.getUtente() != null){
            try {
                // Costruisce il percorso completo del file FXML
                URL fileUrl = getClass().getResource("/org/scenes/items/" + itemScene + ".fxml"); //trova la scena news
                if (fileUrl == null) {
                    throw new java.io.FileNotFoundException("FXML file can't be found");
                }

                FXMLLoader loader = new FXMLLoader(fileUrl, resLang);
                VBox userProfileItem = loader.load();
                OrderTotalPriceItemController orderTotalPriceItemController = loader.getController();
                orderTotalPriceItemController.setValues(ordine);
                item.getChildren().add(userProfileItem);

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
    private void research() { //button per scegliere il giorno
        System.out.println("Going back");

        LoadPage.getPartialScene(fxmlLoader, "orderTotalPriceChooseDate", user, null);
    }
}