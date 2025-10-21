package View;

import Controller.ClienteDAO;
import DAO.FabricaConexao;
import Model.ModelClientes;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class Clientes extends JFrame {
    public JPanel painelClientes;
    private JPanel jpTopo;
    private JLabel jlTopo;
    private JPanel jpCentral;
    private JTextField jtfNome;
    private JTextField jtfCpf;
    private JTextField jtfTelefone;
    private JTextField jtfEmail;
    private JButton jbSair;
    private JButton jbSalvar;
    private JButton jbNovo;
    private JButton jbDeletar;
    private JPanel painelTabela;
    private JTable tabelaClientes;
    private JButton jbEditar;
    private JTextField jtfEndereco;
    private JTextField jtfSexo;
    private JTextField jtfId;
    private JTextField jtfDataDeNascimento;
    private JButton jbCancelar;

    public Clientes() {

        setTitle("Cadastro");
        setSize(1060, 640);
        setContentPane(painelClientes);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        carregarDados();
        desabilita();
        jtfId.setEnabled(false);
        jbSair.setEnabled(true);

        jbNovo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpaCampos();
                habilita();
                jtfNome.requestFocus();

                jbCancelar.setEnabled(true);
            }
        });


        jbSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        jtfNome.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                jtfNome.setBackground(Color.getHSBColor(206, 209, 218));
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                jtfNome.setBackground(Color.getHSBColor(170, 191, 222));
            }
        });

        jbSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = jtfId.getText();
                String nome = jtfNome.getText();
                String cpf = jtfCpf.getText();
                String telefone = jtfTelefone.getText();
                String dataN = jtfDataDeNascimento.getText();
                String end = jtfEndereco.getText();
                String email = jtfEmail.getText();
                String sexo = jtfSexo.getText();

                ModelClientes clientes = new ModelClientes();
                clientes.setNome(nome);
                clientes.setCpf(cpf);
                clientes.setEndereco(end);
                clientes.setTelefone(telefone);
                clientes.setEmail(email);
                clientes.setSexo(sexo);
                clientes.setDataNascimento(dataN);

                ClienteDAO cliDAO = new ClienteDAO();


                // if (id == null || id.trim().isEmpty()) {
                if (nome.isEmpty() || cpf.isEmpty() || end.isEmpty() || telefone.isEmpty() || email.isEmpty() || sexo.isEmpty() || dataN.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
                    carregarDados();
                } else {
                    clientes.setId(id);
                    cliDAO.inserirCliente(clientes);
                    cliDAO.updateClientes(clientes);

                    String mensagem = "Nome: " + nome +
                            "\nCPF: " + cpf +
                            "\nTelefone: " + telefone +
                            "\nDataDeNascimento: " + dataN +
                            "\nEmail: " + email +
                            "\nEndereço: " + end +
                            "\nSexo: " + sexo;
                    JOptionPane.showMessageDialog(null, mensagem, "Dados Cadastrados", JOptionPane.INFORMATION_MESSAGE);

                    carregarDados();
                    limpaCampos();
                    desabilita();
                    jbNovo.setEnabled(true);

                }
            }
        });

        jbDeletar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jtfId.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Selecione um cliente para deletar.");
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(null, "Deseja realmente deletar este cliente?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    String id = jtfId.getText();
                    ClienteDAO dao = new ClienteDAO();
                    dao.deletarCliente(Integer.parseInt(id));

                    carregarDados();
                    limpaCampos();
                }
            }
        });

        tabelaClientes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                habilita();
                jbNovo.setEnabled(false);
                jbSalvar.setEnabled(false);
                jbCancelar.setEnabled(true);
                if (!e.getValueIsAdjusting()) {
                    int linhaSelecionada = tabelaClientes.getSelectedRow();
                    if (linhaSelecionada != -1) {

                        jtfId.setText(tabelaClientes.getValueAt(linhaSelecionada, 0).toString());
                        jtfNome.setText(tabelaClientes.getValueAt(linhaSelecionada, 1).toString());
                        jtfCpf.setText(tabelaClientes.getValueAt(linhaSelecionada, 2).toString());
                        jtfEndereco.setText(tabelaClientes.getValueAt(linhaSelecionada, 3).toString());
                        jtfTelefone.setText(tabelaClientes.getValueAt(linhaSelecionada, 4).toString());
                        jtfEmail.setText(tabelaClientes.getValueAt(linhaSelecionada, 5).toString());
                        jtfSexo.setText(tabelaClientes.getValueAt(linhaSelecionada, 6).toString());
                        jtfDataDeNascimento.setText(tabelaClientes.getValueAt(linhaSelecionada, 7).toString());


                    }
                }
            }
        });


        jbEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    String id = jtfId.getText();
                    String nome = jtfNome.getText();
                    String cpf = jtfCpf.getText();
                    String telefone = jtfTelefone.getText();
                    String dataN = jtfDataDeNascimento.getText();
                    String end = jtfEndereco.getText();
                    String email = jtfEmail.getText();
                    String sexo = jtfSexo.getText();

                    ModelClientes clientes = new ModelClientes();
                    clientes.setId(id);
                    clientes.setNome(nome);
                    clientes.setCpf(cpf);
                    clientes.setEndereco(end);
                    clientes.setTelefone(telefone);
                    clientes.setEmail(email);
                    clientes.setSexo(sexo);
                    clientes.setDataNascimento(dataN);

                    ClienteDAO cliDAO = new ClienteDAO();
                    cliDAO.updateClientes(clientes);

                    carregarDados();


            }
        });


        jbCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desabilita();
                limpaCampos();

            }
        });
    }
    public void limpaCampos() {
        jtfNome.setText("");
        jtfCpf.setText("");
        jtfTelefone.setText("");
        jtfEmail.setText("");
        jtfEndereco.setText("");
        jtfSexo.setText("");
        jtfDataDeNascimento.setText("");
        jtfId.setText("");
    }

    public void desabilita() {
        jtfNome.setEnabled(false);
        jtfCpf.setEnabled(false);
        jtfTelefone.setEnabled(false);
        jtfEmail.setEnabled(false);
        jtfEndereco.setEnabled(false);
        jtfSexo.setEnabled(false);
        jtfDataDeNascimento.setEnabled(false);
        jtfId.setEnabled(false);
        jbEditar.setEnabled(false);
        jbNovo.setEnabled(true);
        jbSalvar.setEnabled(false);
        jbCancelar.setEnabled(false);
        jbDeletar.setEnabled(false);
        jbSair.setEnabled(true);
    }

    public void habilita() {
        jtfNome.setEnabled(true);
        jtfCpf.setEnabled(true);
        jtfTelefone.setEnabled(true);
        jtfEmail.setEnabled(true);
        jtfEndereco.setEnabled(true);
        jtfSexo.setEnabled(true);
        jtfDataDeNascimento.setEnabled(true);
        jtfId.setEnabled(false);
        jbEditar.setEnabled(true);
        jbNovo.setEnabled(false);
        jbSalvar.setEnabled(true);
        jbDeletar.setEnabled(true);
        jbSair.setEnabled(true);
    }


    public void carregarDados() {
        try (Connection conn = FabricaConexao.conectar()) {
            String query = "SELECT id,nome, cpf, endereco, telefone, email, sexo, dataNascimento FROM clientes";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            Vector<String> colunas = new Vector<>();
            colunas.add("Id");
            colunas.add("Nome");
            colunas.add("CPF");
            colunas.add("Endereço");
            colunas.add("Telefone");
            colunas.add("Email");
            colunas.add("Sexo");
            colunas.add("Data de Nascimento");

            System.out.println("Conectado com sucesso ao banco.");

            Vector<Vector<Object>> dados = new Vector<>();
            while (rs.next()) {
                Vector<Object> linha = new Vector<>();
                linha.add(rs.getString("id"));
                linha.add(rs.getString("nome"));
                linha.add(rs.getString("cpf"));
                linha.add(rs.getString("endereco"));
                linha.add(rs.getString("telefone"));
                linha.add(rs.getString("email"));
                linha.add(rs.getString("sexo"));
                linha.add(rs.getString("dataNascimento"));
                dados.add(linha);
            }

            tabelaClientes.setModel(new DefaultTableModel(dados, colunas));
            tabelaClientes.setRowHeight(30);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao carregar dados", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}




