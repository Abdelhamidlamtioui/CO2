package domain;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String nom;
    private int age;
    private List<Consommation> consommations;

    public User(String nom, int age) {
        this.nom = nom;
        this.age = age;
        this.consommations = new ArrayList<>();
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public List<Consommation> getConsommations() { return consommations; }

    public void addConsommation(Consommation consommation) {
        consommations.add(consommation);
    }

    public double calculateTotalImpact() {
        return consommations.stream().mapToDouble(Consommation::calculerImpact).sum();
    }
}