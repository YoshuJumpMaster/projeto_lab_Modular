package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Cardapio implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Double> pratos;
    private Map<String, Double> bebidas;

    public Cardapio() {
        pratos = new HashMap<>();
        bebidas = new HashMap<>();
        inicializarCardapio();
    }

    private void inicializarCardapio() {
        pratos.put("Moqueca de Tilápia", 30.0);
        pratos.put("Falafel Assado", 25.0);
        pratos.put("Salada Primavera com Macarrão Konjac", 20.0);
        pratos.put("Escondidinho de Frango", 27.0);
        pratos.put("Strogonoff", 29.0);
        pratos.put("Caçarola de carne com legumes", 32.0);
        
        bebidas.put("Água", 5.0);
        bebidas.put("Suco", 7.0);
        bebidas.put("Refrigerante", 6.0);
        bebidas.put("Cerveja", 10.0);
        bebidas.put("Taça de vinho", 15.0);
    }

    public Map<String, Double> getPratos() {
        return pratos;
    }

    public Map<String, Double> getBebidas() {
        return bebidas;
    }
}
