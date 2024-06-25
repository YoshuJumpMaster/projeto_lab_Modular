package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Double> itens;
    private LocalDateTime horaEntrada;
    private Cliente cliente; 

    public Pedido() {
        this.itens = new HashMap<>();
        this.horaEntrada = LocalDateTime.now();
    }

    public void adicionarItem(String nome, double preco) {
        itens.put(nome, preco);
    }

    public double getValorTotal() {
        return itens.values().stream().mapToDouble(Double::doubleValue).sum();
    }

    public Map<String, Double> getItens() {
        return itens;
    }

    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    public double calcularValorComServico() {
        return getValorTotal() * 1.10;
    }

    public LocalDateTime getData() {
        return horaEntrada;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
