package br.com.cotiinformatica.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.cotiinformatica.entities.Produto;
import br.com.cotiinformatica.factories.ConnectionFactory;

public class ProdutoRepository {

	public void create(Produto produto) throws Exception {

		Connection connection = ConnectionFactory.getConnection();

		PreparedStatement statement = connection
				.prepareStatement("insert into produto(nome, preco, quantidade) values(?,?,?)");
		statement.setString(1, produto.getNome());
		statement.setDouble(2, produto.getPreco());
		statement.setInt(3, produto.getQuantidade());
		statement.execute();
		
		statement = connection.prepareStatement("select lastval() as idproduto");
		ResultSet resultSet = statement.executeQuery();
		
		if(resultSet.next())
			produto.setIdProduto(resultSet.getInt("idproduto"));

		connection.close();
	}

	public void update(Produto produto) throws Exception {

		Connection connection = ConnectionFactory.getConnection();

		PreparedStatement statement = connection
				.prepareStatement("update produto set nome=?, preco=?, quantidade=? where idproduto=?");
		statement.setString(1, produto.getNome());
		statement.setDouble(2, produto.getPreco());
		statement.setInt(3, produto.getQuantidade());
		statement.setInt(4, produto.getIdProduto());
		statement.execute();

		connection.close();
	}
	
	public void delete(Integer idProduto) throws Exception {
		
		Connection connection = ConnectionFactory.getConnection();
		
		PreparedStatement statement = connection
				.prepareStatement("delete from produto where idproduto=?");
		statement.setInt(1, idProduto);
		statement.execute();
		
		connection.close();
	}
	
	public List<Produto> findAll() throws Exception {
		
		Connection connection = ConnectionFactory.getConnection();
		
		PreparedStatement statement = connection
				.prepareStatement("select * from produto");
		ResultSet resultSet = statement.executeQuery();
		
		List<Produto> lista = new ArrayList<Produto>();
		
		while(resultSet.next()) {
			
			Produto produto = new Produto();
			
			produto.setIdProduto(resultSet.getInt("idproduto"));
			produto.setNome(resultSet.getString("nome"));
			produto.setPreco(resultSet.getDouble("preco"));
			produto.setQuantidade(resultSet.getInt("quantidade"));
			
			lista.add(produto);
		}
		
		connection.close();
		return lista;
	}
	
	public Produto findById(Integer idProduto) throws Exception {
		
		Connection connection = ConnectionFactory.getConnection();
		
		PreparedStatement statement = connection.prepareStatement("select * from produto where idproduto=?");
		statement.setInt(1, idProduto);
		ResultSet resultSet = statement.executeQuery();
		
		Produto produto = null;
		
		if(resultSet.next()) {
			
			produto = new Produto();
			
			produto.setIdProduto(resultSet.getInt("idproduto"));
			produto.setNome(resultSet.getString("nome"));
			produto.setPreco(resultSet.getDouble("preco"));
			produto.setQuantidade(resultSet.getInt("quantidade"));			
		}
		
		connection.close();
		return produto;
	}

}













