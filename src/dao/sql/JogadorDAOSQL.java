package dao.sql;

import dao.JogadorDAO;
import model.Jogador;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (implementacao SQL) - Unico lugar que fala SQL/JDBC sobre a tabela "jogador".
 * Traduz linhas do banco em objetos Jogador (e o inverso).
 *
 * Padrao usado em todos os metodos:
 *  - try-with-resources: Connection, PreparedStatement e ResultSet sao fechados sozinhos;
 *  - PreparedStatement com "?": os valores nunca sao concatenados no SQL (evita SQL injection);
 *  - SQLException vira RuntimeException com mensagem clara, que a tela mostra ao usuario.
 */

public class JogadorDAOSQL implements JogadorDAO {

     // Colunas lidas em todos os SELECTs (evita repetir a lista)
    private static final String COLS =
            "id, nome, idade, nacionalidade, posicao, gols, assistencias, cartoes_amarelos, cartoes_vermelhos";

     // Converte a linha atual do ResultSet em um objeto Jogador
    private Jogador map(ResultSet rs) throws SQLException {
        return new Jogador(rs.getInt("id"), rs.getString("nome"), rs.getInt("idade"),
                rs.getString("nacionalidade"), rs.getString("posicao"), rs.getInt("gols"),
                rs.getInt("assistencias"), rs.getInt("cartoes_amarelos"), rs.getInt("cartoes_vermelhos"));
    }

    // Preenche os 8 campos editáveis (mesma ordem no INSERT e no UPDATE). O id nao entra aqui
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

    // SELECT de todos os jogadores, ordenados por nome. 
    @Override
    public List<Jogador> listarTodos() {
        return buscar("SELECT " + COLS + " FROM jogador ORDER BY nome", null);
    }

    // SELECT com LIKE: jogadores cujo nome contem o texto. 
    @Override
    public List<Jogador> buscarPorNome(String nome) {
        return buscar("SELECT " + COLS + " FROM jogador WHERE nome LIKE ? ORDER BY nome", "%" + nome + "%");
    }

    // Executa um SELECT (com ou sem parametro de texto) e devolve a lista de jogadores. 
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

    // SELECT pela chave primaria. Devolve null se nao existir. 
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

    // INSERT de um jogador novo (o id e gerado pelo banco). 
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

    // UPDATE de todos os campos do jogador identificado pelo id.
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

    // DELETE pelo id. Os vinculos do jogador somem junto (ON DELETE CASCADE no banco).
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