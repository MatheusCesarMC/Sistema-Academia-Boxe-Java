import View.Login;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame f = new JFrame("Sistema de Gestão - Hajime Boxing");
        f.setSize(350, 210);
        f.setContentPane(new Login().painelPrincipalLogin);
        f.setUndecorated(true);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);





    }
}