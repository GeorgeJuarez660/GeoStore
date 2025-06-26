package org.controller.masks;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import org.controlsfx.control.PopOver;
import org.models.*;
import org.services.Service;
import org.utility.Translater;

import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class CodeAssociateMaskController implements Initializable {

    @FXML
    private ChoiceBox<String> adminCode, userEmail;

    private PopOver popOver;

    private Service service;
    private String IDkey; //usato per la ricerca/modifica/rimozione

    //------------------INITIALIZE-----------------------

    //per l'associazione /modifica prodotto
    public void setUserEmail(){
        service = new Service();
        Map<Integer, Utente> users = new HashMap<>();
        users = service.elencoUtenti();

        for(Utente utente : users.values()){
            Cliente cliente = (Cliente) utente;
            userEmail.getItems().add(cliente.getId() + " - " + cliente.getEmail());
        }
    }

    public void setAdminCode(){
        service = new Service();
        Map<Integer, Codice> codes = new HashMap<>();
        codes = service.elencoCodici();

        for(Codice codice : codes.values()){
            adminCode.getItems().add(codice.getId() + " - " + codice.getCodice());
        }
    }
    //per la modifica codice associato
    public void getValues(String IDkey){

        service = new Service();
        CodiceAssociateDTO codiceAssociate;
        codiceAssociate = service.ottieniCodiceAssociato(IDkey);

        adminCode.setValue(codiceAssociate.getIdCodice() + " - " + codiceAssociate.getCodiceAdmin());
        userEmail.setValue(codiceAssociate.getIdUtente() + " - " + codiceAssociate.getEmailUtente());

        this.IDkey = IDkey;
    }

    //------------------GETTING FROM CRUD CONTROLLER-----------------------

    //per l'associazione codice
    public CodiceAssociateDTO setValues() throws ParseException { //recuperato da mask
        CodiceAssociateDTO associateDTO = new CodiceAssociateDTO();
        service = new Service();

        if(adminCode.getValue() != null){
            associateDTO.setIdCodice(Integer.parseInt(adminCode.getValue().substring(0, adminCode.getValue().indexOf(" - "))));
            associateDTO.setCodiceAdmin(adminCode.getValue().substring(adminCode.getValue().indexOf(" - ") + 3));
        }
        else{
            associateDTO.setIdCodice(null);
            associateDTO.setCodiceAdmin(null);
        }

        if(userEmail.getValue() != null){
            associateDTO.setIdUtente(Integer.parseInt(userEmail.getValue().substring(0, userEmail.getValue().indexOf(" - "))));
            associateDTO.setEmailUtente(userEmail.getValue().substring(userEmail.getValue().indexOf(" - ") + 3));
        }
        else{
            associateDTO.setIdUtente(null);
            associateDTO.setEmailUtente(null);
        }


        return associateDTO;
    }

    //per la modifica codice
    public CodiceAssociateDTO setValuesWithID() throws ParseException { //recuperato da mask
        CodiceAssociateDTO associateDTO = new CodiceAssociateDTO();
        service = new Service();

        if(adminCode.getValue() != null){
            associateDTO.setKey(IDkey);
            associateDTO.setIdCodice(Integer.parseInt(adminCode.getValue().substring(0, adminCode.getValue().indexOf(" - "))));
            associateDTO.setCodiceAdmin(adminCode.getValue().substring(adminCode.getValue().indexOf(" - ") + 3));
        }
        else{
            associateDTO.setIdCodice(null);
            associateDTO.setCodiceAdmin(null);
        }

        if(userEmail.getValue() != null){
            associateDTO.setIdUtente(Integer.parseInt(userEmail.getValue().substring(0, userEmail.getValue().indexOf(" - "))));
            associateDTO.setEmailUtente(userEmail.getValue().substring(userEmail.getValue().indexOf(" - ") + 3));
        }
        else{
            associateDTO.setIdUtente(null);
            associateDTO.setEmailUtente(null);
        }

        return associateDTO;
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