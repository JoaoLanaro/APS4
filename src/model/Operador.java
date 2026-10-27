package model;

/**
 * MODEL - Operadores de comparacao permitidos no filtro numerico da Consulta.
 * Lista fechada (como o ColunaView), para o operador nunca vir de texto livre.
 */

public enum Operador {
    IGUAL("="), DIFERENTE("<>"), MAIOR(">"), MAIOR_IGUAL(">="), MENOR("<"), MENOR_IGUAL("<=");

    private final String sql;

    Operador(String sql) { this.sql = sql; }

    public String getSql() { return sql; }

    @Override public String toString() { return sql; }  // mostrado no combo
}