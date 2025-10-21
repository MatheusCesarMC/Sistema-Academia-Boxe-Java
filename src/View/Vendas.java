    package View;

    import Controller.ClienteDAO;
    import Controller.VendedorDAO;
    import DAO.FabricaConexao;

    import javax.swing.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.util.ArrayList;

    public class Vendas extends JFrame {
        private JPanel painelPrincipal;
        private JPanel jpTopo;
        private JPanel jpcentro;
        private JPanel jpbaixo;
        private JButton jbVenda;
        private JButton jbFinaliza;
        private JButton jbSair;
        private JPanel jpLateral;
        private JComboBox<String> jcbVendedor;
        private JTextField textField1;
        private JTextField textField2;
        private JComboBox<String> jcbCliente;
        private JRadioButton pixRadioButton;
        private JRadioButton cartãoCreditoRadioButton;
        private JRadioButton cartãoDébitoRadioButton;
        private JRadioButton boletoRadioButton;
        private JComboBox<String> jcbProduto;
        private JTextField jtfDesconto;
        public JTextField jtfValorUnitario;
        private JTextField jtfAcrecimo;
        private JTextField jtfCodigoProduto;
        private JButton jbInserir;
        private JLabel jlabel;
        private JLabel jlTotal;

        private StringBuilder itensSelecionados;
        private double acumulado = 0.0;
        private ButtonGroup grupoPagamento;

        public Vendas() {
            setSize(1322, 686);
            setContentPane(painelPrincipal);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
            setVisible(true);

            itensSelecionados = new StringBuilder("Itens selecionados:<br>");

            grupoPagamento = new ButtonGroup();
            grupoPagamento.add(pixRadioButton);
            grupoPagamento.add(cartãoCreditoRadioButton);
            grupoPagamento.add(cartãoDébitoRadioButton);
            grupoPagamento.add(boletoRadioButton);

            ListaClientes();
            ListaVendedores();
            ListaProdutos();

            jcbProduto.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String nomeSelecionado = (String) jcbProduto.getSelectedItem();
                    if (nomeSelecionado != null) {
                        try {
                            String sql = "SELECT id, precoVenda FROM produtos WHERE descricao = ?";
                            PreparedStatement ps = FabricaConexao.conectar().prepareStatement(sql);
                            ps.setString(1, nomeSelecionado);
                            ResultSet rs = ps.executeQuery();

                            if (rs.next()) {
                                double preco = rs.getDouble("precoVenda");
                                jtfValorUnitario.setText(String.format("%.2f", preco));
                                String id = String.valueOf(rs.getInt("id"));
                                jtfCodigoProduto.setText(id);
                            } else {
                                jtfValorUnitario.setText("Não encontrado");
                            }
                            rs.close();
                            ps.close();
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Erro ao buscar preço: " + ex.getMessage());
                        }
                    }
                }
            });

            jbSair.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });

            jbInserir.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String item = (String) jcbProduto.getSelectedItem();
                    String valorTexto = jtfValorUnitario.getText();
                    String descontoTexto = jtfDesconto.getText();
                    String acrecimoTexto = jtfAcrecimo.getText();

                    if (!valorTexto.isEmpty() && !valorTexto.equals("Não encontrado")) {
                        valorTexto = valorTexto.replace(",", ".");
                        descontoTexto = descontoTexto.replace(",", ".");
                        acrecimoTexto = acrecimoTexto.replace(",", ".");

                        try {
                            double valorDouble = Double.parseDouble(valorTexto);

                            double desconto = 0.0;
                            if (!descontoTexto.isEmpty()) {
                                desconto = Double.parseDouble(descontoTexto);
                                if (desconto < 0) desconto = 0;
                            }

                            double acrecimo = 0.0;
                            if (!acrecimoTexto.isEmpty()) {
                                acrecimo = Double.parseDouble(acrecimoTexto);
                                if (acrecimo < 0) acrecimo = 0;
                            }

                            double valorFinal = valorDouble - desconto + acrecimo;
                            if (valorFinal < 0) valorFinal = 0;

                            acumulado += valorFinal;
                            jlTotal.setText("Valor acumulado: " + String.format("%.2f", acumulado));
                            jtfValorUnitario.setText("Não encontrado");

                            itensSelecionados.append(item).append("\n");
                            itensSelecionados.append(String.format("Valor: %.2f (Desconto: %.2f, Acréscimo: %.2f)", valorFinal, desconto, acrecimo)).append("\n");
                            jlabel.setText("<html>" + itensSelecionados.toString().replace("\n", "<br>") + "</html>");

                            jcbProduto.setSelectedIndex(0);
                            jtfDesconto.setText("");
                            jtfAcrecimo.setText("");
                            jtfCodigoProduto.setText("");

                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Valor, desconto ou acréscimo inválido.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Por favor, insira um valor válido.");
                    }
                }
            });



            jbVenda.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jpLateral.setEnabled(true);
                    jpLateral.setVisible(true);

                    jtfValorUnitario.setText("");
                    jtfDesconto.setText("");
                    jtfAcrecimo.setText("");
                    jtfCodigoProduto.setText("");
                    jcbProduto.setSelectedIndex(0);

                    grupoPagamento.clearSelection();

                    acumulado = 0.0;
                    jlTotal.setText("0.0");

                    itensSelecionados = new StringBuilder();
                    jlabel.setText("");

                    limparCampos();
                }
            });

            jbFinaliza.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String cliente = (String) jcbCliente.getSelectedItem();
                    String vendedor = (String) jcbVendedor.getSelectedItem();
                    String formaPagamento = "";

                    if (pixRadioButton.isSelected()) formaPagamento = "Pix";
                    else if (cartãoCreditoRadioButton.isSelected()) formaPagamento = "Cartão Crédito";
                    else if (cartãoDébitoRadioButton.isSelected()) formaPagamento = "Cartão Débito";
                    else if (boletoRadioButton.isSelected()) formaPagamento = "Boleto";

                    String totalTexto = jlTotal.getText().replace("Valor acumulado: ", "").replace(",", ".");
                    double total = acumulado;

                    String dataVenda = textField1.getText();
                    String codigoVenda = textField2.getText();
                    String detalhes = itensSelecionados.toString();
                    detalhes = detalhes.replaceAll("<[^>]*>", "").trim();


                    if (cliente == null || vendedor == null || formaPagamento.isEmpty()
                            || total == 0.0 || dataVenda.isEmpty() || codigoVenda.isEmpty() || detalhes.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Preencha todos os campos e adicione ao menos um item.");
                        return;
                    }

                    try {
                        String sql = "INSERT INTO vendas (cliente, vendedor, formaDePagamento, total, dataVenda, codigoVenda, detalhes) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?)";

                        PreparedStatement ps = FabricaConexao.conectar().prepareStatement(sql);
                        ps.setString(1, cliente);
                        ps.setString(2, vendedor);
                        ps.setString(3, formaPagamento);
                        ps.setString(4, String.format("%.2f", total));
                        ps.setString(5, dataVenda);
                        ps.setString(6, codigoVenda);
                        ps.setString(7, detalhes);

                        ps.executeUpdate();
                        ps.close();

                        JOptionPane.showMessageDialog(null, "Venda registrada com sucesso!");

                        limparCampos();
                        jlabel.setText("");
                        acumulado = 0.0;
                        jlTotal.setText("Valor acumulado: 0.00");
                        grupoPagamento.clearSelection();
                        itensSelecionados = new StringBuilder("Itens selecionados: ");
                        jtfValorUnitario.setText("");
                        jtfDesconto.setText("");
                        jtfAcrecimo.setText("");
                        jtfCodigoProduto.setText("");
                        jcbProduto.setSelectedIndex(0);
                        jpLateral.setEnabled(false);
                        jpLateral.setVisible(false);

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao salvar venda: " + ex.getMessage());
                    }
                }
            });
        }

        private void ListaClientes() {
            try {
                ClienteDAO cli = new ClienteDAO();
                ResultSet rs = cli.ListaClientes();
                ArrayList<String> listaClientes = new ArrayList<>();
                while (rs.next()) {
                    String nome = rs.getString("nome");
                    String cpf = rs.getString("cpf");
                    listaClientes.add(nome + " - " + cpf);
                }
                for (String cliente : listaClientes) {
                    jcbCliente.addItem(cliente);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        private void ListaProdutos() {
            try {
                String sql = "SELECT descricao FROM produtos";
                PreparedStatement ps = FabricaConexao.conectar().prepareStatement(sql);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    jcbProduto.addItem(rs.getString("descricao"));
                }
                rs.close();
                ps.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao carregar produtos: " + e.getMessage());
            }
        }

        private void ListaVendedores() {
            try {
                VendedorDAO vdao = new VendedorDAO();
                ResultSet rs = vdao.ListaVendedores();
                ArrayList<String> listV = new ArrayList<>();
                while (rs.next()) {
                    String nome = rs.getString("nome");
                    String cpf = rs.getString("cpf");
                    listV.add(nome + " - " + cpf);
                }
                for (String vendedor : listV) {
                    jcbVendedor.addItem(vendedor);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        private void limparCampos() {
            textField1.setText("");
            textField2.setText("");
            jtfDesconto.setText("");
            jtfValorUnitario.setText("Não encontrado");
            jtfAcrecimo.setText("");
            jtfCodigoProduto.setText("");

            jcbVendedor.setSelectedIndex(0);
            jcbCliente.setSelectedIndex(0);
            jcbProduto.setSelectedIndex(0);

            pixRadioButton.setSelected(false);
            cartãoCreditoRadioButton.setSelected(false);
            cartãoDébitoRadioButton.setSelected(false);
            boletoRadioButton.setSelected(false);

            jlTotal.setText("0.0");
            itensSelecionados = new StringBuilder();
        }
    }
