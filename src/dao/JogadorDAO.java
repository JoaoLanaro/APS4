package dao;

import java.util.List;
import model.Jogador;

public interface JogadorDAO {
    List<Jogador> listarTodos();
    List<Jogador> buscarPorNome(String nome);
    Jogador buscarPorId(int id);
    void add(Jogador j);
    void atualizar(Jogador j);
    void remover(int id);
}
