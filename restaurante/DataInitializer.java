package restaurante;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalTime;
import java.util.ArrayList;

public class DataInitializer {
    public static void main(String[] args) {
        ArrayList<Mesa> mesas = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            mesas.add(new Mesa(4, true));
            mesas.add(new Mesa(6, true));
        }
        mesas.add(new Mesa(8, true));
        mesas.add(new Mesa(8, true));

        Menu menu = Menu.getInstance();
        menu.adicionarItem(new ItemMenu("Moqueca de Tilápia", 50.0));
        menu.adicionarItem(new ItemMenu("Falafel Assado", 30.0));
        menu.adicionarItem(new ItemMenu("Salada Primavera com Macarrão Konjac", 25.0));
        menu.adicionarItem(new ItemMenu("Escondidinho de Frango", 40.0));
        menu.adicionarItem(new ItemMenu("Strogonoff", 35.0));
        menu.adicionarItem(new ItemMenu("Caçarola de carne com legumes", 45.0));
        menu.adicionarItem(new ItemMenu("Água", 5.0));
        menu.adicionarItem(new ItemMenu("Suco", 10.0));
        menu.adicionarItem(new ItemMenu("Refrigerante", 7.0));
        menu.adicionarItem(new ItemMenu("Cerveja", 12.0));
        menu.adicionarItem(new ItemMenu("Taça de vinho", 15.0));

        ListaDeEspera listaDeEspera = new ListaDeEspera(mesas);
        RequisicaoDeMesa requisicao1 = new RequisicaoDeMesa("Cliente1", 4, LocalTime.now(), mesas.get(0));
        RequisicaoDeMesa requisicao2 = new RequisicaoDeMesa("Cliente2", 2, LocalTime.now(), mesas.get(1));

        listaDeEspera.adicionarNaLista(requisicao1);
        listaDeEspera.adicionarNaLista(requisicao2);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("mesas.ser"))) {
            oos.writeObject(mesas);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("menu.ser"))) {
            oos.writeObject(menu);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("listaDeEspera.ser"))) {
            oos.writeObject(listaDeEspera);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
