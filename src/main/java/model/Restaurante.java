package model;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Restaurante implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Mesa> mesas;
    private List<Cliente> filaEspera;
    private List<Pedido> pedidos;
    private Cardapio cardapio;

    public Restaurante() {
        this.mesas = new ArrayList<>();
        this.filaEspera = new ArrayList<>();
        this.pedidos = new ArrayList<>();
        this.cardapio = new Cardapio();

        for (int i = 0; i < 4; i++) {
            mesas.add(new Mesa(4));
        }
        for (int i = 0; i < 4; i++) {
            mesas.add(new Mesa(6));
        }
        for (int i = 0; i < 2; i++) {
            mesas.add(new Mesa(8));
        }
    }

    public List<Mesa> getMesas() {
        return mesas;
    }

    public List<Cliente> getFilaEspera() {
        return filaEspera;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public Cardapio getCardapio() {
        return cardapio;
    }

    public void adicionarClienteNaFila(Cliente cliente) {
        filaEspera.add(cliente);
    }

    public void removerClienteDaFila(Cliente cliente) {
        filaEspera.remove(cliente);
    }

    public void adicionarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    public void salvarDados(String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(this);
        } catch (IOException e) {
            throw new IOException("Erro ao salvar dados", e);
        }
    }

    public static Restaurante carregarDados(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (Restaurante) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new IOException("Erro ao carregar dados", e);
        }
    }

    public double calcularReceitaPorData(LocalDateTime data) {
        double total = 0.0;
        for (Pedido pedido : pedidos) {
            LocalDateTime pedidoData = pedido.getData();
            Calendar cal = Calendar.getInstance();
            cal.setTime(Date.from(pedidoData.atZone(ZoneId.systemDefault()).toInstant()));
            if (cal.get(Calendar.YEAR) == data.getYear() &&
                cal.get(Calendar.DAY_OF_YEAR) == data.getDayOfYear()) {
                total += pedido.getValorTotal();
            }
        }
        return total;
    }
}
