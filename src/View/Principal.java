package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Principal extends JFrame {
    public JPanel painelPrincipal;
    private JPanel jpTop;
    private JPanel jpImagem;
    private JMenuBar jpBar;
    public JMenu jmCadastro;
    private JMenu jmRegistros;
    private JMenu jmAgendamento;
    private JMenu jmSair;
    private JMenuItem jmiClientes;
    private JMenuItem jmiSair;
    private JLabel jlImagem;
    private JMenuItem jmiAAulas;
    private JMenuItem jmiClientesRegistrados;
    private JMenu jmSobre;
    private JMenuItem jmiMais;
    private JMenu jmAtendimento;
    private JMenuItem jmiVendas;
    private JMenuItem jmiAulasRegistradas;
    private JMenuItem jmiVendasRegistradas;

    public Principal(String perfil) {
        setSize(1090, 600);
        setContentPane(painelPrincipal);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        if (perfil.equalsIgnoreCase("usuario")) {
            jmCadastro.setEnabled(false);
            jmRegistros.setEnabled(false);
            jmAgendamento.setEnabled(false);
            jmAtendimento.setEnabled(false);
        }

        setVisible(true);

        jmiSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int resposta = JOptionPane.showConfirmDialog(null, "Você Deseja Sair ",
                        "Sair do Sistema Hajime Boxing", JOptionPane.YES_NO_OPTION);
                if (resposta == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        jmiClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Clientes();
            }
        });

        jmiAAulas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Agendamentos();
            }
        });

        jmiMais.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Sobre();
            }
        });

        jmiVendas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Vendas();
            }
        });

        jmiClientesRegistrados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegistrosClientes();
            }
        });

        jmiAulasRegistradas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegistrosAulas();
            }
        });

        jmiVendasRegistradas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegistrosVendas();
            }
        });
    }
}
