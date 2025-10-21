package View;

import Controller.LoginDAO;
import Model.Usuarios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.SQLException;


public class Login extends JFrame {
    public JPanel painelPrincipalLogin;
    private JPanel jpTopo;
    private JPanel jpInferior;
    private JTextField jtfLogin;
    private JButton jbSair;
    private JButton jbLogar;
    private JPasswordField jsfSenha;
    private JComboBox jcbBox;
    private JPanel jpDireito;

    public Login() {

        jbLogar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String login = jtfLogin.getText();

                String senha = new String(jsfSenha.getPassword());
                String perfil = String.valueOf(jcbBox.getSelectedItem());
                LoginDAO loginDAO = new LoginDAO();
                Usuarios resultado = null;
                try {
                    resultado = loginDAO.validarLogin(login, senha, perfil);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                if (resultado!=null) {
                    System.out.println("Login bem-sucedido!");
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(painelPrincipalLogin);
                    frame.dispose();
                    Usuarios finalResultado = resultado;
                    SwingUtilities.invokeLater(() -> {
                        Principal home = new Principal(finalResultado.getPerfil());
                        home.setVisible(true);

                    });

                } else {
                    System.out.println("Usuário ou senha inválidos.");
                    jtfLogin.requestFocus();
                    jtfLogin.setText("");
                    jsfSenha.setText("");
                    JOptionPane.showMessageDialog(null, "Login ou senha Incorretos!");
                }
            }
        });
        jtfLogin.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                jtfLogin.setBackground(Color.getHSBColor(255 ,218 ,185));
            }
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                jtfLogin.setBackground(Color.getHSBColor(55, 255, 55));
            }
        });
        jsfSenha.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                jsfSenha.setBackground(Color.getHSBColor(255 ,218 ,185));
            }
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                jsfSenha.setBackground(Color.getHSBColor(55, 255, 55));
            }
        });
        jbSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Saindo da Aplicação!");

                System.exit(0);
            }
        });
    }



}
