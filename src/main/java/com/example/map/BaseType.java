package com.example.map;

import javafx.scene.image.Image;

// Type de base, herbe, eau, sol...
public abstract class BaseType {
    // Image
    private final Image texture;


    // Constructeur
    public BaseType(Image texture) {
        this.texture = texture;
    }

    // Getter
    public Image getTexture() {
        return texture;
    }
    public abstract String getCode();

    // Méthode pour savoir si le type est un obstacle
    public abstract boolean isObstacle();

}
