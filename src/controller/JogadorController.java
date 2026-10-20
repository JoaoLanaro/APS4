package controller;

import dao.JogadorDAO;
import model.Jogador;
import java.util.List;

public class JogadorController {
    private final JogadorDAO dao;   // interface, não DAOSQL

    public JogadorController(JogadorDAO dao) { this.dao = dao; }

    public List<Jogador> listar() { return dao.listarTodos(); }

    public List<Jogador> buscar(String nome) {
        return (nome == null || nome.isBlank()) ? dao.listarTodos() : dao.buscarPorNome(nome.trim());
    }

    public void adicionar(String nome, int idade, String nacionalidade, String posicao,
                          int gols, int assistencias, int amarelos, int vermelhos) {
        dao.add(validar(0, nome, idade, nacionalidade, posicao, gols, assistencias, amarelos, vermelhos));
    }

    public void atualizar(int id, String nome, int idade, String nacionalidade, String posicao,
                          int gols, int assistencias, int amarelos, int vermelhos) {
        if (id <= 0) throw new IllegalArgumentException("Selecione um jogador na tabela.");
        dao.atualizar(validar(id, nome, idade, nacionalidade, posicao, gols, assistencias, amarelos, vermelhos));
    }

    public void remover(int id) {
        if (id <= 0) throw new IllegalArgumentException("Selecione um jogador na tabela.");
        dao.remover(id);
    }

    private Jogador validar(int id, String nome, int idade, String nacionalidade, String posicao,
                            int gols, int assistencias, int amarelos, int vermelhos) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome é obrigatório.");
        if (idade < 0 || gols < 0 || assistencias < 0 || amarelos < 0 || vermelhos < 0)
            throw new IllegalArgumentException("Os valores não podem ser negativos.");
        return new Jogador(id, nome.trim(), idade,
                nacionalidade == null ? "" : nacionalidade.trim(),
                posicao == null ? "" : posicao.trim(),
                gols, assistencias, amarelos, vermelhos);
    }
}