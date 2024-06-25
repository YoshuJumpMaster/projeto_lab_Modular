package controller;

import model.Cardapio;
import model.Cliente;
import model.Mesa;
import model.Pedido;
import model.Restaurante;

import java.io.IOException;
import java.util.List;

public class RestauranteController {
    private Restaurante restaurante;

    public RestauranteController() {
        this.restaurante = new Restaurante();
    }

    public List<Mesa> getMesas() {
        return restaurante.getMesas();
    }

    public void adicionarClienteNaFila(Cliente cliente) {
        restaurante.adicionarClienteNaFila(cliente);
        tentarAtribuirMesa(cliente);
    }

    public List<Cliente> getFilaEspera() {
        return restaurante.getFilaEspera();
    }

    public void adicionarPedido(Pedido pedido) {
        restaurante.adicionarPedido(pedido);
    }

    public List<Pedido> getPedidos() {
        return restaurante.getPedidos();
    }

    public void salvarDados(String filePath) throws IOException {
        restaurante.salvarDados(filePath);
    }

    public void carregarDados(String filePath) throws IOException, ClassNotFoundException {
        restaurante = Restaurante.carregarDados(filePath);
    }

    public Cardapio getCardapio() {
        return restaurante.getCardapio();
    }

    public void tentarAtribuirMesa(Cliente cliente) {
        for (Mesa mesa : restaurante.getMesas()) {
            if (!mesa.isOcupada() && mesa.getCapacidade() >= cliente.getNumeroPessoas()) {
                mesa.setCliente(cliente);
                restaurante.removerClienteDaFila(cliente);
                return;
            }
        }
    }

    public void adicionarPedidoAMesa(int indiceMesa, String item) {
        Mesa mesa = restaurante.getMesas().get(indiceMesa);
        if (mesa.isOcupada()) {
            Pedido pedido = new Pedido();
            pedido.adicionarItem(item, restaurante.getCardapio().getPratos().getOrDefault(item, 0.0));
            adicionarPedido(pedido);
        }
    }

    public double calcularContaMesa(int indiceMesa) {
        Mesa mesa = restaurante.getMesas().get(indiceMesa);
        Cliente cliente = mesa.getCliente();
        double total = 0.0;
        for (Pedido pedido : restaurante.getPedidos()) {
            
            if (pedido.getData().isAfter(cliente.getHoraEntrada())) {
                total += pedido.getValorTotal();
            }
        }
        return total * 1.10; 
    }
}
