package dao;

import java.util.List;
import model.Jogador;

/**
 * DAO (interface) - O "contrato" de acesso aos dados de jogador.
 *
 * Ideia central da arquitetura: o programa (controllers) depende desta INTERFACE,
 * nunca da implementacao (JogadorDAOSQL). Para trocar de banco bastaria criar outra
 * implementacao e mudar uma linha no Main.
 */

public interface JogadorDAO {
    List<Jogador> listarTodos();                // todos os jogadores, por nome
    List<Jogador> buscarPorNome(String nome);   // nome contendo o texto
    Jogador buscarPorId(int id);                // um jogador ou null
    void add(Jogador j);                        // INSERT
    void atualizar(Jogador j);                  // UPDATE pelo id
    void remover(int id);                       // DELETE pelo id
}
