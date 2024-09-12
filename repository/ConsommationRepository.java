package repository;

import domain.Consommation;
import java.time.LocalDate;
import java.util.List;

public interface ConsommationRepository {
    Consommation save(Consommation consommation, int userId);
    List<Consommation> findByUserId(int userId);
    List<Consommation> findByUserIdAndDateRange(int userId, LocalDate start, LocalDate end);
}