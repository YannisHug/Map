
# JavaProjectEcosystem

### **Projet Java - ENSTA UE 3.1**

Ce projet a pour objectif le développement d'un jeu basé sur un **écosystème interactif** en Java. Le joueur évolue sur une carte générée à partir d'un fichier ou aléatoirement, tout en interagissant avec son environnement selon des contraintes définies.

---

## **Méthodologie : SPEED 1 WEEKEND**

Le projet est structuré en plusieurs **jalons**, chacun représentant une étape clé du développement. Les fonctionnalités sont conçues de manière itérative et progressive.

---

## **Contributions de Yannis HUGUENIN : Génération de maps aléatoires**

### **1er Jalon : Génération d'une map simple avec le bruit de Perlin**

#### Objectif :
Le premier jalon vise à poser les bases pour permettre à l'utilisateur de :
1. Créer manuellement une carte à l'aide des différents types de tiles disponibles.
2. Générer une carte aléatoire en utilisant le bruit de Perlin, avec un positionnement des tiles conditionné par les cases environnantes.

Ce travail s'inscrit dans la continuité du projet principal, tout en respectant les logiques d'affichage existantes (GridPane, etc.), et en y ajoutant les fonctionnalités nécessaires à la génération aléatoire.

---

### **Fonctionnalités :**

#### 1. **Création manuelle d'une carte (mapEnvironnement) via une interface graphique** :
- **Interface graphique intuitive** :
    - Affiche en temps réel l'environnement créé par l'utilisateur.
    - Permet d'ajouter des éléments (arbres, nénuphars, etc.) et de modifier les types de terrain de base (ex. herbe) via des boutons.
- **Taille personnalisable** :
    - L'utilisateur peut définir la taille de la carte.
    - Il peut se déplacer librement sur la carte en construction.
- **Limites** :
    - La sélection manuelle des tiles peut être fastidieuse, notamment pour les utilisateurs souhaitant générer rapidement une grande carte de base.

#### 2. **Génération automatique d'une carte (mapGenerer) à l'aide du bruit de Perlin** :
- **Création algorithmique** :
    - Génère une carte à partir d'un bruit de Perlin, avec des règles basées sur les cases environnantes pour positionner les tiles.
- **Interface graphique** :
    - Visualise la carte générée.
    - Permet à l'utilisateur de se déplacer sur la carte générée.
- **Limites** :
    - La carte générée n'est pas modifiable.
    - Les conditions de positionnement des tiles manquent d'optimisation et de lisibilité.

---

#### **Objectif final du 1er Jalon :**
À la fin de ce jalon, l'utilisateur peut :
- Créer une carte manuellement ou la générer automatiquement.
- Se déplacer sur la carte avec une caméra dynamique qui suit ses mouvements.

✅ **Statut : Terminé le 31/12/2024.**

---

### **2ème Jalon : Optimisation et complexification**

#### Objectif :
Simplifier et optimiser le positionnement des tiles grâce à la méthode du **bitmasking**. Cette approche permettra de :
- Réduire la complexité du code.
- Rendre le code plus lisible et facile à maintenir.

---

#### **Fonctionnalités prévues :**
Le deuxième jalon introduira également des interactions entre différents acteurs de l'écosystème (humains, zombies, etc.) pour modéliser un monde vivant et dynamique :
- **Bitmasking pour les tiles** :
    - Utilisation d'un masque binaire pour gérer efficacement les transitions entre types de terrain (ex. sable/herbe, eau/sable).
- **Interactions dynamiques** :
    - Ajout de comportements aléatoires et de transformations dans l'écosystème (ex. zombies infectant des humains).

---
