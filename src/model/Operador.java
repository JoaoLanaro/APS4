package model;

public enum Operador {
    IGUAL("="), DIFERENTE("<>"), MAIOR(">"), MAIOR_IGUAL(">="), MENOR("<"), MENOR_IGUAL("<=");

    private final String sql;

    Operador(String sql) { this.sql = sql; }

    public String getSql() { return sql; }

    @Override public String toString() { return sql; }
}