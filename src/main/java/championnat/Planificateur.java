/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package championnat;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.sql.SQLException;

/**
 *
 * @author ibrah
 */

/**
 * Gère une saison de championnat : saisie des températures, matchs au hasard,
 * mise à jour des statistiques et enregistrement dans MySQL.
 */
public class Planificateur {
    /** Mélange les équipes, puis joue deux matchs : cases 0-1 et cases 2-3. */

    private Equipe[] equipes;
    private int semainesDeGel;
    private Scanner clavier;
    private static final double DIVISEUR_BUTS = 10;
    private Random hasard = new Random();
    private static final double SEUIL_GEL = 32;
    private static final int SEMAINES_GEL_MAX = 3;

    public Planificateur(Equipe[] equipes) {
        this.equipes = equipes;
        this.clavier = new Scanner(System.in);
        this.semainesDeGel = 0;
    }

    private double lireTemperature() {
        while (true) {
            System.out.print("Entrer la Température : ");
            String ligne = clavier.nextLine().replace(',', '.');
            try {
                double t = Double.parseDouble(ligne);
                if (Double.isNaN(t) || Double.isInfinite(t)) {
                    System.out.println("Valeur invalide, recommence.");
                } else {
                    return t;
                }
            } catch (NumberFormatException e) {
                System.out.println("Ce n'est pas un nombre, recommence.");
            }
        }
    }

    private int genererScore(double temperature) {
        int max = (int) (temperature / DIVISEUR_BUTS);
        max = Math.max(0, Math.min(max, 12));
        return hasard.nextInt(max + 1);
    }

    private void jouerMatch(double temperature, Equipe domicile, Equipe exterieur) throws SQLException {
        int scoreDom = genererScore(temperature);
        int scoreExt = genererScore(temperature);

        Match m = new Match(temperature, domicile, scoreDom, exterieur, scoreExt);

        // 1) le résultat
        if (scoreDom > scoreExt) {
            domicile.ajouterVictoire();
            exterieur.ajouterDefaite();
        } else if (scoreDom < scoreExt) {
            exterieur.ajouterVictoire();
            domicile.ajouterDefaite();

        } else {
            domicile.ajouterNul();
            exterieur.ajouterNul();

        }

        // 2) les buts
        domicile.ajouterButs(scoreDom, scoreExt);
        exterieur.ajouterButs(scoreExt, scoreDom);

        // 3) la sauvegarde
        EquipeDAO.sauvegarder(domicile);
        EquipeDAO.sauvegarder(exterieur);
        MatchDAO.enregistrer(m);
    }

    private void jouerSemaine(double temperature) throws SQLException {
        Collections.shuffle(Arrays.asList(equipes));
        jouerMatch(temperature, equipes[0], equipes[1]);
        jouerMatch(temperature, equipes[2], equipes[3]);

    }

    public void lancerSaison() throws SQLException {
        reinitialiserSaison();
        while (semainesDeGel < SEMAINES_GEL_MAX) {
            double t = lireTemperature();
            if (t <= SEUIL_GEL) {
                semainesDeGel++;
                System.out.println("Too cold to play.");

            } else {
                semainesDeGel = 0;
                jouerSemaine(t);

            }
        }
        System.out.println("Season is over");
        afficherBilan();
    }

    public void afficherBilan() throws SQLException {
        System.out.println();
        System.out.println("*********RESULTATS*********");
        System.out.println();

        Equipe[] resultats = EquipeDAO.chargerEquipes();
        for (Equipe e : resultats) {
            System.out.println(e.getNom());
            System.out.println("Victoires : " + e.getVictoires()
                    + ", Défaites : " + e.getDefaites()
                    + ", Matchs nuls : " + e.getNuls());
            System.out.println("Points marqués : " + e.getTotalButMarque()
                    + ", Points concédés : " + e.getTotalButConcede());
            System.out.println();
        }
        MatchDAO.afficherTous();
        MatchDAO.afficherTemperatures();

    }

    private void reinitialiserSaison() throws SQLException {
        for (Equipe e : equipes) {
            e.setVictoires(0);
            e.setDefaites(0);
            e.setNuls(0);
            e.setTotalButConcede(0);
            e.setTotalButMarque(0);
            EquipeDAO.sauvegarder(e);       // la copie en base
        }
        MatchDAO.supprimerTous();
        semainesDeGel = 0;
    }

}
