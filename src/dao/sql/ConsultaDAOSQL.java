package dao.sql;

import dao.ConsultaDAO;
import model.ColunaView;
import model.FiltroConsulta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (implementacao SQL) - Monta o SELECT da aba Consulta dinamicamente,
 * de acordo com as colunas marcadas e os filtros preenchidos pelo usuario.
 */

public class ConsultaDAOSQL implements ConsultaDAO {

    @Override
    public List<Object[]> consultar(List<ColunaView> colunas, FiltroConsulta f) {
        // 1) SELECT + colunas. Os nomes vem do enum ColunaView (lista fechada), nunca do texto digitado.
        StringBuilder sql = new StringBuilder("SELECT ");
        if (f.isDistinto()) sql.append("DISTINCT ");
        for (int i = 0; i < colunas.size(); i++) {
            if (i > 0) sql.append(", ");
            sql.append(colunas.get(i).getSql());
        }
        // "WHERE 1=1" permite que cada filtro comece com "AND", sem saber qual e o primeiro
        sql.append(" FROM vw_jogador_time WHERE 1=1");

        // 2) Filtros. Os VALORES digitados vao sempre por parametro (?) -> sem SQL injection.
        List<Object> params = new ArrayList<>();
        if (f.getTexto() != null && !f.getTexto().isBlank()) {
            sql.append(" AND (jogador_nome LIKE ? OR time_nome LIKE ?)");
            String like = "%" + f.getTexto().trim() + "%";
            params.add(like);
            params.add(like);
        }
        if (f.getColuna() != null) {
            // coluna e operador tambem vem de enums (listas fechadas); so o valor vai em "?"
            sql.append(" AND ").append(f.getColuna().getSql())
               .append(" ").append(f.getOperador().getSql()).append(" ?");
            params.add(f.getValor());
        }
        sql.append(" ORDER BY 1");   // ordena pela primeira coluna escolhida (compativel com DISTINCT)

        // 3) Executa e converte cada linha em um Object[] (um valor por coluna pedida)
        int n = colunas.size();
        List<Object[]> linhas = new ArrayList<>();
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement ps = c.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) ps.setObject(i + 1, params.get(i));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] linha = new Object[n];
                    for (int i = 0; i < n; i++) linha[i] = rs.getObject(i + 1);
                    linhas.add(linha);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar a view vw_jogador_time", e);
        }
        return linhas;
    }
}