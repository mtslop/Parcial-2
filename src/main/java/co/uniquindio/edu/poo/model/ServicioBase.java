package co.uniquindio.edu.poo.model;

public abstract class ServicioBase implements ElementoTecnologico {

    private String nombre;
    private String codigo;
    private String descripcion;
    private double costoBase;

    /**
     * Crea un servicio.
     *
     * @param codigo codigo del servicio
     * @param nombre nombre del servicio
     * @param descripcion descripcion del servicio
     * @param costoBase costo base del servicio
     * @throws IllegalArgumentException si el codigo, el nombre o el costo son invalidos
     */
    protected ServicioBase(String codigo, String nombre, String descripcion, double costoBase) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El codigo del servicio es obligatorio");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del servicio es obligatorio");
        }
        if (costoBase < 0) {
            throw new IllegalArgumentException("El costo base no puede ser negativo");
        }
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costoBase = costoBase;
    }

    @Override
    public String getCodigo() {
        return codigo;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public double getCosto() {
        return costoBase;
    }

    @Override
    public double calcularCosto() {
        return costoBase;
    }

    /**
     * @return false por defecto; los servicios presenciales lo sobrescriben
     */
    @Override
    public boolean requiereTecnico() {
        return false;
    }

    @Override
    public String toString() {
        return codigo + " - " + nombre + " ($" + costoBase + ")";
    }
}