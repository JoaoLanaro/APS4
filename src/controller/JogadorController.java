package controller;

import dao.JogadorDAO;
import model.Jogador;
import java.util.List;

/**
 * CONTROLLER - Camada do meio entre a tela (view) e o banco (DAO).
 *  - Recebe os valores crus da tela;
 *  - VALIDA (nome obrigatorio, sem negativos, id selecionado) e monta o objeto Jogador;
 *  - chama o DAO.
 * Nao tem Swing (nada de JOptionPane) nem SQL. Quando algo esta errado, lanca uma
 * IllegalArgumentException; a tela decide como mostrar a mensagem.
 * Conhece apenas a INTERFACE JogadorDAO, nunca o JogadorDAOSQL.
 */
public class JogadorController {
    private final JogadorDAO dao;   // interface, não DAOSQL

    public JogadorController(JogadorDAO dao) { this.dao = dao; }

    public List<Jogador> listar() { return dao.listarTodos(); }

    /** Texto vazio lista tudo; senao filtra pelo nome. */
    public List<Jogador> buscar(String nome) {
        return (nome == null || nome.isBlank()) ? dao.listarTodos() : dao.buscarPorNome(nome.trim());
    }

    /** Cadastro: id = 0 porque o banco gera o id. */
    public void adicionar(String nome, int idade, String nacionalidade, String posicao,
                          int gols, int assistencias, int amarelos, int vermelhos) {
        dao.add(validar(0, nome, idade, nacionalidade, posicao, gols, assistencias, amarelos, vermelhos));
    }

    /** Atualizacao: exige um jogador selecionado (id > 0). */
    public void atualizar(int id, String nome, int idade, String nacionalidade, String posicao,
                          int gols, int assistencias, int amarelos, int vermelhos) {
        if (id <= 0) throw new IllegalArgumentException("Selecione um jogador na tabela.");
        dao.atualizar(validar(id, nome, idade, nacionalidade, posicao, gols, assistencias, amarelos, vermelhos));
    }

    /** Remocao: exige um jogador selecionado (id > 0). */
    public void remover(int id) {
        if (id <= 0) throw new IllegalArgumentException("Selecione um jogador na tabela.");
        dao.remover(id);
    }

    /** Regras de validacao; devolve o Jogador pronto (com textos sem espacos nas pontas). */
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