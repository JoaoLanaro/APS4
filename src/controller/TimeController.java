package controller;

import dao.TimeDAO;
import model.Time;
import java.util.List;

public class TimeController {
    private final TimeDAO dao;   // interface, não DAOSQL

    public TimeController(TimeDAO dao) { this.dao = dao; }

    public List<Time> listar() { return dao.listarTodos(); }

    public List<Time> buscar(String nome) {
        return (nome == null || nome.isBlank()) ? dao.listarTodos() : dao.buscarPorNome(nome.trim());
    }

    public void adicionar(String nome, int pontos, int vitorias, int empates,
                          int derrotas, int golsPro, int golsContra) {
        dao.add(validar(0, nome, pontos, vitorias, empates, derrotas, golsPro, golsContra));
    }

    public void atualizar(int id, String nome, int pontos, int vitorias, int empates,
                          int derrotas, int golsPro, int golsContra) {
        if (id <= 0) throw new IllegalArgumentException("Selecione um time na tabela.");
        dao.atualizar(validar(id, nome, pontos, vitorias, empates, derrotas, golsPro, golsContra));
    }

    public void remover(int id) {
        if (id <= 0) throw new IllegalArgumentException("Selecione um time na tabela.");
        dao.remover(id);
    }

    private Time validar(int id, String nome, int pontos, int vitorias, int empates,
                         int derrotas, int golsPro, int golsContra) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome é obrigatório.");
        if (pontos < 0 || vitorias < 0 || empates < 0 || derrotas < 0 || golsPro < 0 || golsContra < 0)
            throw new IllegalArgumentException("Os valores não podem ser negativos.");
        return new Time(id, nome.trim(), pontos, vitorias, empates, derrotas, golsPro, golsContra);
    }
}