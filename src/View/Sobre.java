package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Sobre extends JFrame {

    private JPanel painelSobre;
    private JPanel jpTopo;
    private JPanel jpCentro;
    private JButton jbSair;
    private JTextArea jtaCentral;
    private JTextPane jtpCentral;
    private JLabel jlCentral;

    public Sobre() {
        setSize(500, 300);
        setContentPane(painelSobre);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        jbSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        jtaCentral.setText(
                "Esse sistema foi desenvolvido para auxiliar na gestão de uma academia de boxe.\n" +
                        "Permite cadastro de alunos, agendamentos de aulas e visualização de relatórios.\n\n" +
                        "Versão: 1.1\n" +
                        "Desenvolvedor: Matheus Cesar Medeiros De Carvalho\n" +
                        "Contato: maatheuscr7m10@gmail.com"
        );

        jtaCentral.setWrapStyleWord(true);
        jtaCentral.setEditable(false);
        jtaCentral.setFocusable(false);
        jtaCentral.setOpaque(false);
    }
}
