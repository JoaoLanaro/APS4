package model;

/**
 * MODEL - Lista FECHADA das colunas da view vw_jogador_time que o usuario pode escolher
 * na aba Consulta.
 *
 * Por que um enum? O nome de uma coluna nao pode ir como parametro (?) de um PreparedStatement.
 * Se o texto vindo da tela fosse colado no SQL, abriria espaco para SQL injection.
 * Com o enum, so os nomes escritos aqui chegam ao SQL.
 */

public enum ColunaView {
    // Colunas de jogador
    JOGADOR_NOME("jogador_nome", "Jogador", "Jogador", false),
    IDADE("idade", "Idade", "Jogador", true),
    NACIONALIDADE("nacionalidade", "Nacionalidade", "Jogador", false),
    POSICAO("posicao", "Posição", "Jogador", false),
    GOLS("gols", "Gols", "Jogador", true),
    ASSISTENCIAS("assistencias", "Assistências", "Jogador", true),
    AMARELOS("cartoes_amarelos", "Amarelos", "Jogador", true),
    VERMELHOS("cartoes_vermelhos", "Vermelhos", "Jogador", true),

    // Colunas de time
    TIME_NOME("time_nome", "Time", "Time", false),
    PONTOS("pontos", "Pontos", "Time", true),
    VITORIAS("vitorias", "Vitórias", "Time", true),
    EMPATES("empates", "Empates", "Time", true),
    DERROTAS("derrotas", "Derrotas", "Time", true),
    GOLS_PRO("gols_pro", "Gols pró", "Time", true),
    GOLS_CONTRA("gols_contra", "Gols contra", "Time", true),

    // Colunas da tabela relacional
    NUMERO_CAMISA("numero_camisa", "Camisa", "Vínculo", true),
    PARTIDAS("partidas", "Partidas", "Vínculo", true);

    private final String sql;       // nome real da coluna na view
    private final String titulo;    // texto mostrado na tela  
    private final String grupo;     // agrupa as caixas de selecao (Jogador, Time, Vinculo)
    private final boolean numerica; // so as numericas aparecem no filtro "coluna operador valor"

    ColunaView(String sql, String titulo, String grupo, boolean numerica) {
        this.sql = sql; this.titulo = titulo; this.grupo = grupo; this.numerica = numerica;
    }

    public String getSql() { return sql; }
    public String getTitulo() { return titulo; }
    public String getGrupo() { return grupo; }
    public boolean isNumerica() { return numerica; }

    // Usado quando o enum aparece num JComboBox: mostra o titulo.
    @Override public String toString() { return titulo; }   // usado no JComboBox
}