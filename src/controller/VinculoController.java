package controller;

import dao.JogadorDAO;
import dao.TimeDAO;
import dao.VinculoDAO;
import model.Jogador;
import model.Time;
import model.Vinculo;
import java.util.List;

/**
 * CONTROLLER - Regras da relacao jogador x time.
 * Recebe os tres DAOs porque alem de gravar vinculos precisa listar jogadores e times
 * para preencher os combos da tela de cadastro.
 */
public class VinculoController {
    private final VinculoDAO vinculoDao;
    private final JogadorDAO jogadorDao;
    private final TimeDAO timeDao;

    public VinculoController(VinculoDAO vinculoDao, JogadorDAO jogadorDao, TimeDAO timeDao) {
        this.vinculoDao = vinculoDao;
        this.jogadorDao = jogadorDao;
        this.timeDao = timeDao;
    }

    // Dados para os combos do formulario de vinculo
    public List<Jogador> jogadores() { return jogadorDao.listarTodos(); }
    public List<Time> times() { return timeDao.listarTodos(); }

    // Usado pelo Cadastro 
    public void vincular(Jogador j, Time t, int numeroCamisa, int partidas) {
        if (j == null || t == null) throw new IllegalArgumentException("Selecione um jogador e um time.");
        validar(numeroCamisa, partidas);
        vinculoDao.vincular(j.getId(), t.getId(), numeroCamisa, partidas);
    }

    // Usados pelas abas Atualizar e Deletar
    public List<Vinculo> listar() { return vinculoDao.listarTodos(); }

    public List<Vinculo> buscar(String texto) {
        return (texto == null || texto.isBlank()) ? vinculoDao.listarTodos() : vinculoDao.buscar(texto.trim());
    }

    /** O vinculo e identificado pelo par (jogadorId, timeId); so camisa e partidas mudam. */
    public void atualizar(int jogadorId, int timeId, int numeroCamisa, int partidas) {
        if (jogadorId <= 0 || timeId <= 0) throw new IllegalArgumentException("Selecione um vínculo na tabela.");
        validar(numeroCamisa, partidas);
        vinculoDao.atualizar(new Vinculo(jogadorId, timeId, numeroCamisa, partidas));
    }

    public void remover(int jogadorId, int timeId) {
        if (jogadorId <= 0 || timeId <= 0) throw new IllegalArgumentException("Selecione um vínculo na tabela.");
        vinculoDao.remover(jogadorId, timeId);
    }

     /** Camisa maior que zero e partidas nao negativas. */
    private void validar(int numeroCamisa, int partidas) {
        if (numeroCamisa <= 0) throw new IllegalArgumentException("Informe o número da camisa (maior que zero).");
        if (partidas < 0) throw new IllegalArgumentException("Partidas não pode ser negativo.");
    }
}