package model;

import java.io.Serializable;

public class Mesa implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int capacidade;
    private boolean ocupada;
    private Cliente cliente;

    public Mesa(int capacidade) {
        this.capacidade = capacidade;
        this.ocupada = false;
        this.cliente = null;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        this.ocupada = (cliente != null);
    }
}
