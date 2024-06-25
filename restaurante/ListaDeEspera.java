package restaurante;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListaDeEspera implements Serializable {
    private ArrayList<RequisicaoDeMesa> listaRequisicao;
    private ArrayList<RequisicaoDeMesa> historico;
    private ArrayList<Mesa> mesasDisponiveis;

    public ListaDeEspera(ArrayList<Mesa> mesasDisponiveis) {
        this.listaRequisicao = new ArrayList<>();
        this.historico = new ArrayList<>();
        this.mesasDisponiveis = mesasDisponiveis;
    }

    public void adicionarNaLista(RequisicaoDeMesa requisicao) {
        if (requisicao == null) {
            throw new IllegalArgumentException("Requisição não pode ser vazia");
        }
        this.listaRequisicao.add(requisicao);
        this.historico.add(requisicao);
    }

    public void removerDaLista(RequisicaoDeMesa requisicao) {
        this.listaRequisicao.remove(requisicao);
    }

    public void removerDaListaPorNome(String nomeCliente) {
        listaRequisicao.stream()
            .filter(requisicao -> requisicao.getNomeCliente().equals(nomeCliente))
            .findFirst()
            .ifPresent(requisicao -> {
                requisicao.getMesaAtribuida().desocuparMesa();
                listaRequisicao.remove(requisicao);
            });
    }

    public String imprimirLista() {
        return listaRequisicao.stream()
            .map(requisicao -> "Cliente: " + requisicao.getNomeCliente() +
                               ", Lugares: " + requisicao.getQuantiaPessoas() +
                               ", Mesa: " + requisicao.getMesaAtribuida().getNumeroAssentos() + " lugares\n")
            .collect(Collectors.joining());
    }

    public String imprimirHistorico() {
        return historico.stream()
            .map(requisicao -> "Cliente: " + requisicao.getNomeCliente() +
                               ", Lugares: " + requisicao.getQuantiaPessoas() + "\n")
            .collect(Collectors.joining());
    }

    public String imprimirCliente(String nomeCliente) {
        return listaRequisicao.stream()
            .filter(requisicao -> requisicao.getNomeCliente().equals(nomeCliente))
            .map(requisicao -> "Cliente: " + requisicao.getNomeCliente() +
                               ", Número de assentos: " + requisicao.getQuantiaPessoas() +
                               ", Hora de entrada: " + requisicao.getHoraEntrada().toString() +
                               ", Mesa: " + requisicao.getMesaAtribuida().getNumeroAssentos() + " lugares")
            .findFirst()
            .orElse("Cliente não encontrado na lista.");
    }

    public List<RequisicaoDeMesa> getListaRequisicao() {
        return listaRequisicao;
    }
}
