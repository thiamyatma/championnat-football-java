/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package championnat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author ibrah
 */

/**
 * Accès à la table match_jeu : enregistrement et lecture des matchs.
 * Tout le SQL concernant les matchs est regroupé ici.
 */
public class MatchDAO {
    
     /** Insère un match et récupère l'id généré par MySQL (AUTO_INCREMENT). */
    public static void enregistrer(Match m) throws SQLException {
        String sql = "INSERT INTO match_jeu "
                + "(temperature, equipe_domicile, equipe_exterieur, score_domicile, score_exterieur) "
                + "VALUES (?, ?, ?, ?, ?)";

        try ( Connection c = Connexion.getConnexion();  PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDouble(1, m.getTemperature());
            ps.setString(2, m.getEquipeDomicile().getNom());
            ps.setString(3, m.getEquipeExterieur().getNom());
            ps.setInt(4, m.getScoreDomicile());
            ps.setInt(5, m.getScoreExterieur());

            ps.executeUpdate();

            try ( ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    m.setId(rs.getInt(1));
                }
            }
        }

    }

    public static void afficherTous() throws SQLException {
        String sql = "SELECT * FROM match_jeu ORDER BY id";

        try ( Connection c = Connexion.getConnexion();  PreparedStatement ps = c.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.println("Match n° " + rs.getInt("id"));
                System.out.println("Température : " + rs.getDouble("temperature"));
                System.out.println("Equipe à l'extérieur : " + rs.getString("equipe_exterieur")
                        + ", " + rs.getInt("score_exterieur"));
                System.out.println("Equipe à domicile : " + rs.getString("equipe_domicile")
                        + ", " + rs.getInt("score_domicile"));
                System.out.println();
            }
        }
    }

    public static void afficherTemperatures() throws SQLException {
        String sql = "SELECT MAX(temperature), AVG(temperature) FROM match_jeu";

        try ( Connection c = Connexion.getConnexion();  PreparedStatement ps = c.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                double max = rs.getDouble(1);
                if (rs.wasNull()) {
                    return;                      // aucun match joué : rien à afficher
                }
                double moyenne = rs.getDouble(2);
                System.out.println("Temp. la plus chaude : " + max);
                System.out.println("Temp. moyenne : " + moyenne);
            }
        }
    }

    public static void supprimerTous() throws SQLException {
        String sql = "TRUNCATE TABLE match_jeu";

        try ( Connection c = Connexion.getConnexion();  PreparedStatement ps = c.prepareStatement(sql)) {
            ps.executeUpdate();
        }
    }
}
