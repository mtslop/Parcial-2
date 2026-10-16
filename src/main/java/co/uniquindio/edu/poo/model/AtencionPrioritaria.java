package co.uniquindio.edu.poo.model;


import co.uniquindio.edu.poo.model.ElementoTecnologico;

public class AtencionPrioritaria extends ServicioDecorator {

    public static final double INCREMENTO = 35000;

    /**
     * @param elemento elemento a decorar
     */
    public AtencionPrioritaria(ElementoTecnologico elemento) {
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
        return "Atencion prioritaria";
    }
}