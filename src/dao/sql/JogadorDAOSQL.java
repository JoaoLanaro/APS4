package dao.sql;

import dao.JogadorDAO;
import model.Jogador;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JogadorDAOSQL implements JogadorDAO {

    private static final String COLS =
            "id, nome, idade, nacionalidade, posicao, gols, assistencias, cartoes_amarelos, cartoes_vermelhos";

    private Jogador map(ResultSet rs) throws SQLException {
        return new Jogador(rs.getInt("id"), rs.getString("nome"), rs.getInt("idade"),
                rs.getString("nacionalidade"), rs.getString("posicao"), rs.getInt("gols"),
                rs.getInt("assistencias"), rs.getInt("cartoes_amarelos"), rs.getInt("cartoes_vermelhos"));
    }

    // Preenche os 8 campos editáveis (usado por INSERT e UPDATE)
    private void preencher(PreparedStatement ps, Jogador j) throws SQLException {
        ps.setString(1, j.getNome());
        ps.setInt(2, j.getIdade());
        ps.setString(3, j.getNacionalidade());
        ps.setString(4, j.getPosicao());
        ps.setInt(5, j.getGols());
        ps.setInt(6, j.getAssistencias());
        ps.setInt(7, j.getAmarelos());
        ps.setInt(8, j.getVermelhos());
    }

    @Override
    public List<Jogador> listarTodos() {
        return buscar("SELECT " + COLS + " FROM jogador ORDER BY nome", null);
    }

    @Override
    public List<Jogador> buscarPorNome(String nome) {
        return buscar("SELECT " + COLS + " FROM jogador WHERE nome LIKE ? ORDER BY nome", "%" + nome + "%");
    }

    private List<Jogador> buscar(String sql, String param) {
        List<Jogador> lista = new ArrayList<>();
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            if (param != null) ps.setString(1, param);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar jogadores", e);
        }
        return lista;
    }

    @Override
    public Jogador buscarPorId(int id) {
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT " + COLS + " FROM jogador WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar jogador", e);
        }
    }

    @Override
    public void add(Jogador j) {
        String sql = "INSERT INTO jogador (nome, idade, nacionalidade, posicao, gols, assistencias, "
                   + "cartoes_amarelos, cartoes_vermelhos) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            preencher(ps, j);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar jogador", e);
        }
    }

    @Override
    public void atualizar(Jogador j) {
        String sql = "UPDATE jogador SET nome=?, idade=?, nacionalidade=?, posicao=?, gols=?, "
                   + "assistencias=?, cartoes_amarelos=?, cartoes_vermelhos=? WHERE id=?";
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            preencher(ps, j);
            ps.setInt(9, j.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar jogador", e);
        }
    }

    @Override
    public void remover(int id) {
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM jogador WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover jogador", e);
        }
    }
}