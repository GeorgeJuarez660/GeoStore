package org.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.models.Amministratore;
import org.models.Cliente;
import org.services.LoadPage;

import java.sql.Date;
import java.util.ResourceBundle;

public class LoadingController {
    @FXML
    private Label response;

    //------------------INITIALIZE-----------------------

    public void response(String answer, ResourceBundle resLang) {
        System.out.println(answer);

        switch (answer) {
            case "LOAD-REG":
                response.setText(resLang.getString("loading.register"));
                break;
            case "LOAD-LOG":
                response.setText(resLang.getString("loading.login"));
                break;
            case "LOAD-CHL":
                response.setText(resLang.getString("loading.language"));
                break;
            case "LOAD-CRT":
                response.setText(resLang.getString("loading.create"));
                break;
            case "LOAD-UPT":
                response.setText(resLang.getString("loading.update"));
                break;
            case "LOAD-DLT":
                response.setText(resLang.getString("loading.delete"));
                break;
            case "LOAD-CSC":
                response.setText(resLang.getString("loading.associate"));
                break;
            case "LOAD-USC":
                response.setText(resLang.getString("loading.updateAssociate"));
                break;
            case "LOAD-DSC":
                response.setText(resLang.getString("loading.dissociate"));
                break;
            default:
                break;
        }
    }


}