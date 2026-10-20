package view;

import controller.ConsultaController;
import model.ColunaView;
import model.Operador;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ConsultaPanel extends JPanel {
    private final ConsultaController controller;

    private final Map<ColunaView, JCheckBox> checks = new EnumMap<>(ColunaView.class);

    private final DefaultTableModel modelo = new DefaultTableModel() {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable tabela = new JTable(modelo);

    private final JTextField txtBusca = new JTextField(12);
    private final JComboBox<ColunaView> cbFiltroColuna = new JComboBox<>();
    private final JComboBox<Operador> cbOperador = new JComboBox<>(Operador.values());
    private final JTextField txtValor = new JTextField(5);
    private final JCheckBox chkDistinto = new JCheckBox("Sem linhas repetidas");

    public ConsultaPanel(ConsultaController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());

        add(criarPainelColunas(), BorderLayout.WEST);
        add(criarPainelFiltros(), BorderLayout.NORTH);

        tabela.setAutoCreateRowSorter(true);   
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        atualizar();
    }

    private JComponent criarPainelColunas() {
        JPanel lista = new JPanel();
        lista.setLayout(new BoxLayout(lista, BoxLayout.Y_AXIS));

        String grupoAtual = null;
        JPanel painelGrupo = null;
        for (ColunaView c : ColunaView.values()) {
            if (!c.getGrupo().equals(grupoAtual)) {
                grupoAtual = c.getGrupo();
                painelGrupo = new JPanel();
                painelGrupo.setLayout(new BoxLayout(painelGrupo, BoxLayout.Y_AXIS));
                painelGrupo.setBorder(BorderFactory.createTitledBorder(grupoAtual));
                painelGrupo.setAlignmentX(Component.LEFT_ALIGNMENT);
                lista.add(painelGrupo);
            }
            JCheckBox cb = new JCheckBox(c.getTitulo());
            cb.setSelected(c == ColunaView.JOGADOR_NOME || c == ColunaView.POSICAO
                        || c == ColunaView.GOLS || c == ColunaView.TIME_NOME);
            checks.put(c, cb);
            painelGrupo.add(cb);
        }

        JButton btnTodas = new JButton("Marcar todas");
        JButton btnNenhuma = new JButton("Desmarcar todas");
        btnTodas.addActionListener(e -> checks.values().forEach(cb -> cb.setSelected(true)));
        btnNenhuma.addActionListener(e -> checks.values().forEach(cb -> cb.setSelected(false)));

        JPanel botoes = new JPanel(new GridLayout(2, 1, 0, 4));
        botoes.add(btnTodas);
        botoes.add(btnNenhuma);

        JPanel conteudo = new JPanel(new BorderLayout());
        conteudo.setBorder(BorderFactory.createTitledBorder("Colunas a exibir"));
        conteudo.add(new JScrollPane(lista), BorderLayout.CENTER);
        conteudo.add(botoes, BorderLayout.SOUTH);
        return conteudo;
    }

    private JComponent criarPainelFiltros() {
        cbFiltroColuna.addItem(null);   
        for (ColunaView c : ColunaView.values())
            if (c.isNumerica()) cbFiltroColuna.addItem(c);

        JButton btnConsultar = new JButton("Consultar");
        btnConsultar.addActionListener(e -> atualizar());
        txtBusca.addActionListener(e -> atualizar());   

        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.add(new JLabel("Buscar jogador ou time:")); p.add(txtBusca);
        p.add(new JLabel("   Filtrar por:")); p.add(cbFiltroColuna); p.add(cbOperador); p.add(txtValor);
        p.add(chkDistinto);
        p.add(btnConsultar);
        return p;
    }

    public void atualizar() {
        try {
            List<ColunaView> colunas = new ArrayList<>();
            for (Map.Entry<ColunaView, JCheckBox> en : checks.entrySet())
                if (en.getValue().isSelected()) colunas.add(en.getKey());

            String v = txtValor.getText().trim();
            Integer valor = v.isEmpty() ? null : Integer.valueOf(v);

            List<Object[]> linhas = controller.consultar(colunas, txtBusca.getText(),
                    (ColunaView) cbFiltroColuna.getSelectedItem(), (Operador) cbOperador.getSelectedItem(),
                    valor, chkDistinto.isSelected());

            String[] titulos = new String[colunas.size()];
            for (int i = 0; i < titulos.length; i++) titulos[i] = colunas.get(i).getTitulo();

            modelo.setDataVector(linhas.toArray(new Object[0][]), titulos);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "O valor do filtro deve ser um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
        ex.printStackTrace();   // agora o console mostra o Caused by
        String msg = ex.getMessage();
        if (ex.getCause() != null) msg += "\n\nMotivo: " + ex.getCause().getMessage();
        JOptionPane.showMessageDialog(this, msg, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}