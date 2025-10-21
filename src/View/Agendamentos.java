package View;

import DAO.FabricaConexao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Agendamentos extends JFrame {
    private JPanel painelAgendamentos;
    private JPanel painelTop;
    private JComboBox<String> jcbDescricaoServico;
    private JTextField jtfValorServico;
    private JTextField jtfDataAgendamento;
    private JTextField jtfHoraAgendamento;
    private JTable jtCAgendamentos;
    private JButton jbSair;
    private JPanel painelCentral;
    private JButton jbAgendar;
    private JComboBox<String> jcbAlunos;
    private JTextField jtfIdCliente;
    private JTable tabelaAgendamentos;
    private JButton jbCancelar;
    private JButton jbDeletar;
    private JPanel PainelTabela;
    private JButton jbEditar;

    public Agendamentos() {
        setTitle("Agendamentos");
        setSize(1030, 568);
        setContentPane(painelAgendamentos);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        carregarAlunos();
        carregarAgendamentos();
        jtfIdCliente.setEnabled(false);

        jcbDescricaoServico.addItem("Boxe Iniciante");
        jcbDescricaoServico.addItem("Boxe Intermediário");
        jcbDescricaoServico.addItem("Boxe Avançado");
        jcbDescricaoServico.addItem("Funcional Boxe");

        jcbDescricaoServico.addActionListener(e -> {
            String aula = (String) jcbDescricaoServico.getSelectedItem();
            if (aula != null) {
                switch (aula) {
                    case "Boxe Iniciante" -> jtfValorServico.setText("50");
                    case "Boxe Intermediário" -> jtfValorServico.setText("60");
                    case "Boxe Avançado" -> jtfValorServico.setText("70");
                    case "Funcional Boxe" -> jtfValorServico.setText("40");
                    default -> jtfValorServico.setText("");
                }
            }
        });

        jcbAlunos.addActionListener(e -> {
            String alunoNome = (String) jcbAlunos.getSelectedItem();

            if (alunoNome == null || alunoNome.equals("Selecione:")) {
                jtfIdCliente.setText("");
                return;
            }

            try (Connection conn = FabricaConexao.conectar()) {
                String sql = "SELECT id FROM clientes WHERE nome = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, alunoNome);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    jtfIdCliente.setText(String.valueOf(rs.getInt("id")));
                } else {
                    jtfIdCliente.setText("");
                }

                rs.close();
                ps.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao buscar ID do aluno: " + ex.getMessage());
            }
        });

        jbAgendar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String alunoNome = (String) jcbAlunos.getSelectedItem();
                String tipoAula = (String) jcbDescricaoServico.getSelectedItem();
                String valorStr = jtfValorServico.getText();
                String data = jtfDataAgendamento.getText();
                String hora = jtfHoraAgendamento.getText();
                String idStr = jtfIdCliente.getText();

                if (alunoNome == null || alunoNome.equals("Selecione:") || tipoAula == null
                        || valorStr.isEmpty() || data.isEmpty() || hora.isEmpty() || idStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                    return;
                }

                try {
                    int alunoId = Integer.parseInt(idStr);
                    Connection conn = FabricaConexao.conectar();

                    String sqlCheck = "SELECT * FROM agendamentos WHERE dataAgendada = ? AND horaAgendada = ?";
                    PreparedStatement psCheck = conn.prepareStatement(sqlCheck);
                    psCheck.setString(1, data);
                    psCheck.setString(2, hora);
                    ResultSet rs = psCheck.executeQuery();

                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null, "Esse horário já está agendado!");
                    } else {
                        String sqlInsert = "INSERT INTO agendamentos (fk_idCliente, tipoAula, Valor, dataAgendada, horaAgendada) VALUES (?, ?, ?, ?, ?)";
                        PreparedStatement psInsert = conn.prepareStatement(sqlInsert);
                        psInsert.setInt(1, alunoId);
                        psInsert.setString(2, tipoAula);
                        psInsert.setInt(3, Integer.parseInt(valorStr));
                        psInsert.setString(4, data);
                        psInsert.setString(5, hora);
                        psInsert.executeUpdate();

                        JOptionPane.showMessageDialog(null, "Agendamento salvo com sucesso!");
                        limparCampos();
                        carregarAgendamentos();
                    }

                    rs.close();
                    psCheck.close();
                    conn.close();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao agendar: " + ex.getMessage());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Valor inválido!");
                }
            }
        });

        jbCancelar.addActionListener(e -> limparCampos());

        jbDeletar.addActionListener(e -> {
            int linha = tabelaAgendamentos.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(null, "Selecione um agendamento para excluir.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(null, "Deseja excluir este agendamento?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int id = (int) tabelaAgendamentos.getValueAt(linha, 0);

                try (Connection conn = FabricaConexao.conectar()) {
                    PreparedStatement ps = conn.prepareStatement("DELETE FROM agendamentos WHERE id = ?");
                    ps.setInt(1, id);
                    ps.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Agendamento excluído com sucesso!");
                    carregarAgendamentos();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao excluir: " + ex.getMessage());
                }
            }
        });

        jbEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = tabelaAgendamentos.getSelectedRow();
                if (linhaSelecionada == -1) {
                    JOptionPane.showMessageDialog(null, "Selecione um agendamento na tabela para editar.");
                    return;
                }

                try {
                    int idAgendamento = (int) tabelaAgendamentos.getValueAt(linhaSelecionada, 0);

                    String alunoNome = (String) jcbAlunos.getSelectedItem();
                    String tipoAula = (String) jcbDescricaoServico.getSelectedItem();
                    String valorStr = jtfValorServico.getText();
                    String data = jtfDataAgendamento.getText();
                    String hora = jtfHoraAgendamento.getText();
                    String idClienteStr = jtfIdCliente.getText();

                    if (alunoNome == null || alunoNome.equals("Selecione:") || tipoAula == null ||
                            valorStr.isEmpty() || data.isEmpty() || hora.isEmpty() || idClienteStr.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                        return;
                    }

                    int idCliente = Integer.parseInt(idClienteStr);
                    int valor = Integer.parseInt(valorStr);

                    Connection conn = FabricaConexao.conectar();

                    String sqlUpdate = "UPDATE agendamentos SET fk_idCliente = ?, tipoAula = ?, Valor = ?, dataAgendada = ?, horaAgendada = ? WHERE id = ?";
                    PreparedStatement ps = conn.prepareStatement(sqlUpdate);
                    ps.setInt(1, idCliente);
                    ps.setString(2, tipoAula);
                    ps.setInt(3, valor);
                    ps.setString(4, data);
                    ps.setString(5, hora);
                    ps.setInt(6, idAgendamento);

                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Agendamento atualizado com sucesso!");

                    ps.close();
                    conn.close();

                    limparCampos();
                    carregarTabela();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + ex.getMessage());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Valor inválido!");
                }
            }
        });

        tabelaAgendamentos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int linha = tabelaAgendamentos.getSelectedRow();
                if (linha != -1) {
                    String id = tabelaAgendamentos.getValueAt(linha, 0).toString();
                    String nome = tabelaAgendamentos.getValueAt(linha, 1).toString();
                    String tipoAula = tabelaAgendamentos.getValueAt(linha, 2).toString();
                    String valor = tabelaAgendamentos.getValueAt(linha, 3).toString();
                    String data = tabelaAgendamentos.getValueAt(linha, 4).toString();
                    String hora = tabelaAgendamentos.getValueAt(linha, 5).toString();

                    jtfIdCliente.setText(id);
                    jcbAlunos.setSelectedItem(nome);
                    jcbDescricaoServico.setSelectedItem(tipoAula);
                    jtfValorServico.setText(valor);
                    jtfDataAgendamento.setText(data);
                    jtfHoraAgendamento.setText(hora);
                }
            }
        });



        jbSair.addActionListener(e -> dispose());

        jtfValorServico.setEditable(false);

        setVisible(true);
    }

    private void carregarAlunos() {
        try (Connection conn = FabricaConexao.conectar()) {
            String sql = "SELECT nome FROM clientes ORDER BY nome";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            jcbAlunos.removeAllItems();
            jcbAlunos.addItem("Selecione:");

            while (rs.next()) {
                jcbAlunos.addItem(rs.getString("nome"));
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar alunos: " + e.getMessage());
        }
    }

    private void carregarAgendamentos() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Cliente");
        modelo.addColumn("Tipo Aula");
        modelo.addColumn("Valor");
        modelo.addColumn("Data");
        modelo.addColumn("Hora");

        try (Connection conn = FabricaConexao.conectar()) {
            String sql = "SELECT a.id, c.nome, a.tipoAula, a.Valor, a.dataAgendada, a.horaAgendada " +
                    "FROM agendamentos a JOIN clientes c ON a.fk_idCliente = c.id";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("tipoAula"),
                        rs.getInt("Valor"),
                        rs.getString("dataAgendada"),
                        rs.getString("horaAgendada")
                });
            }

            tabelaAgendamentos.setModel(modelo);
            tabelaAgendamentos.setRowHeight(30);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar agendamentos: " + e.getMessage());
        }
    }

    private void limparCampos() {
        jcbAlunos.setSelectedIndex(0);
        jtfIdCliente.setText("");
        jcbDescricaoServico.setSelectedIndex(0);
        jtfValorServico.setText("");
        jtfDataAgendamento.setText("");
        jtfHoraAgendamento.setText("");
    }
    private void carregarTabela() {
        try {
            Connection conn = FabricaConexao.conectar();
            String sql = "SELECT a.id, c.nome, a.tipoAula, a.valor, a.dataAgendada, a.horaAgendada " +
                    "FROM agendamentos a JOIN clientes c ON a.fk_idCliente = c.id";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            DefaultTableModel modelo = new DefaultTableModel();
            modelo.addColumn("ID");
            modelo.addColumn("Cliente");
            modelo.addColumn("Tipo Aula");
            modelo.addColumn("Valor");
            modelo.addColumn("Data");
            modelo.addColumn("Hora");

            while (rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("tipoAula"),
                        rs.getInt("valor"),
                        rs.getString("dataAgendada"),
                        rs.getString("horaAgendada")
                });
            }

            tabelaAgendamentos.setModel(modelo);

            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar tabela: " + ex.getMessage());
        }
    }

}
