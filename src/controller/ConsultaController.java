package controller;

import dao.ConsultaDAO;
import model.ColunaView;
import model.FiltroConsulta;
import model.Operador;
import java.util.List;

/**
 * CONTROLLER - Valida a configuracao da aba Consulta e entrega ao ConsultaDAO.
 * Regras: ao menos uma coluna marcada; o filtro numerico precisa de coluna, operador e valor juntos.
 */
public class ConsultaController {
    private final ConsultaDAO dao;   
    public ConsultaController(ConsultaDAO dao) { this.dao = dao; }

    public List<Object[]> consultar(List<ColunaView> colunas, String texto, ColunaView colunaFiltro,
                                    Operador operador, Integer valor, boolean distinto) {
        if (colunas == null || colunas.isEmpty())
            throw new IllegalArgumentException("Marque ao menos uma coluna para exibir.");
        if (colunaFiltro != null && (operador == null || valor == null))
            throw new IllegalArgumentException("Informe o operador e o valor do filtro.");
        if (colunaFiltro == null && valor != null)
            throw new IllegalArgumentException("Escolha a coluna do filtro numérico.");
        return dao.consultar(colunas, new FiltroConsulta(texto, colunaFiltro, operador, valor, distinto));
    }
}