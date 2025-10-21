package Controller;
import DAO.FabricaConexao;
import Model.Usuarios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {

    public Usuarios validarLogin(String login, String senha, String perfil) throws SQLException {

        Usuarios levaDados;
        levaDados = null;

        String sql = "SELECT * FROM usuarios WHERE login = ? AND senha = ? AND perfil = ?";

        try (Connection conn = FabricaConexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);
            stmt.setString(2, senha);
            stmt.setString(3, perfil);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                levaDados = new Usuarios();
                levaDados.setLogin(rs.getNString("login"));
                levaDados.setSenha(rs.getNString("senha"));
                levaDados.setPerfil(rs.getNString("perfil"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao validar login: " + e.getMessage());

        }
return levaDados;

    }

}
