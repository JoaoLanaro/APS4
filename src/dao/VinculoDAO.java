package dao;

import java.util.List;
import model.Vinculo;

public interface VinculoDAO {
    void vincular(int jogadorId, int timeId, int numeroCamisa, int partidas);   

    List<Vinculo> listarTodos();
    List<Vinculo> buscar(String texto);      
    void atualizar(Vinculo v);               
    void remover(int jogadorId, int timeId);
}