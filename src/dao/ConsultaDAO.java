package dao;

import java.util.List;
import model.ColunaView;
import model.FiltroConsulta;

public interface ConsultaDAO {
    List<Object[]> consultar(List<ColunaView> colunas, FiltroConsulta filtro);
}