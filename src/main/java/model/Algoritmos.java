package model;

import java.util.*;

/**
 * Algoritmos de grafos aplicados a la red social: Prim (árbol de expansión mínima por componente),
 * Dijkstra (distancias mínimas desde un origen), verificación de conexidad y sugerencia de reconexión.
 */
public class Algoritmos {

    /**
     * Resultado interno de ejecutar Prim sobre una sola componente: la lista de aristas del MST
     * y el conjunto de nodos que pertenecen a esa componente (todos los alcanzables desde el inicial).
     */
    private static class ResultadoPrim {
        final List<Arista> arbol;
        final Set<User> visitados;

        ResultadoPrim(List<Arista> arbol, Set<User> visitados) {
            this.arbol = arbol;
            this.visitados = visitados;
        }
    }

    /**
     * Ejecuta el algoritmo de Prim a partir de un único nodo. Obtiene el MST de la componente
     * que contiene a ese nodo y el conjunto de todos los nodos de esa componente.
     *
     * @param grafo   grafo no dirigido con pesos en las aristas
     * @param inicial nodo desde el cual se inicia Prim (puede ser cualquiera de la componente)
     * @return el MST como lista de aristas y el set de nodos visitados (toda la componente)
     */
    private static ResultadoPrim primDesde(Grafo grafo, User inicial) {
        List<Arista> arbolMinimo = new ArrayList<>();
        Set<User> visitados = new HashSet<>();
        // Cola de prioridad: siempre se extrae la arista de menor peso entre las que unen visitados con no visitados.
        PriorityQueue<Arista> cola = new PriorityQueue<>(Comparator.comparingInt(Arista::getPeso));

        visitados.add(inicial);
        List<Arista> adyInicial = grafo.getAdyacentes(inicial);
        if (adyInicial != null) {
            cola.addAll(adyInicial);
        }

        while (!cola.isEmpty()) {
            // Saca la arista de menor peso.
            Arista aristaMin = cola.poll();
            User destino = aristaMin.getDestino();

            // Solo se usa si el destino aún no estaba en el árbol (evita ciclos).
            if (!visitados.contains(destino)) {
                arbolMinimo.add(aristaMin);
                visitados.add(destino);
                // Añade a la cola las aristas que van de destino hacia nodos no visitados (nuevos candidatos).
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
     * Resultado público de prim(grafo): el bosque (una lista de MST, uno por componente)
     * y la lista de nodos iniciales (uno por componente, el nodo desde el que se ejecutó Prim).
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
     * Calcula un árbol de expansión mínima por cada componente conexa del grafo.
     * Si el grafo es conexo, devuelve una sola lista de aristas; si está desconectado, una lista por componente.
     * También devuelve el nodo inicial usado en cada ejecución de Prim (útil para sugerir reconexión).
     */
    public static ResultadoPrimCompleto prim(Grafo grafo) {
        List<List<Arista>> bosque = new ArrayList<>();
        List<User> nodosIniciales = new ArrayList<>();
        Set<User> restantes = new HashSet<>(grafo.getUsuarios());

        if (restantes.isEmpty()) {
            return new ResultadoPrimCompleto(bosque, nodosIniciales);
        }

        // Mientras quede algún nodo sin asignar a ningún árbol, hay una nueva componente: se ejecuta Prim desde uno de ellos.
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
     * Devuelve un representante (nodo inicial de Prim) por cada componente.
     * Sirve para sugerir que esas personas se conecten y así unir las componentes (reconexión de la red).
     */
    public static List<User> sugerirAmigos(Grafo grafo) {
        return prim(grafo).getNodosIniciales();
    }

    /**
     * Suma los pesos de una lista de aristas (ej. costo total de un MST).
     */
    public static int costoTotal(List<Arista> aristas) {
        int total = 0;
        for (Arista a : aristas) {
            total += a.getPeso();
        }
        return total;
    }

    /**
     * Algoritmo de Dijkstra: calcula la distancia mínima desde un usuario origen hasta todos los demás.
     * Las aristas tienen peso; la "distancia" es la suma de pesos del camino.
     *
     * @param grafo  grafo no dirigido con pesos
     * @param origen usuario desde el cual se calculan las distancias
     * @return mapa usuario -> distancia mínima desde origen (origen tiene distancia 0)
     */
    public static Map<User, Integer> dijkstra(Grafo grafo, User origen) {
        Map<User, Integer> distancias = new HashMap<>();
        PriorityQueue<NodoDistancia> cola = new PriorityQueue<>(
                Comparator.comparingInt(NodoDistancia::getDistancia));

        for (User u : grafo.getUsuarios()) {
            distancias.put(u, Integer.MAX_VALUE);
        }

        distancias.put(origen, 0);
        cola.add(new NodoDistancia(origen, 0));

        while (!cola.isEmpty()) {
            NodoDistancia actual = cola.poll();
            User uActual = actual.getUser();

            // Entrada obsoleta: ya se encontró un camino más corto a uActual; se descarta.
            if (actual.getDistancia() > distancias.get(uActual)) continue;

            for (Arista arista : grafo.getAdyacentes(uActual)) {
                User vecino = arista.getDestino();
                int nuevaDistancia = distancias.get(uActual) + arista.getPeso();

                if (nuevaDistancia < distancias.get(vecino)) {
                    distancias.put(vecino, nuevaDistancia);
                    cola.add(new NodoDistancia(vecino, nuevaDistancia));
                }
            }
        }
        return distancias;
    }

    /**
     * Par (usuario, distancia) para la cola de prioridad de Dijkstra:
     * se ordena por distancia para siempre expandir el nodo más cercano al origen.
     */
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
     * Determina si el grafo es conexo (una sola componente).
     * Usa Prim: si el bosque tiene exactamente un árbol, el grafo es conexo.
     */
    public static boolean esConexo(Grafo grafo) {
        return prim(grafo).getBosque().size() == 1;
    }
}
