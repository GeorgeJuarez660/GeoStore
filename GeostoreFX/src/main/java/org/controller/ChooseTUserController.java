package org.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import org.models.Amministratore;
import org.models.Cliente;
import org.services.LoadPage;
import org.services.Service;
import org.utility.PartialSceneDTO;

public class ChooseTUserController {
    private Cliente user;
    private BorderPane fxmlLoader;
    private Service service;

    //------------------INITIALIZE-----------------------

    public void save(BorderPane fxmlLoader, Cliente utente){

        if(utente instanceof Amministratore){
            Amministratore admin = (Amministratore) utente;
            user = admin;
        }
        else{
            user = utente;
        }

        this.fxmlLoader = fxmlLoader;
    }

    //------------------BUTTONS-----------------------

    @FXML
    private void lookUserProfile() {
        System.out.println("goes to look user profile");
        PartialSceneDTO partialSceneDTO = new PartialSceneDTO();
        partialSceneDTO.setFxmlLoader(fxmlLoader);
        partialSceneDTO.setInnerScene("readProfileUser");
        partialSceneDTO.setUser(user);
        LoadPage.getPartialSceneCRU(partialSceneDTO, null, null);
    }

    @FXML
    private void lookUsers() {
        System.out.println("goes to look users");
        PartialSceneDTO partialSceneDTO = new PartialSceneDTO();
        partialSceneDTO.setFxmlLoader(fxmlLoader);
        partialSceneDTO.setInnerScene("read");
        partialSceneDTO.setItemScene("user");
        partialSceneDTO.setUser(user);
        LoadPage.getPartialSceneCRU(partialSceneDTO, null, null);
    }

    @FXML
    private void lookCodes() {
        System.out.println("goes to code");
        LoadPage.getPartialScene(fxmlLoader, "chooseTCodeAdmin", user, null);
    }

    @FXML
    private void createUser() {
        System.out.println("goes to create user");
        PartialSceneDTO partialSceneDTO = new PartialSceneDTO();
        partialSceneDTO.setFxmlLoader(fxmlLoader);
        partialSceneDTO.setInnerScene("create");
        partialSceneDTO.setItemScene("user");
        partialSceneDTO.setUser(user);
        LoadPage.getPartialSceneCRU(partialSceneDTO, null, null);
    }

}