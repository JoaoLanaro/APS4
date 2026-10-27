package model;

/**
 * MODEL - Representa um time (uma linha da tabela "time_" do banco).
 *
 * Somente guarda dados (atributos, getters e setters). Os pontos, vitorias etc. sao
 * digitados pelo usuario e gravados como estao (nao sao calculados pelo programa).
 */

public class Time {
    private int id;     // chave primária que será gerada pelo banco de dados (AUTO_INCREMENT)
    private String nome;
    private int pontos;
    private int vitorias;
    private int empates;
    private int derrotas;
    private int gols_pro;
    private int gols_contra;

    // Constructor
    public Time(int id, String nome, int pontos, int vitorias, int empates,
        int derrotas, int gols_pro, int gols_contra) {
        this.id = id; 
        this.nome = nome; 
        this.pontos = pontos;
        this.vitorias = vitorias;
        this.empates = empates;
        this.derrotas = derrotas;
        this.gols_pro = gols_pro;
        this.gols_contra = gols_contra;
    }

    // Getters: local de onde o DAO e as telas leem os dados
    public int getId() { return id; }
    public String getNome() { return nome; }
    public int getPontos() { return pontos; }
    public int getVitorias() { return vitorias; }
    public int getEmpates() { return empates; }
    public int getDerrotas() { return derrotas; }
    public int getGolsPro() { return gols_pro; }
    public int getGolsContra() { return gols_contra; }

    // Setter: id não é definido em java, portanto não possui setter
    public void setNome(String nome) {this.nome = nome;}
    public void setPontos(int pontos) {this.pontos = pontos;}
    public void setVitorias(int vitorias) {this.vitorias = vitorias;}
    public void setEmpates(int empates) {this.empates = empates;}
    public void setDerrotas(int derrotas) {this.derrotas = derrotas;}
    public void setGolsPro(int gols_pro) {this.gols_pro = gols_pro;}
    public void setGolsContra(int gols_contra) {this.gols_contra = gols_contra;}

    // Mostra somente o nome. Os combos (JComboBox) das telas chamam toString() para exibir cada item 
    @Override 
    public String toString() { return nome; }
}