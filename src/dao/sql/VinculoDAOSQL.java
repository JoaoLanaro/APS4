package dao.sql;

import dao.VinculoDAO;
import model.Vinculo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VinculoDAOSQL implements VinculoDAO {

    // A leitura usa a view (JOIN das 3 tabelas); a escrita vai direto em jogador_time
    private static final String SELECT_VIEW =
            "SELECT jogador_id, time_id, jogador_nome, time_nome, numero_camisa, partidas "
          + "FROM vw_jogador_time";

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

    @Override
    public List<Vinculo> listarTodos() {
        return consultar(SELECT_VIEW + " ORDER BY jogador_nome, time_nome", null);
    }

    @Override
    public List<Vinculo> buscar(String texto) {
        return consultar(SELECT_VIEW + " WHERE jogador_nome LIKE ? OR time_nome LIKE ? "
                + "ORDER BY jogador_nome, time_nome", "%" + texto + "%");
    }

    private List<Vinculo> consultar(String sql, String like) {
        List<Vinculo> lista = new ArrayList<>();
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            if (like != null) {
                ps.setString(1, like);
                ps.setString(2, like);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Vinculo(rs.getInt("jogador_id"), rs.getInt("time_id"),
                            rs.getString("jogador_nome"), rs.getString("time_nome"),
                            rs.getInt("numero_camisa"), rs.getInt("partidas")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar vínculos", e);
        }
        return lista;
    }

    @Override
    public void atualizar(Vinculo v) {
        String sql = "UPDATE jogador_time SET numero_camisa=?, partidas=? WHERE jogador_id=? AND time_id=?";
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, v.getNumeroCamisa());
            ps.setInt(2, v.getPartidas());
            ps.setInt(3, v.getJogadorId());
            ps.setInt(4, v.getTimeId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar vínculo", e);
        }
    }

    @Override
    public void remover(int jogadorId, int timeId) {
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement ps = c.prepareStatement(
                     "DELETE FROM jogador_time WHERE jogador_id=? AND time_id=?")) {
            ps.setInt(1, jogadorId);
            ps.setInt(2, timeId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover vínculo", e);
        }
    }
}