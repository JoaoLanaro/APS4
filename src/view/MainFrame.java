package view;

import controller.ConsultaController;
import controller.JogadorController;
import controller.TimeController;
import controller.VinculoController;
import javax.swing.*;
import java.awt.Component;

/**
 * VIEW - Janela principal (JFrame) com as quatro abas do programa:
 *   Consulta  -> visualizar com colunas e filtros a escolha (ConsultaPanel)
 *   Cadastro  -> adicionar jogador, time e vinculo (CadastroPanel)
 *   Atualizar -> editar entradas existentes (GerenciarPanel em modo ATUALIZAR)
 *   Deletar   -> remover entradas existentes (GerenciarPanel em modo REMOVER)
 * Recebe os controllers e os repassa aos paineis; nao conhece o banco.
 */
public class MainFrame extends JFrame {

    public MainFrame(JogadorController jc, TimeController tc, VinculoController vc, ConsultaController cc) {
        setTitle("Campeonato - Jogadores e Times");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Cria um painel para cada aba
        ConsultaPanel consulta = new ConsultaPanel(cc);
        CadastroPanel cadastro = new CadastroPanel(jc, tc, vc);
        GerenciarPanel abaAtualizar = new GerenciarPanel(Modo.ATUALIZAR, jc, tc, vc);
        GerenciarPanel abaDeletar = new GerenciarPanel(Modo.REMOVER, jc, tc, vc);

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Consulta", consulta);
        abas.addTab("Cadastro", cadastro);
        abas.addTab("Atualizar", abaAtualizar);
        abas.addTab("Deletar", abaDeletar);

        // Ao abrir uma aba, recarrega os dados: outra aba pode ter alterado o banco
        abas.addChangeListener(e -> {
            Component c = abas.getSelectedComponent();
            if (c == consulta) consulta.atualizar();
            else if (c == abaAtualizar) abaAtualizar.recarregar();
            else if (c == abaDeletar) abaDeletar.recarregar();
        });

        add(abas);
        setSize(1000, 620);
        setLocationRelativeTo(null);    // centraliza na tela
    }
}