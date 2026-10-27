package dao;

import java.util.List;
import model.ColunaView;
import model.FiltroConsulta;

/**
 * DAO (interface) - Consulta flexivel sobre a view vw_jogador_time (aba Consulta).
 * Devolve Object[] em vez de uma classe fixa, porque as colunas mudam a cada consulta.
 */

public interface ConsultaDAO {
    // Cada Object[] é uma linha, com um valor por coluna pedida, na mesma ordem
    List<Object[]> consultar(List<ColunaView> colunas, FiltroConsulta filtro);
}