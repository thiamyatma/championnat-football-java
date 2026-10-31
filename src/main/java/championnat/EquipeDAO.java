/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package championnat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ibrah
 */
/**
 * Accès à la table equipe : lecture et sauvegarde des statistiques des équipes.
 */
public class EquipeDAO {
    
     /** Lit les 4 équipes en base (triées par nom) et recrée les objets Equipe. */
    public static Equipe[] chargerEquipes() throws SQLException {
        String sql = "SELECT * FROM equipe ORDER BY nom";
        Equipe[] equipes = new Equipe[4];
        int i = 0;

        try ( Connection c = Connexion.getConnexion();  PreparedStatement ps = c.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String nom = rs.getString("nom");

                int victoires = rs.getInt("victoires");
                int defaites = rs.getInt("defaites");
                int nuls = rs.getInt("nuls");
                int butsMarques = rs.getInt("buts_marques");
                int butsConcedes = rs.getInt("buts_concedes");

                equipes[i] = new Equipe(nom, victoires, defaites, nuls, butsMarques, butsConcedes);
                i++;
            }
        }
        return equipes;

    }

    public static void sauvegarder(Equipe e) throws SQLException {
        String sql = "UPDATE equipe SET victoires = ?, defaites = ?, nuls = ?, buts_marques = ?, buts_concedes = ?  WHERE nom = ?";

        try ( Connection c = Connexion.getConnexion();  PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, e.getVictoires());
            ps.setInt(2, e.getDefaites());
            ps.setInt(3, e.getNuls());
            ps.setInt(4, e.getTotalButMarque());
            ps.setInt(5, e.getTotalButConcede());
            ps.setString(6, e.getNom());

            ps.executeUpdate();
        }
    }
}
