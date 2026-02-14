package controller;

import model.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Grafo grafo = new Grafo();

        User u1 = new User("Baltazar","Marenda","123", "balta@mail.com");
        User u2 = new User("Manuela", "Poustis", "456", "manu@mail.com");
        User u3 = new User("Matias", "Calles", "789", "mati@mail.com");
        User u4 = new User("Sofia", "Lopez", "111", "sofia@mail.com");
        User u5 = new User("Raul", "SinAmigos", "222", "Raul@mail.com");
        User u6 = new User("Jazmin", "PocosAmigos", "333", "Jazmin@mail.com");
        User pedro = new User("Pedro", "Garcia", "444", "pedro@mail.com");
        User roberto = new User("Roberto", "Martinez", "555", "roberto@mail.com");

        grafo.agregarAmigo(u1, u2, 1000);
        grafo.agregarAmigo(u2, u1, 2);
        grafo.agregarAmigo(u1, u3, 1000);
        grafo.agregarAmigo(u2, u3, 5);
        grafo.agregarAmigo(u2, u4, 10);
        grafo.agregarAmigo(u3, u4, 3);
        grafo.agregarAmigo(u5, u6, 1);
        //grafo.agregarAmigo(u4, u5, 2);
        grafo.agregarAmigo(pedro, roberto, 1);

        System.out.println("=== 1. CONECTIVIDAD MINIMA (Algoritmo de Prim) ===");
        Algoritmos.ResultadoPrimCompleto resPrim = Algoritmos.prim(grafo);
        Impresion.conectividadMinima(resPrim.getBosque());

        System.out.println("\n=== 2. RECOMENDACION DE AMIGOS (Algoritmo de Dijkstra) ===");
        User usuarioRaiz = u1;
        Map<User, Integer> distancias = Algoritmos.dijkstra(grafo, usuarioRaiz);

        Impresion.recomendacionAmigos(usuarioRaiz, distancias);




        boolean conexo = Algoritmos.esConexo(grafo);
        System.out.println("\n=== 3. CONEXION DEL GRAFO ===");
        System.out.println("El grafo es conexo: " + conexo);
        Impresion.imprimirSugerencias(resPrim.getNodosIniciales());
    }
}
