package restaurante;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Pedido implements Serializable {
    private List<ItemMenu> itens;
    private double total;

    public Pedido() {
        this.itens = new ArrayList<>();
        this.total = 0.0;
    }

    public void adicionarItem(ItemMenu item) {
        itens.add(item);
        total += item.getPreco();
    }

    public void removerItem(ItemMenu item) {
        if (itens.remove(item)) {
            total -= item.getPreco();
        }
    }

    public double calcularTotal() {
        return total * 1.1; // Taxa de serviço de 10%
    }

    public List<ItemMenu> getItens() {
        return itens;
    }

    public void limparPedido() {
        itens.clear();
        total = 0.0;
    }

    @Override
    public String toString() {
        String pedido = itens.stream()
            .map(ItemMenu::toString)
            .collect(Collectors.joining("\n"));
        return pedido + "\nTotal: R$ " + total + "\n";
    }
}
