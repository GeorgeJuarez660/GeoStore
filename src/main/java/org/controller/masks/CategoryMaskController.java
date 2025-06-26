package org.controller.masks;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;
import org.models.Categoria;
import org.services.LoadPage;
import org.services.Service;
import org.utility.PartialSceneDTO;
import org.utility.Translater;

import java.io.File;
import java.net.URL;
import java.text.ParseException;
import java.util.*;

public class CategoryMaskController implements Initializable {

    @FXML
    private TextField nameIt, nameEn, nameJa;

    private PopOver popOver;

    private Service service;
    private String IDkey, IDNumKey; //usato per la ricerca/modifica/rimozione

    //------------------INITIALIZE-----------------------

    //per la modifica categoria
    public void getValues(String IDkey){

        service = new Service();
        Categoria categoria;
        categoria = service.ottieniCategoria(IDkey, true);

        // Divide la stringa usando il simbolo "#"
        String[] traduzioni = categoria.getNome().split("#");

        nameIt.setText(traduzioni[0]);
        nameEn.setText(traduzioni[1]);
        nameJa.setText(traduzioni[2]);

        this.IDkey = IDkey;
        this.IDNumKey = categoria.getId().toString();
    }

    //------------------GETTING FROM CRUD CONTROLLER-----------------------

    //per la creazione categoria
    public Categoria setValues() throws ParseException { //recuperato da mask

        Categoria categoria = new Categoria();

        String concatName = nameIt.getText().toUpperCase() + "#" + nameEn.getText().toUpperCase() + "#" + nameJa.getText().toUpperCase();

        //funzione random che va da 100 a 999
        Random random = new Random();
        int num = random.nextInt(900) + 100;

        categoria.setNome(concatName);
        categoria.setCodice("CAT" + num);

        return categoria;
    }

    //per la modifica categoria
    public Categoria setValuesWithID() throws ParseException { //recuperato da mask

        Categoria categoria = new Categoria();

        String concatName = nameIt.getText().toUpperCase() + "#" + nameEn.getText().toUpperCase() + "#" + nameJa.getText().toUpperCase();

        categoria.setId(Integer.parseInt(IDNumKey));
        categoria.setCodice(IDkey);
        categoria.setNome(concatName);

        return categoria;
    }

    //------------------BUTTONS-----------------------

    /*@FXML
    private void fileChoosing(ActionEvent event){ //button per andare alla pagina di modifica categoria
        System.out.println("Choosing img...");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(resLang.getString("filechooser.title"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(resLang.getString("filechooser.type"), "*.png", "*.jpg", "*.jpeg", "*.gif"));

        // Ottiene lo stage principale
        Stage stage = (Stage) img.getScene().getWindow();

        File selectedFile = fileChooser.showOpenDialog(stage);
        if(selectedFile != null){
            System.out.println(selectedFile.getName());
            img.setText(selectedFile.getName());
        }
    }*/

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