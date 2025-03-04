package org.controller.items;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.models.*;
import org.services.LoadPage;
import org.services.Service;
import org.utility.PartialSceneDTO;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductTypeButtonController implements Initializable {

    //category and material buttons

    @FXML
    private Label id, name;
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
                    || admin.getCodiceAdmin().getCodice().contains("P")
                    || admin.getCodiceAdmin().getCodice().contains("Q"));
        }
        else{
            user = utente;
            isAdmin = false;
        }

        this.fxmlLoader = fxmlLoader;
    }

    public void setCategoryValues(Categoria categoria){
        id.setText("#" + categoria.getId().toString());
        name.setText(categoria.getNome());
    }

    public void setMaterialValues(Materiale materiale){
        id.setText("#" + materiale.getId().toString());
        name.setText(materiale.getNome());
    }

    public void enableButtons(){
        update.setVisible(isAdmin);
        update.setManaged(isAdmin);
        delete.setVisible(isAdmin);
        delete.setManaged(isAdmin);
    }

    //------------------BUTTONS-----------------------

    @FXML
    private void updatingCategory(ActionEvent event){ //button per andare alla pagina di modifica categoria
        System.out.println("goes to update category");
        event.consume(); //evita che si propaga al pulsante esterno (non viene cliccato il pusante categoria)
        PartialSceneDTO partialSceneDTO = new PartialSceneDTO();
        partialSceneDTO.setFxmlLoader(fxmlLoader);
        partialSceneDTO.setInnerScene("update");
        partialSceneDTO.setItemScene("category");
        partialSceneDTO.setUser(user);
        String idKey = id.getText().replace("#", "");
        LoadPage.getPartialSceneCRU(partialSceneDTO, idKey, null);
    }

    @FXML
    private void updatingMaterial(ActionEvent event){ //button per andare alla pagina di modifica materia
        System.out.println("goes to update material");
        event.consume(); //evita che si propaga al pulsante esterno (non viene cliccato il pusante materia)
        PartialSceneDTO partialSceneDTO = new PartialSceneDTO();
        partialSceneDTO.setFxmlLoader(fxmlLoader);
        partialSceneDTO.setInnerScene("update");
        partialSceneDTO.setItemScene("material");
        partialSceneDTO.setUser(user);
        String idKey = id.getText().replace("#", "");
        LoadPage.getPartialSceneCRU(partialSceneDTO, idKey, null);
    }

    @FXML
    private void deletingCategory(ActionEvent event){ //button per eliminare categoria
        System.out.println("goes to delete category");
        event.consume(); //evita che si propaga al pulsante esterno (non viene cliccato il pusante categoria)
        System.out.println("Start deleting");
        LoadPage.loadingScene("LOAD-DLT", null);

        Service service = new Service();

        //notizia per la creazione categoria


        service.eliminazioneCategoria(id.getText().replace("#", ""), user);
    }

    @FXML
    private void deletingMaterial(ActionEvent event){ //button per eliminare materia
        System.out.println("goes to delete material");
        event.consume(); //evita che si propaga al pulsante esterno (non viene cliccato il pusante materia)
        System.out.println("Start deleting");
        LoadPage.loadingScene("LOAD-DLT", null);

        Service service = new Service();

        service.eliminazioneMateriale(id.getText().replace("#", ""), user);
    }

    @FXML
    private void lookProductsByCategory(){ //button per cercare i prodotti via categoria
        System.out.println("goes to look products by category");
        PartialSceneDTO partialSceneDTO = new PartialSceneDTO();
        partialSceneDTO.setFxmlLoader(fxmlLoader);
        partialSceneDTO.setInnerScene("readProductsByCM");
        partialSceneDTO.setItemScene("product-C");
        partialSceneDTO.setUser(user);
        String idKey = id.getText().replace("#", "");
        LoadPage.getPartialSceneCRU(partialSceneDTO, idKey, null);
    }

    @FXML
    private void lookProductsByMaterial(){ //button per cercare i prodotti via materia
        System.out.println("goes to look products by category");
        PartialSceneDTO partialSceneDTO = new PartialSceneDTO();
        partialSceneDTO.setFxmlLoader(fxmlLoader);
        partialSceneDTO.setInnerScene("readProductsByCM");
        partialSceneDTO.setItemScene("product-M");
        partialSceneDTO.setUser(user);
        String idKey = id.getText().replace("#", "");
        LoadPage.getPartialSceneCRU(partialSceneDTO, idKey, null);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}