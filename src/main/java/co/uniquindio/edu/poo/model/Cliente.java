package co.uniquindio.edu.poo.model;

public class Cliente {

    private String documento;
    private String nombre;
    private String correo;
    private String telefono;

    /**
     * @param documento documento de identificacion
     * @param nombre nombre completo
     * @param correo correo electronico
     * @param telefono telefono de contacto
     */
    public Cliente(String documento, String nombre, String correo, String telefono) {
        this.documento = documento;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
    }

    /**
     * @return true si documento, nombre y correo son validos
     */
    public boolean validarDatos() {
        return documento != null && !documento.isBlank()
                && nombre != null && !nombre.isBlank()
                && correo != null && correo.contains("@");
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return documento + " - " + nombre;
    }
}