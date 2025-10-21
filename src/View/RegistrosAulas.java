package View;

import DAO.FabricaConexao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class RegistrosAulas extends JFrame {

    private JPanel painelTopo;
    private JPanel painelRegistrosAulas;
    private JTable tabelaRegistrosAulas;
    private JButton jbSair;
    private JPanel painelInferior;
    private JPanel painelTabela;


    public RegistrosAulas() {
        setSize(900, 400);
        setContentPane(painelRegistrosAulas);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        String[] colunas = {"ID", "ID Cliente", "Tipo de Aula", "Valor", "Hora", "Data"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        tabelaRegistrosAulas.setModel(model);
        tabelaRegistrosAulas.setRowHeight(30);

        carregarAulas(model);

        jbSair.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void carregarAulas(DefaultTableModel model) {
        String sql = "SELECT id, fk_idCliente, tipoAula, Valor, horaAgendada, dataAgendada FROM agendamentos";

        try (Connection con = FabricaConexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] linha = new Object[]{
                        rs.getInt("id"),
                        rs.getInt("fk_idCliente"),
                        rs.getString("tipoAula"),
                        rs.getInt("Valor"),
                        rs.getString("horaAgendada"),
                        rs.getString("dataAgendada")
                };
                model.addRow(linha);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar aulas: " + ex.getMessage());
        }
    }
}
