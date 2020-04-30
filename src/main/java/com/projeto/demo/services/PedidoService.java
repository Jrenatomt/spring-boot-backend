package com.projeto.demo.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.demo.domain.ItemPedido;
import com.projeto.demo.domain.PagamentoComBoleto;
import com.projeto.demo.domain.Pedido;
import com.projeto.demo.domain.enums.EstadoPagamento;
import com.projeto.demo.repositories.ItemPedidoRepository;
import com.projeto.demo.repositories.PagamentoRepository;
import com.projeto.demo.repositories.PedidoRepository;
import com.projeto.demo.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;
	@Autowired
	private BoletoService boletoService;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public Pedido find(Integer id) {
		Optional<Pedido> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	@Transactional
	public Pedido insert(Pedido obj) {

		obj.setId(null);

		obj.setInstante(new Date());

		obj.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);

		obj.getPagamento().setPedido(obj);

		if (obj.getPagamento() instanceof PagamentoComBoleto) {

			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();

			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());

		}

		obj = repository.save(obj);

		pagamentoRepository.save(obj.getPagamento());

		for (ItemPedido ip : obj.getItens()) {

			ip.setDisconto(0.0);

			ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());

			ip.setPedido(obj);

		}

		itemPedidoRepository.saveAll(obj.getItens());

		return obj;

	}

}