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

/**
 * VIEW - Aba CONSULTA (somente visualizacao).
 * O usuario escolhe quais colunas ver (caixas de selecao, a esquerda), digita um texto de
 * busca e/ou um filtro numerico (no topo) e clica em Consultar. A tabela muda de colunas
 * conforme a escolha. Os dados vem da view vw_jogador_time, via ConsultaController.
 */
public class ConsultaPanel extends JPanel {
    private final ConsultaController controller;

    // Uma caixa de selecao por coluna. EnumMap mantem a mesma ordem do enum ColunaView.
    private final Map<ColunaView, JCheckBox> checks = new EnumMap<>(ColunaView.class);

    // Tabela sem colunas fixas: colunas e linhas sao definidas a cada consulta
    private final DefaultTableModel modelo = new DefaultTableModel() {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable tabela = new JTable(modelo);

    // Componentes dos filtros
    private final JTextField txtBusca = new JTextField(12);
    private final JComboBox<ColunaView> cbFiltroColuna = new JComboBox<>();
    private final JComboBox<Operador> cbOperador = new JComboBox<>(Operador.values());
    private final JTextField txtValor = new JTextField(5);
    private final JCheckBox chkDistinto = new JCheckBox("Sem linhas repetidas");

    public ConsultaPanel(ConsultaController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());

        add(criarPainelColunas(), BorderLayout.WEST);       // escolha das colunas
        add(criarPainelFiltros(), BorderLayout.NORTH);      // busca e filtros

        tabela.setAutoCreateRowSorter(true);                // clicar no cabecalho ordena
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        atualizar();                                        // ja abre mostrando dados
    }

    /** Painel da esquerda: caixas de selecao agrupadas em Jogador, Time e Vinculo. */
    private JComponent criarPainelColunas() {
        JPanel lista = new JPanel();
        lista.setLayout(new BoxLayout(lista, BoxLayout.Y_AXIS));

        String grupoAtual = null;
        JPanel painelGrupo = null;
        for (ColunaView c : ColunaView.values()) {
            // Quando o grupo muda (Jogador -> Time -> Vinculo), abre uma nova caixa com titulo
            if (!c.getGrupo().equals(grupoAtual)) {
                grupoAtual = c.getGrupo();
                painelGrupo = new JPanel();
                painelGrupo.setLayout(new BoxLayout(painelGrupo, BoxLayout.Y_AXIS));
                painelGrupo.setBorder(BorderFactory.createTitledBorder(grupoAtual));
                painelGrupo.setAlignmentX(Component.LEFT_ALIGNMENT);
                lista.add(painelGrupo);
            }
            JCheckBox cb = new JCheckBox(c.getTitulo());
            // Colunas marcadas por padrao ao abrir
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

    /** Painel do topo: busca por texto, filtro numerico (coluna, operador, valor) e botao Consultar. */
    private JComponent criarPainelFiltros() {
        cbFiltroColuna.addItem(null);               // primeira opcao em branco = sem filtro numerico
        for (ColunaView c : ColunaView.values())
            if (c.isNumerica()) cbFiltroColuna.addItem(c);  // so colunas numericas

        JButton btnConsultar = new JButton("Consultar");
        btnConsultar.addActionListener(e -> atualizar());
        txtBusca.addActionListener(e -> atualizar());       // Enter no campo tambem consulta

        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.add(new JLabel("Buscar jogador ou time:")); p.add(txtBusca);
        p.add(new JLabel("   Filtrar por:")); p.add(cbFiltroColuna); p.add(cbOperador); p.add(txtValor);
        p.add(chkDistinto);
        p.add(btnConsultar);
        return p;
    }

    /**
     * Executa a consulta com as opcoes da tela e redesenha a tabela.
     * Publico porque o MainFrame chama ao voltar para esta aba.
     */
    public void atualizar() {
        try {
            // Colunas marcadas, na ordem do enum
            List<ColunaView> colunas = new ArrayList<>();
            for (Map.Entry<ColunaView, JCheckBox> en : checks.entrySet())
                if (en.getValue().isSelected()) colunas.add(en.getKey());

            // Valor do filtro numerico (vazio = sem valor)
            String v = txtValor.getText().trim();
            Integer valor = v.isEmpty() ? null : Integer.valueOf(v);

            List<Object[]> linhas = controller.consultar(colunas, txtBusca.getText(),
                    (ColunaView) cbFiltroColuna.getSelectedItem(), (Operador) cbOperador.getSelectedItem(),
                    valor, chkDistinto.isSelected());

            // Os titulos da tabela sao os das colunas escolhidas
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