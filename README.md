
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

### **2ème Jalon : Optimisation de la méthode de placement des tiles**

#### Objectif :
Simplifier et optimiser le positionnement des tiles grâce à la méthode du **bitmasking**. Cette approche permettra de :
- Réduire la complexité du code.
- Rendre le code plus lisible et facile à maintenir.

✅ **Statut : Terminé le 31/12/2024.**


---

### **3ème Jalon : Création dynamique de la map (Chunks) en fonction de la position du joueur**

#### Objectif :
Mettre en place une génération dynamique de la carte en divisant celle-ci en **chunks** (zones fixes), inspirée des mécanismes des jeux vidéo.

#### Fonctionnalités prévues :
1. **Division en Chunks** :
   - La carte est découpée en zones de taille fixe pour permettre une génération ciblée et une gestion optimale des ressources.
2. **Génération Dynamique** :
   - Seuls les chunks visibles ou proches de la position actuelle du joueur sont générés ou chargés en mémoire.
3. **Gestion des Transitions** :
   - Les bordures des chunks sont harmonisées pour garantir une continuité visuelle et éviter les ruptures dans l'affichage.
4. **Optimisation des Performances** :
   - Déchargement des chunks éloignés du joueur pour limiter l'utilisation de la mémoire.
   - Génération en arrière-plan via threading pour un jeu fluide.
5. **Compatibilité avec l'Extension Future** :
   - Préparation pour l’ajout de biomes ou autres éléments complexes dans les prochains jalons.

✅ **Statut : Terminé le 01/01/2025.**
---

### **4ème Jalon : Complexification et création de biomes**

#### Objectif :
Par manque de temps, et étant donné que mes camarades ont beaucoup travaillé sur les interactions entre les espèces, les déplacements spécifiques, etc., sans prendre en considération la notion de chunk, qui modifie entièrement la logique des déplacements, j'ai repris ce qui avait été fait au jalon 2 et je me suis limité à une carte de taille fixe, déjà largement suffisante pour notre projet. Nous aurions pu intégrer le concept de chunk pour obtenir une carte de taille infinie, mais cela n'était pas nécessaire dans ce cas. 
L'objectif de ce jalon était de commencer à fusionner les logiques utilisées par mes camarades et les miennes afin de lier nos codes. Pour cela, j'ai introduit un cache de cases (CaseFactory) afin d'éviter de créer de nouvelles cases à chaque fois et de réutiliser celles déjà existantes pour optimiser les calculs. J'ai également factorisé la méthode de mise à jour des bordures du jalon 2. Enfin, le but ultime de ce jalon est d'introduire une nouvelle dimension au jeu avec des biomes variés et interactifs.

#### Fonctionnalités prévues :
1. **Diversité des Biomes** :
   - Types de terrains spécifiques (forêt, désert, montagne, plaine, etc.).
   - Conditions uniques pour chaque biome (ex. densité des arbres, types d’entités, climat).
2. **Transitions Naturelles entre Biomes** :
   - Les zones de transition entre biomes sont générées en douceur, en utilisant des algorithmes basés sur le bruit de Perlin ou d’autres méthodes similaires.
3. **Interactions Dynamiques** :
   - Les entités et objets réagissent différemment selon le biome dans lequel elles se trouvent (par exemple, certaines créatures ne survivent que dans des biomes spécifiques).
4. **Génération Basée sur le Bitmasking** :
   - Utilisation de techniques d'optimisation pour simplifier le placement des tiles tout en garantissant une cohérence visuelle.

--- 


