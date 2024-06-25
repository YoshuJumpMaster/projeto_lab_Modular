package main.java;

import controller.RestauranteController;
import view.RestauranteView;

public class RestauranteApp {
    public static void main(String[] args) {
        RestauranteController controller = new RestauranteController();
        RestauranteView view = new RestauranteView(controller);
        view.mostrar();
    }
}
