package org.utility;

import javafx.scene.layout.BorderPane;
import org.models.Cliente;

import java.sql.Date;
import java.util.Objects;

public class PartialSceneDTO {
    private BorderPane fxmlLoader;
    private String innerScene;
    private String itemScene;
    private Cliente user;

    public BorderPane getFxmlLoader() {
        return fxmlLoader;
    }

    public void setFxmlLoader(BorderPane fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
    }

    public String getInnerScene() {
        return innerScene;
    }

    public void setInnerScene(String innerScene) {
        this.innerScene = innerScene;
    }

    public String getItemScene() {
        return itemScene;
    }

    public void setItemScene(String itemScene) {
        this.itemScene = itemScene;
    }

    public Cliente getUser() {
        return user;
    }

    public void setUser(Cliente user) {
        this.user = user;
    }
}
