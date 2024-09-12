package service;

import domain.*;
import repository.ConsommationRepository;
import repository.ConsommationRepositoryImpl;
import java.time.LocalDate;
import java.util.List;

public class ConsommationService {
    private ConsommationRepository consommationRepository;

    public ConsommationService() {
        this.consommationRepository = new ConsommationRepositoryImpl();
    }

    public void addTransport(int userId, LocalDate date, double distance, String typeVehicule) {
        Transport transport = new Transport(date, distance, typeVehicule);
        consommationRepository.save(transport, userId);
    }

    public void addLogement(int userId, LocalDate date, double consommationEnergie, String typeEnergie) {
        Logement logement = new Logement(date, consommationEnergie, typeEnergie);
        consommationRepository.save(logement, userId);
    }

    public void addAlimentation(int userId, LocalDate date, String typeAliment, double quantite) {
        Alimentation alimentation = new Alimentation(date, typeAliment, quantite);
        consommationRepository.save(alimentation, userId);
    }

    public List<Consommation> getUserConsommations(int userId) {
        return consommationRepository.findByUserId(userId);
    }

    public List<Consommation> getUserConsommationsByDateRange(int userId, LocalDate start, LocalDate end) {
        return consommationRepository.findByUserIdAndDateRange(userId, start, end);
    }

    public double calculateTotalImpact(int userId) {
        List<Consommation> consommations = getUserConsommations(userId);
        return consommations.stream().mapToDouble(Consommation::calculerImpact).sum();
    }

    public double calculateImpactByDateRange(int userId, LocalDate start, LocalDate end) {
        List<Consommation> consommations = getUserConsommationsByDateRange(userId, start, end);
        return consommations.stream().mapToDouble(Consommation::calculerImpact).sum();
    }
}