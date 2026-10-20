package model;

public class Time {
    private int id;
    private String nome;
    private int pontos;
    private int vitorias;
    private int empates;
    private int derrotas;
    private int gols_pro;
    private int gols_contra;


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

    public int getId() { return id; }
    public String getNome() { return nome; }
    public int getPontos() { return pontos; }
    public int getVitorias() { return vitorias; }
    public int getEmpates() { return empates; }
    public int getDerrotas() { return derrotas; }
    public int getGolsPro() { return gols_pro; }
    public int getGolsContra() { return gols_contra; }

    public void setNome(String nome) {this.nome = nome;}
    public void setPontos(int pontos) {this.pontos = pontos;}
    public void setVitorias(int vitorias) {this.vitorias = vitorias;}
    public void setEmpates(int empates) {this.empates = empates;}
    public void setDerrotas(int derrotas) {this.derrotas = derrotas;}
    public void setGolsPro(int gols_pro) {this.gols_pro = gols_pro;}
    public void setGolsContra(int gols_contra) {this.gols_contra = gols_contra;}

    @Override 
    public String toString() { return nome; }
}