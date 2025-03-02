package org.controller.masks;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import org.controlsfx.control.PopOver;
import org.models.Amministratore;
import org.models.Cliente;
import org.models.Utente;
import org.services.Service;
import org.utility.Translater;
import org.utility.Utility;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

public class UserMaskController implements Initializable {

    @FXML
    private DatePicker bornDate;
    @FXML
    private TextField name, surname, sex, address, phoneNumber, email, adminCode, wallet;
    @FXML
    private PasswordField password;

    private PopOver popOver;

    private Service service;
    private String IDkey; //usato per la ricerca/modifica/rimozione

    //------------------INITIALIZE-----------------------

    //per la modifica utente
    public void getValues(String IDkey){

        service = new Service();
        Utente utente;
        utente = service.ottieniProfiloUtente(Integer.parseInt(IDkey));

        Calendar calendario = Calendar.getInstance();
        calendario.setTime(utente.getDataNascita());
        int giorno = calendario.get(Calendar.DAY_OF_MONTH);
        int mese = calendario.get(Calendar.MONTH) + 1;
        int anno = calendario.get(Calendar.YEAR);

        name.setText(utente.getNome());
        surname.setText(utente.getCognome());
        sex.setText(utente.getSesso());
        bornDate.setValue(LocalDate.of(anno, mese, giorno));
        address.setText(utente.getIndirizzo());
        phoneNumber.setText(utente.getTelefono());

        if(utente instanceof Amministratore){
            Amministratore admin = (Amministratore) utente;
            email.setText(admin.getEmail());
            password.setText(admin.getPassword());
            adminCode.setText(admin.getCodeAdmin());
            wallet.setText(Utility.formatValueBigDecimal(admin.getPortafoglio()));
        }
        else{
            Cliente cliente = (Cliente) utente;
            email.setText(cliente.getEmail());
            password.setText(cliente.getPassword());
            wallet.setText(Utility.formatValueBigDecimal(cliente.getPortafoglio()));
        }

        this.IDkey = IDkey;
    }

    //------------------GETTING FROM CRUD CONTROLLER-----------------------

    //per la creazione utente
    public Cliente setValues() throws ParseException { //recuperato da mask
        Cliente cliente;
        if(adminCode != null && adminCode.getText() != null && !adminCode.getText().isEmpty() && !adminCode.getText().isBlank()){
            cliente = new Amministratore();
            Amministratore admin = (Amministratore) cliente;
            admin.setCodeAdmin(adminCode.getText().toUpperCase());
            admin.setNome(Utility.getStringFirstLetterMaiusc(name.getText()));
            admin.setCognome(Utility.getStringFirstLetterMaiusc(surname.getText()));
            admin.setSesso(sex.getText().toUpperCase());
            if(admin.checkCorrectBornDate(bornDate.getEditor().getText())){
                admin.setDataNascita(Date.valueOf(bornDate.getValue()));
            }
            else{
                admin.setDataNascita(null);
            }
            admin.setIndirizzo(Utility.getStringFirstLetterMaiusc(address.getText()));
            admin.setTelefono(phoneNumber.getText());
            admin.setEmail(email.getText().toLowerCase());
            admin.setPassword(password.getText());
            if(wallet != null && wallet.getText() != null && !wallet.getText().isEmpty() && !wallet.getText().isBlank()) {
                admin.setPortafoglio(Utility.formatValueString(wallet.getText()));
            }
            else {
                admin.setPortafoglio(new BigDecimal(0));
            }
        }
        else {
            cliente = new Cliente();
            cliente.setNome(Utility.getStringFirstLetterMaiusc(name.getText()));
            cliente.setCognome(Utility.getStringFirstLetterMaiusc(surname.getText()));
            cliente.setSesso(sex.getText().toUpperCase());
            if(cliente.checkCorrectBornDate(bornDate.getEditor().getText())){
                cliente.setDataNascita(Date.valueOf(bornDate.getValue()));
            }
            else{
                cliente.setDataNascita(null);
            }
            cliente.setIndirizzo(Utility.getStringFirstLetterMaiusc(address.getText()));
            cliente.setTelefono(phoneNumber.getText());
            cliente.setEmail(email.getText().toLowerCase());
            cliente.setPassword(password.getText());
            if (wallet != null && wallet.getText() != null && !wallet.getText().isEmpty() && !wallet.getText().isBlank()) {
                cliente.setPortafoglio(Utility.formatValueString(wallet.getText()));
            } else {
                cliente.setPortafoglio(new BigDecimal(0));
            }
        }
        return cliente;
    }

    //per la modifica utente
    public Cliente setValuesWithID() throws ParseException { //recuperato da mask
        Cliente cliente;
        if(adminCode != null && adminCode.getText() != null && !adminCode.getText().isEmpty() && !adminCode.getText().isBlank()){
            cliente = new Amministratore();
            Amministratore admin = (Amministratore) cliente;
            admin.setId(Integer.parseInt(IDkey));
            admin.setCodeAdmin(adminCode.getText().toUpperCase());
            admin.setNome(Utility.getStringFirstLetterMaiusc(name.getText()));
            admin.setCognome(Utility.getStringFirstLetterMaiusc(surname.getText()));
            admin.setSesso(sex.getText().toUpperCase());
            if(admin.checkCorrectBornDate(bornDate.getEditor().getText())){
                admin.setDataNascita(Date.valueOf(bornDate.getValue()));
            }
            else{
                admin.setDataNascita(null);
            }
            admin.setIndirizzo(Utility.getStringFirstLetterMaiusc(address.getText()));
            admin.setTelefono(phoneNumber.getText());
            admin.setEmail(email.getText().toLowerCase());
            admin.setPassword(password.getText());
            if(wallet != null && wallet.getText() != null && !wallet.getText().isEmpty() && !wallet.getText().isBlank()) {
                admin.setPortafoglio(Utility.formatValueString(wallet.getText()));
            }
            else {
                admin.setPortafoglio(new BigDecimal(0));
            }
        }
        else{
            cliente = new Cliente();
            cliente.setId(Integer.parseInt(IDkey));
            cliente.setNome(Utility.getStringFirstLetterMaiusc(name.getText()));
            cliente.setCognome(Utility.getStringFirstLetterMaiusc(surname.getText()));
            cliente.setSesso(sex.getText().toUpperCase());
            if(cliente.checkCorrectBornDate(bornDate.getEditor().getText())){
                cliente.setDataNascita(Date.valueOf(bornDate.getValue()));
            }
            else{
                cliente.setDataNascita(null);
            }
            cliente.setIndirizzo(Utility.getStringFirstLetterMaiusc(address.getText()));
            cliente.setTelefono(phoneNumber.getText());
            cliente.setEmail(email.getText().toLowerCase());
            cliente.setPassword(password.getText());
            if(wallet != null && wallet.getText() != null && !wallet.getText().isEmpty() && !wallet.getText().isBlank()) {
                cliente.setPortafoglio(Utility.formatValueString(wallet.getText()));
            }
            else {
                cliente.setPortafoglio(new BigDecimal(0));
            }
        }
        return cliente;
    }

    //------------------NUMBER FIELD (TEXT FIELD WITH PATTERN)-----------------------

    @FXML
    private void patternNumberWallet(KeyEvent event){
        String check = event.getCharacter();
        if (!check.matches("[0-9,]")) {
            String currentText = wallet.getText();
            wallet.setText(currentText.replaceAll("[^0-9,]", ""));
        }
        else {
            System.out.println("OK");
        }
    }

    @FXML
    private void patternPhoneNumber(KeyEvent event){
        String check = event.getCharacter();
        if (!check.matches("[0-9]")) {
            String currentText = phoneNumber.getText();
            phoneNumber.setText(currentText.replaceAll("[^0-9]", ""));
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
    private void showPopOverSex(MouseEvent event){
        if(popOver == null){ //controlla se è vuoto
            Label info = new Label(); // Crea un label
            info.setText(resLang.getString("popover.text")); // Testo da visualizzare
            info.setTextFill(Color.rgb(63, 81, 181));
            info.setFont(new Font("Press Start 2P", 9));
            info.setWrapText(true);
            info.setTextAlignment(TextAlignment.CENTER);

            Label info2 = new Label(); // Crea un label
            info2.setText(resLang.getString("popover.sex")); // Testo da visualizzare
            info2.setTextFill(Color.rgb(63, 81, 181));
            info2.setFont(new Font("Press Start 2P", 8));
            info2.setWrapText(true);
            info2.setTextAlignment(TextAlignment.CENTER);

            // Crea il VBox per il popover
            VBox vBox = new VBox(6);
            vBox.setPrefWidth(130);
            vBox.setPrefHeight(10); // Altezza per includere anche la freccia
            vBox.setAlignment(Pos.CENTER);
            vBox.getChildren().addAll(info, info2);

            // Crea il popover
            popOver = new PopOver(vBox);
            popOver.setAnimated(false); // Disabilita l'animazione
            popOver.setCornerRadius(10);
        }
        popOver.show((Node) event.getSource()); //verrà mostrato solo quando il cursore si trova sopra al text area
    }

    @FXML
    private void showPopOverBornDate(MouseEvent event){
        if(popOver == null){ //controlla se è vuoto
            Label info = new Label(); // Crea un label
            info.setText(resLang.getString("popover.text")); // Testo da visualizzare
            info.setTextFill(Color.rgb(63, 81, 181));
            info.setFont(new Font("Press Start 2P", 9));
            info.setWrapText(true);
            info.setTextAlignment(TextAlignment.CENTER);

            Label info2 = new Label(); // Crea un label
            info2.setText(resLang.getString("popover.date")); // Testo da visualizzare
            info2.setTextFill(Color.rgb(63, 81, 181));
            info2.setFont(new Font("Press Start 2P", 8));
            info2.setWrapText(true);
            info2.setTextAlignment(TextAlignment.CENTER);

            // Crea il VBox per il popover
            VBox vBox = new VBox(6);
            vBox.setPrefWidth(130);
            vBox.setPrefHeight(10); // Altezza per includere anche la freccia
            vBox.setAlignment(Pos.CENTER);
            vBox.getChildren().addAll(info, info2);

            // Crea il popover
            popOver = new PopOver(vBox);
            popOver.setAnimated(false); // Disabilita l'animazione
            popOver.setCornerRadius(10);
        }
        popOver.show((Node) event.getSource()); //verrà mostrato solo quando il cursore si trova sopra al text area
    }

    @FXML
    private void showPopOverEmail(MouseEvent event){
        if(popOver == null){ //controlla se è vuoto
            Label info = new Label(); // Crea un label
            info.setText(resLang.getString("popover.text")); // Testo da visualizzare
            info.setTextFill(Color.rgb(63, 81, 181));
            info.setFont(new Font("Press Start 2P", 9));
            info.setWrapText(true);
            info.setTextAlignment(TextAlignment.CENTER);

            Label info2 = new Label(); // Crea un label
            info2.setText(resLang.getString("popover.email")); // Testo da visualizzare
            info2.setTextFill(Color.rgb(63, 81, 181));
            info2.setFont(new Font("Press Start 2P", 8));
            info2.setWrapText(true);
            info2.setTextAlignment(TextAlignment.CENTER);

            // Crea il VBox per il popover
            VBox vBox = new VBox(6);
            vBox.setPrefWidth(130);
            vBox.setPrefHeight(10); // Altezza per includere anche la freccia
            vBox.setAlignment(Pos.CENTER);
            vBox.getChildren().addAll(info, info2);

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
            popOver = null; //per pulire l'istanza che cosi posso utilizzare gli altri popOver
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}