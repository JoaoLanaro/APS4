package model;

/**
 * MODEL - Representa um jogador (uma linha da tabela "jogador" do banco).
 *
 * Somente guarda dados: atributos, getters e setters. Nao tem SQL nem componentes de tela.
 * Quem cria objetos desta classe: JogadorDAOSQL (ao ler do banco) e
 * JogadorController (ao validar os dados digitados na tela).
 */

public class Jogador {
    private int id;             // chave primária que será gerada pelo banco de dados (AUTO_INCREMENT)
    private String nome;
    private int idade;
    private String nacionalidade;
    private String posicao;
    private int gols;
    private int assistencias;
    private int cartoes_amarelos;
    private int cartoes_vermelhos;

    // Constructor
    public Jogador(int id, String nome, int idade, String nacionalidade, String posicao, 
        int gols, int assistencias, int cartoes_amarelos, int cartoes_vermelhos) {
        this.id = id; 
        this.nome = nome; 
        this.idade = idade;
        this.nacionalidade = nacionalidade;
        this.posicao = posicao;
        this.gols = gols;
        this.assistencias = assistencias;
        this.cartoes_amarelos = cartoes_amarelos;
        this.cartoes_vermelhos = cartoes_vermelhos;
    }

    // Getters: local de onde o DAO e as telas leem os dados
    public int getId() { return id; }
    public String getNome() { return nome; }
    public int getIdade() { return idade; }
    public String getNacionalidade() { return nacionalidade; }
    public String getPosicao() { return posicao; }
    public int getGols() { return gols; }
    public int getAssistencias() { return assistencias; }
    public int getAmarelos() { return cartoes_amarelos; }
    public int getVermelhos() { return cartoes_vermelhos; }

    // Setter: id não é definido em java, portanto não possui setter
    public void setNome(String nome) {this.nome = nome;}
    public void setIdade(int idade) {this.idade = idade;}
    public void setNacionalidade(String nacionalidade) {this.nacionalidade = nacionalidade;}
    public void setPosicao(String posicao) {this.posicao = posicao;}
    public void setGols(int gols) {this.gols = gols;}
    public void setAssistencias(int assistencias) {this.assistencias = assistencias;}
    public void setAmarelos(int cartoes_amarelos) {this.cartoes_amarelos = cartoes_amarelos;}
    public void setVermelhos(int cartoes_vermelhos) {this.cartoes_vermelhos = cartoes_vermelhos;}

    // Mostra somente o nome. Os combos (JComboBox) das telas chamam toString() para exibir cada item 
    @Override 
    public String toString() { return nome; }

}