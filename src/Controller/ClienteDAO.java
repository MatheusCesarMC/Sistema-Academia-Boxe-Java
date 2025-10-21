package Controller;

import DAO.FabricaConexao;
import Model.ModelClientes;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class ClienteDAO {

    public ResultSet ListaClientes() {
        String sql = "SELECT * FROM Clientes";
        try {
        Connection conn = FabricaConexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
            return stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Erro ao validar login: " + e.getMessage());
        }
        return null;
    }

    public void deletarCliente(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?";
        try (Connection conn = FabricaConexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao deletar cliente");
        }
    }


    public void inserirCliente(ModelClientes clientes) {
        String sql = "INSERT INTO clientes (nome, cpf, endereco, telefone, email, sexo, dataNascimento) VALUES (?, ?, ?, ?, ?, ?, ?)";
        System.out.println("Sucesso!!");
        System.out.println(clientes.getNome());
        try (Connection conn = FabricaConexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, clientes.getNome());
            stmt.setString(2, clientes.getCpf());
            stmt.setString(3, clientes.getEndereco());
            stmt.setString(4, clientes.getTelefone());
            stmt.setString(5, clientes.getEmail());
            stmt.setString(6, clientes.getSexo());
            stmt.setString(7, clientes.getDataNascimento());

            int linhasAfetadas = stmt.executeUpdate();
            System.out.println("Dados gravados com sucesso! " + linhasAfetadas);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao inserir cliente");
        }
    }
    public void updateClientes(ModelClientes c) {

        String query = "UPDATE clientes SET nome = ?,cpf = ?, endereco = ?, telefone = ?, email = ?, sexo = ?, dataNascimento = ? WHERE id = ?";




        try (Connection connection = FabricaConexao.conectar();
             PreparedStatement pstmt = connection.prepareStatement(query)) {


            pstmt.setString(1, c.getNome());
            pstmt.setString(2, c.getCpf());
            pstmt.setString(3, c.getEndereco());
            pstmt.setString(4, c.getTelefone());
            pstmt.setString(5, c.getEmail());
            pstmt.setString(6, c.getSexo());
            pstmt.setString(7, c.getDataNascimento());
            pstmt.setString(8, c.getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
}
