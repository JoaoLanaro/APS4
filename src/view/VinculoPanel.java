package view;

import controller.VinculoController;
import model.Vinculo;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VinculoPanel extends JPanel {
    private final VinculoController controller;
    private final Modo modo;

    private final JTextField txtJogador = new JTextField(14);
    private final JTextField txtTime = new JTextField(14);
    private final JTextField txtCamisa = new JTextField(4);
    private final JTextField txtPartidas = new JTextField(4);
    private final JTextField txtBusca = new JTextField(12);

    private final DefaultTableModel modelo = new DefaultTableModel(
            new String[]{"Jogador", "Time", "Camisa", "Partidas"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable tabela = new JTable(modelo);

    private List<Vinculo> atuais = new ArrayList<>();   // o que está na tabela, na mesma ordem
    private Vinculo selecionado = null;

    public VinculoPanel(VinculoController controller, Modo modo) {
        this.controller = controller;
        this.modo = modo;
        setLayout(new BorderLayout());

        txtJogador.setEditable(false);   // a chave (jogador, time) não muda
        txtTime.setEditable(false);
        txtCamisa.setEditable(modo == Modo.ATUALIZAR);
        txtPartidas.setEditable(modo == Modo.ATUALIZAR);

        JPanel linha1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        linha1.add(new JLabel("Jogador:")); linha1.add(txtJogador);
        linha1.add(new JLabel("Time:")); linha1.add(txtTime);
        linha1.add(new JLabel("Camisa:")); linha1.add(txtCamisa);
        linha1.add(new JLabel("Partidas:")); linha1.add(txtPartidas);
        if (modo == Modo.ATUALIZAR) linha1.add(new JLabel("(só camisa e partidas podem ser alteradas)"));

        JButton btnAcao = new JButton(modo == Modo.ATUALIZAR ? "Atualizar" : "Remover");
        JButton btnLimpar = new JButton("Limpar");
        JButton btnBuscar = new JButton("Buscar");

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botoes.add(btnAcao); botoes.add(btnLimpar);
        botoes.add(new JLabel("   Buscar jogador ou time:")); botoes.add(txtBusca); botoes.add(btnBuscar);

        JPanel topo = new JPanel();
        topo.setLayout(new BoxLayout(topo, BoxLayout.Y_AXIS));
        topo.add(linha1); topo.add(botoes);

        add(topo, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) selecionarLinha();
        });

        btnAcao.addActionListener(e -> executar(() -> {
            if (modo == Modo.ATUALIZAR) atualizar(); else remover();
        }));
        btnLimpar.addActionListener(e -> { limpar(); recarregar(); });
        btnBuscar.addActionListener(e -> executar(() -> preencher(controller.buscar(txtBusca.getText()))));

        recarregar();
    }

    public void recarregar() { executar(() -> preencher(controller.listar())); }

    private void atualizar() {
        exigirSelecao();
        controller.atualizar(selecionado.getJogadorId(), selecionado.getTimeId(),
                num(txtCamisa), num(txtPartidas));
        limpar(); recarregar();
    }

    private void remover() {
        exigirSelecao();
        int r = JOptionPane.showConfirmDialog(this,
                "Remover o vínculo de " + selecionado.getJogadorNome() + " com " + selecionado.getTimeNome() + "?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (r != JOptionPane.YES_OPTION) return;
        controller.remover(selecionado.getJogadorId(), selecionado.getTimeId());
        limpar(); recarregar();
    }

    private void preencher(List<Vinculo> lista) {
        atuais = lista;
        modelo.setRowCount(0);
        for (Vinculo v : lista)
            modelo.addRow(new Object[]{v.getJogadorNome(), v.getTimeNome(), v.getNumeroCamisa(), v.getPartidas()});
    }

    private void selecionarLinha() {
        int linha = tabela.getSelectedRow();
        if (linha < 0 || linha >= atuais.size()) return;
        selecionado = atuais.get(linha);
        txtJogador.setText(selecionado.getJogadorNome());
        txtTime.setText(selecionado.getTimeNome());
        txtCamisa.setText(String.valueOf(selecionado.getNumeroCamisa()));
        txtPartidas.setText(String.valueOf(selecionado.getPartidas()));
    }

    private void limpar() {
        selecionado = null;
        txtJogador.setText(""); txtTime.setText("");
        txtCamisa.setText(""); txtPartidas.setText(""); txtBusca.setText("");
        tabela.clearSelection();
    }

    private void exigirSelecao() {
        if (selecionado == null) throw new IllegalArgumentException("Selecione um vínculo na tabela.");
    }

    private int num(JTextField f) {
        String s = f.getText().trim();
        return s.isEmpty() ? 0 : Integer.parseInt(s);
    }

    private void executar(Runnable acao) {
        try {
            acao.run();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Informe apenas números inteiros em camisa e partidas.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            String msg = ex.getMessage();
            if (ex.getCause() != null) msg += "\n\nMotivo: " + ex.getCause().getMessage();
            JOptionPane.showMessageDialog(this, msg, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}