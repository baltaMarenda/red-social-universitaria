package model;

import java.util.*;

/**
 * Grafo no dirigido que modela la red de amigos: cada usuario es un nodo,
 * cada amistad es una arista con peso. Implementado con lista de adyacencia.
 */
public class Grafo {
    /** Para cada usuario (nodo), la lista de aristas que lo unen a sus amigos (adyacentes). */
    private Map<User, List<Arista>> listaAdyacencia;

    /** Inicializa un grafo vacío (sin nodos ni aristas). */
    public Grafo() {
        listaAdyacencia = new HashMap<>();
    }

    /**
     * Agrega un usuario como nodo del grafo si aún no está.
     * No crea amistades; solo asegura que el nodo exista con una lista de adyacencia vacía.
     */
    public void agregarUsuario(User u) {
        listaAdyacencia.putIfAbsent(u, new ArrayList<>());
    }

    /**
     * Crea una amistad (arista no dirigida) entre u1 y u2 con el peso dado.
     * Si alguno no estaba en el grafo, lo agrega. La arista se guarda en ambas direcciones
     * (u1->u2 y u2->u1) para que el grafo sea no dirigido.
     */
    public void agregarAmigo(User u1, User u2, int peso) {
        agregarUsuario(u1);
        agregarUsuario(u2);

        listaAdyacencia.get(u1).add(new Arista(u1, u2, peso));
        listaAdyacencia.get(u2).add(new Arista(u2, u1, peso));
    }

    /** Devuelve el mapa interno: usuario -> lista de aristas hacia sus adyacentes. */
    public Map<User, List<Arista>> getListaAdyacencia() {
        return listaAdyacencia;
    }

    /** Devuelve el conjunto de todos los usuarios (nodos) del grafo. */
    public Set<User> getUsuarios() {
        return listaAdyacencia.keySet();
    }

    /** Devuelve la lista de aristas que salen del usuario u (sus amigos y el peso de cada conexión). Puede ser null si u no está en el grafo. */
    public List<Arista> getAdyacentes(User u) {
        return listaAdyacencia.get(u);
    }
}
