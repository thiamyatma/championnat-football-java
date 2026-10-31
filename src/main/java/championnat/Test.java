/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package championnat;

import java.sql.SQLException;

/**
 * Point d'entrée du programme : charge les équipes depuis MySQL
 * puis lance une saison de championnat.
 */
public class Test {

    public static void main(String[] args) {
        try {
            Equipe[] equipes = EquipeDAO.chargerEquipes();
            Planificateur p = new Planificateur(equipes);
            p.lancerSaison();
        } catch (SQLException ex) {
            System.out.println("Erreur de base de données : " + ex.getMessage());
            System.out.println("Vérifie que MySQL est démarré (XAMPP) et que championnat.sql est importé.");
        }
    }
}