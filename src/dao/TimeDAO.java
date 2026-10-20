package dao;

import java.util.List;
import model.Time;

public interface TimeDAO {
    List<Time> listarTodos();
    List<Time> buscarPorNome(String nome);
    Time buscarPorId(int id);
    void add(Time t);
    void atualizar(Time t);
    void remover(int id);
}
