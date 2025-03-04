package org.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import org.controlsfx.control.PopOver;
import org.models.Amministratore;
import org.models.Cliente;
import org.models.Codice;
import org.models.UtenteRepository;
import org.services.LoadPage;
import org.services.Service;
import org.utility.Translater;
import org.utility.Utility;
import java.sql.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class AccessController {
    @FXML
    private TextField name, surname, gender, phoneNumber, address, email, adminCode;

    @FXML
    private PasswordField password, confirmPassword;

    @FXML
    private DatePicker bornDate;

    private PopOver popOver;

    private Service service;

    //------------------BUTTONS-----------------------

    @FXML
    private void signup(ActionEvent event) {
        System.out.println("Signing up");
        LoadPage.saveStage(event);
        LoadPage.loadingScene("LOAD-REG", null);

        Cliente user;
        service = new Service();

        if(!password.getText().equals(confirmPassword.getText())){
            LoadPage.answerScene("negative", "PWD-NOMATCH", null);
            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(e -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.getFullScene("register", null);
            });
            delay.play();
        }
        else if(bornDate.getValue() == null){
            LoadPage.answerScene("negative", "BD-ERR", null);
            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(e -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.getFullScene("register", null);
            });
            delay.play();
        }
        else if(!Utility.getAge(Date.valueOf(bornDate.getValue()))){
            LoadPage.answerScene("negative", "AGE-ERR", null);
            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(e -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.getFullScene("register", null);
            });
            delay.play();
        }
        else{
            if(adminCode != null && adminCode.getText() != null && !adminCode.getText().isEmpty() && !adminCode.getText().isBlank()){
                user = new Amministratore();
                Amministratore admin = (Amministratore) user;
                admin.setNome(Utility.getStringFirstLetterMaiusc(name.getText()));
                admin.setCognome(Utility.getStringFirstLetterMaiusc(surname.getText()));
                admin.setGenere(gender.getText().toUpperCase());
                if(admin.checkCorrectBornDate(bornDate.getEditor().getText())){
                    admin.setDataNascita(Date.valueOf(bornDate.getValue()));
                }
                else{
                    admin.setDataNascita(null);
                }
                admin.setTelefono(phoneNumber.getText());
                admin.setIndirizzo(Utility.getStringFirstLetterMaiusc(address.getText()));
                admin.setEmail(email.getText().toLowerCase());
                admin.setPassword(password.getText());
                admin.setPortafoglio(Utility.insertBigDecimal("50"));
                Codice codice = new Codice();
                codice.setCodice(adminCode.getText().toUpperCase());
                admin.setCodeAdmin(codice);
                user = admin;
            }
            else{
                user = new Cliente();
                user.setNome(Utility.getStringFirstLetterMaiusc(name.getText()));
                user.setCognome(Utility.getStringFirstLetterMaiusc(surname.getText()));
                user.setGenere(gender.getText().toUpperCase());
                if(user.checkCorrectBornDate(bornDate.getEditor().getText())){
                    user.setDataNascita(Date.valueOf(bornDate.getValue()));
                }
                else{
                    user.setDataNascita(null);
                }
                user.setTelefono(phoneNumber.getText());
                user.setIndirizzo(Utility.getStringFirstLetterMaiusc(address.getText()));
                user.setEmail(email.getText().toLowerCase());
                user.setPassword(password.getText());
                user.setPortafoglio(Utility.insertBigDecimal("50"));
            }

            service.registerUtente(user);
        }

    }

    @FXML
    private void signin(ActionEvent event) {
        System.out.println("Signing in");
        LoadPage.saveStage(event);
        LoadPage.loadingScene("LOAD-LOG", null);

        Cliente user;
        service = new Service();

        if(adminCode != null && adminCode.getText() != null && !adminCode.getText().isEmpty() && !adminCode.getText().isBlank()){
            user = new Amministratore();
            Amministratore admin = (Amministratore) user;
            admin.setEmail(email.getText().toLowerCase());
            admin.setPassword(password.getText());
            Codice codice = new Codice();
            codice.setCodice(adminCode.getText().toUpperCase());
            admin.setCodeAdmin(codice);
            user = admin;
        }
        else{
            user = new Cliente();
            user.setEmail(email.getText().toLowerCase());
            user.setPassword(password.getText());
        }

        service.loginUtente(user);

    }

    @FXML
    private void back(ActionEvent event) {
        System.out.println("Going back");

        LoadPage.getFullScene("prepage", null);
    }

    //------------------POP OVER (ON MOUSE ENTERED AND EXITED)-----------------------

    Locale locale = new Locale(Translater.getLanguage()); // Setti il linguaggio di default da prendere il resource
    ResourceBundle resLang = ResourceBundle.getBundle("org.languages.language", locale); //prende la risorsa dove ci sono i messaggi già citati

    @FXML
    private void showPopOver(MouseEvent event){
        if(popOver == null){ //controlla se è vuoto
            Label info = new Label(); // Crea un label
            info.setText(resLang.getString("popover.text")); // Testo da visualizzare
            info.setTextFill(Color.rgb(35, 82, 164));
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
    private void showPopOverGender(MouseEvent event){

        Locale locale = new Locale(Translater.getLanguage()); // Setti il linguaggio di default da prendere il resource
        ResourceBundle resLang = ResourceBundle.getBundle("org.languages.language", locale); //prende la risorsa dove ci sono i messaggi già citati

        if(popOver == null){ //controlla se è vuoto
            Label info = new Label(); // Crea un label
            info.setText(resLang.getString("popover.text")); // Testo da visualizzare
            info.setTextFill(Color.rgb(35, 82, 164));
            info.setFont(new Font("Press Start 2P", 9));
            info.setWrapText(true);
            info.setTextAlignment(TextAlignment.CENTER);

            Label info2 = new Label(); // Crea un label
            info2.setText(resLang.getString("popover.gender")); // Testo da visualizzare
            info2.setTextFill(Color.rgb(35, 82, 164));
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
            info.setTextFill(Color.rgb(35, 82, 164));
            info.setFont(new Font("Press Start 2P", 9));
            info.setWrapText(true);
            info.setTextAlignment(TextAlignment.CENTER);

            Label info2 = new Label(); // Crea un label
            info2.setText(resLang.getString("popover.date")); // Testo da visualizzare
            info2.setTextFill(Color.rgb(35, 82, 164));
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
            info.setTextFill(Color.rgb(35, 82, 164));
            info.setFont(new Font("Press Start 2P", 9));
            info.setWrapText(true);
            info.setTextAlignment(TextAlignment.CENTER);

            Label info2 = new Label(); // Crea un label
            info2.setText(resLang.getString("popover.email")); // Testo da visualizzare
            info2.setTextFill(Color.rgb(35, 82, 164));
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
    private void showPopOverConfirmPwd(MouseEvent event){
        if(popOver == null){ //controlla se è vuoto
            Label info = new Label(); // Crea un label
            info.setText(resLang.getString("popover.text")); // Testo da visualizzare
            info.setTextFill(Color.rgb(35, 82, 164));
            info.setFont(new Font("Press Start 2P", 9));
            info.setWrapText(true);
            info.setTextAlignment(TextAlignment.CENTER);

            Label info2 = new Label(); // Crea un label
            info2.setText(resLang.getString("popover.password")); // Testo da visualizzare
            info2.setTextFill(Color.rgb(35, 82, 164));
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
    private void showPopOverCode(MouseEvent event){
        if(popOver == null){ //controlla se è vuoto
            Label info = new Label(); // Crea un label
            info.setText(resLang.getString("popover.text")); // Testo da visualizzare
            info.setTextFill(Color.rgb(35, 82, 164));
            info.setFont(new Font("Press Start 2P", 9));
            info.setWrapText(true);
            info.setTextAlignment(TextAlignment.CENTER);

            Label info2 = new Label(); // Crea un label
            info2.setText(resLang.getString("popover.code")); // Testo da visualizzare
            info2.setTextFill(Color.rgb(35, 82, 164));
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

}