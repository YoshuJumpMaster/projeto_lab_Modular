package model;

import java.io.Serializable;

public abstract class Pagamento implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public abstract double calcularDesconto(double valor);
    public abstract int calcularPrazo();
}

class Dinheiro extends Pagamento {
    @Override
    public double calcularDesconto(double valor) {
        return 0;
    }

    @Override
    public int calcularPrazo() {
        return 0;
    }
}

class Pix extends Pagamento {
    @Override
    public double calcularDesconto(double valor) {
        return Math.min(valor * 0.0145, 10);
    }

    @Override
    public int calcularPrazo() {
        return 0;
    }
}

class Debito extends Pagamento {
    @Override
    public double calcularDesconto(double valor) {
        return valor * 0.014;
    }

    @Override
    public int calcularPrazo() {
        return 14;
    }
}

class Credito extends Pagamento {
    @Override
    public double calcularDesconto(double valor) {
        return valor * 0.031;
    }

    @Override
    public int calcularPrazo() {
        return 30;
    }
}
