package model;

public enum ColunaView {
    JOGADOR_NOME("jogador_nome", "Jogador", "Jogador", false),
    IDADE("idade", "Idade", "Jogador", true),
    NACIONALIDADE("nacionalidade", "Nacionalidade", "Jogador", false),
    POSICAO("posicao", "Posição", "Jogador", false),
    GOLS("gols", "Gols", "Jogador", true),
    ASSISTENCIAS("assistencias", "Assistências", "Jogador", true),
    AMARELOS("cartoes_amarelos", "Amarelos", "Jogador", true),
    VERMELHOS("cartoes_vermelhos", "Vermelhos", "Jogador", true),

    TIME_NOME("time_nome", "Time", "Time", false),
    PONTOS("pontos", "Pontos", "Time", true),
    VITORIAS("vitorias", "Vitórias", "Time", true),
    EMPATES("empates", "Empates", "Time", true),
    DERROTAS("derrotas", "Derrotas", "Time", true),
    GOLS_PRO("gols_pro", "Gols pró", "Time", true),
    GOLS_CONTRA("gols_contra", "Gols contra", "Time", true),

    NUMERO_CAMISA("numero_camisa", "Camisa", "Vínculo", true),
    PARTIDAS("partidas", "Partidas", "Vínculo", true);

    private final String sql;
    private final String titulo;
    private final String grupo;
    private final boolean numerica;

    ColunaView(String sql, String titulo, String grupo, boolean numerica) {
        this.sql = sql; this.titulo = titulo; this.grupo = grupo; this.numerica = numerica;
    }

    public String getSql() { return sql; }
    public String getTitulo() { return titulo; }
    public String getGrupo() { return grupo; }
    public boolean isNumerica() { return numerica; }

    @Override public String toString() { return titulo; }   // usado no JComboBox
}