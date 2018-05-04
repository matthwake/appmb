package com.quadystudio.mbbaby;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.quadystudio.mbbaby.domain.Categoria;
import com.quadystudio.mbbaby.domain.Cidade;
import com.quadystudio.mbbaby.domain.Cliente;
import com.quadystudio.mbbaby.domain.Endereco;
import com.quadystudio.mbbaby.domain.Estado;
import com.quadystudio.mbbaby.domain.Pagamento;
import com.quadystudio.mbbaby.domain.PagamentoComBoleto;
import com.quadystudio.mbbaby.domain.PagamentoComCartao;
import com.quadystudio.mbbaby.domain.Pedido;
import com.quadystudio.mbbaby.domain.Produto;
import com.quadystudio.mbbaby.domain.enums.EstadoPagamento;
import com.quadystudio.mbbaby.domain.enums.TipoCliente;
import com.quadystudio.mbbaby.repositories.CategoriaRepository;
import com.quadystudio.mbbaby.repositories.CidadeRepository;
import com.quadystudio.mbbaby.repositories.ClienteRepository;
import com.quadystudio.mbbaby.repositories.EnderecoRepository;
import com.quadystudio.mbbaby.repositories.EstadoRepository;
import com.quadystudio.mbbaby.repositories.PagamentoRepository;
import com.quadystudio.mbbaby.repositories.PedidoRepository;
import com.quadystudio.mbbaby.repositories.ProdutoRepository;

@SpringBootApplication
public class MbbabyApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(MbbabyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Macacões");
		Categoria cat2 = new Categoria(null, "Camisetas");
		
		Produto p1 = new Produto(null, "Macacão Homem-Aranha", 12.00);
		Produto p2 = new Produto(null, "Macacão Superman", 12.00);
		Produto p3 = new Produto(null, "Macacão Snoopy", 12.00);
		
		Produto p4 = new Produto(null, "Camiseta Homem-Aranha", 13.00);
		Produto p5 = new Produto(null, "Camiseta Superman", 13.00);
		Produto p6 = new Produto(null, "Camiseta Snoopy", 13.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p4, p5, p6));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat2));
		p6.getCategorias().addAll(Arrays.asList(cat2));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6));
		
		Estado est1 = new Estado(null, "Goías");
		Estado est2 = new Estado(null, "Minas Gerais");
		Estado est3 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Goiânia", est1);
		Cidade c2 = new Cidade(null, "Aparecida de Goiânia", est1);
		Cidade c3 = new Cidade(null, "Anápolis", est1);
		
		Cidade c4 = new Cidade(null, "Uberlândia", est2);
		Cidade c5 = new Cidade(null, "Belo Horizonte", est2);
		Cidade c6 = new Cidade(null, "Juiz de Fora", est2);
		
		Cidade c7 = new Cidade(null, "São Paulo", est3);
		Cidade c8 = new Cidade(null, "Campinas", est3);
		
		est1.getCidades().addAll(Arrays.asList(c1, c2, c3));
		est2.getCidades().addAll(Arrays.asList(c4, c5, c6));
		est3.getCidades().addAll(Arrays.asList(c7, c8));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2, est3));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5, c6, c7, c8));
	
		Cliente cli1 = new Cliente(null, "Breno Henrique", "brenohenrique@gmail.com", "36383418729", TipoCliente.PESSOAFISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("+55629887799620", "+5562974569812"));
		
		Endereco e1 = new Endereco(null, "RUA C 21", "535", "APT 19", "JARDIM NOE", "74265220", cli1, c1);
		Endereco e2 = new Endereco(null, "RUA C 22", "536", "SC", "JARDIM NOE", "74265220", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("04/05/2018 13:06"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("02/05/2018 10:46"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("06/05/2018 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		
	}
	
	
}
