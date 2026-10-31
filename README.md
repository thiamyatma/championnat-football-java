# Championnat de football (Java + MySQL)

Projet de cours de Licence 2 (Ingénierie Informatique, Polytech Diamniadio – UAM) : un programme Java qui simule un championnat de football à 4 équipes. Les équipes, leurs statistiques et les matchs sont enregistrés dans une base MySQL.

Le sujet est l'exercice « Java Foundations, Section 8 : Le championnat de football » d'Oracle Academy. Le sujet demandait une `ArrayList` pour les matchs ; mon professeur a demandé de la remplacer par une base de données MySQL.

## Comment ça marche

Chaque semaine, l'utilisateur saisit une température (en degrés Fahrenheit, comme dans l'exemple du sujet) :

- si elle est inférieure ou égale à 32, il fait trop froid et aucun match n'est joué ;
- sinon, 2 matchs sont joués avec des adversaires tirés au hasard (chaque équipe joue une fois par semaine) ;
- le nombre maximal de buts d'une équipe augmente avec la température ;
- après 3 semaines de gel consécutives, la saison est terminée.

À la fin, le programme affiche les statistiques de chaque équipe, le détail de chaque match, la température la plus chaude et la température moyenne.

Chaque lancement démarre une nouvelle saison : les statistiques et les matchs de la saison précédente sont remis à zéro. Les données de la dernière saison restent consultables dans MySQL jusqu'au lancement suivant.

## Technologies

- Java 21 (projet Maven, ouvert avec NetBeans)
- JDBC avec `mysql-connector-j` 8.4.0
- MySQL (XAMPP, qui embarque MariaDB)

## Structure du projet

```
ChampionnatFootball/
├── src/main/java/championnat/
│   ├── Equipe.java          une équipe et ses statistiques
│   ├── Match.java           un match (température, équipes, scores)
│   ├── Planificateur.java   la saison : saisie, gel, hasard, bilan
│   ├── EquipeDAO.java       SQL de la table equipe
│   ├── MatchDAO.java        SQL de la table match_jeu
│   ├── Connexion.java       connexion JDBC vers MySQL
│   └── Test.java            point d'entrée (méthode main)
├── sql/championnat.sql      création de la base, des tables et des 4 équipes
├── docs/                    rapport de projet
└── pom.xml
```

## Base de données

Deux tables :

- `equipe` : le nom (clé primaire) et les compteurs (victoires, défaites, nuls, buts marqués, buts concédés) ;
- `match_jeu` : l'identifiant (`AUTO_INCREMENT`), la température, les deux équipes (clés étrangères vers `equipe`) et leurs scores.

## Installation

1. Installer [XAMPP](https://www.apachefriends.org/) et démarrer **MySQL** (et **Apache** pour utiliser phpMyAdmin).
2. Dans phpMyAdmin, importer `sql/championnat.sql`. Attention : le script supprime et recrée les tables `equipe` et `match_jeu` si elles existent déjà.
3. Ouvrir le projet dans NetBeans (JDK 21). Une connexion internet est nécessaire au premier lancement pour que Maven télécharge le connecteur MySQL.
4. Exécuter la classe `Test`.

La connexion utilise `localhost:3306`, l'utilisateur `root` et un mot de passe vide (configuration par défaut de XAMPP). Si ta configuration est différente, modifie les constantes de `Connexion.java`.

## Exemple d'exécution

Saisie : `90`, `70`, `20`, `20`, `20` (extrait ; les scores changent à chaque lancement) :

```
Entrer la Température : 90
Entrer la Température : 70
Entrer la Température : 20
Too cold to play.
Entrer la Température : 20
Too cold to play.
Entrer la Température : 20
Too cold to play.
Season is over

*********RESULTATS*********

Barcelone
Victoires : 1, Défaites : 1, Matchs nuls : 0
Points marqués : 6, Points concédés : 7
...

Match n° 1
Température : 90.0
Equipe à l'extérieur : Barcelone, 1
Equipe à domicile : Paris Saint Germain, 3
...

Temp. la plus chaude : 90.0
Temp. moyenne : 80.0
```

## Limites connues

Ce projet est un exercice pédagogique, avec des limites que je connais :

- un match demande trois écritures séparées (deux sauvegardes d'équipes, un enregistrement de match), sans transaction : une panne au milieu laisserait des statistiques incohérentes ;
- le programme suppose exactement 4 équipes dans la table `equipe` ;
- l'identifiant et le mot de passe MySQL sont écrits en dur dans `Connexion.java` (acceptable pour XAMPP en local, pas pour un vrai projet) ;
- l'historique des saisons n'est pas conservé ;
- chaque requête ouvre sa propre connexion (pas de pool de connexions) ;
- certaines méthodes de `MatchDAO` affichent directement à l'écran au lieu de renvoyer les données.

## Auteur

Ibrahima Yatma Thiam
