package br.com.cotiinformatica.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.GetProdutosDTO;
import br.com.cotiinformatica.dtos.PostProdutosDTO;
import br.com.cotiinformatica.dtos.PutProdutosDTO;
import br.com.cotiinformatica.entities.Produto;
import br.com.cotiinformatica.repositories.ProdutoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Controle de produtos")
@RestController
public class ProdutosController {

	@ApiOperation("Serviço para cadastro de produto.")
	@PostMapping("/api/produtos")
	public ResponseEntity<GetProdutosDTO> post(@RequestBody PostProdutosDTO dto) {

		GetProdutosDTO result = new GetProdutosDTO();
		
		try {
			
			Produto produto = new Produto();
			
			produto.setNome(dto.getNome());
			produto.setPreco(dto.getPreco());
			produto.setQuantidade(dto.getQuantidade());
			
			ProdutoRepository produtoRepository = new ProdutoRepository();
			produtoRepository.create(produto);
			
			//retornando os dados do produto cadastrado
			result.setIdProduto(produto.getIdProduto());
			result.setNome(produto.getNome());
			result.setPreco(produto.getPreco());
			result.setQuantidade(produto.getQuantidade());
			
			return ResponseEntity.status(HttpStatus.CREATED).body(result);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}
	}

	@ApiOperation("Serviço para atualização de produto.")
	@PutMapping("/api/produtos")
	public ResponseEntity<GetProdutosDTO> put(@RequestBody PutProdutosDTO dto) {

		GetProdutosDTO result = new GetProdutosDTO();
		
		try {
			
			ProdutoRepository produtoRepository = new ProdutoRepository();
			Produto produto = produtoRepository.findById(dto.getIdProduto());
			
			if(produto != null) {
				
				produto.setNome(dto.getNome());
				produto.setPreco(dto.getPreco());
				produto.setQuantidade(dto.getQuantidade());
				
				produtoRepository.update(produto);
				
				result.setIdProduto(produto.getIdProduto());
				result.setNome(produto.getNome());
				result.setPreco(produto.getPreco());
				result.setQuantidade(produto.getQuantidade());
				
				return ResponseEntity.status(HttpStatus.OK).body(result);
			}
			else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}
	}

	@ApiOperation("Serviço para exclusão de produto.")
	@DeleteMapping("/api/produtos/{id}")
	public ResponseEntity<GetProdutosDTO> delete(@PathVariable("id") Integer idProduto) {

		GetProdutosDTO result = new GetProdutosDTO();
		
		try {
			
			ProdutoRepository produtoRepository = new ProdutoRepository();
			Produto produto = produtoRepository.findById(idProduto);
			
			if(produto != null) {
				
				produtoRepository.delete(idProduto);
				
				result.setIdProduto(produto.getIdProduto());
				result.setNome(produto.getNome());
				result.setPreco(produto.getPreco());
				result.setQuantidade(produto.getQuantidade());
				
				return ResponseEntity.status(HttpStatus.OK).body(result);
			}
			else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}
		
	}

	@ApiOperation("Serviço para consulta de produtos.")
	@GetMapping("/api/produtos")
	public ResponseEntity<List<GetProdutosDTO>> getAll() {

		List<GetProdutosDTO> lista = new ArrayList<GetProdutosDTO>();
		
		try {
			
			ProdutoRepository produtoRepository = new ProdutoRepository();
			for(Produto produto : produtoRepository.findAll()) {
				
				GetProdutosDTO dto = new GetProdutosDTO();
				dto.setIdProduto(produto.getIdProduto());
				dto.setNome(produto.getNome());
				dto.setPreco(produto.getPreco());
				dto.setQuantidade(produto.getQuantidade());
				
				lista.add(dto);
			}			
			
			return ResponseEntity.status(HttpStatus.OK).body(lista);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(lista);
		}
		
	}
}
