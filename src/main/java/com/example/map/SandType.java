package com.example.map;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SandType extends BaseType {
    // Variant possible pour l'eau
    public enum VariantHerbeJaune {
        CENTRE, GAUCHE, DROIT, HAUT, BAS, HAUT_GAUCHE, HAUT_DROITE, BAS_GAUCHE, BAS_DROITE,
        VERTICAL_HAUT, VERTICAL_BAS, VERTICAL_MILIEU, HORIZONTAL_GAUCHE, HORIZONTAL_DROITE,
        HORIZONTAL_MILIEU, UNIQUE
    }

    // Code
    private String code;

    // Map des textures possibles
    private static final Map<VariantHerbeJaune, Image> textureMap = new HashMap<>();

    // Chargement des textures
    static {
        textureMap.put(VariantHerbeJaune.CENTRE, new Image(Objects.requireNonNull(SandType.class.getResourceAsStream("/ressources/textures/baseType/yellow_grass/yellow_grass_centre.png"))));
        textureMap.put(VariantHerbeJaune.GAUCHE, new Image(Objects.requireNonNull(SandType.class.getResourceAsStream("/ressources/textures/baseType/yellow_grass/yellow_grass_gauche.png"))));
        textureMap.put(VariantHerbeJaune.DROIT, new Image(Objects.requireNonNull(SandType.class.getResourceAsStream("/ressources/textures/baseType/yellow_grass/yellow_grass_droite.png"))));
        textureMap.put(VariantHerbeJaune.HAUT, new Image(Objects.requireNonNull(SandType.class.getResourceAsStream("/ressources/textures/baseType/yellow_grass/yellow_grass_haut.png"))));
        textureMap.put(VariantHerbeJaune.BAS, new Image(Objects.requireNonNull(SandType.class.getResourceAsStream("/ressources/textures/baseType/yellow_grass/yellow_grass_bas.png"))));
        textureMap.put(VariantHerbeJaune.HAUT_GAUCHE, new Image(Objects.requireNonNull(SandType.class.getResourceAsStream("/ressources/textures/baseType/yellow_grass/yellow_grass_haut_gauche.png"))));
        textureMap.put(VariantHerbeJaune.HAUT_DROITE, new Image(Objects.requireNonNull(SandType.class.getResourceAsStream("/ressources/textures/baseType/yellow_grass/yellow_grass_haut_droite.png"))));
        textureMap.put(VariantHerbeJaune.BAS_GAUCHE, new Image(Objects.requireNonNull(SandType.class.getResourceAsStream("/ressources/textures/baseType/yellow_grass/yellow_grass_bas_gauche.png"))));
        textureMap.put(VariantHerbeJaune.BAS_DROITE, new Image(Objects.requireNonNull(SandType.class.getResourceAsStream("/ressources/textures/baseType/yellow_grass/yellow_grass_bas_droite.png"))));
        textureMap.put(VariantHerbeJaune.VERTICAL_HAUT, new Image(Objects.requireNonNull(SandType.class.getResourceAsStream("/ressources/textures/baseType/yellow_grass/yellow_grass_vertical_haut_unique.png"))));
        textureMap.put(VariantHerbeJaune.VERTICAL_BAS, new Image(Objects.requireNonNull(SandType.class.getResourceAsStream("/ressources/textures/baseType/yellow_grass/yellow_grass_vertical_bas_unique.png"))));
        textureMap.put(VariantHerbeJaune.VERTICAL_MILIEU, new Image(Objects.requireNonNull(SandType.class.getResourceAsStream("/ressources/textures/baseType/yellow_grass/yellow_grass_vertical_milieu_unique.png"))));
        textureMap.put(VariantHerbeJaune.HORIZONTAL_GAUCHE, new Image(Objects.requireNonNull(SandType.class.getResourceAsStream("/ressources/textures/baseType/yellow_grass/yellow_grass_horizontal_gauche_unique.png"))));
        textureMap.put(VariantHerbeJaune.HORIZONTAL_DROITE, new Image(Objects.requireNonNull(SandType.class.getResourceAsStream("/ressources/textures/baseType/yellow_grass/yellow_grass_horizontal_droite_unique.png"))));
        textureMap.put(VariantHerbeJaune.HORIZONTAL_MILIEU, new Image(Objects.requireNonNull(SandType.class.getResourceAsStream("/ressources/textures/baseType/yellow_grass/yellow_grass_horizontal_milieu_unique.png"))));
        textureMap.put(VariantHerbeJaune.UNIQUE, new Image(Objects.requireNonNull(SandType.class.getResourceAsStream("/ressources/textures/baseType/yellow_grass/yellow_grass_centre_unique.png"))));
    }

    // Type de variant
    private VariantHerbeJaune variant;

    // Constructeur
    public SandType(VariantHerbeJaune variant) {
        super(textureMap.get(variant));
        this.variant = variant;
        this.code = switch (variant) {
            // ICI LE CODE DES TYPES DE BASES
            case CENTRE -> "18" ;
            case GAUCHE -> "19" ;
            case DROIT -> "20" ;
            case HAUT -> "21" ;
            case BAS -> "22" ;
            case HAUT_GAUCHE -> "23" ;
            case HAUT_DROITE -> "24" ;
            case BAS_GAUCHE -> "25" ;
            case BAS_DROITE -> "26" ;
            case VERTICAL_HAUT -> "27" ;
            case VERTICAL_BAS -> "28" ;
            case VERTICAL_MILIEU -> "29" ;
            case HORIZONTAL_GAUCHE -> "30" ;
            case HORIZONTAL_DROITE -> "31" ;
            case HORIZONTAL_MILIEU -> "32" ;
            case UNIQUE -> "33" ;
            default -> throw new IllegalArgumentException("Type d'eau inconnu : " + variant);

        };
    }
    // Getter
    public VariantHerbeJaune getVariant() {
        return variant;
    }
    public String getCode() { return code; }
    public static Map<VariantHerbeJaune, Image> getTextureMap() {
        return textureMap;
    }


    @Override
    public boolean isObstacle() {
        return true;
    }
}
