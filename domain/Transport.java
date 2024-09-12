package domain;

import java.time.LocalDate;

public class Transport extends Consommation {
    private double distance;
    private String typeVehicule;

    public Transport(LocalDate date, double distance, String typeVehicule) {
        super(date);
        this.distance = distance;
        this.typeVehicule = typeVehicule;
    }

    @Override
    public double calculerImpact() {
        double facteur = typeVehicule.equalsIgnoreCase("voiture") ? 0.5 : 0.1;
        this.impact = distance * facteur;
        return this.impact;
    }

    // Getters and setters
    public double getDistance() { return distance; }
    public void setDistance(double distance) { this.distance = distance; }
    public String getTypeVehicule() { return typeVehicule; }
    public void setTypeVehicule(String typeVehicule) { this.typeVehicule = typeVehicule; }
}