package domain;

import java.time.LocalDate;

public abstract class Consommation {
    protected LocalDate date;
    protected double impact;

    public Consommation(LocalDate date) {
        this.date = date;
    }

    public abstract double calculerImpact();

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public double getImpact() { return impact; }
}