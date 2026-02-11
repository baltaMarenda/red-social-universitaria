package controller;

import model.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Grafo grafo = new Grafo();

        User u1 = new User("Baltazar","Marenda","123", "balta@mail.com");
        User u2 = new User("Manuela", "Poustis", "456", "manu@mail.com");
        User u3 = new User("Matias", "Calles", "789", "mati@mail.com");
        User u4 = new User("Sofia", "Lopez", "111", "sofia@mail.com");


        grafo.agregarAmigo(u1, u2, 4);
        grafo.agregarAmigo(u1, u3, 2);
        grafo.agregarAmigo(u2, u3, 5);
        grafo.agregarAmigo(u2, u4, 10);
        grafo.agregarAmigo(u3, u4, 3);

        System.out.println("=== 1. CONECTIVIDAD MÍNIMA (Algoritmo de Prim) ===");
        try {
            List<Arista> mst = Algoritmos.prim(grafo);

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

        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n=== 2. RECOMENDACIÓN DE AMIGOS (Algoritmo de Dijkstra) ===");
        // Calculamos distancias desde u1
        User usuarioRaiz = u1;
        Map<User, Integer> distancias = Algoritmos.dijkstra(grafo, usuarioRaiz);

        System.out.println("Caminos más cortos desde " + usuarioRaiz.getNombre() + ":");
        distancias.forEach((usuario, distancia) -> {
            if (!usuario.equals(usuarioRaiz)) {
                String sugerencia = (distancia <= 5) ? "[Sugerencia Alta]" : "[Sugerencia Baja]";
                System.out.println("-> " + usuario.getNombre() + " | Distancia: " + distancia + " " + sugerencia);
            }
        });

    }
}
