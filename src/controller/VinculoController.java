package controller;

import dao.JogadorDAO;
import dao.TimeDAO;
import dao.VinculoDAO;
import model.Jogador;
import model.Time;
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

    public void vincular(Jogador j, Time t, int numeroCamisa, int partidas) {
        if (j == null || t == null) throw new IllegalArgumentException("Selecione um jogador e um time.");
        if (numeroCamisa <= 0) throw new IllegalArgumentException("Informe o número da camisa (maior que zero).");
        if (partidas < 0) throw new IllegalArgumentException("Partidas não pode ser negativo.");
        vinculoDao.vincular(j.getId(), t.getId(), numeroCamisa, partidas);
    }
}