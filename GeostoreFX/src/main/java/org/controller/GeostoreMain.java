package org.controller;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.services.LoadPage;
import org.utility.Sounds;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class GeostoreMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Locale locale = new Locale("it"); // Setti il linguaggio di default da prendere il resource
        ResourceBundle resLang = ResourceBundle.getBundle("org.languages.language", locale);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/scenes/welcome.fxml"), resLang);
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("GeoStore");
        stage.setScene(scene);
        stage.show();
        Sounds.soundOpen(); //genera il suono quando apre

        //regola la chiusura del programma
        stage.setOnCloseRequest(event -> {
            event.consume(); //utilizzato per ritardare la chiusura imminente
            Sounds.soundClose(); //genera il suono quando chiude

            LoadPage.saveStageForClose(event);
            LoadPage.getFullScene("goodbye", null);

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event2 -> {
                // Dopo 3 secondi, carica la scena goodbye
                Platform.exit();
            });
            delay.play();
        });
    }

    public static void main(String[] args) {
        launch();
    }
}