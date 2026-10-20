package model;

public class Jogador {
    private int id;
    private String nome;
    private int idade;
    private String nacionalidade;
    private String posicao;
    private int gols;
    private int assistencias;
    private int cartoes_amarelos;
    private int cartoes_vermelhos;


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
    public int getId() { return id; }
    public String getNome() { return nome; }
    public int getIdade() { return idade; }
    public String getNacionalidade() { return nacionalidade; }
    public String getPosicao() { return posicao; }
    public int getGols() { return gols; }
    public int getAssistencias() { return assistencias; }
    public int getAmarelos() { return cartoes_amarelos; }
    public int getVermelhos() { return cartoes_vermelhos; }

    public void setNome(String nome) {this.nome = nome;}
    public void setIdade(int idade) {this.idade = idade;}
    public void setNacionalidade(String nacionalidade) {this.nacionalidade = nacionalidade;}
    public void setPosicao(String posicao) {this.posicao = posicao;}
    public void setGols(int gols) {this.gols = gols;}
    public void setAssistencias(int assistencias) {this.assistencias = assistencias;}
    public void setAmarelos(int cartoes_amarelos) {this.cartoes_amarelos = cartoes_amarelos;}
    public void setVermelhos(int cartoes_vermelhos) {this.cartoes_vermelhos = cartoes_vermelhos;}

    @Override 
    public String toString() { return nome; }

}