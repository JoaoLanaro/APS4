import controller.ConsultaController;
import controller.JogadorController;
import controller.TimeController;
import controller.VinculoController;
import dao.ConsultaDAO;
import dao.JogadorDAO;
import dao.TimeDAO;
import dao.VinculoDAO;
import dao.sql.ConsultaDAOSQL;
import dao.sql.JogadorDAOSQL;
import dao.sql.TimeDAOSQL;
import dao.sql.VinculoDAOSQL;
import view.MainFrame;
import javax.swing.SwingUtilities;

/**
 * PONTO DE ENTRADA - Monta o programa ligando as camadas:
 *   DAOs (acesso ao banco) -> Controllers (regras) -> View (telas).
 *
 * Este e o UNICO lugar que conhece as implementacoes SQL (import dao.sql.*).
 * Todo o resto do programa so enxerga as interfaces (JogadorDAO, TimeDAO, ...).
 */
public class Main {
    public static void main(String[] args) {
        // 1) DAOs: o tipo da variavel e a INTERFACE; o objeto criado e a implementacao SQL
        JogadorDAO jogadorDao = new JogadorDAOSQL();
        TimeDAO timeDao = new TimeDAOSQL();
        VinculoDAO vinculoDao = new VinculoDAOSQL();
        ConsultaDAO consultaDao = new ConsultaDAOSQL();

        // 2) Controllers: recebem os DAOs pelo construtor
        JogadorController jc = new JogadorController(jogadorDao);
        TimeController tc = new TimeController(timeDao);
        VinculoController vc = new VinculoController(vinculoDao, jogadorDao, timeDao);
        ConsultaController cc = new ConsultaController(consultaDao);

        // 3) View: abre a janela na thread de eventos do Swing (forma correta de criar a interface)
        SwingUtilities.invokeLater(() -> new MainFrame(jc, tc, vc, cc).setVisible(true));
    }
}