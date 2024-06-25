package restaurante;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalTime;
import java.util.ArrayList;

public class DataInitializer {
    public static void main(String[] args) {
        ArrayList<Mesa> mesas = new ArrayList<>();
        mesas.add(new Mesa(4, true));
        mesas.add(new Mesa(6, true));
        mesas.add(new Mesa(8, true));

        // Inicializar menu
        Menu menu = Menu.getInstance();
        menu.adicionarItem("Hamburguer", 10.0);
        menu.adicionarItem("Batata Frita", 5.0);
        menu.adicionarItem("Refrigerante", 3.0);

        // Inicializar lista de espera com requisições de mesa
        ListaDeEspera listaDeEspera = new ListaDeEspera(mesas);
        RequisicaoDeMesa requisicao1 = new RequisicaoDeMesa("Cliente1", 4, LocalTime.now(), mesas.get(0));
        listaDeEspera.adicionarNaLista(requisicao1);

        RequisicaoDeMesa requisicao2 = new RequisicaoDeMesa("Cliente2", 2, LocalTime.now(), mesas.get(1));
        listaDeEspera.adicionarNaLista(requisicao2);

        try (ObjectOutputStream oosMesas = new ObjectOutputStream(new FileOutputStream("mesas.ser"));
             ObjectOutputStream oosMenu = new ObjectOutputStream(new FileOutputStream("menu.ser"));
             ObjectOutputStream oosListaDeEspera = new ObjectOutputStream(new FileOutputStream("listaDeEspera.ser"))) {
            oosMesas.writeObject(mesas);
            oosMenu.writeObject(menu);
            oosListaDeEspera.writeObject(listaDeEspera);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
