package com.projeto.demo;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.projeto.demo.domain.Categoria;
import com.projeto.demo.domain.Cidade;
import com.projeto.demo.domain.Cliente;
import com.projeto.demo.domain.Endereco;
import com.projeto.demo.domain.Estado;
import com.projeto.demo.domain.ItemPedido;
import com.projeto.demo.domain.Pagamento;
import com.projeto.demo.domain.PagamentoComBoleto;
import com.projeto.demo.domain.PagamentoComCartao;
import com.projeto.demo.domain.Pedido;
import com.projeto.demo.domain.Produto;
import com.projeto.demo.domain.enums.EstadoPagamento;
import com.projeto.demo.domain.enums.TipoCliente;
import com.projeto.demo.repositories.CategoriaRepository;
import com.projeto.demo.repositories.CidadeRepository;
import com.projeto.demo.repositories.ClienteRepository;
import com.projeto.demo.repositories.EnderecoRepository;
import com.projeto.demo.repositories.EstadoRepository;
import com.projeto.demo.repositories.ItemPedidoRepository;
import com.projeto.demo.repositories.PagamentoRepository;
import com.projeto.demo.repositories.PedidoRepository;
import com.projeto.demo.repositories.ProdutoRepository;

@SpringBootApplication
public class ProjetoSpring2Application implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired 
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	//@Autowired
	//private PedidoRepository pedidoRepository;

	//@Autowired
	//private PagamentoRepository pagamentoRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProjetoSpring2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");

		Produto p1 = new Produto(null, "computador", 20000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.0);

		// add produtos nas categorias
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		// add categorias nos produtos
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		// salvando categorias e produtos
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

		// add estados

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		// add cidades

		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente(null, "Maria jose", "Maria@gmail.com", "112118877", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("991300456", "991300654"));

		Endereco e1 = new Endereco(null, "Rua Flores", "32", "sala 800", "centro", "58475000", cli1, c1);
		Endereco e2 = new Endereco(null, "Rua Fulano de tal", "35", "sala 85", "centro", "58475123", cli1, c2);

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("11/04/2019 20:30"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/03/2019 18:00"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("13/03/2019 15:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
			
		pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1,pagto2));	
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.0);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItems().addAll(Arrays.asList(ip1,ip2));
		ped2.getItems().addAll(Arrays.asList(ip3));
		
		p1.getItems().addAll(Arrays.asList(ip1));
		p2.getItems().addAll(Arrays.asList(ip3));
		p3.getItems().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));

	}
}
