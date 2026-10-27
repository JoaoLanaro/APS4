package dao.sql;

import dao.VinculoDAO;
import model.Vinculo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (implementacao SQL) - Acesso a relacao jogador x time.
 *  - LEITURA: consulta a VIEW vw_jogador_time (ja faz o JOIN das 3 tabelas).
 *  - ESCRITA: INSERT/UPDATE/DELETE direto na tabela jogador_time.
 */

public class VinculoDAOSQL implements VinculoDAO {

    // SELECT base na view; os metodos de leitura acrescentam WHERE/ORDER BY
    private static final String SELECT_VIEW =
            "SELECT jogador_id, time_id, jogador_nome, time_nome, numero_camisa, partidas "
          + "FROM vw_jogador_time";

    // INSERT na tabela relacional. A chave (jogador_id, time_id) nao pode se repetir.
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

    // Todos os vinculos (lidos da view), ordenados por jogador e time.
    @Override
    public List<Vinculo> listarTodos() {
        return consultar(SELECT_VIEW + " ORDER BY jogador_nome, time_nome", null);
    }

    // Vinculos em que o nome do jogador OU do time contem o texto.
    @Override
    public List<Vinculo> buscar(String texto) {
        return consultar(SELECT_VIEW + " WHERE jogador_nome LIKE ? OR time_nome LIKE ? "
                + "ORDER BY jogador_nome, time_nome", "%" + texto + "%");
    }

    // Executa o SELECT na view. Com "like" preenche os dois "?" (jogador e time).
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

    // UPDATE de camisa e partidas. O par (jogador, time) identifica a linha e nao muda. 
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

    // DELETE do vinculo. Nao apaga o jogador nem o time, so a ligacao entre eles. 
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