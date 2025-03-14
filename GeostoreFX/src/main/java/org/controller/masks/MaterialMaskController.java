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
import org.models.Materiale;
import org.services.Service;
import org.utility.Translater;

import java.io.File;
import java.net.URL;
import java.text.ParseException;
import java.util.Locale;
import java.util.ResourceBundle;

public class MaterialMaskController implements Initializable {

    @FXML
    private TextField name;
    @FXML
    private Label img;

    private PopOver popOver;

    private Service service;
    private String IDkey; //usato per la ricerca/modifica/rimozione

    //------------------INITIALIZE-----------------------

    //per la modifica materia
    public void getValues(String IDkey){

        service = new Service();
        Materiale materiale;
        materiale = service.ottieniMateriale(Integer.parseInt(IDkey));

        name.setText(materiale.getNome());

        this.IDkey = IDkey;
    }

    //------------------GETTING FROM CRUD CONTROLLER-----------------------

    //per la creazione materia
    public Materiale setValues() throws ParseException { //recuperato da mask
        Materiale materiale = new Materiale();

        materiale.setNome(name.getText().toUpperCase());

        return materiale;
    }

    //per la modifica materia
    public Materiale setValuesWithID() throws ParseException { //recuperato da mask
        Materiale materiale = new Materiale();

        materiale.setId(Integer.parseInt(IDkey));
        materiale.setNome(name.getText().toUpperCase());

        return materiale;
    }

    //------------------BUTTONS-----------------------

    /*@FXML
    private void fileChoosing(ActionEvent event){ //button per andare alla pagina di modifica categoria
        System.out.println("Choosing img...");

        //creazione di un FileChooser per selezionare un file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(resLang.getString("filechooser.title"));
        //aggiunge un filtro per selezionare solo immagini (PNG, JPG, JPEG, GIF)
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(resLang.getString("filechooser.type"), "*.png", "*.jpg", "*.jpeg", "*.gif"));

        //ottiene lo stage principale dal pulsante (img) per aprire il FileChooser nella stessa finestra
        Stage stage = (Stage) img.getScene().getWindow();

        //mostra la finestra di selezione file e attende che l'utente scelga un file
        File selectedFile = fileChooser.showOpenDialog(stage);
        if(selectedFile != null){ //controlla se il file è stato selezionato
            System.out.println(selectedFile.getName());
            img.setText(selectedFile.getName()); //se si allora valorizza il label
        }
    }
*/
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