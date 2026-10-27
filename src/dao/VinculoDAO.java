package dao;

import java.util.List;
import model.Vinculo;

/**
 * DAO (interface) - Contrato de acesso a tabela relacional jogador_time.
 * A LEITURA usa a view vw_jogador_time (JOIN das 3 tabelas); a ESCRITA vai direto na tabela.
 */

public interface VinculoDAO {
    // Cria um vinculo (usado na aba Cadastro)
    void vincular(int jogadorId, int timeId, int numeroCamisa, int partidas);   

    // Usados pelas abas Atualizar e Deletar
    List<Vinculo> listarTodos();
    List<Vinculo> buscar(String texto);      // por nome do jogador ou do time
    void atualizar(Vinculo v);               // altera camisa e partidas
    void remover(int jogadorId, int timeId);
}