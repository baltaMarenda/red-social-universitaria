package model;

import java.util.*;
public class Grafo {
    private Map<User, List<Arista>> listaAdyacencia;

    public Grafo(){
        listaAdyacencia = new HashMap<>();
    }

    //Agregar usuario, que seria el nodo del grafo
    public void agregarUsuario(User u){
        listaAdyacencia.putIfAbsent(u, new ArrayList<>());
    }

    //Agregar amigo, que seria la arista
    public void agregarAmigo(User u1, User u2, int peso){
        agregarUsuario(u1);
        agregarUsuario(u2);

        listaAdyacencia.get(u1).add(new Arista(u1,u2,peso));
        listaAdyacencia.get(u2).add(new Arista(u2,u1,peso));
    }

    public Map<User, List<Arista>> getListaAdyacencia() {
        return listaAdyacencia;
    }

    public Set<User> getUsuarios(){
        return listaAdyacencia.keySet();
    }

    public List<Arista> getAdyacentes(User u){
        return listaAdyacencia.get(u);
    }
}
