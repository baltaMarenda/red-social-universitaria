package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
    private String nombre;
    private String apellido;
    private String telefono;
    private String mail;
    private int id;
    private List<Publicacion> publicaciones;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    private static int contadorId = 1;

    public User(String nombre, String apellido, String telefono, String mail) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.mail = mail;
        this.id = contadorId++;
        this.publicaciones = new ArrayList<>();
    }

    public void agregarPublicacion(Publicacion p){
        publicaciones.add(p);
    }

    public String getNombre() {
        return nombre;
    }
}
