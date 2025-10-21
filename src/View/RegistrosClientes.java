package View;

import DAO.FabricaConexao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class RegistrosClientes extends JFrame {
    private JPanel painelRegistrosClientes;
    private JTable tabelaRegistrosClientes;
    private JButton jbSair;
    private JPanel jpTopo;

    private JPanel painelTabela;
    private JPanel painelInferior;


    public RegistrosClientes() {
        setSize(900, 400);
        setContentPane(painelRegistrosClientes);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        String[] colunas = {"ID", "Nome", "CPF", "Endereço", "Telefone", "Email", "Sexo", "Data Nascimento"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        tabelaRegistrosClientes.setModel(model);
        tabelaRegistrosClientes.setRowHeight(30);


        carregarClientes(model);

        jbSair.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void carregarClientes(DefaultTableModel model) {
        String sql = "SELECT id,nome, cpf, endereco, telefone, email, sexo, dataNascimento FROM clientes";

        try (Connection con = FabricaConexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] linha = new Object[] {
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("endereco"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("sexo"),
                        rs.getString("dataNascimento")
                };
                model.addRow(linha);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar clientes: " + ex.getMessage());
        }
    }
}
