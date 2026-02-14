package model;

import java.util.*;

public class Algoritmos {

    /** Resultado de Prim sobre una componente: el MST (lista de aristas) y los nodos de esa componente. */
    private static class ResultadoPrim {
        final List<Arista> arbol;
        final Set<User> visitados;

        ResultadoPrim(List<Arista> arbol, Set<User> visitados) {
            this.arbol = arbol;
            this.visitados = visitados;
        }
    }

    /**
     * Ejecuta Prim desde un nodo dado. Devuelve el MST de la componente que contiene a ese nodo
     * y el conjunto de nodos visitados (toda la componente).
     */
    private static ResultadoPrim primDesde(Grafo grafo, User inicial) {
        List<Arista> arbolMinimo = new ArrayList<>();
        Set<User> visitados = new HashSet<>();
        PriorityQueue<Arista> cola = new PriorityQueue<>(Comparator.comparingInt(Arista::getPeso));

        visitados.add(inicial);
        List<Arista> adyInicial = grafo.getAdyacentes(inicial);
        if (adyInicial != null) {
            cola.addAll(adyInicial);
        }

        while (!cola.isEmpty()) {
            Arista aristaMin = cola.poll();
            User destino = aristaMin.getDestino();

            if (!visitados.contains(destino)) {
                arbolMinimo.add(aristaMin);
                visitados.add(destino);
                List<Arista> adyDestino = grafo.getAdyacentes(destino);
                if (adyDestino != null) {
                    for (Arista a : adyDestino) {
                        if (!visitados.contains(a.getDestino())) {
                            cola.add(a);
                        }
                    }
                }
            }
        }

        return new ResultadoPrim(arbolMinimo, visitados);
    }

    /**
     * Resultado de Prim: bosque (lista de MST por componente) y nodo inicial de cada árbol.
     */
    public static class ResultadoPrimCompleto {
        private final List<List<Arista>> bosque;
        private final List<User> nodosIniciales;

        ResultadoPrimCompleto(List<List<Arista>> bosque, List<User> nodosIniciales) {
            this.bosque = bosque;
            this.nodosIniciales = nodosIniciales;
        }

        public List<List<Arista>> getBosque() { return bosque; }
        public List<User> getNodosIniciales() { return nodosIniciales; }
    }

    /**
     * Árbol(es) de expansión mínima: una lista de aristas por cada componente conexa,
     * y el nodo inicial desde el que se ejecutó Prim en cada componente.
     */
    public static ResultadoPrimCompleto prim(Grafo grafo) {
        List<List<Arista>> bosque = new ArrayList<>();
        List<User> nodosIniciales = new ArrayList<>();
        Set<User> restantes = new HashSet<>(grafo.getUsuarios());

        if (restantes.isEmpty()) {
            return new ResultadoPrimCompleto(bosque, nodosIniciales);
        }

        while (!restantes.isEmpty()) {
            User inicio = restantes.iterator().next();
            nodosIniciales.add(inicio);
            ResultadoPrim r = primDesde(grafo, inicio);
            bosque.add(r.arbol);
            restantes.removeAll(r.visitados);
        }

        return new ResultadoPrimCompleto(bosque, nodosIniciales);
    }

    /**
     * Devuelve el nodo inicial de cada árbol MST (uno por componente).
     * Sirve para sugerir que esas personas se conecten y unan las componentes.
     */
    public static List<User> sugerirAmigos(Grafo grafo) {
        return prim(grafo).getNodosIniciales();
    }

    public static int costoTotal(List<Arista> aristas) {
        int total = 0;
        for (Arista a : aristas) {
            total += a.getPeso();
        }
        return total;
    }
    //---- dijkstra

    public static Map<User, Integer> dijkstra(Grafo grafo, User origen) {
        // Mapa para guardar la distancia mínima desde el origen a cada usuario
        Map<User, Integer> distancias = new HashMap<>();
        // Priorizamos los usuarios según la distancia acumulada más corta
        PriorityQueue<NodoDistancia> cola = new PriorityQueue<>(
                Comparator.comparingInt(NodoDistancia::getDistancia));

        // Inicializamos todas las distancias como "infinito"
        for (User u : grafo.getUsuarios()) {
            distancias.put(u, Integer.MAX_VALUE);
        }

        // La distancia al origen es 0
        distancias.put(origen, 0);
        cola.add(new NodoDistancia(origen, 0));

        while (!cola.isEmpty()) {
            NodoDistancia actual = cola.poll();
            User uActual = actual.getUser();

            // Si ya encontramos un camino más corto, ignoramos este
            if (actual.getDistancia() > distancias.get(uActual)) continue;

            // Revisamos los adyacentes
            for (Arista arista : grafo.getAdyacentes(uActual)) {
                User vecino = arista.getDestino();
                int nuevaDistancia = distancias.get(uActual) + arista.getPeso();

                // Si el nuevo camino es más corto, actualizamos
                if (nuevaDistancia < distancias.get(vecino)) {
                    distancias.put(vecino, nuevaDistancia);
                    cola.add(new NodoDistancia(vecino, nuevaDistancia));
                }
            }
        }
        return distancias;
    }

    // Clase interna auxiliar para la PriorityQueue
    private static class NodoDistancia {
        private User user;
        private int distancia;

        public NodoDistancia(User user, int distancia) {
            this.user = user;
            this.distancia = distancia;
        }

        public User getUser() { return user; }
        public int getDistancia() { return distancia; }
    }


    /**
     * Indica si el grafo es conexo: hay exactamente un árbol de expansión mínima (una componente).
     * Usa el algoritmo Prim por componentes para contar cuántos MST hay.
     */
    public static boolean esConexo(Grafo grafo) {
        return prim(grafo).getBosque().size() == 1;
    }






    
}
