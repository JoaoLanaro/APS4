package model;

/**
 * MODEL - Agrupa tudo o que o usuario configurou na aba Consulta alem das colunas:
 * texto de busca, filtro numerico e opcao "sem linhas repetidas".
 * E criado pelo ConsultaController e lido pelo ConsultaDAOSQL para montar o WHERE.
 */

public class FiltroConsulta {
    private final String texto;         // busca em jogador_nome e time_nome (pode ser vazio)          
    private final ColunaView coluna;    // coluna do filtro numerico (null = sem filtro)
    private final Operador operador;    // =, >, >=, ...
    private final Integer valor;        // valor da comparacao
    private final boolean distinto;     // true = SELECT DISTINCT (sem linhas repetidas)

    public FiltroConsulta(String texto, ColunaView coluna, Operador operador, Integer valor, boolean distinto) {
        this.texto = texto; this.coluna = coluna; this.operador = operador;
        this.valor = valor; this.distinto = distinto;
    }

    public String getTexto() { return texto; }
    public ColunaView getColuna() { return coluna; }
    public Operador getOperador() { return operador; }
    public Integer getValor() { return valor; }
    public boolean isDistinto() { return distinto; }
}