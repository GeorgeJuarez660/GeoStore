package org.controller.masks;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import org.controlsfx.control.PopOver;
import org.models.*;
import org.services.Service;
import org.utility.Translater;
import org.utility.Utility;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrderMaskController implements Initializable {

    @FXML
    private TextField productName, userEmail, quantity, productPrice;
    @FXML
    private DatePicker orderDate;
    @FXML
    private ChoiceBox<String> status;

    private PopOver popOver;

    private Service service;
    private String IDkey; //usato per la ricerca/modifica/rimozione

    private Cliente userOrder;
    private Prodotto productOrder;

    //------------------INITIALIZE-----------------------

    //per l'ordinazione prodotto
    public void getValuesForOrder(String IDOrderkey, Cliente user){

        service = new Service();
        Prodotto prodotto;
        prodotto = service.ottieniProdotto(Integer.parseInt(IDOrderkey));

        productName.setText(prodotto.getNome());
        userEmail.setText(user.getEmail());

        Calendar calendario = Calendar.getInstance();
        calendario.setTime(Date.valueOf(LocalDate.now()));
        int giorno = calendario.get(Calendar.DAY_OF_MONTH);
        int mese = calendario.get(Calendar.MONTH) + 1;
        int anno = calendario.get(Calendar.YEAR);

        String giornoEsatto = String.format("%02d", giorno);
        String meseEsatto = String.format("%02d", mese);

        orderDate.setPromptText(giornoEsatto+"/"+meseEsatto+"/"+anno);
        productPrice.setText(Utility.formatValueInStringWithZeros(prodotto.getPrezzo()));

        userOrder = user;
        productOrder = prodotto;
    }

    //per la modifica ordine
    public void setStatus(){
        service = new Service();
        Map<Integer, Stato> st = new HashMap<>();
        st = service.ottieniStato();

        for(Stato stato : st.values()){
            status.getItems().add(stato.getId() + " - " + stato.getCode());
        }
    }

    public void getValues(String IDkey){

        service = new Service();
        Ordine ordine;
        ordine = service.ottieniOrdine(Integer.parseInt(IDkey));

        productName.setText(ordine.getProdotto().getNome());

        if(ordine.getUtente() instanceof Amministratore){
            Amministratore admin = (Amministratore) ordine.getUtente();
            userEmail.setText(admin.getEmail());
            userOrder = admin;
        }
        else{
            Cliente cliente = (Cliente) ordine.getUtente();
            userEmail.setText(cliente.getEmail());
            userOrder = cliente;
        }

        Calendar calendario = Calendar.getInstance();
        calendario.setTime(ordine.getData_ordine());
        int giorno = calendario.get(Calendar.DAY_OF_MONTH);
        int mese = calendario.get(Calendar.MONTH) + 1;
        int anno = calendario.get(Calendar.YEAR);

        String giornoEsatto = String.format("%02d", giorno);
        String meseEsatto = String.format("%02d", mese);

        orderDate.setPromptText(giornoEsatto+"/"+meseEsatto+"/"+anno);
        quantity.setText(ordine.getQuantita().toString());
        productPrice.setText(Utility.formatValueInStringWithZeros(ordine.getPrezzo_unitario()));
        status.setValue(ordine.getStato().getId() + " - " + ordine.getStato().getCode());

        this.IDkey = IDkey;
        productOrder = ordine.getProdotto();
    }

    //------------------GETTING FROM CRUD CONTROLLER-----------------------

    //per l'ordinazione prodotto
    public Ordine setValues() throws ParseException { //recuperato da mask
        Ordine ordine = new Ordine();

        ordine.setUtente(userOrder);
        ordine.setProdotto(productOrder);

        if(productPrice != null && productPrice.getText() != null && !productPrice.getText().isEmpty() && !productPrice.getText().isBlank()) {
            ordine.setPrezzo_unitario(Utility.formatValueInBigDecimalWithoutZeros(productPrice.getText()));
        }
        else{
            ordine.setPrezzo_unitario(new BigDecimal(0));
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(orderDate.getPromptText(), formatter);

        ordine.setData_ordine(Timestamp.valueOf(localDate.atStartOfDay()));

        if(quantity != null && quantity.getText() != null && !quantity.getText().isEmpty() && !quantity.getText().isBlank()){
            ordine.setQuantita(Integer.parseInt(quantity.getText()));
        }
        else{
            ordine.setQuantita(0);
        }

        Stato stato = new Stato();
        stato.setId(1);

        ordine.setStato(stato);

        return ordine;
    }

    //per la modifica ordine
    public Ordine setValuesWithID() throws ParseException { //recuperato da mask
        Ordine ordine = new Ordine();

        ordine.setId(Integer.parseInt(IDkey));
        ordine.setUtente(userOrder);
        ordine.setProdotto(productOrder);

        if(productPrice != null && productPrice.getText() != null && !productPrice.getText().isEmpty() && !productPrice.getText().isBlank()) {
            ordine.setPrezzo_unitario(Utility.formatValueInBigDecimalWithoutZeros(productPrice.getText()));
        }
        else{
            ordine.setPrezzo_unitario(new BigDecimal(0));
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(orderDate.getPromptText(), formatter);

        ordine.setData_ordine(Timestamp.valueOf(localDate.atStartOfDay()));

        if(quantity != null && quantity.getText() != null && !quantity.getText().isEmpty() && !quantity.getText().isBlank()){
            ordine.setQuantita(Integer.parseInt(quantity.getText()));
        }
        else{
            ordine.setQuantita(0);
        }

        Stato stato = new Stato();

        if(status.getValue() != null){
            stato.setId(Integer.parseInt(status.getValue().replaceAll("[^0-9]", "")));
            stato.setCode(status.getValue().replaceAll(".*[^a-zA-Z]", ""));
        }

        ordine.setStato(stato);

        return ordine;
    }

    //------------------NUMBER FIELD (TEXT FIELD WITH PATTERN)-----------------------

    @FXML
    private void patternNumberQuantity(KeyEvent event){
        String check = event.getCharacter();
        if (!check.matches("[0-9]")) {
            String currentText = quantity.getText();
            quantity.setText(currentText.replaceAll("[^0-9]", ""));
        }
        else {
            System.out.println("OK");
        }
    }

    //------------------POP OVER (ON MOUSE ENTERED AND EXITED)-----------------------

    Locale locale = new Locale(Translater.getLanguage()); // Setti il linguaggio di default da prendere il resource
    ResourceBundle resLang = ResourceBundle.getBundle("org.languages.language", locale); //prende la risorsa dove ci sono i messaggi già citati

    @FXML
    private void showPopOver(MouseEvent event){
        if(popOver == null){ //controlla se è vuoto
            Label info = new Label(); // Crea un label
            info.setText(resLang.getString("popover.text")); // Testo da visualizzare
            info.setTextFill(Color.rgb(63, 81, 181));
            info.setFont(new Font("Press Start 2P", 9));
            info.setWrapText(true);
            info.setTextAlignment(TextAlignment.CENTER);

            // Crea il VBox per il popover
            VBox vBox = new VBox(info);
            vBox.setPrefWidth(130);
            vBox.setPrefHeight(10); // Altezza per includere anche la freccia
            vBox.setAlignment(Pos.CENTER);

            // Crea il popover
            popOver = new PopOver(vBox);
            popOver.setAnimated(false); // Disabilita l'animazione
            popOver.setCornerRadius(10);
        }
        popOver.show((Node) event.getSource()); //verrà mostrato solo quando il cursore si trova sopra al text area
    }

    @FXML
    private void hidePopOver(MouseEvent event){
        if(popOver != null && popOver.isShowing()){  //controllo se non è vuoto e se sta mostrando
            popOver.hide(); //verrà nascosto solo quando il cursore non si trova sopra al text area
            popOver = null;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}