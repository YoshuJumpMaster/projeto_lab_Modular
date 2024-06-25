package restaurante;

import java.io.Serializable;

public class PedidoDelivery extends Pedido implements Serializable {
    private double taxaEntrega;

    public PedidoDelivery(double taxaEntrega) {
        this.taxaEntrega = taxaEntrega;
    }

    @Override
    public double calcularTotal() {
        return super.calcularTotal() + taxaEntrega;
    }
}
