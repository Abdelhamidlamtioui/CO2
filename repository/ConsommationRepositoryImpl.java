package repository;

import Config.DbConnection;
import domain.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConsommationRepositoryImpl implements ConsommationRepository {

    @Override
    public Consommation save(Consommation consommation, int userId) {
        String sql = "INSERT INTO consommations (user_id, type, date, impact) VALUES (?, ?, ?, ?)";
        try (Connection conn = DbConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, getTypeAsString(consommation));
            pstmt.setDate(3, Date.valueOf(consommation.getDate()));
            pstmt.setDouble(4, consommation.calculerImpact());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consommation;
    }

    @Override
    public List<Consommation> findByUserId(int userId) {
        List<Consommation> consommations = new ArrayList<>();
        String sql = "SELECT * FROM consommations WHERE user_id = ?";
        try (Connection conn = DbConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                consommations.add(createConsommationFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consommations;
    }

    @Override
    public List<Consommation> findByUserIdAndDateRange(int userId, LocalDate start, LocalDate end) {
        List<Consommation> consommations = new ArrayList<>();
        String sql = "SELECT * FROM consommations WHERE user_id = ? AND date BETWEEN ? AND ?";
        try (Connection conn = DbConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setDate(2, Date.valueOf(start));
            pstmt.setDate(3, Date.valueOf(end));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                consommations.add(createConsommationFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consommations;
    }

    private String getTypeAsString(Consommation consommation) {
        if (consommation instanceof Transport) return "TRANSPORT";
        if (consommation instanceof Logement) return "LOGEMENT";
        if (consommation instanceof Alimentation) return "ALIMENTATION";
        throw new IllegalArgumentException("Unknown Consommation type");
    }

    private Consommation createConsommationFromResultSet(ResultSet rs) throws SQLException {
        String type = rs.getString("type");
        LocalDate date = rs.getDate("date").toLocalDate();
        double impact = rs.getDouble("impact");

        switch (type) {
            case "TRANSPORT":
                return new Transport(date, impact, "");
            case "LOGEMENT":
                return new Logement(date, impact, "");
            case "ALIMENTATION":
                return new Alimentation(date, "", impact);
            default:
                throw new IllegalArgumentException("Unknown Consommation type: " + type);
        }
    }
}