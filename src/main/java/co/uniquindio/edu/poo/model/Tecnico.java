package co.uniquindio.edu.poo.model;

public class Tecnico {

    private String id;
    private String nombre;
    private String especializacion;
    private String telefono;
    private boolean disponible;
    private double costoDesplazamiento;

    /**
     * Crea un tecnico disponible sin costo de desplazamiento.
     *
     * @param id identificador del tecnico
     * @param nombre nombre del tecnico
     * @param especializacion especialidad del tecnico
     * @param telefono telefono de contacto
     */
    public Tecnico(String id, String nombre, String especializacion, String telefono) {
        this(id, nombre, especializacion, telefono, true, 0);
    }

    /**
     * Crea un tecnico con su disponibilidad y costo de desplazamiento.
     *
     * @param id identificador del tecnico
     * @param nombre nombre del tecnico
     * @param especializacion especialidad del tecnico
     * @param telefono telefono de contacto
     * @param disponible true si esta disponible
     * @param costoDesplazamiento costo del desplazamiento
     */
    public Tecnico(String id, String nombre, String especializacion, String telefono,
                   boolean disponible, double costoDesplazamiento) {
        this.id = id;
        this.nombre = nombre;
        this.especializacion = especializacion;
        this.telefono = telefono;
        this.disponible = disponible;
        this.costoDesplazamiento = costoDesplazamiento;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecializacion() {
        return especializacion;
    }

    public void setEspecializacion(String especializacion) {
        this.especializacion = especializacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public double getCostoDesplazamiento() {
        return costoDesplazamiento;
    }

    public void setCostoDesplazamiento(double costoDesplazamiento) {
        this.costoDesplazamiento = costoDesplazamiento;
    }

    /**
     * Dos tecnicos son iguales si tienen el mismo identificador.
     *
     * @param obj objeto a comparar
     * @return true si el identificador coincide
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Tecnico)) {
            return false;
        }
        Tecnico otro = (Tecnico) obj;
        return id != null && id.equals(otro.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    @Override
    public String toString() {
        if (!disponible) {
            return "Sin tecnico disponible";
        }
        return id + " - " + nombre + " (" + especializacion + ") - Desplazamiento: $" + costoDesplazamiento;
    }
}