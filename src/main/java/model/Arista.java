package model;

/**
 * Representa una arista (conexión) entre dos usuarios en el grafo.
 * En la red social: una amistad entre origen y destino, con un peso (ej. afinidad, distancia).
 */
public class Arista {

    /** Usuario que representa el extremo origen de la arista. */
    private User origen;
    /** Usuario que representa el extremo destino de la arista. */
    private User destino;
    /** Peso de la arista (ej. costo, distancia o afinidad entre los usuarios). */
    private int peso;

    /**
     * Crea una arista entre dos usuarios con el peso dado.
     * @param origen usuario en un extremo de la conexión
     * @param destino usuario en el otro extremo
     * @param peso valor numérico asociado a la conexión (ej. para Prim/Dijkstra)
     */
    public Arista(User origen, User destino, int peso) {
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
    }

    /** Devuelve el usuario destino de esta arista. */
    public User getDestino() {
        return destino;
    }

    /** Devuelve el peso de esta arista. */
    public int getPeso() {
        return peso;
    }

    /** Devuelve el usuario origen de esta arista. */
    public User getOrigen() {
        return origen;
    }
}
