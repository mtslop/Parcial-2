package co.uniquindio.edu.poo.model;


import co.uniquindio.edu.poo.model.ElementoTecnologico;

public class SoporteTreintaDias extends ServicioDecorator {

    public static final double INCREMENTO = 50000;

    /**
     * @param elemento elemento a decorar
     */
    public SoporteTreintaDias(ElementoTecnologico elemento) {
        super(elemento);
    }

    @Override
    public double calcularCosto() {
        return super.calcularCosto() + INCREMENTO;
    }

    @Override
    public boolean requiereTecnico() {
        return super.requiereTecnico();
    }

    @Override
    public double getIncremento() {
        return INCREMENTO;
    }

    @Override
    public String getNombreComplemento() {
        return "Soporte tecnico 30 dias";
    }
}