package model;

import java.util.List;
import java.util.Map;

public class Impresion {

    /**
     * Imprime uno o varios árboles de expansión mínima (MST), uno por componente conexa.
     * Formato: "Arbol 1 ---", aristas, "Arbol 2 ---", ...
     */
    public static void conectividadMinima(List<List<Arista>> bosque) {
        if (bosque == null || bosque.isEmpty()) {
            System.out.println("No hay árboles de expansión mínima.");
            return;
        }

        int numArbol = 1;
        for (List<Arista> mst : bosque) {
            System.out.println("Arbol " + numArbol + " ---");
            if (mst == null || mst.isEmpty()) {
                System.out.println("  (sin aristas)");
            } else {
                int costoTotal = 0;
                int paso = 1;
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
     * Imprime la sugerencia de conexión: para que todas las personas estén conectadas,
     * se sugiere que los representantes de cada componente (nodo inicial de cada árbol) se hagan amigos.
     * Si ya hay una sola componente o ninguna, lo indica.
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

    public static void recomendacionAmigos(User usuarioRaiz, Map<User, Integer> distancias) {
        if (usuarioRaiz == null || distancias == null || distancias.isEmpty()) {
            System.out.println("No hay datos para recomendar amigos.");
            return;
        }

        System.out.println("Caminos más cortos desde " + usuarioRaiz.getNombre() + ":");
        distancias.forEach((usuario, distancia) -> {
            if (!usuario.equals(usuarioRaiz)) {
                String sugerencia = (distancia <= 5) ? "[Sugerencia Alta]" : "[Sugerencia Baja]";
                System.out.println("-> " + usuario.getNombre() + " | Distancia: " + distancia + " " + sugerencia);
            }
        });
    }
}
