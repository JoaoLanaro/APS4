package model;

/**
 * MODEL - Representa uma linha da tabela relacional "jogador_time":
 * o vinculo de um jogador com um time, com informacoes proprias (camisa e partidas).
 *
 * Usada nas abas Atualizar e Deletar (aba "Vinculos"). Os nomes vem da view
 * vw_jogador_time, que faz o JOIN das tres tabelas.
 * A chave do vinculo e o par (jogadorId, timeId).
 */

public class Vinculo {
    private final int jogadorId;
    private final int timeId;
    private final String jogadorNome;   // vem da view (so para exibir)
    private final String timeNome;      // vem da view (so para exibir)
    private final int numeroCamisa; // informacao propria do vinculo
    private final int partidas;     // informacao propria do vinculo

    // Constructor: é usado ao ler da view
    public Vinculo(int jogadorId, int timeId, String jogadorNome, String timeNome,
                   int numeroCamisa, int partidas) {
        this.jogadorId = jogadorId; this.timeId = timeId;
        this.jogadorNome = jogadorNome; this.timeNome = timeNome;
        this.numeroCamisa = numeroCamisa; this.partidas = partidas;
    }

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