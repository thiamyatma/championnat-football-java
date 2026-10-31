/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package championnat;

/**
 *
 * @author ibrah
 */
/** Une équipe du championnat, avec ses statistiques de saison. */
public class Equipe {
    
    private String nom;
    private int victoires;
    private int defaites;
    private int nuls;
    private int totalButMarque;
    private int totalButConcede;

    public Equipe(String nom) {
        this(nom, 0, 0, 0, 0, 0);
    }

    public Equipe(String nom, int victoires, int defaites, int nuls, int totalButMarque, int totalButConcede) {

        this.nom = nom;
        this.victoires = victoires;
        this.defaites = defaites;
        this.nuls = nuls;
        this.totalButMarque = totalButMarque;
        this.totalButConcede = totalButConcede;

    }

    public String getNom() {
        return nom;
    }

    public int getVictoires() {
        return victoires;
    }

    public int getDefaites() {
        return defaites;
    }

    public int getNuls() {
        return nuls;
    }

    public int getTotalButMarque() {
        return totalButMarque;
    }

    public int getTotalButConcede() {
        return totalButConcede;
    }
    //Setters

    public void setNom(String nom) {
        if (nom != null && !nom.isBlank()) {
            this.nom = nom;
        }
    }

    public void setVictoires(int victoires) {
        if (victoires >= 0) {
            this.victoires = victoires;
        }
    }

    public void setDefaites(int defaites) {
        if (defaites >= 0) {
            this.defaites = defaites;
        }
    }

    public void setNuls(int nuls) {
        if (nuls >= 0) {
            this.nuls = nuls;
        }
    }

    public void setTotalButMarque(int totalButMarque) {
        if (totalButMarque >= 0) {
            this.totalButMarque = totalButMarque;
        }
    }

    public void setTotalButConcede(int totalButConcede) {
        if (totalButConcede >= 0) {
            this.totalButConcede = totalButConcede;
        }
    }

    // Les Methodes
    public void ajouterVictoire() {
        victoires++;
    }

    public void ajouterDefaite() {
        defaites++;
    }

    public void ajouterNul() {
        nuls++;

    }
    /** Ajoute les buts d'un match aux totaux de l'équipe. */
    public void ajouterButs(int marques, int concedes) {
        this.totalButMarque += marques;
        this.totalButConcede += concedes;
    }

}
