package model;

import java.io.Serializable;

public class Delivery extends Pedido implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private double taxaEntrega;

    public Delivery() {
        super();
        this.taxaEntrega = 0.0;
    }

    public void setTaxaEntrega(double taxaEntrega) {
        this.taxaEntrega = taxaEntrega;
    }

    @Override
    public double calcularValorComServico() {
        return getValorTotal() + taxaEntrega;
    }
}
