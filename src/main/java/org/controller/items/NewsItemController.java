package org.controller.items;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.models.Amministratore;
import org.models.Cliente;
import org.models.News;
import org.models.Prodotto;
import org.services.LoadPage;
import org.services.Service;
import org.utility.PartialSceneDTO;
import org.utility.Sounds;

import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

public class NewsItemController implements Initializable {

    @FXML
    private Label date, text, id;
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

    public void setValues(News notizia, ResourceBundle resLang){
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(notizia.getDataPub());
        int giorno = calendario.get(Calendar.DAY_OF_MONTH);
        int mese = calendario.get(Calendar.MONTH) + 1;
        int anno = calendario.get(Calendar.YEAR);

        String giornoEsatto = String.format("%02d", giorno);
        String meseEsatto = String.format("%02d", mese);

        date.setText(giornoEsatto+"/"+meseEsatto+"/"+anno+":");

        Prodotto prodNews = new Prodotto();

        text.setText(prodNews.dynamicAvailability(notizia.dynamicText(notizia.getTesto(), resLang), resLang));
        id.setText("#"+notizia.getId().toString());
    }

    public void enableButtons(){
        update.setVisible(isAdmin);
        update.setManaged(isAdmin);
        delete.setVisible(isAdmin);
        delete.setManaged(isAdmin);
    }

    //------------------BUTTONS-----------------------

    @FXML
    private void updating(){ //button per andare alla pagina di modifica notizia
        System.out.println("goes to update news");
        Sounds.soundGo();

        PartialSceneDTO partialSceneDTO = new PartialSceneDTO();
        partialSceneDTO.setFxmlLoader(fxmlLoader);
        partialSceneDTO.setInnerScene("update");
        partialSceneDTO.setUser(user);
        String idKey = id.getText().replace("#", "");
        LoadPage.getPartialSceneCRU(partialSceneDTO, idKey, null);
    }

    @FXML
    private void deleting(){ //button per eliminare notizia
        Sounds.soundGo();

        LoadPage.questionScene("Q-DN", null, user, null, id.getText(), false);
    }

    public void startDeleting(String id, Cliente user){
        System.out.println("goes to delete news");
        System.out.println("Start deleting");
        LoadPage.loadingScene("LOAD-DLT", null);

        Service service = new Service();

        service.eliminazioneNotizia(id.replace("#", ""), user);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}