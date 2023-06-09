package com.nostra.pizzaria.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nostra.pizzaria.entities.enums.StatusPedido;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_pedido")
public class Pedido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	private Instant momentoPedido;

	private Integer statusPedido;

	@ManyToOne
	@JoinColumn(name = "pedidopessoa_id")
	private Pessoa pedidoPessoa;

	@OneToMany(mappedBy = "id.pedido")
	private Set<ItemPedido> itens = new HashSet<>();
	
	@OneToOne(mappedBy = "pedido", cascade =  CascadeType.ALL)
	private Pagamento pagamento;

	public Pedido() {

	}

	public Pedido(Long id, Instant momentoPedido, StatusPedido statusPedido, Pessoa pedidoPessoa) {
		super();
		this.id = id;
		this.momentoPedido = momentoPedido;
		setStatusPedido(statusPedido);
		this.pedidoPessoa = pedidoPessoa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getMomentoPedido() {
		return momentoPedido;
	}

	public void setMomentoPedido(Instant momentoPedido) {
		this.momentoPedido = momentoPedido;
	}

	public Pessoa getPedidoPessoa() {
		return pedidoPessoa;
	}

	public void setPedidoPessoa(Pessoa pedidoPessoa) {
		this.pedidoPessoa = pedidoPessoa;
	}

	public StatusPedido getStatusPedido() {
		return StatusPedido.valueOf(statusPedido);
	}

	public void setStatusPedido(StatusPedido statusPedido) {
		if (statusPedido != null) {
			this.statusPedido = statusPedido.getNumero();

		}
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public Set<ItemPedido> getItens() {
		return itens;
	}
	public double getTotal() {
		double soma = 0;
		for(ItemPedido z: itens) {
			 soma += z.getSubTotal();
		}
		return soma;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		return Objects.equals(id, other.id);
	}

}
