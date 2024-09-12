package domain;

import java.time.LocalDate;

public class Alimentation extends Consommation {
    private String typeAliment;
    private double quantite;

    public Alimentation(LocalDate date, String typeAliment, double quantite) {
        super(date);
        this.typeAliment = typeAliment;
        this.quantite = quantite;
    }

    @Override
    public double calculerImpact() {
        double facteur = typeAliment.equalsIgnoreCase("viande") ? 5.0 : 0.5;
        this.impact = quantite * facteur;
        return this.impact;
    }

    // Getters and setters
    public String getTypeAliment() { return typeAliment; }
    public void setTypeAliment(String typeAliment) { this.typeAliment = typeAliment; }
    public double getQuantite() { return quantite; }
    public void setQuantite(double quantite) { this.quantite = quantite; }
}