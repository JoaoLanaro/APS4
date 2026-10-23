package view;

import controller.TimeController;
import model.Time;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TimePanel extends JPanel {
    private static final String[] ROTULOS =
            {"Nome", "Pontos", "Vitórias", "Empates", "Derrotas", "Gols pró", "Gols contra"};

    private final TimeController controller;
    private final Modo modo;
    private final JTextField[] campos = new JTextField[ROTULOS.length];
    private final JTextField txtBusca = new JTextField(12);
    private final DefaultTableModel modelo;
    private final JTable tabela;
    private int idSelecionado = 0;

    public TimePanel(TimeController controller, Modo modo) {
        this.controller = controller;
        this.modo = modo;
        setLayout(new BorderLayout());

        String[] colunas = new String[ROTULOS.length + 1];
        colunas[0] = "ID";
        System.arraycopy(ROTULOS, 0, colunas, 1, ROTULOS.length);
        modelo = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabela = new JTable(modelo);

        JPanel form = new JPanel(new GridLayout(0, 8, 6, 4));
        for (int i = 0; i < ROTULOS.length; i++) {
            campos[i] = new JTextField();
            campos[i].setEditable(modo == Modo.ATUALIZAR);   // em Deletar, só leitura
            form.add(new JLabel(ROTULOS[i] + ":"));
            form.add(campos[i]);
        }

        JButton btnAcao = new JButton(modo == Modo.ATUALIZAR ? "Atualizar" : "Remover");
        JButton btnLimpar = new JButton("Limpar");
        JButton btnBuscar = new JButton("Buscar");

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botoes.add(btnAcao); botoes.add(btnLimpar);
        botoes.add(new JLabel("   Buscar por nome:")); botoes.add(txtBusca); botoes.add(btnBuscar);

        JPanel topo = new JPanel(new BorderLayout());
        topo.setBorder(BorderFactory.createEmptyBorder(6, 6, 0, 6));
        topo.add(form, BorderLayout.CENTER);
        topo.add(botoes, BorderLayout.SOUTH);

        add(topo, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) preencherCampos();
        });

        btnAcao.addActionListener(e -> executar(() -> {
            if (modo == Modo.ATUALIZAR) atualizar(); else remover();
        }));
        btnLimpar.addActionListener(e -> { limpar(); recarregar(); });
        btnBuscar.addActionListener(e -> executar(() -> preencherTabela(controller.buscar(txtBusca.getText()))));

        recarregar();
    }

    public void recarregar() { executar(() -> preencherTabela(controller.listar())); }

    private void atualizar() {
        controller.atualizar(idSelecionado, txt(0), num(1), num(2), num(3), num(4), num(5), num(6));
        limpar(); recarregar();
    }

    private void remover() {
        if (idSelecionado <= 0) throw new IllegalArgumentException("Selecione um time na tabela.");
        int r = JOptionPane.showConfirmDialog(this,
                "Remover \"" + txt(0) + "\"? Os vínculos dos jogadores com esse time também serão removidos.",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (r != JOptionPane.YES_OPTION) return;
        controller.remover(idSelecionado);
        limpar(); recarregar();
    }

    private void preencherTabela(List<Time> lista) {
        modelo.setRowCount(0);
        for (Time t : lista)
            modelo.addRow(new Object[]{t.getId(), t.getNome(), t.getPontos(), t.getVitorias(), t.getEmpates(),
                    t.getDerrotas(), t.getGolsPro(), t.getGolsContra()});
    }

    private void preencherCampos() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) return;
        idSelecionado = (int) modelo.getValueAt(linha, 0);
        for (int i = 0; i < campos.length; i++)
            campos[i].setText(String.valueOf(modelo.getValueAt(linha, i + 1)));
    }

    private void limpar() {
        idSelecionado = 0;
        for (JTextField c : campos) c.setText("");
        txtBusca.setText("");
        tabela.clearSelection();
    }

    private String txt(int i) { return campos[i].getText(); }

    private int num(int i) {
        String s = campos[i].getText().trim();
        return s.isEmpty() ? 0 : Integer.parseInt(s);
    }

    private void executar(Runnable acao) {
        try {
            acao.run();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Informe apenas números inteiros nos campos numéricos.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            String msg = ex.getMessage();
            if (ex.getCause() != null) msg += "\n\nMotivo: " + ex.getCause().getMessage();
            JOptionPane.showMessageDialog(this, msg, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}