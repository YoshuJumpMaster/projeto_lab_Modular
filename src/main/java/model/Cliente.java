package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String nome;
    private int numeroPessoas;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSaida;

    public Cliente(String nome, int numeroPessoas) {
        this.nome = nome;
        this.numeroPessoas = numeroPessoas;
        this.horaEntrada = LocalDateTime.now();
    }

    public String getNome() {
        return nome;
    }

    public int getNumeroPessoas() {
        return numeroPessoas;
    }

    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    public LocalDateTime getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida() {
        this.horaSaida = LocalDateTime.now();
    }
}
