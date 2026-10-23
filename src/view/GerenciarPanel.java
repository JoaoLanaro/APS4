package view;

import controller.JogadorController;
import controller.TimeController;
import controller.VinculoController;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Component;

public class GerenciarPanel extends JPanel {
    private final JogadorPanel jogadores;
    private final TimePanel times;
    private final VinculoPanel vinculos;

    public GerenciarPanel(Modo modo, JogadorController jc, TimeController tc, VinculoController vc) {
        setLayout(new BorderLayout());

        jogadores = new JogadorPanel(jc, modo);
        times = new TimePanel(tc, modo);
        vinculos = new VinculoPanel(vc, modo);

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Jogadores", jogadores);
        abas.addTab("Times", times);
        abas.addTab("Vínculos", vinculos);

        // Ao trocar de sub-aba, recarrega a que abriu
        abas.addChangeListener(e -> {
            Component c = abas.getSelectedComponent();
            if (c == jogadores) jogadores.recarregar();
            else if (c == times) times.recarregar();
            else if (c == vinculos) vinculos.recarregar();
        });

        add(abas, BorderLayout.CENTER);
    }

    /** Recarrega as três sub-abas (o MainFrame chama ao abrir a aba principal). */
    public void recarregar() {
        jogadores.recarregar();
        times.recarregar();
        vinculos.recarregar();
    }
}