package org.controller.items;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.models.Amministratore;
import org.models.Cliente;
import org.models.Codice;
import org.models.CodiceAssociateDTO;
import org.services.LoadPage;
import org.services.Service;
import org.utility.PartialSceneDTO;

import java.net.URL;
import java.util.ResourceBundle;

public class CodeAssociateItemController implements Initializable {

    @FXML
    private Label email, code;
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

    public void setValues(CodiceAssociateDTO codiceAssociato){

        email.setText(codiceAssociato.getEmailUtente());
        code.setText(codiceAssociato.getCodiceAdmin());

    }

    public void enableButtons(){
        update.setVisible(isAdmin);
        update.setManaged(isAdmin);
        delete.setVisible(isAdmin);
        delete.setManaged(isAdmin);
    }

    //------------------BUTTONS-----------------------

    @FXML
    private void updating(){ //button per andare alla pagina di modifica associazione codice
        System.out.println("goes to update associate code");
        PartialSceneDTO partialSceneDTO = new PartialSceneDTO();
        partialSceneDTO.setFxmlLoader(fxmlLoader);
        partialSceneDTO.setInnerScene("updateAssociateCode");
        partialSceneDTO.setUser(user);
        String emailKey = email.getText();
        LoadPage.getPartialSceneCRU(partialSceneDTO, emailKey, null);
    }

    @FXML
    private void dissociating(){ //button per dissociare codice
        System.out.println("goes to dissociate code");
        System.out.println("Start deleting");
        LoadPage.loadingScene("LOAD-DSC", null);

        Service service = new Service();

        service.dissociazioneCodice(email.getText(), user);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}