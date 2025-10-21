package Controller;

import DAO.FabricaConexao;
import Model.Produtos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutosDAO {


    public List<Produtos> listarProdutos() {
        List<Produtos> produtos = new ArrayList<>();
        String sql = "SELECT descricao, precoVenda FROM produtos";

        try (PreparedStatement stmt = FabricaConexao.conectar().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produtos p = new Produtos();
                        rs.getString("descricao");
                        rs.getDouble("precoVenda");
                produtos.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produtos;
    }
}