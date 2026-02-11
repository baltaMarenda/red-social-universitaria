package model;

public class Arista {

    private User origen;
    private User destino;
    private int peso;

    public Arista(User origen, User destino, int peso) {
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
    }

    public User getDestino() {
        return destino;
    }

    public int getPeso() {
        return peso;
    }

    public User getOrigen() {
        return origen;
    }
}
