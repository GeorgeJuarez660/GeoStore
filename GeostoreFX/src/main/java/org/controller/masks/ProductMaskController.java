package org.controller.masks;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
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
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class ProductMaskController implements Initializable {

    @FXML
    private TextField name, price, quantity;
    @FXML
    private ChoiceBox<String> available, category, material;

    private PopOver popOver;

    private Service service;
    private String IDkey; //usato per la ricerca/modifica/rimozione

    //------------------INITIALIZE-----------------------

    //per la creazione/modifica prodotto
    public void setAvailable(){
        service = new Service();
        Map<Integer, Disponibilita> disp = new HashMap<>();
        disp = service.ottieniDisponibilita();

        for(Disponibilita disponibilita : disp.values()){
            available.getItems().add(disponibilita.getId() + " - " + disponibilita.getCode());
        }
    }

    public void setCategory(){
        service = new Service();
        Map<Integer, Categoria> cat = new HashMap<>();
        cat = service.ottieniCategorie();

        for(Categoria categoria : cat.values()){
            category.getItems().add(categoria.getId() + " - " + categoria.getNome());
        }
    }

    public void setMaterial(){
        service = new Service();
        Map<Integer, Materiale> mat = new HashMap<>();
        mat = service.ottieniMateriali();

        for(Materiale materiale : mat.values()){
            material.getItems().add(materiale.getId() + " - " + materiale.getNome());
        }
    }

    //per la modifica prodotto
    public void getValues(String IDkey){

        service = new Service();
        Prodotto prodotto;
        prodotto = service.ottieniProdotto(Integer.parseInt(IDkey));

        name.setText(prodotto.getNome());
        price.setText(Utility.formatValueBigDecimal(prodotto.getPrezzo()));
        available.setValue(prodotto.getDisponibilita().getId() + " - " + prodotto.getDisponibilita().getCode());
        category.setValue(prodotto.getCategoria().getId() + " - " + prodotto.getCategoria().getNome());
        material.setValue(prodotto.getMateriale().getId() + " - " + prodotto.getMateriale().getNome());
        quantity.setText(prodotto.getQuantita_disp().toString());

        this.IDkey = IDkey;
    }

    //------------------GETTING FROM CRUD CONTROLLER-----------------------

    //per la creazione prodotto
    public Prodotto setValues() throws ParseException { //recuperato da mask
        Prodotto prodotto = new Prodotto();
        service = new Service();

        prodotto.setNome(name.getText());

        if(price != null && price.getText() != null && !price.getText().isEmpty() && !price.getText().isBlank()) {
            prodotto.setPrezzo(Utility.formatValueString(price.getText()));
        }
        else{
            prodotto.setPrezzo(new BigDecimal(0));
        }

        Disponibilita disponibilita = new Disponibilita();

        if(available.getValue() != null){
            disponibilita.setId(Integer.parseInt(available.getValue().replaceAll("[^0-9]", "")));
            disponibilita.setCode(available.getValue().replaceAll(".*[^a-zA-Z]", ""));
        }
        else{
            disponibilita.setCode(null);
        }

        prodotto.setDisponibilita(disponibilita);

        Categoria categoria = new Categoria();

        if(category.getValue() != null){
            categoria.setId(Integer.parseInt(category.getValue().replaceAll("[^0-9]", "")));
        }

        prodotto.setCategoria(categoria);

        Materiale materiale = new Materiale();

        if(material.getValue() != null){
            materiale.setId(Integer.parseInt(material.getValue().replaceAll("[^0-9]", "")));
        }

        prodotto.setMateriale(materiale);

        if(quantity != null && quantity.getText() != null && !quantity.getText().isEmpty() && !quantity.getText().isBlank()){
            prodotto.setQuantita_disp(Integer.parseInt(quantity.getText()));
        }
        else{
            prodotto.setQuantita_disp(0);
        }

        return prodotto;
    }

    //per la modifica prodotto
    public Prodotto setValuesWithID() throws ParseException { //recuperato da mask
        Prodotto prodotto = new Prodotto();
        service = new Service();

        prodotto.setId(Integer.parseInt(IDkey));
        prodotto.setNome(name.getText());

        if(price != null && price.getText() != null && !price.getText().isEmpty() && !price.getText().isBlank()) {
            prodotto.setPrezzo(Utility.formatValueString(price.getText()));
        }
        else{
            prodotto.setPrezzo(new BigDecimal(0));
        }

        Disponibilita disponibilita = new Disponibilita();

        if(available.getValue() != null){
            disponibilita.setId(Integer.parseInt(available.getValue().replaceAll("[^0-9]", "")));
        }

        prodotto.setDisponibilita(disponibilita);

        Categoria categoria = new Categoria();

        if(category.getValue() != null){
            categoria.setId(Integer.parseInt(category.getValue().replaceAll("[^0-9]", "")));
        }

        prodotto.setCategoria(categoria);

        Materiale materiale = new Materiale();

        if(material.getValue() != null){
            materiale.setId(Integer.parseInt(material.getValue().replaceAll("[^0-9]", "")));
        }

        prodotto.setMateriale(materiale);

        if(quantity != null && quantity.getText() != null && !quantity.getText().isEmpty() && !quantity.getText().isBlank()){
            prodotto.setQuantita_disp(Integer.parseInt(quantity.getText()));
        }
        else{
            prodotto.setQuantita_disp(0);
        }

        return prodotto;
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

    @FXML
    private void patternNumberPrice(KeyEvent event){
        String check = event.getCharacter();
        if (!check.matches("[0-9,]")) {
            String currentText = price.getText();
            price.setText(currentText.replaceAll("[^0-9,]", ""));
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