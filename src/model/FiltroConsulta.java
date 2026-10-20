package model;

public class FiltroConsulta {
    private final String texto;          
    private final ColunaView coluna;
    private final Operador operador;
    private final Integer valor;
    private final boolean distinto;      

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