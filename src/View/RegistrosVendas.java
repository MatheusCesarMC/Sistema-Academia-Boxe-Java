package View;

import DAO.FabricaConexao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class RegistrosVendas extends JFrame {

    private JPanel painelTabela;
    private JPanel painelTopo;

    private JPanel painelRegistrosVendas;
    private JTable tabelaRegistrosVendas;
    private JButton jbSair;

    private JPanel painelInferior;


    public RegistrosVendas() {
        setSize(1000, 450);
        setContentPane(painelRegistrosVendas);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        String[] colunas = {"ID", "Cliente", "Vendedor", "Forma de Pagamento", "Total", "Data Venda", "Código Venda", "Detalhes"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        tabelaRegistrosVendas.setModel(model);

        tabelaRegistrosVendas.getColumnModel().getColumn(7).setPreferredWidth(300);
        tabelaRegistrosVendas.setRowHeight(30);

        carregarVendas(model);

        jbSair.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void carregarVendas(DefaultTableModel model) {
        String sql = "SELECT idvendas, cliente, vendedor, formaDePagamento, total, dataVenda, codigoVenda, detalhes FROM vendas";

        try (Connection con = FabricaConexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] linha = new Object[]{
                        rs.getInt("idvendas"),
                        rs.getString("cliente"),
                        rs.getString("vendedor"),
                        rs.getString("formaDePagamento"),
                        rs.getString("total"),
                        rs.getString("dataVenda"),
                        rs.getString("codigoVenda"),
                        rs.getString("detalhes")
                };
                model.addRow(linha);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar vendas: " + ex.getMessage());
        }
    }
}
