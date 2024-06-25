package restaurante;

import java.io.Serializable;

public abstract class MetodoPagamento implements Serializable {
    public abstract double calcularDesconto(double valor);
    public abstract int getPrazoRecebimento();
}

class Dinheiro extends MetodoPagamento {
    @Override
    public double calcularDesconto(double valor) {
        return valor;
    }

    @Override
    public int getPrazoRecebimento() {
        return 0;
    }
}

class Pix extends MetodoPagamento {
    @Override
    public double calcularDesconto(double valor) {
        double desconto = valor * 0.0145;
        return valor - (desconto > 10 ? 10 : desconto);
    }

    @Override
    public int getPrazoRecebimento() {
        return 0;
    }
}

class Debito extends MetodoPagamento {
    @Override
    public double calcularDesconto(double valor) {
        return valor - (valor * 0.014);
    }

    @Override
    public int getPrazoRecebimento() {
        return 14;
    }
}

class Credito extends MetodoPagamento {
    @Override
    public double calcularDesconto(double valor) {
        return valor - (valor * 0.031);
    }

    @Override
    public int getPrazoRecebimento() {
        return 30;
    }
}
