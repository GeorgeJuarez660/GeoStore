package org.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controller.items.NewsItemController;
import org.models.*;
import org.services.LoadPage;
import org.services.Service;
import org.utility.PartialSceneDTO;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomepageController {

    @FXML
    private Label title;

    @FXML
    private Button createNews;

    private Cliente user;
    private Boolean isAdmin;
    private BorderPane fxmlLoader;
    private Service service;

    @FXML
    private VBox newsList;

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

    public void title(ResourceBundle resLang) {
        System.out.println("homepage");

        String startMorning = "06:00";
        String endMorning = "12:00";
        String startAfternoon = "12:00";
        String endAfternoon = "17:00";
        String startEvening = "17:00";
        String endEvening = "23:00";
        String startNight = "23:00";
        String endNight = "06:00";

        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formatTime = currentTime.format(formatter);

        String greetings = "";

        if(formatTime.compareTo(startMorning) >= 0 && formatTime.compareTo(endMorning) < 0){
            greetings = resLang.getString("homepage.morningGreetings") + " ";
        }
        else if(formatTime.compareTo(startAfternoon) >= 0 && formatTime.compareTo(endAfternoon) < 0){
            greetings = resLang.getString("homepage.afternoonGreetings") + " ";
        }
        else if(formatTime.compareTo(startEvening) >= 0 && formatTime.compareTo(endEvening) < 0){
            greetings = resLang.getString("homepage.eveningGreetings") + " ";
        }
        else if(formatTime.compareTo(startNight) >= 0 || formatTime.compareTo(endNight) < 0){
            greetings = resLang.getString("homepage.nightGreetings") + " ";
        }
        greetings += user.getNome();
        title.setText(greetings);

        //LoadPage.access("register");
    }

    public void showButtonCreateNews(){
        createNews.setVisible(isAdmin); //rende invisibile
        createNews.setManaged(isAdmin); //ignora la presenza
    }

    public void loadNews(ResourceBundle resLang){
        service = new Service();
        List<News> notizie = new ArrayList<>();

        notizie = service.elencoNotizie();

        for (News notizia : notizie) {
            try {
                // Costruisce il percorso completo del file FXML
                URL fileUrl = getClass().getResource("/org/scenes/items/newsItem.fxml"); //trova la scena news
                if (fileUrl == null) {
                    throw new java.io.FileNotFoundException("FXML file can't be found");
                }

                FXMLLoader loader = new FXMLLoader(fileUrl);
                HBox newsItem = loader.load();
                NewsItemController newsItemController = loader.getController();
                newsItemController.save(fxmlLoader, user);
                newsItemController.setValues(notizia, resLang);
                newsItemController.enableButtons();
                newsList.getChildren().add(newsItem);

                // Carica il file FXML
                // Imposta la scena caricata come contenuto centrale del BorderPane

            } catch (Exception e) {
                System.out.println("No page found. Please check FXMLLoader.");
                e.printStackTrace();
            }
        }
    }

    //------------------BUTTONS-----------------------

    @FXML
    private void loadInfo(){ //button per andare alla pagina info
        System.out.println("goes to info");
        LoadPage.getPartialScene(fxmlLoader, "info", user, null);
    }

    @FXML
    private void creating(){ //button per andare alla pagina di creazione notizia
        System.out.println("goes to create news");
        PartialSceneDTO partialSceneDTO = new PartialSceneDTO();
        partialSceneDTO.setFxmlLoader(fxmlLoader);
        partialSceneDTO.setInnerScene("create");
        partialSceneDTO.setUser(user);
        LoadPage.getPartialSceneCRU(partialSceneDTO, null, null);
    }

    @FXML
    private void searching(){ //button per andare alla pagina di ricerca notizia
        System.out.println("goes to create news");
        PartialSceneDTO partialSceneDTO = new PartialSceneDTO();
        partialSceneDTO.setFxmlLoader(fxmlLoader);
        partialSceneDTO.setInnerScene("read");
        partialSceneDTO.setUser(user);
        LoadPage.getPartialSceneCRU(partialSceneDTO, null, null);
    }

}