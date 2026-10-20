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

public class Main {
    public static void main(String[] args) {
        JogadorDAO jogadorDao = new JogadorDAOSQL();
        TimeDAO timeDao = new TimeDAOSQL();
        VinculoDAO vinculoDao = new VinculoDAOSQL();
        ConsultaDAO consultaDao = new ConsultaDAOSQL();

        JogadorController jc = new JogadorController(jogadorDao);
        TimeController tc = new TimeController(timeDao);
        VinculoController vc = new VinculoController(vinculoDao, jogadorDao, timeDao);
        ConsultaController cc = new ConsultaController(consultaDao);

        SwingUtilities.invokeLater(() -> new MainFrame(jc, tc, vc, cc).setVisible(true));
    }
}