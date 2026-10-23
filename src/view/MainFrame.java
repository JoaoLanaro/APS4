package view;

import controller.ConsultaController;
import controller.JogadorController;
import controller.TimeController;
import controller.VinculoController;
import javax.swing.*;
import java.awt.Component;

public class MainFrame extends JFrame {

    public MainFrame(JogadorController jc, TimeController tc, VinculoController vc, ConsultaController cc) {
        setTitle("Campeonato - Jogadores e Times");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        ConsultaPanel consulta = new ConsultaPanel(cc);
        CadastroPanel cadastro = new CadastroPanel(jc, tc, vc);
        GerenciarPanel abaAtualizar = new GerenciarPanel(Modo.ATUALIZAR, jc, tc, vc);
        GerenciarPanel abaDeletar = new GerenciarPanel(Modo.REMOVER, jc, tc, vc);

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Consulta", consulta);
        abas.addTab("Cadastro", cadastro);
        abas.addTab("Atualizar", abaAtualizar);
        abas.addTab("Deletar", abaDeletar);

        // Ao abrir uma aba, mostra os dados mais recentes
        abas.addChangeListener(e -> {
            Component c = abas.getSelectedComponent();
            if (c == consulta) consulta.atualizar();
            else if (c == abaAtualizar) abaAtualizar.recarregar();
            else if (c == abaDeletar) abaDeletar.recarregar();
        });

        add(abas);
        setSize(1000, 620);
        setLocationRelativeTo(null);
    }
}