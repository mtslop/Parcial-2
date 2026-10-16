package co.uniquindio.edu.poo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import co.uniquindio.edu.poo.model.ElementoTecnologico;


public class PaqueteTecnologico implements ElementoTecnologico {

    private String nombre;
    private String codigo;
    private String descripcion;
    private List<ElementoTecnologico> listaElementos;

    /**
     * Crea un paquete vacio.
     *
     * @param codigo codigo del paquete
     * @param nombre nombre del paquete
     * @param descripcion descripcion del paquete
     * @throws IllegalArgumentException si el codigo o el nombre son invalidos
     */
    public PaqueteTecnologico(String codigo, String nombre, String descripcion) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El codigo del paquete es obligatorio");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del paquete es obligatorio");
        }
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.listaElementos = new ArrayList<>();
    }

    /**
     * Agrega un elemento verificando que no se genere un ciclo.
     *
     * @param elemento servicio, servicio personalizado u otro paquete
     * @throws IllegalArgumentException si es nulo o genera un ciclo
     */
    public void agregarElemento(ElementoTecnologico elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException("El elemento no puede ser nulo");
        }
        if (validarCiclo(elemento)) {
            throw new IllegalArgumentException("Un paquete no puede incluirse a si mismo, directa o indirectamente");
        }
        listaElementos.add(elemento);
    }

    /**
     * @param elemento elemento que se quiere agregar
     * @return true si agregarlo generaria un ciclo
     */
    public boolean validarCiclo(ElementoTecnologico elemento) {
        return elemento.contieneElemento(this);
    }

    public List<ElementoTecnologico> getListaElementos() {
        return Collections.unmodifiableList(listaElementos);
    }

    @Override
    public double calcularCosto() {
        double total = 0;
        for (ElementoTecnologico elemento : listaElementos) {
            total += elemento.calcularCosto();
        }
        return total;
    }

    @Override
    public double getCosto() {
        double total = 0;
        for (ElementoTecnologico elemento : listaElementos) {
            total += elemento.getCosto();
        }
        return total;
    }

    @Override
    public boolean requiereTecnico() {
        for (ElementoTecnologico elemento : listaElementos) {
            if (elemento.requiereTecnico()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getEspecialidad() {
        for (ElementoTecnologico elemento : listaElementos) {
            if (elemento.requiereTecnico()) {
                return elemento.getEspecialidad();
            }
        }
        return "GENERAL";
    }

    @Override
    public boolean contieneElemento(ElementoTecnologico objetivo) {
        if (this == objetivo) {
            return true;
        }
        for (ElementoTecnologico elemento : listaElementos) {
            if (elemento.contieneElemento(objetivo)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public String getCodigo() {
        return codigo;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return codigo + " - " + nombre + " (" + listaElementos.size() + " elementos)";
    }
}