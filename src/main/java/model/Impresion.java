package model;

import java.util.List;
import java.util.Map;

/**
 * Clase de utilidad para imprimir en consola los resultados de los algoritmos
 * (conectividad mínima, sugerencias de reconexión, recomendaciones de amigos).
 */
public class Impresion {

    /**
     * Imprime uno o varios árboles de expansión mínima (MST), uno por componente conexa.
     * Formato: "Arbol 1 ---", luego cada arista numerada (origen - destino | Peso), luego costo total del árbol; igual para Arbol 2, etc.
     *
     * @param bosque lista de listas de aristas: cada lista interna es el MST de una componente (resultado de Algoritmos.prim).
     */
    public static void conectividadMinima(List<List<Arista>> bosque) {
        // Si no hay ningún árbol (grafo vacío o sin datos), se informa y se sale.
        if (bosque == null || bosque.isEmpty()) {
            System.out.println("No hay árboles de expansión mínima.");
            return;
        }

        int numArbol = 1;
        for (List<Arista> mst : bosque) {
            // Encabezado de cada árbol (componente).
            System.out.println("Arbol " + numArbol + " ---");
            if (mst == null || mst.isEmpty()) {
                // Componente con un solo nodo: no hay aristas.
                System.out.println("  (sin aristas)");
            } else {
                int costoTotal = 0;
                int paso = 1;
                // Recorre las aristas en el orden en que Prim las eligió; imprime origen, destino y peso; acumula el costo.
                for (Arista a : mst) {
                    System.out.println(
                            "  " + paso + ". " +
                                    a.getOrigen().getNombre() + " - " +
                                    a.getDestino().getNombre() +
                                    " | Peso: " + a.getPeso()
                    );
                    costoTotal += a.getPeso();
                    paso++;
                }
                System.out.println("  Costo total de este árbol: " + costoTotal);
            }
            numArbol++;
        }
    }

    /**
     * Imprime la sugerencia de reconexión: lista los representantes de cada componente (nodo inicial de cada MST)
     * y sugiere que esas personas se hagan amigos para unir las componentes. Si hay 0 o 1 componente, no sugiere nada.
     *
     * @param nodosInicialesPorArbol lista con un usuario por componente (típicamente Algoritmos.prim(grafo).getNodosIniciales()).
     */
    public static void imprimirSugerencias(List<User> nodosInicialesPorArbol) {
        if (nodosInicialesPorArbol == null || nodosInicialesPorArbol.isEmpty()) {
            System.out.println("No hay componentes; no se requieren sugerencias.");
            return;
        }
        if (nodosInicialesPorArbol.size() == 1) {
            System.out.println("El grafo ya está conectado. No se requieren nuevas amistades.");
            return;
        }
        System.out.println("Para que todas las personas estén conectadas, se sugiere que las siguientes personas se hagan amigos entre sí:");
        for (int i = 0; i < nodosInicialesPorArbol.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + nodosInicialesPorArbol.get(i).getNombre());
        }
    }

    /**
     * Imprime las recomendaciones de amigos a partir de las distancias mínimas desde un usuario raíz (resultado de Dijkstra).
     * Para cada otro usuario muestra la distancia y una etiqueta: "[Sugerencia Alta]" si distancia <= 5, "[Sugerencia Baja]" si no.
     *
     * @param usuarioRaiz usuario desde el cual se calcularon las distancias (origen en Dijkstra).
     * @param distancias mapa usuario -> distancia mínima desde usuarioRaiz (salida de Algoritmos.dijkstra).
     */
    public static void recomendacionAmigos(User usuarioRaiz, Map<User, Integer> distancias) {
        if (usuarioRaiz == null || distancias == null || distancias.isEmpty()) {
            System.out.println("No hay datos para recomendar amigos.");
            return;
        }

        System.out.println("Caminos más cortos desde " + usuarioRaiz.getNombre() + ":");
        distancias.forEach((usuario, distancia) -> {
            // No se imprime la distancia del raíz a sí mismo.
            if (!usuario.equals(usuarioRaiz)) {
                String sugerencia = (distancia <= 5) ? "[Sugerencia Alta]" : "[Sugerencia Baja]";
                System.out.println("-> " + usuario.getNombre() + " | Distancia: " + distancia + " " + sugerencia);
            }
        });
    }
}
