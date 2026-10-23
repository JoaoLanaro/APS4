package controller;

import dao.JogadorDAO;
import dao.TimeDAO;
import dao.VinculoDAO;
import model.Jogador;
import model.Time;
import model.Vinculo;
import java.util.List;

public class VinculoController {
    private final VinculoDAO vinculoDao;
    private final JogadorDAO jogadorDao;
    private final TimeDAO timeDao;

    public VinculoController(VinculoDAO vinculoDao, JogadorDAO jogadorDao, TimeDAO timeDao) {
        this.vinculoDao = vinculoDao;
        this.jogadorDao = jogadorDao;
        this.timeDao = timeDao;
    }

    public List<Jogador> jogadores() { return jogadorDao.listarTodos(); }
    public List<Time> times() { return timeDao.listarTodos(); }

    // ----- usado pelo Cadastro (igual ao que já existia) -----
    public void vincular(Jogador j, Time t, int numeroCamisa, int partidas) {
        if (j == null || t == null) throw new IllegalArgumentException("Selecione um jogador e um time.");
        validar(numeroCamisa, partidas);
        vinculoDao.vincular(j.getId(), t.getId(), numeroCamisa, partidas);
    }

    // ----- novos: usados por Atualizar e Deletar -----
    public List<Vinculo> listar() { return vinculoDao.listarTodos(); }

    public List<Vinculo> buscar(String texto) {
        return (texto == null || texto.isBlank()) ? vinculoDao.listarTodos() : vinculoDao.buscar(texto.trim());
    }

    public void atualizar(int jogadorId, int timeId, int numeroCamisa, int partidas) {
        if (jogadorId <= 0 || timeId <= 0) throw new IllegalArgumentException("Selecione um vínculo na tabela.");
        validar(numeroCamisa, partidas);
        vinculoDao.atualizar(new Vinculo(jogadorId, timeId, numeroCamisa, partidas));
    }

    public void remover(int jogadorId, int timeId) {
        if (jogadorId <= 0 || timeId <= 0) throw new IllegalArgumentException("Selecione um vínculo na tabela.");
        vinculoDao.remover(jogadorId, timeId);
    }

    private void validar(int numeroCamisa, int partidas) {
        if (numeroCamisa <= 0) throw new IllegalArgumentException("Informe o número da camisa (maior que zero).");
        if (partidas < 0) throw new IllegalArgumentException("Partidas não pode ser negativo.");
    }
}