package com.example.map;

import processing.core.PApplet;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class MapGeneration extends PApplet {
    //Paramètre de la map
    public GridPane gridPane;
    private int rows;
    private int cols;

    //Gestion des Chunks
    private int chunkSize;
    private int activeRadius = 1; // Rayon en chunks autour du joueur
    private Map<Point, Chunk> loadedChunks = new HashMap<>();
    private Set<Point> currentChunks;

    public float scl; // Paramètre pour le bruit de Perlin
    public final VBox root;



    public MapGeneration(){
        this.gridPane = new GridPane();
        this.rows = 10000; //Nombre de ligne total
        this.cols = 10000; //Nombre de colonne total
        this.chunkSize = 100; // Par exemple 100*100
        this.scl = 0.07f;
        this.root = new VBox();
        root.getChildren().addAll(gridPane);

    }

    private Chunk getOrCreateChunk(int chunkRow, int chunkCol) {
        Point chunkKey = new Point(chunkRow, chunkCol);
        return loadedChunks.computeIfAbsent(chunkKey, key -> generateChunk(chunkRow, chunkCol));
    }

    public Chunk generateChunk(int chunkRow, int chunkCol) {
        Chunk chunk = new Chunk(chunkSize, chunkSize);
        for (int row = 0; row < chunkSize; row++) {
            for (int col = 0; col < chunkSize; col++) {
                int globalCol = chunkCol * chunkSize + col;
                int globalRow = chunkRow * chunkSize + row;
                float noiseValue = noise(globalRow * scl, globalCol * scl);
                // Génération de type basée sur noise
                // (identique à ton code existant)
                chunk.setCase(row, col, noiseValue);
            }
        }
        return chunk;
    }

    public void updateChunksAroundPlayer(Player player) {
        int pos_x = player.x;
        int pos_y = player.y;
        Set<Point> currentChunks = new HashSet<>();

        // Chunk actuel
        int current_chunkCol = pos_x / chunkSize;
        int current_chunkRow = pos_y / chunkSize;

        // Bornes des chunks actifs
        int startChunkCol = Math.max(0, current_chunkCol - activeRadius);
        int startChunkRow = Math.max(0, current_chunkRow - activeRadius);
        int endChunkCol = Math.min(cols / chunkSize - 1, current_chunkCol + activeRadius);
        int endChunkRow = Math.min(rows / chunkSize - 1, current_chunkRow + activeRadius);

        // Charger les chunks actifs
        for (int chunkCol = startChunkCol; chunkCol <= endChunkCol; chunkCol++) {
            for (int chunkRow = startChunkRow; chunkRow <= endChunkRow; chunkRow++) {
                getOrCreateChunk(chunkRow, chunkCol);
                currentChunks.add(new Point(chunkRow, chunkCol));
            }
        }

        this.setCurrentChunks(currentChunks);
        // Nettoyer les chunks inactifs
        cleanInactiveChunks(startChunkRow, endChunkRow, startChunkCol, endChunkCol);
    }

    public void cleanInactiveChunks(int startChunkRow, int endChunkRow, int startChunkCol, int endChunkCol) {
        // Utiliser un itérateur pour éviter ConcurrentModificationException
        Iterator<Map.Entry<Point, Chunk>> iterator = loadedChunks.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Point, Chunk> entry = iterator.next();
            Point chunkKey = entry.getKey();

            int chunkRow = chunkKey.x;
            int chunkCol = chunkKey.y;

            // Si le chunk est hors des bornes actives, on le supprime
            if (chunkRow < startChunkRow || chunkRow > endChunkRow || chunkCol < startChunkCol || chunkCol > endChunkCol) {
                iterator.remove(); // Supprime l'entrée de la map
            }
        }
    }

    public void displayMap(int tileSize, Player player) {
        gridPane.getChildren().clear();

        int startCol = Math.max(player.getX() - player.getVisionRange(), 0);
        int startRow = Math.max(player.getY() - player.getVisionRange(), 0);
        int endCol = Math.min(player.getX() + player.getVisionRange(), cols);
        int endRow = Math.min(player.getY() + player.getVisionRange(), rows);

        // Limites d'affichage
        for (int row = startRow; row < endRow; row++) {
            for (int col = startCol; col < endCol; col++) {
                // Déterminer le chunk contenant cette cellule
                int chunkRow = row / chunkSize;
                int chunkCol = col / chunkSize;

                // Récupérer le chunk ou ignorer si non chargé
                Chunk chunk = loadedChunks.get(new Point(chunkRow, chunkCol));
                if (chunk == null) continue;

                // Coordonnées locales dans le chunk
                int localRow = row % chunkSize;
                int localCol = col % chunkSize;

                // Récupérer la case dans le chunk
                Case cell = chunk.getCase(localRow, localCol);

                // Créer un conteneur pour empiler les types de bases et les éléments
                StackPane cellPane = new StackPane();

                // Fond (case de la grille) avec texture
                ImageView baseImage = new ImageView(cell.getBaseType().getTexture());
                baseImage.setFitWidth(tileSize);
                baseImage.setFitHeight(tileSize);
                cellPane.getChildren().add(baseImage);

                // Texture de l'élément (si présent)
                if (cell.getElement() != null) {
                    ImageView elementImage = new ImageView(cell.getElement().getTexture());
                    elementImage.setFitWidth(tileSize);
                    elementImage.setFitHeight(tileSize);
                    cellPane.getChildren().add(elementImage);
                }

                // Ajouter le conteneur au GridPane
                gridPane.add(cellPane, col - startCol, row - startRow); // Décalage pour afficher correctement
            }
        }
    }

    public void saveChunks() {
        String filePath = "chunks_map.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Point chunkKey : loadedChunks.keySet()) {
                Chunk chunk = loadedChunks.get(chunkKey);
                writer.write("Chunk at (" + chunkKey.x + "," + chunkKey.y + "):\n");

                for (int row = 0; row < chunkSize; row++) {
                    for (int col = 0; col < chunkSize; col++) {
                        Case currentCase = chunk.getCase(row, col);
                        writer.write(currentCase.getCode() + " ");
                    }
                    writer.newLine();
                }

                writer.newLine(); // Séparer chaque chunk par une ligne vide
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de l'écriture dans le fichier : " + e.getMessage());
        }
    }

    public void updateBorders() {
        for (Point chunkKey : loadedChunks.keySet()) {
            Chunk chunk = loadedChunks.get(chunkKey);

            for (int localRow = 0; localRow < chunkSize; localRow++) {
                for (int localCol = 0; localCol < chunkSize; localCol++) {
                    Case currentCase = chunk.getCase(localRow, localCol);
                    BaseType currentType = currentCase.getBaseType();

                    if (currentType instanceof SableType || currentType instanceof EauType) {
                        BaseType top = getNeighborBaseType(chunkKey, localRow - 1, localCol);
                        BaseType bottom = getNeighborBaseType(chunkKey, localRow + 1, localCol);
                        BaseType left = getNeighborBaseType(chunkKey, localRow, localCol - 1);
                        BaseType right = getNeighborBaseType(chunkKey, localRow, localCol + 1);

                        Class<? extends BaseType> oppositeType = null;
                        if (currentType instanceof EauType) {
                            oppositeType = SableType.class;
                        } else if (currentType instanceof SableType) {
                            oppositeType = HerbeType.class;
                        }

                        int mask = 0;

                        if (top != null && top.getClass().equals(oppositeType)) {
                            mask |= 0b0001;
                        }
                        if (bottom != null && bottom.getClass().equals(oppositeType)) {
                            mask |= 0b0010;
                        }
                        if (left != null && left.getClass().equals(oppositeType)) {
                            mask |= 0b0100;
                        }
                        if (right != null && right.getClass().equals(oppositeType)) {
                            mask |= 0b1000;
                        }

                        bitMasking(currentCase, mask);
                    }
                }
            }
        }
    }

    private BaseType getNeighborBaseType(Point currentChunkKey, int localRow, int localCol) {
        // Déterminer si le voisin est dans un autre chunk
        int neighborChunkRow = currentChunkKey.x;
        int neighborChunkCol = currentChunkKey.y;

        if (localRow < 0) {
            neighborChunkRow -= 1;
            localRow += chunkSize;
        } else if (localRow >= chunkSize) {
            neighborChunkRow += 1;
            localRow -= chunkSize;
        }

        if (localCol < 0) {
            neighborChunkCol -= 1;
            localCol += chunkSize;
        } else if (localCol >= chunkSize) {
            neighborChunkCol += 1;
            localCol -= chunkSize;
        }

        Point neighborChunkKey = new Point(neighborChunkRow, neighborChunkCol);
        Chunk neighborChunk = loadedChunks.get(neighborChunkKey);

        if (neighborChunk != null) {
            return neighborChunk.getCase(localRow, localCol).getBaseType();
        }

        // Si le chunk voisin n'est pas chargé, retourne null
        return null;
    }

    public void bitMasking(Case currentCase, int neigbhors) {
        if (currentCase.getBaseType() instanceof EauType) {
            EauType.VariantWater variant = switch (neigbhors) {
                case 0b1111 -> EauType.VariantWater.UNIQUE;
                case 0b0001 -> EauType.VariantWater.HAUT;
                case 0b0010 -> EauType.VariantWater.BAS;
                case 0b0100 -> EauType.VariantWater.GAUCHE;
                case 0b1000 -> EauType.VariantWater.DROITE;
                case 0b0011 -> EauType.VariantWater.HORIZONTAL_MILIEU;
                case 0b0111 -> EauType.VariantWater.HORIZONTAL_GAUCHE;
                case 0b1011 -> EauType.VariantWater.HORIZONTAL_DROITE;
                case 0b1100 -> EauType.VariantWater.VERTICAL_MILIEU;
                case 0b1110 -> EauType.VariantWater.VERTICAL_BAS;
                case 0b1101 -> EauType.VariantWater.VERTICAL_HAUT;
                case 0b0110 -> EauType.VariantWater.BAS_GAUCHE;
                case 0b1010 -> EauType.VariantWater.BAS_DROITE;
                case 0b0101 -> EauType.VariantWater.HAUT_GAUCHE;
                case 0b1001 -> EauType.VariantWater.HAUT_DROITE;
                default -> EauType.VariantWater.CENTRE;
            };
            currentCase.setBaseType(new EauType(variant));
        } else if (currentCase.getBaseType() instanceof SableType) {
            SableType.VariantSable variant = switch (neigbhors) {
                case 0b1111 -> SableType.VariantSable.UNIQUE;
                case 0b0001 -> SableType.VariantSable.HAUT;
                case 0b0010 -> SableType.VariantSable.BAS;
                case 0b0100 -> SableType.VariantSable.GAUCHE;
                case 0b1000 -> SableType.VariantSable.DROITE;
                case 0b0011 -> SableType.VariantSable.HORIZONTAL_MILIEU;
                case 0b0111 -> SableType.VariantSable.HORIZONTAL_GAUCHE;
                case 0b1011 -> SableType.VariantSable.HORIZONTAL_DROITE;
                case 0b1100 -> SableType.VariantSable.VERTICAL_MILIEU;
                case 0b1110 -> SableType.VariantSable.VERTICAL_BAS;
                case 0b1101 -> SableType.VariantSable.VERTICAL_HAUT;
                case 0b0110 -> SableType.VariantSable.BAS_GAUCHE;
                case 0b1010 -> SableType.VariantSable.BAS_DROITE;
                case 0b0101 -> SableType.VariantSable.HAUT_GAUCHE;
                case 0b1001 -> SableType.VariantSable.HAUT_DROITE;
                default -> SableType.VariantSable.CENTRE;
            };
            currentCase.setBaseType(new SableType(variant));
        }
    }

    //Getters
    private void setCurrentChunks(Set<Point> Chunks) {
        this.currentChunks = Chunks;
    }

    //Getters
    public Set<Point> getCurrentChunks(){
        return currentChunks;
    }
    public VBox getRoot() {
        return root;
    }
    public int getRows() {return rows;
    }
    public int getCols() {
        return cols;
    }
}

