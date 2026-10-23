package model;

public class Vinculo {
    private final int jogadorId;
    private final int timeId;
    private final String jogadorNome;
    private final String timeNome;
    private final int numeroCamisa;
    private final int partidas;

    public Vinculo(int jogadorId, int timeId, String jogadorNome, String timeNome,
                   int numeroCamisa, int partidas) {
        this.jogadorId = jogadorId; this.timeId = timeId;
        this.jogadorNome = jogadorNome; this.timeNome = timeNome;
        this.numeroCamisa = numeroCamisa; this.partidas = partidas;
    }

    /** Para atualizar (os nomes não são necessários). */
    public Vinculo(int jogadorId, int timeId, int numeroCamisa, int partidas) {
        this(jogadorId, timeId, "", "", numeroCamisa, partidas);
    }

    public int getJogadorId() { return jogadorId; }
    public int getTimeId() { return timeId; }
    public String getJogadorNome() { return jogadorNome; }
    public String getTimeNome() { return timeNome; }
    public int getNumeroCamisa() { return numeroCamisa; }
    public int getPartidas() { return partidas; }
}