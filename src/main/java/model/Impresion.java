package model;

import java.util.List;
import java.util.Map;

public class Impresion {

    public static void conectividadMinima(List<Arista> mst) {
        if (mst == null || mst.isEmpty()) {
            System.out.println("No hay aristas en el árbol de expansión mínima.");
            return;
        }

        int costoTotal = 0;

        for (Arista a : mst) {
            System.out.println(
                    a.getOrigen().getNombre() + " - " +
                            a.getDestino().getNombre() +
                            " | Peso: " + a.getPeso()
            );
            costoTotal += a.getPeso();
        }

        System.out.println("Costo total mínimo: " + costoTotal);
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
