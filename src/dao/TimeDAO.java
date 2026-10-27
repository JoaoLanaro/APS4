package dao;

import java.util.List;
import model.Time;

/** DAO (interface) - Contrato de acesso aos dados de time. Mesma ideia do JogadorDAO. */

public interface TimeDAO {
    List<Time> listarTodos();
    List<Time> buscarPorNome(String nome);
    Time buscarPorId(int id);
    void add(Time t);
    void atualizar(Time t);
    void remover(int id);
}
