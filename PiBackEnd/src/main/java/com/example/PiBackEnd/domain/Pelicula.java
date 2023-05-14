package com.example.PiBackEnd.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "peliculas")
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String titulo;

    @Column
    private String imagen;

    @Column
    private String descripcion;

    @ManyToMany(fetch = FetchType.EAGER,cascade = { CascadeType.PERSIST })
    @JoinTable(
            name = "pelicula_categoria",
            joinColumns = { @JoinColumn(name = "pelicula_id") },
            inverseJoinColumns = { @JoinColumn(name = "categoria_id") }
    )
    private Set<Categoria> categorias = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER,cascade = { CascadeType.PERSIST })
    @JoinTable(
            name = "pelicula_fecha",
            joinColumns = { @JoinColumn(name = "pelicula_id") },
            inverseJoinColumns = { @JoinColumn(name = "fecha_id") }
    )
    private Set<Fecha> fechas = new HashSet<>();

    public Boolean chequearAtributosVacios(){
        if(null == this.titulo || null == this.imagen || null == this.descripcion || this.categorias.isEmpty() || this.fechas.isEmpty()){
            return true;
        }
        return false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void agregarCategoria(Categoria categoria){
        categorias.add(categoria);
    }

    public Set<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(Set<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Set<Fecha> getFechas() {
        return fechas;
    }

    public void setFechas(Set<Fecha> fechas) {
        this.fechas = fechas;
    }
}
