package view;

import controller.JogadorController;
import controller.TimeController;
import controller.VinculoController;
import model.Jogador;
import model.Time;
import javax.swing.*;
import java.awt.*;

public class CadastroPanel extends JPanel {
    private final JogadorController jogadorController;
    private final TimeController timeController;
    private final VinculoController vinculoController;

    private final JComboBox<Jogador> cbJogador = new JComboBox<>();
    private final JComboBox<Time> cbTime = new JComboBox<>();

    public CadastroPanel(JogadorController jc, TimeController tc, VinculoController vc) {
        this.jogadorController = jc;
        this.timeController = tc;
        this.vinculoController = vc;
        setLayout(new BorderLayout());

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Novo jogador", criarFormJogador());
        abas.addTab("Novo time", criarFormTime());
        abas.addTab("Vincular jogador a time", criarFormVinculo());
        abas.addChangeListener(e -> recarregarCombos());
        add(abas, BorderLayout.CENTER);

        recarregarCombos();
    }


    private JPanel criarFormJogador() {
        JTextField nome = new JTextField(20), idade = new JTextField(5);
        JTextField nac = new JTextField(15), pos = new JTextField(15);
        JTextField gols = new JTextField(5), assist = new JTextField(5);
        JTextField amarelos = new JTextField(5), vermelhos = new JTextField(5);

        JPanel form = new JPanel(new GridBagLayout());
        campo(form, 0, "Nome:", nome);
        campo(form, 1, "Idade:", idade);
        campo(form, 2, "Nacionalidade:", nac);
        campo(form, 3, "Posição:", pos);
        campo(form, 4, "Gols:", gols);
        campo(form, 5, "Assistências:", assist);
        campo(form, 6, "Cartões amarelos:", amarelos);
        campo(form, 7, "Cartões vermelhos:", vermelhos);

        JButton btn = new JButton("Cadastrar jogador");
        botao(form, 8, btn);
        btn.addActionListener(e -> executar(() -> {
            jogadorController.adicionar(nome.getText(), num(idade), nac.getText(), pos.getText(),
                    num(gols), num(assist), num(amarelos), num(vermelhos));
            limpar(nome, idade, nac, pos, gols, assist, amarelos, vermelhos);
            recarregarCombos();
            JOptionPane.showMessageDialog(this, "Jogador cadastrado.");
        }));
        return envolver(form);
    }

    private JPanel criarFormTime() {
        JTextField nome = new JTextField(20), pontos = new JTextField(5);
        JTextField vitorias = new JTextField(5), empates = new JTextField(5), derrotas = new JTextField(5);
        JTextField golsPro = new JTextField(5), golsContra = new JTextField(5);

        JPanel form = new JPanel(new GridBagLayout());
        campo(form, 0, "Nome:", nome);
        campo(form, 1, "Pontos:", pontos);
        campo(form, 2, "Vitórias:", vitorias);
        campo(form, 3, "Empates:", empates);
        campo(form, 4, "Derrotas:", derrotas);
        campo(form, 5, "Gols pró:", golsPro);
        campo(form, 6, "Gols contra:", golsContra);

        JButton btn = new JButton("Cadastrar time");
        botao(form, 7, btn);
        btn.addActionListener(e -> executar(() -> {
            timeController.adicionar(nome.getText(), num(pontos), num(vitorias), num(empates),
                    num(derrotas), num(golsPro), num(golsContra));
            limpar(nome, pontos, vitorias, empates, derrotas, golsPro, golsContra);
            recarregarCombos();
            JOptionPane.showMessageDialog(this, "Time cadastrado.");
        }));
        return envolver(form);
    }

    private JPanel criarFormVinculo() {
        JTextField camisa = new JTextField(5), partidas = new JTextField(5);

        JPanel form = new JPanel(new GridBagLayout());
        campo(form, 0, "Jogador:", cbJogador);
        campo(form, 1, "Time:", cbTime);
        campo(form, 2, "Número da camisa:", camisa);
        campo(form, 3, "Partidas no time:", partidas);

        JButton btn = new JButton("Vincular");
        botao(form, 4, btn);
        btn.addActionListener(e -> executar(() -> {
            vinculoController.vincular((Jogador) cbJogador.getSelectedItem(), (Time) cbTime.getSelectedItem(),
                    num(camisa), num(partidas));
            limpar(camisa, partidas);
            JOptionPane.showMessageDialog(this, "Vínculo cadastrado.");
        }));
        return envolver(form);
    }

    private void recarregarCombos() {
        executar(() -> {
            cbJogador.removeAllItems();
            cbTime.removeAllItems();
            for (Jogador j : vinculoController.jogadores()) cbJogador.addItem(j);
            for (Time t : vinculoController.times()) cbTime.addItem(t);
        });
    }

    private void campo(JPanel form, int linha, String rotulo, JComponent componente) {
        GridBagConstraints g = new GridBagConstraints();
        g.gridy = linha;
        g.insets = new Insets(4, 6, 4, 6);
        g.anchor = GridBagConstraints.WEST;
        g.gridx = 0; form.add(new JLabel(rotulo), g);
        g.gridx = 1; form.add(componente, g);
    }

    private void botao(JPanel form, int linha, JButton btn) {
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 1; g.gridy = linha;
        g.insets = new Insets(10, 6, 4, 6);
        g.anchor = GridBagConstraints.WEST;
        form.add(btn, g);
    }

    private JPanel envolver(JPanel form) { 
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.add(form);
        return p;
    }

    private void limpar(JTextField... campos) {
        for (JTextField c : campos) c.setText("");
    }

    private int num(JTextField f) {
        String s = f.getText().trim();
        return s.isEmpty() ? 0 : Integer.parseInt(s);
    }

    private void executar(Runnable acao) {
    try {
        acao.run();
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Informe apenas números inteiros nos campos numéricos.", "Erro", JOptionPane.ERROR_MESSAGE);
    } catch (Exception ex) {
        ex.printStackTrace();   // agora o console mostra o Caused by
        String msg = ex.getMessage();
        if (ex.getCause() != null) msg += "\n\nMotivo: " + ex.getCause().getMessage();
        JOptionPane.showMessageDialog(this, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
}