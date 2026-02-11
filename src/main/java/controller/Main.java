package controller;

import model.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Grafo grafo = new Grafo();

        User u1 = new User("Baltazar","Marenda","123", "balta@mail.com");
        User u2 = new User("Ana", "Gomez", "456", "ana@mail.com");
        User u3 = new User("Luis", "Martinez", "789", "luis@mail.com");
        User u4 = new User("Sofia", "Lopez", "111", "sofia@mail.com");


        grafo.agregarAmigo(u1, u2, 4);
        grafo.agregarAmigo(u1, u3, 2);
        grafo.agregarAmigo(u2, u3, 5);
        grafo.agregarAmigo(u2, u4, 10);
        grafo.agregarAmigo(u3, u4, 3);


        try {
            List<Arista> mst = Algoritmos.prim(grafo);

            System.out.println("Árbol de Recubrimiento Mínimo:");
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

    }
}
