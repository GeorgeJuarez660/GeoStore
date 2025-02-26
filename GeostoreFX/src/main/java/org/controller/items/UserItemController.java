package org.controller.items;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import org.models.Amministratore;
import org.models.Cliente;
import org.models.Utente;
import org.services.LoadPage;
import org.services.Service;
import org.utility.PartialSceneDTO;

import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

public class UserItemController implements Initializable {

    @FXML
    private Label id, name, surname, sex, bornDate, phoneNumber, address, email, password, adminCode, wallet;
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

    public void setValues(Utente utente){

        Calendar calendario = Calendar.getInstance();
        calendario.setTime(utente.getDataNascita());
        int giorno = calendario.get(Calendar.DAY_OF_MONTH);
        int mese = calendario.get(Calendar.MONTH) + 1;
        int anno = calendario.get(Calendar.YEAR);

        String giornoEsatto = String.format("%02d", giorno);
        String meseEsatto = String.format("%02d", mese);

        if(utente instanceof Amministratore){
            Amministratore admin = (Amministratore) utente;
            if(id != null){
                id.setText(admin.getId().toString());
            }
            name.setText(admin.getNome());
            surname.setText(admin.getCognome());
            sex.setText(admin.getSesso());
            bornDate.setText(giornoEsatto+"/"+meseEsatto+"/"+anno);
            phoneNumber.setText(admin.getTelefono());
            address.setText(admin.getIndirizzo());
            email.setText(admin.getEmail());
            password.setText(admin.getPassword());
            adminCode.setText(admin.getCodeAdmin());
            wallet.setText(admin.getPortafoglio().toString()+" C");
        }
        else{
            Cliente cliente = (Cliente) utente;
            if(id != null){
                id.setText(cliente.getId().toString());
            }
            name.setText(cliente.getNome());
            surname.setText(cliente.getCognome());
            sex.setText(cliente.getSesso());
            bornDate.setText(giornoEsatto+"/"+meseEsatto+"/"+anno);
            phoneNumber.setText(cliente.getTelefono());
            address.setText(cliente.getIndirizzo());
            email.setText(cliente.getEmail());
            password.setText(cliente.getPassword());
            adminCode.setText("-");
            wallet.setText(cliente.getPortafoglio().toString()+" C");
        }
    }

    public void enableButtons(){
        update.setVisible(isAdmin);
        update.setManaged(isAdmin);
        delete.setVisible(isAdmin);
        delete.setManaged(isAdmin);
    }

    //------------------BUTTONS-----------------------

    @FXML
    private void updating(){ //button per andare alla pagina di modifica utente
        System.out.println("goes to update user");
        PartialSceneDTO partialSceneDTO = new PartialSceneDTO();
        partialSceneDTO.setFxmlLoader(fxmlLoader);
        partialSceneDTO.setInnerScene("update");
        partialSceneDTO.setItemScene("user");
        partialSceneDTO.setUser(user);
        String idKey = id.getText();
        LoadPage.getPartialSceneCRU(partialSceneDTO, idKey, null);
    }

    @FXML
    private void deleting(){ //button per eliminare utente
        System.out.println("goes to delete user");
        System.out.println("Start deleting");
        LoadPage.loadingScene("LOAD-DLT", null);

        Service service = new Service();

        if(id.getText().equals(user.getId().toString())){
            LoadPage.answerScene("negative", "USR-DLN", null);

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.goesToMenu(user, null);
            });
            delay.play();
        }
        else{
            service.eliminazioneUtente(id.getText(), user);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}