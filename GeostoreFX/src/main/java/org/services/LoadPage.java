package org.services;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.controller.*;
import org.models.Amministratore;
import org.models.Cliente;
import org.models.Utente;
import org.utility.PartialSceneDTO;
import org.utility.Sounds;
import org.utility.Translater;


import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoadPage {
    @FXML
    private static Stage savedStage; // Questo è il BorderPane di menu.fxml

    public static void saveStage(Event event) {
        //carica lo stage per cambiare scene
        savedStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    }

    public static void saveStageForClose(WindowEvent event) {
        //carica lo stage per cambiare scene
        savedStage = (Stage) event.getSource();
    }

    @FXML
    public static void getFullScene(String choosedScene, String lang) {
        Pane view = null;
        try {
            // Costruisce il percorso completo del file FXML
            lang = lang != null ? lang : Translater.getLanguage(); //per prima cosa controlla la lingua per impostarla
            Locale locale = new Locale(lang); // Setti il linguaggio di default da prendere il resource
            ResourceBundle resLang = ResourceBundle.getBundle("org.languages.language", locale); //prende la risorsa dove ci sono i messaggi già citati

            // Costruisce il percorso completo del file FXML
            URL fileUrl = GeostoreMain.class.getResource("/org/scenes/" + choosedScene + ".fxml");
            if (fileUrl == null) {
                throw new java.io.FileNotFoundException("Nessun file FXML trovato");
            }

            FXMLLoader loader = new FXMLLoader(fileUrl, resLang);
            Pane newScene = loader.load();

            Translater.setLanguage(lang); //conserva la lingua per la prossima volta

            //carica la scena
            double prefWidth = savedStage.getWidth(); //dimensione rimane invariata o mantenuta dall'utente
            double prefHeight = savedStage.getHeight();
            Scene scene = new Scene(newScene);
            savedStage.setWidth(prefWidth);
            savedStage.setHeight(prefHeight); //inserendo dimensioni fisse
            savedStage.setScene(scene);
            savedStage.show();

        } catch (Exception e) {
            System.out.println("No page found. Please check FXMLLoader.");
            e.printStackTrace();
        }
    }

    @FXML
    public static void goesToMenu(Cliente user, String lang, boolean canRefreshUser, boolean newEntry) {
        Pane view = null;
        try {

            //innanzitutto mi faccio un refresh dell'utente per aggiornare i dati in tempo reale

            //aggiorno l'utente loggato se è possibile
            if(canRefreshUser){
                if(user.getId() != null){

                    Service service = new Service();
                    Utente utente = service.ottieniProfiloUtente(user.getId());

                    if(utente instanceof Amministratore){
                        Amministratore admin = (Amministratore) utente;
                        user = admin;
                    }
                    else{
                        Cliente cliente = (Cliente) utente;
                        user = cliente;
                    }

                }
                else{
                    System.out.println("L'utente risulta inesistente");
                }
            }

            // Costruisce il percorso completo del file FXML
            lang = lang != null ? lang : Translater.getLanguage(); //per prima cosa controlla la lingua per impostarla
            Locale locale = new Locale(lang); // Setti il linguaggio di default da prendere il resource
            ResourceBundle resLang = ResourceBundle.getBundle("org.languages.language", locale); //prende la risorsa dove ci sono i messaggi già citati

            URL fileUrl = GeostoreMain.class.getResource("/org/scenes/menu.fxml");
            if (fileUrl == null) {
                throw new java.io.FileNotFoundException("Nessun file FXML trovato");
            }

            if(newEntry){ //se viene effettuato l'accesso per la prima volta
                Sounds.soundWelcome(); //genera il suono per la scena menu
            }

            FXMLLoader loader = new FXMLLoader(fileUrl, resLang);
            Pane newScene = loader.load();
            MenuController menuController = loader.getController(); //Ottieni il controller della scena caricata
            menuController.saveUser(user);
            menuController.loadHomepage(); //carica l'homepage

            Translater.setLanguage(lang); //conserva la lingua per la prossima volta

            //carica la scena
            double prefWidth = savedStage.getWidth(); //dimensione rimane invariata o mantenuta dall'utente
            double prefHeight = savedStage.getHeight();
            Scene scene = new Scene(newScene);
            savedStage.setWidth(prefWidth);
            savedStage.setHeight(prefHeight); //inserendo dimensioni fisse
            savedStage.setScene(scene);
            savedStage.show();

        } catch (Exception e) {
            System.out.println("No page found. Please check FXMLLoader.");
            e.printStackTrace();
        }
    }

    @FXML
    public static void access(String choose, String lang) {
        try {
            String chooseType =  "login" + choose;

            URL fileUrl = null;

            lang = lang != null ? lang : Translater.getLanguage(); //per prima cosa controlla la lingua per impostarla
            Locale locale = new Locale(lang); // Setti il linguaggio di default da prendere il resource
            ResourceBundle resLang = ResourceBundle.getBundle("org.languages.language", locale); //prende la risorsa dove ci sono i messaggi già citati

            // Costruisce il percorso completo del file FXML
            if(chooseType.equals("loginAdmin")){
                fileUrl = GeostoreMain.class.getResource("/org/scenes/loginAdmin.fxml");
                if (fileUrl == null) {
                    throw new java.io.FileNotFoundException("Nessun file FXML trovato");
                }
            }
            else if(chooseType.equals("loginUser")){
                fileUrl = GeostoreMain.class.getResource("/org/scenes/loginCliente.fxml");
                if (fileUrl == null) {
                    throw new java.io.FileNotFoundException("Nessun file FXML trovato");
                }
            }
            else{
                fileUrl = GeostoreMain.class.getResource("/org/scenes/register.fxml");
                if (fileUrl == null) {
                    throw new java.io.FileNotFoundException("Nessun file FXML trovato");
                }
            }

            FXMLLoader loader = new FXMLLoader(fileUrl, resLang);
            Pane newScene = loader.load();
            /*Object controller = loader.getController(); //Ottieni il controller della scena caricata

            if(controller instanceof AccessController){
                AccessController accessController = (AccessController) controller;
            }*/

            Translater.setLanguage(lang); //conserva la lingua per la prossima volta

            //carica la scena
            double prefWidth = savedStage.getWidth();
            double prefHeight = savedStage.getHeight();
            Scene scene = new Scene(newScene);
            savedStage.setWidth(prefWidth);
            savedStage.setHeight(prefHeight);
            savedStage.setScene(scene);
            savedStage.show();

        } catch (Exception e) {
            System.out.println("No page found. Please check FXMLLoader.");
            e.printStackTrace();
        }
    }

    @FXML
    public static void answerScene(String choose, String response, String lang) {
        try {
            URL fileUrl = null;

            lang = lang != null ? lang : Translater.getLanguage(); //per prima cosa controlla la lingua per impostarla
            Locale locale = new Locale(lang); // Setti il linguaggio di default da prendere il resource
            ResourceBundle resLang = ResourceBundle.getBundle("org.languages.language", locale); //prende la risorsa dove ci sono i messaggi già citati

            // Costruisce il percorso completo del file FXML
            if(choose.equals("positive")){
                fileUrl = GeostoreMain.class.getResource("/org/scenes/positiveAnswer.fxml");
                if (fileUrl == null) {
                    throw new java.io.FileNotFoundException("Nessun file FXML trovato");
                }
            }
            else if(choose.equals("negative")){
                fileUrl = GeostoreMain.class.getResource("/org/scenes/negativeAnswer.fxml");
                if (fileUrl == null) {
                    throw new java.io.FileNotFoundException("Nessun file FXML trovato");
                }
            }
            else if(choose.equals("info")){
                fileUrl = GeostoreMain.class.getResource("/org/scenes/infoAnswer.fxml");
                if (fileUrl == null) {
                    throw new java.io.FileNotFoundException("Nessun file FXML trovato");
                }

                Sounds.soundInfo(); //genera il suono per le scene info
            }

            FXMLLoader loader = new FXMLLoader(fileUrl, resLang);
            Pane newScene = loader.load();

            AnswerController answerController = (AnswerController) loader.getController(); //Ottieni il controller della scena caricata
            answerController.response(response, resLang);

            Translater.setLanguage(lang); //conserva la lingua per la prossima volta

            //carica la scena
            double prefWidth = savedStage.getWidth();
            double prefHeight = savedStage.getHeight();
            Scene scene = new Scene(newScene);
            savedStage.setWidth(prefWidth);
            savedStage.setHeight(prefHeight);
            savedStage.setScene(scene);
            savedStage.show();

        } catch (Exception e) {
            System.out.println("No page found. Please check FXMLLoader.");
            e.printStackTrace();
        }
    }

    @FXML
    public static void loadingScene(String response, String lang) {
        try {
            URL fileUrl = null;

            lang = lang != null ? lang : Translater.getLanguage(); //per prima cosa controlla la lingua per impostarla
            Locale locale = new Locale(lang); // Setti il linguaggio di default da prendere il resource
            ResourceBundle resLang = ResourceBundle.getBundle("org.languages.language", locale); //prende la risorsa dove ci sono i messaggi già citati

            // Costruisce il percorso completo del file FXML
            fileUrl = GeostoreMain.class.getResource("/org/scenes/loading.fxml");
            if (fileUrl == null) {
                throw new java.io.FileNotFoundException("Nessun file FXML trovato");
            }

            FXMLLoader loader = new FXMLLoader(fileUrl, resLang);
            Pane newScene = loader.load();

            LoadingController loadingController = (LoadingController) loader.getController(); //Ottieni il controller della scena caricata
            loadingController.response(response, resLang);

            Translater.setLanguage(lang); //conserva la lingua per la prossima volta

            //carica la scena
            double prefWidth = savedStage.getWidth();
            double prefHeight = savedStage.getHeight();
            Scene scene = new Scene(newScene);
            savedStage.setWidth(prefWidth);
            savedStage.setHeight(prefHeight);
            savedStage.setScene(scene);
            savedStage.show();

        } catch (Exception e) {
            System.out.println("No page found. Please check FXMLLoader.");
            e.printStackTrace();
        }
    }

    @FXML
    public static void questionScene(String request, String lang, Cliente user, Object maskController, String idForDelete, boolean boolForReceipt) {
        try {
            URL fileUrl = null;

            lang = lang != null ? lang : Translater.getLanguage(); //per prima cosa controlla la lingua per impostarla
            Locale locale = new Locale(lang); // Setti il linguaggio di default da prendere il resource
            ResourceBundle resLang = ResourceBundle.getBundle("org.languages.language", locale); //prende la risorsa dove ci sono i messaggi già citati

            // Costruisce il percorso completo del file FXML
            fileUrl = GeostoreMain.class.getResource("/org/scenes/question.fxml");
            if (fileUrl == null) {
                throw new java.io.FileNotFoundException("Nessun file FXML trovato");
            }

            FXMLLoader loader = new FXMLLoader(fileUrl, resLang);
            Pane newScene = loader.load();

            QuestionController questionController = (QuestionController) loader.getController(); //Ottieni il controller della scena caricata
            questionController.request(request, resLang, user, maskController, idForDelete, boolForReceipt);

            Translater.setLanguage(lang); //conserva la lingua per la prossima volta

            //carica la scena
            double prefWidth = savedStage.getWidth();
            double prefHeight = savedStage.getHeight();
            Scene scene = new Scene(newScene);
            savedStage.setWidth(prefWidth);
            savedStage.setHeight(prefHeight);
            savedStage.setScene(scene);
            savedStage.show();

        } catch (Exception e) {
            System.out.println("No page found. Please check FXMLLoader.");
            e.printStackTrace();
        }
    }

    @FXML
    public static void getPartialScene(BorderPane fxmlLoader, String innerScene, Cliente user, String lang) {
        Pane view = null;
        try {

            lang = lang != null ? lang : Translater.getLanguage(); //per prima cosa controlla la lingua per impostarla
            Locale locale = new Locale(lang); // Setti il linguaggio di default da prendere il resource
            ResourceBundle resLang = ResourceBundle.getBundle("org.languages.language", locale); //prende la risorsa dove ci sono i messaggi già citati

            // Costruisce il percorso completo del file FXML
            URL fileUrl = GeostoreMain.class.getResource("/org/scenes/" + innerScene + ".fxml");
            if (fileUrl == null) {
                throw new java.io.FileNotFoundException("FXML file can't be found");
            }

            FXMLLoader loader = new FXMLLoader(fileUrl, resLang);
            Pane newScene = loader.load();
            Object controller = loader.getController(); // Ottieni il controller della scena caricata

            // setta i il controller a seconda della scena caricata
            if(controller instanceof HomepageController){
                HomepageController homepageController = (HomepageController) controller;
                homepageController.save(fxmlLoader, user);
                homepageController.title(resLang); //andare a homepage
                homepageController.showButtonCreateNews();
                homepageController.loadNews(resLang);
            }
            else if(controller instanceof InfoController){
                InfoController infoController = (InfoController) controller;
                infoController.save(fxmlLoader, user);
                infoController.setVersion("1.6.0");
            }
            else if(controller instanceof ChooseTUserController){
                ChooseTUserController chooseTUserController = (ChooseTUserController) controller;
                chooseTUserController.save(fxmlLoader, user);
            }
            else if(controller instanceof ChooseTCodeController){
                ChooseTCodeController chooseTCodeController = (ChooseTCodeController) controller;
                chooseTCodeController.save(fxmlLoader, user);
            }
            else if(controller instanceof ChooseTProductController){
                ChooseTProductController chooseTProductController = (ChooseTProductController) controller;
                chooseTProductController.save(fxmlLoader, user);
            }
            else if(controller instanceof ChooseTOrderController){
                ChooseTOrderController chooseTOrderController = (ChooseTOrderController) controller;
                chooseTOrderController.save(fxmlLoader, user);
            }
            else if(controller instanceof OrderTotalPriceController){
                OrderTotalPriceController orderTotalPriceController = (OrderTotalPriceController) controller;
                orderTotalPriceController.save(fxmlLoader, user);
            }
            else if(controller instanceof ChooseTCategoryController){
                ChooseTCategoryController chooseTCategoryController = (ChooseTCategoryController) controller;
                chooseTCategoryController.save(fxmlLoader, user);
            }
            else if(controller instanceof ChooseTMaterialController){
                ChooseTMaterialController chooseTMaterialController = (ChooseTMaterialController) controller;
                chooseTMaterialController.save(fxmlLoader, user);
            }
            // Carica il file FXML
            // Imposta la scena caricata come contenuto centrale del BorderPane
            fxmlLoader.setCenter(newScene);

            Translater.setLanguage(lang); //conserva la lingua per la prossima volta

        } catch (Exception e) {
            System.out.println("No page found. Please check FXMLLoader.");
            e.printStackTrace();
        }
    }

    @FXML
    public static void getPartialSceneCRU(PartialSceneDTO partialSceneDTO, String IDkey, String lang) {
        Pane view = null;
        try {

            lang = lang != null ? lang : Translater.getLanguage(); //per prima cosa controlla la lingua per impostarla
            Locale locale = new Locale(lang); // Setti il linguaggio di default da prendere il resource
            ResourceBundle resLang = ResourceBundle.getBundle("org.languages.language", locale); //prende la risorsa dove ci sono i messaggi già citati

            // Costruisce il percorso completo del file FXML
            URL fileUrl = GeostoreMain.class.getResource("/org/scenes/" + partialSceneDTO.getInnerScene() + ".fxml");
            if (fileUrl == null) {
                throw new java.io.FileNotFoundException("FXML file can't be found");
            }

            FXMLLoader loader = new FXMLLoader(fileUrl, resLang);
            Pane newScene = loader.load();
            Object controller = loader.getController(); // Ottieni il controller della scena caricata

            // setta i il controller a seconda della scena caricata
            if(controller instanceof CreateController){
                CreateController createController = (CreateController) controller;
                createController.save(partialSceneDTO.getFxmlLoader(), partialSceneDTO.getUser(), resLang);
                createController.setTitle(partialSceneDTO.getItemScene());
                createController.setButtonTextAndSaveRpt(partialSceneDTO.getItemScene());
                createController.loadMask(partialSceneDTO.getItemScene(), IDkey);
            }
            else if(controller instanceof CreateAssociateUserController){
                CreateAssociateUserController createAssociateUserController = (CreateAssociateUserController) controller;
                createAssociateUserController.save(partialSceneDTO.getFxmlLoader(), partialSceneDTO.getUser(), resLang);
                createAssociateUserController.loadMask("codeAssociateMask");
            }
            else if(controller instanceof ReadController){
                ReadController readController = (ReadController) controller;
                readController.save(partialSceneDTO.getFxmlLoader(), partialSceneDTO.getUser(), resLang);
                readController.setTitle(partialSceneDTO.getItemScene());
                readController.showSearchBar(false);
                readController.loadItems(partialSceneDTO.getItemScene(), IDkey);
            }
            else if(controller instanceof ReadCodesController){
                ReadCodesController readCodesController = (ReadCodesController) controller;
                readCodesController.save(partialSceneDTO.getFxmlLoader(), partialSceneDTO.getUser(), resLang);
                readCodesController.setTitle(partialSceneDTO.getItemScene());
                readCodesController.showSearchBar(false);
                readCodesController.enableAssociateCodici(partialSceneDTO.getItemScene());
                readCodesController.loadItems(partialSceneDTO.getItemScene(), IDkey);
            }
            else if(controller instanceof ReadProfileUserController){
                ReadProfileUserController readProfileUserController = (ReadProfileUserController) controller;
                readProfileUserController.save(partialSceneDTO.getFxmlLoader(), partialSceneDTO.getUser(), resLang);
                readProfileUserController.setTitle();
                readProfileUserController.loadItem("userProfileItem");
                readProfileUserController.enableBackBtn();
            }
            else if(controller instanceof ReadOrderTotalPriceController){
                ReadOrderTotalPriceController readOrderTotalPriceController = (ReadOrderTotalPriceController) controller;
                readOrderTotalPriceController.save(partialSceneDTO.getFxmlLoader(), partialSceneDTO.getUser(), resLang);
                readOrderTotalPriceController.setTitle(IDkey);
                readOrderTotalPriceController.loadItem("orderItemTotalPrice", IDkey, Boolean.parseBoolean(partialSceneDTO.getItemScene()));
            }
            else if(controller instanceof ReadProductTypeController){
                ReadProductTypeController readProductTypeController = (ReadProductTypeController) controller;
                readProductTypeController.save(partialSceneDTO.getFxmlLoader(), partialSceneDTO.getUser(), resLang);
                readProductTypeController.setTitle(partialSceneDTO.getItemScene());
                readProductTypeController.loadButtons(partialSceneDTO.getItemScene());
                readProductTypeController.enableBackBtn();
            }
            else if(controller instanceof ReadProductsByCMController){
                ReadProductsByCMController readProductsByCMController = (ReadProductsByCMController) controller;
                readProductsByCMController.save(partialSceneDTO.getFxmlLoader(), partialSceneDTO.getUser(), resLang);
                readProductsByCMController.setTitle(partialSceneDTO.getItemScene());
                readProductsByCMController.showSearchBar(false);
                readProductsByCMController.loadItems(partialSceneDTO.getItemScene(), IDkey);
            }
            else if(controller instanceof UpdateController){
                UpdateController updateController = (UpdateController) controller;
                updateController.save(partialSceneDTO.getFxmlLoader(), partialSceneDTO.getUser(), resLang);
                updateController.setTitle(partialSceneDTO.getItemScene());
                updateController.setSaveRpt(partialSceneDTO.getItemScene());
                updateController.loadMask(partialSceneDTO.getItemScene(), IDkey);
            }
            else if(controller instanceof UpdateAssociateUserController){
                UpdateAssociateUserController updateAssociateUserController = (UpdateAssociateUserController) controller;
                updateAssociateUserController.save(partialSceneDTO.getFxmlLoader(), partialSceneDTO.getUser(), resLang);
                updateAssociateUserController.loadMask("codeAssociateMask", IDkey);
            }

            // Carica il file FXML
            // Imposta la scena caricata come contenuto centrale del BorderPane
            partialSceneDTO.getFxmlLoader().setCenter(newScene);

        } catch (Exception e) {
            System.out.println("No page found. Please check FXMLLoader.");
            e.printStackTrace();
        }
    }
}

