package dao.sql;

import dao.TimeDAO;
import model.Time;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (implementacao SQL) - Acesso a tabela "time_". Mesmo padrao do JogadorDAOSQL
 * (try-with-resources, PreparedStatement e conversao de SQLException).
 */

public class TimeDAOSQL implements TimeDAO {

    private static final String COLS = "id, nome, pontos, vitorias, empates, derrotas, gols_pro, gols_contra";

    // Converte a linha atual do ResultSet em um objeto Time. 
    private Time map(ResultSet rs) throws SQLException {
        return new Time(rs.getInt("id"), rs.getString("nome"), rs.getInt("pontos"),
                rs.getInt("vitorias"), rs.getInt("empates"), rs.getInt("derrotas"),
                rs.getInt("gols_pro"), rs.getInt("gols_contra"));
    }

    // Preenche os 7 campos editáveis (usado por INSERT e UPDATE)
    private void preencher(PreparedStatement ps, Time t) throws SQLException {
        ps.setString(1, t.getNome());
        ps.setInt(2, t.getPontos());
        ps.setInt(3, t.getVitorias());
        ps.setInt(4, t.getEmpates());
        ps.setInt(5, t.getDerrotas());
        ps.setInt(6, t.getGolsPro());
        ps.setInt(7, t.getGolsContra());
    }

    @Override
    public List<Time> listarTodos() {
        return buscar("SELECT " + COLS + " FROM time_ ORDER BY nome", null);
    }

    @Override
    public List<Time> buscarPorNome(String nome) {
        return buscar("SELECT " + COLS + " FROM time_ WHERE nome LIKE ? ORDER BY nome", "%" + nome + "%");
    }

    // Executa um SELECT (com ou sem parametro de texto) e devolve a lista de times.
    private List<Time> buscar(String sql, String param) {
        List<Time> lista = new ArrayList<>();
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            if (param != null) ps.setString(1, param);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar times", e);
        }
        return lista;
    }

    @Override
    public Time buscarPorId(int id) {
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT " + COLS + " FROM time_ WHERE id=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar time", e);
        }
    }

    // INSERT de um time novo. O nome e UNIQUE no banco: nome repetido gera erro.
    @Override
    public void add(Time t) {
        String sql = "INSERT INTO time_ (nome, pontos, vitorias, empates, derrotas, gols_pro, gols_contra) "
                   + "VALUES (?,?,?,?,?,?,?)";
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            preencher(ps, t);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar time", e);
        }
    }

    // UPDATE de todos os campos do time identificado pelo id. 
    @Override
    public void atualizar(Time t) {
        String sql = "UPDATE time_ SET nome=?, pontos=?, vitorias=?, empates=?, derrotas=?, "
                   + "gols_pro=?, gols_contra=? WHERE id=?";
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            preencher(ps, t);
            ps.setInt(8, t.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar time", e);
        }
    }

    // DELETE pelo id. Os vinculos do time somem junto (ON DELETE CASCADE no banco).
    @Override
    public void remover(int id) {
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM time_ WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover time", e);
        }
    }
}