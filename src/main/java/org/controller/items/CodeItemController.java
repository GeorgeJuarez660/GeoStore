package org.controller.items;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.models.Amministratore;
import org.models.Cliente;
import org.models.Codice;
import org.services.LoadPage;
import org.services.Service;
import org.utility.PartialSceneDTO;
import org.utility.Sounds;

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
            isAdmin = admin.getCodiceAdmin().getCodice() != null && !admin.getCodiceAdmin().getCodice().isEmpty() && !admin.getCodiceAdmin().getCodice().isBlank() &&
                    (admin.getCodiceAdmin().getCodice().contains("A")
                    || admin.getCodiceAdmin().getCodice().contains("U")
                    || admin.getCodiceAdmin().getCodice().contains("N"));
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
        Sounds.soundGo();

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
        Sounds.soundGo();

        LoadPage.questionScene("Q-DA", null, user, null, id.getText(), false);
    }

    public void startDeleting(String id, Cliente user){
        System.out.println("goes to delete codice");
        System.out.println("Start deleting");
        LoadPage.loadingScene("LOAD-DLT", null);

        Service service = new Service();

        service.eliminazioneCodice(id, user);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}