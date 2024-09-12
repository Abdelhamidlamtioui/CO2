package domain;

import java.time.LocalDate;

public class Logement extends Consommation {
    private double consommationEnergie;
    private String typeEnergie;

    public Logement(LocalDate date, double consommationEnergie, String typeEnergie) {
        super(date);
        this.consommationEnergie = consommationEnergie;
        this.typeEnergie = typeEnergie;
    }

    @Override
    public double calculerImpact() {
        double facteur = typeEnergie.equalsIgnoreCase("electricite") ? 1.5 : 2.0;
        this.impact = consommationEnergie * facteur;
        return this.impact;
    }

    // Getters and setters
    public double getConsommationEnergie() { return consommationEnergie; }
    public void setConsommationEnergie(double consommationEnergie) { this.consommationEnergie = consommationEnergie; }
    public String getTypeEnergie() { return typeEnergie; }
    public void setTypeEnergie(String typeEnergie) { this.typeEnergie = typeEnergie; }
}