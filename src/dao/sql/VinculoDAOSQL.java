package dao.sql;

import dao.VinculoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VinculoDAOSQL implements VinculoDAO {

    @Override
    public void vincular(int jogadorId, int timeId, int numeroCamisa, int partidas) {
        String sql = "INSERT INTO jogador_time (jogador_id, time_id, numero_camisa, partidas) VALUES (?,?,?,?)";
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, jogadorId);
            ps.setInt(2, timeId);
            ps.setInt(3, numeroCamisa);
            ps.setInt(4, partidas);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao vincular (esse jogador já está nesse time?)", e);
        }
    }
}