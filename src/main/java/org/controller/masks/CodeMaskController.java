package org.controller.masks;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
import java.text.ParseException;
import java.util.*;

public class CodeMaskController implements Initializable {

    @FXML
    private TextField code;
    @FXML
    private TextArea description;

    private PopOver popOver;

    private Service service;
    private String IDkey; //usato per la ricerca/modifica/rimozione

    //------------------INITIALIZE-----------------------

    //per la modifica prodotto
    public void getValues(String IDkey){

        service = new Service();
        Codice codice;
        codice = service.ottieniCodice(Integer.parseInt(IDkey));

        code.setText(codice.getCodice());
        description.setText(codice.getDescrizione());

        this.IDkey = IDkey;
    }

    //------------------GETTING FROM CRUD CONTROLLER-----------------------

    //per la creazione codice
    public Codice setValues() throws ParseException { //recuperato da mask
        Codice codice = new Codice();
        service = new Service();

        //funzione random che va da 100 a 999
        Random random = new Random();
        int num = random.nextInt(900) + 100;

        codice.setCodice(code.getText().toUpperCase() + num); //inserisce i primi tre caratteri e il numero random
        codice.setDescrizione(description.getText());

        return codice;
    }

    //per la modifica codice
    public Codice setValuesWithID() throws ParseException { //recuperato da mask
        Codice codice = new Codice();
        service = new Service();

        codice.setId(Integer.parseInt(IDkey));
        codice.setCodice(code.getText());
        codice.setDescrizione(description.getText());

        return codice;
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
    private void showPopOverCode(MouseEvent event){
        if(popOver == null){ //controlla se è vuoto
            Label info = new Label(); // Crea un label
            info.setText(resLang.getString("popover.text")); // Testo da visualizzare
            info.setTextFill(Color.rgb(63, 81, 181));
            info.setFont(new Font("Press Start 2P", 9));
            info.setWrapText(true);
            info.setTextAlignment(TextAlignment.CENTER);

            Label info2 = new Label(); // Crea un label
            info2.setText(resLang.getString("popover.code")); // Testo da visualizzare
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
            popOver = null;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}