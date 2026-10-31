/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package championnat;

/**
 * Un match du championnat : température, deux équipes et leurs scores.
 */
public class Match {

    private double temperature;
    /**
     * Identifiant du match : 0 tant que le match n'est pas enregistré, puis
     * numéro attribué par MySQL.
     */
    private int id;
    private int scoreDomicile;
    private int scoreExterieur;
    private Equipe equipeDomicile;
    private Equipe equipeExterieur;

    public Match(double temperature, Equipe equipeDomicile, int scoreDomicile,
            Equipe equipeExterieur, int scoreExterieur) {
        if (equipeDomicile == equipeExterieur) {
            throw new IllegalArgumentException("Une équipe ne peut pas jouer contre elle-même.");
        }
        this.temperature = temperature;
        this.scoreDomicile = scoreDomicile;
        this.equipeDomicile = equipeDomicile;
        this.equipeExterieur = equipeExterieur;

        this.scoreExterieur = scoreExterieur;

    }

    public double getTemperature() {
        return temperature;
    }

    public int getId() {
        return id;
    }

    public int getScoreDomicile() {
        return scoreDomicile;
    }

    public int getScoreExterieur() {
        return scoreExterieur;
    }

    public Equipe getEquipeDomicile() {
        return equipeDomicile;
    }

    public Equipe getEquipeExterieur() {
        return equipeExterieur;
    }

    public void setId(int id) {
        this.id = id;

    }

}
