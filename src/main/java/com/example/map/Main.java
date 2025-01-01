package com.example.map;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;


public class Main extends javafx.application.Application {
    // Définition de la taille des cases
    private static final int TILE_SIZE = 20;

    // Le jeu
    private MapGeneration mapGen;

    public void start(Stage primaryStage) {
        Player player = new Player();
        Scanner scanner = new Scanner(System.in);

        mapGen = new MapGeneration();
        mapGen.updateChunksAroundPlayer(player);
        mapGen.updateBorders();

        // Sauvegarder les chunks actifs
        mapGen.saveChunks();

        // Taille de la scène en fonction de la vision du joueur
        int sceneWidth = (2 * player.getVisionRange()) * TILE_SIZE;
        int sceneHeight = (2 * player.getVisionRange()) * TILE_SIZE;

        // Création de la scène et affichage
        Scene scene = new Scene(mapGen.getRoot(), sceneWidth, sceneHeight);
        mapGen.displayMap(TILE_SIZE, player);
        mapGen.getRoot().requestFocus();

        // Titre de la fenêtre
        primaryStage.setTitle("Map Generator");

        primaryStage.setScene(scene);
        primaryStage.show();

        // Evénements clavier
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
                player.setY(Math.min(Math.max(player.getY() - player.getVitesse(), player.getVisionRange()), mapGen.getRows() - player.getVisionRange()));
            } else if (event.getCode() == KeyCode.DOWN) {
                player.setY(Math.min(Math.max(player.getY() + player.getVitesse(), player.getVisionRange()), mapGen.getRows() - player.getVisionRange()));
            } else if (event.getCode() == KeyCode.LEFT) {
                player.setX(Math.min(Math.max(player.getX() - player.getVitesse(), player.getVisionRange()), mapGen.getCols() - player.getVisionRange()));
            } else if (event.getCode() == KeyCode.RIGHT) {
                player.setX(Math.min(Math.max(player.getX() + player.getVitesse(), player.getVisionRange()), mapGen.getCols() - player.getVisionRange()));
            }
        });

        // Initialisation des chunks précédents (On m'a dit d'utiliser AtomicReference pour pas avoir d'erreur)
        AtomicReference<Set<Point>> previousChunks = new AtomicReference<>(new HashSet<Point>(mapGen.getCurrentChunks()));

        // Boucle principale
        // Création du Timeline pour mise à jour périodique
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(50), e -> {
                    mapGen.updateChunksAroundPlayer(player);  // Mettre à jour les chunks actifs à chaque frame

                    // Récupérer les chunks actuels (sans modification)
                    Set<Point> currentChunks = mapGen.getCurrentChunks();

                    // Vérifier si la zone des chunks a changé
                    if (!currentChunks.equals(previousChunks.get())) {
                        // Si les chunks ont changé, mettre à jour les borduress
                        mapGen.updateBorders();
                        previousChunks.set(new HashSet<>(currentChunks));  // Mise à jour des chunks précédents
                    }

                    mapGen.displayMap(TILE_SIZE, player);  // Affichage des nouveaux chunks
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE); // Répéter indéfiniment
        timeline.play(); // Lancer la mise à jour périodique
    }


    public static void main(String[] args) {
        launch(args);
    }
}
