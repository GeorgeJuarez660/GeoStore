package org.controller.items;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.models.*;
import org.services.LoadPage;
import org.services.Service;
import org.utility.PartialSceneDTO;
import org.utility.Utility;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductItemController implements Initializable {

    @FXML
    private Label id, name, price, available, category, material, quantity;
    @FXML
    private Button update, delete, shop;

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
                    || admin.getCodeAdmin().contains("P")
                    || admin.getCodeAdmin().contains("Q"));
        }
        else{
            user = utente;
            isAdmin = false;
        }

        this.fxmlLoader = fxmlLoader;
    }

    public void setValues(Prodotto prodotto){

        id.setText(prodotto.getId().toString());
        name.setText(prodotto.getNome());
        price.setText(Utility.formatValueBigDecimal(prodotto.getPrezzo()) + " C");
        available.setText(prodotto.getDisponibilita().getCode());
        category.setText(prodotto.getCategoria().getNome());
        material.setText(prodotto.getMateria().getNome());
        quantity.setText(prodotto.getQuantita_disp().toString());

    }

    public void enableButtons(){
        update.setVisible(isAdmin);
        update.setManaged(isAdmin);
        delete.setVisible(isAdmin);
        delete.setManaged(isAdmin);
        shop.setVisible(false);
        shop.setManaged(false);
    }

    public void enableButtonsForOrder(){
        update.setVisible(false);
        update.setManaged(false);
        delete.setVisible(false);
        delete.setManaged(false);
        shop.setVisible(true);
        shop.setManaged(true);
    }

    //------------------BUTTONS-----------------------

    @FXML
    private void updating(){ //button per andare alla pagina di modifica prodotto
        System.out.println("goes to update product");
        PartialSceneDTO partialSceneDTO = new PartialSceneDTO();
        partialSceneDTO.setFxmlLoader(fxmlLoader);
        partialSceneDTO.setInnerScene("update");
        partialSceneDTO.setItemScene("product");
        partialSceneDTO.setUser(user);
        String idKey = id.getText();
        LoadPage.getPartialSceneCRU(partialSceneDTO, idKey);
    }

    @FXML
    private void deleting(){ //button per eliminare prodotto
        System.out.println("goes to delete product");
        System.out.println("Start deleting");
        LoadPage.loadingScene("LOAD-DLT", null);

        Service service = new Service();

        service.eliminazioneProdotto(id.getText(), user);
    }

    @FXML
    private void shopping(){ //button per andare alla pagina di ordinazione prodotto
        System.out.println("goes to update product");
        PartialSceneDTO partialSceneDTO = new PartialSceneDTO();
        partialSceneDTO.setFxmlLoader(fxmlLoader);
        partialSceneDTO.setInnerScene("create");
        partialSceneDTO.setItemScene("order");
        partialSceneDTO.setUser(user);
        String idKey = id.getText();
        LoadPage.getPartialSceneCRU(partialSceneDTO, idKey);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}