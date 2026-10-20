package view;

import controller.ConsultaController;
import controller.JogadorController;
import controller.TimeController;
import controller.VinculoController;
import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame(JogadorController jc, TimeController tc, VinculoController vc, ConsultaController cc) {
        setTitle("Campeonato - Jogadores e Times");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        ConsultaPanel consulta = new ConsultaPanel(cc);
        CadastroPanel cadastro = new CadastroPanel(jc, tc, vc);

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Consulta", consulta);
        abas.addTab("Cadastro", cadastro);

        abas.addChangeListener(e -> {
            if (abas.getSelectedComponent() == consulta) consulta.atualizar();
        });

        add(abas);
        setSize(1000, 620);
        setLocationRelativeTo(null);
    }
}