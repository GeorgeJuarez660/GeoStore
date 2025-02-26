package org.controller.items;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.models.Amministratore;
import org.models.Cliente;
import org.models.Codice;
import org.models.Prodotto;
import org.services.LoadPage;
import org.services.Service;
import org.utility.PartialSceneDTO;
import org.utility.Utility;

import java.net.URL;
import java.util.ResourceBundle;

public class CodeItemController implements Initializable {

    @FXML
    private Label id, code;
    @FXML
    private Button update, delete;

    private Cliente user;
    private Boolean isAdmin;
    private BorderPane fxmlLoader;

    //------------------INITIALIZE-----------------------

    public void save(BorderPane fxmlLoader, Cliente utente){

        if(utente instanceof Amministratore){
            Amministratore admin = (Amministratore) utente;
            user = admin;
            isAdmin = admin.getCodeAdmin() != null && !admin.getCodeAdmin().isEmpty() && !admin.getCodeAdmin().isBlank() &&
                    (admin.getCodeAdmin().contains("A")
                    || admin.getCodeAdmin().contains("U")
                    || admin.getCodeAdmin().contains("N"));
        }
        else{
            user = utente;
            isAdmin = false;
        }

        this.fxmlLoader = fxmlLoader;
    }

    public void setValues(Codice codice){

        id.setText(codice.getId().toString());
        code.setText(codice.getCodice());

    }

    public void enableButtons(){
        update.setVisible(isAdmin);
        update.setManaged(isAdmin);
        delete.setVisible(isAdmin);
        delete.setManaged(isAdmin);
    }

    //------------------BUTTONS-----------------------

    @FXML
    private void updating(){ //button per andare alla pagina di modifica codice
        System.out.println("goes to update code");
        PartialSceneDTO partialSceneDTO = new PartialSceneDTO();
        partialSceneDTO.setFxmlLoader(fxmlLoader);
        partialSceneDTO.setInnerScene("update");
        partialSceneDTO.setItemScene("code");
        partialSceneDTO.setUser(user);
        String idKey = id.getText();
        LoadPage.getPartialSceneCRU(partialSceneDTO, idKey, null);
    }

    @FXML
    private void deleting(){ //button per eliminare codice
        System.out.println("goes to delete codice");
        System.out.println("Start deleting");
        LoadPage.loadingScene("LOAD-DLT", null);

        Service service = new Service();

        service.eliminazioneCodice(id.getText(), user);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}