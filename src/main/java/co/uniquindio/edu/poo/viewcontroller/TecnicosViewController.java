package co.uniquindio.edu.poo.viewcontroller;

import co.uniquindio.edu.poo.Controller.ControllerSistema;
import co.uniquindio.edu.poo.model.Tecnico;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class TecnicosViewController {

    @FXML private ComboBox<String> cmbEspecialidad;
    @FXML private Label lblTecnicoId;
    @FXML private Label lblTecnicoNombre;
    @FXML private Label lblTecnicoEspecialidad;
    @FXML private Label lblTecnicoDisponibilidad;
    @FXML private Label lblTecnicoDesplazamiento;

    @FXML private ComboBox<String> cmbSolicitud;
    @FXML private Label lblEstado;
    @FXML private TextArea txtDesglose;
    @FXML private Label lblSubtotal;
    @FXML private Label lblDesplazamiento;
    @FXML private Label lblTotal;

    @FXML private TextField txtNuevoId;
    @FXML private TextField txtNuevoNombre;
    @FXML private ComboBox<String> cmbNuevoEspecialidad;
    @FXML private TextField txtNuevoTelefono;
    @FXML private TextField txtNuevoCostoDesplazamiento;
    @FXML private CheckBox chkNuevoDisponible;

    private ObservableList<Tecnico> listaTecnicos;
    private ControllerSistema controllerSistema;

    @FXML
    public void initialize() {
        controllerSistema = ControllerSistema.obtenerInstancia();
        listaTecnicos = FXCollections.observableArrayList(controllerSistema.obtenerTecnicos());

        ObservableList<String> especialidades = FXCollections.observableArrayList(
                "Mantenimiento Preventivo",
                "Mantenimiento Correctivo",
                "Instalación de Redes",
                "Recuperación de Información"
        );

        cmbEspecialidad.setItems(especialidades);
        if (cmbNuevoEspecialidad != null) {
            cmbNuevoEspecialidad.setItems(especialidades);
        }

        cmbSolicitud.setItems(FXCollections.observableArrayList(
                "SRV-001 - Mantenimiento Oficina Principal",
                "SRV-002 - Redes Laboratorio Sistemas"
        ));
    }

    @FXML
    public void onConsultarTecnico() {
        String especialidadSeleccionada = cmbEspecialidad.getValue();
        if (especialidadSeleccionada == null || especialidadSeleccionada.isBlank()) {
            lblEstado.setText("Seleccione una especialidad para consultar.");
            return;
        }

        Tecnico tecnicoEncontrado = listaTecnicos.stream()
                .filter(t -> t.getEspecializacion().equalsIgnoreCase(especialidadSeleccionada) && t.isDisponible())
                .findFirst()
                .orElse(null);

        if (tecnicoEncontrado != null) {
            lblTecnicoId.setText(tecnicoEncontrado.getId());
            lblTecnicoNombre.setText(tecnicoEncontrado.getNombre());
            lblTecnicoEspecialidad.setText(tecnicoEncontrado.getEspecializacion());
            lblTecnicoDisponibilidad.setText("Disponible");
            lblTecnicoDesplazamiento.setText("$" + tecnicoEncontrado.getCostoDesplazamiento());
            lblEstado.setText("Técnico encontrado con éxito.");
        } else {
            lblTecnicoId.setText("N/A");
            lblTecnicoNombre.setText("Sin técnico disponible");
            lblTecnicoEspecialidad.setText(especialidadSeleccionada);
            lblTecnicoDisponibilidad.setText("No disponible");
            lblTecnicoDesplazamiento.setText("$0");
            lblEstado.setText("No hay técnicos disponibles para esta especialidad.");
        }
    }

    @FXML
    public void onRegistrarTecnico() {
        try {
            String id = txtNuevoId.getText();
            String nombre = txtNuevoNombre.getText();
            String especialidad = cmbNuevoEspecialidad != null ? cmbNuevoEspecialidad.getValue() : null;
            String telefono = txtNuevoTelefono.getText();
            String costoStr = txtNuevoCostoDesplazamiento.getText();
            boolean disponible = chkNuevoDisponible != null && chkNuevoDisponible.isSelected();

            if (id == null || id.isBlank() || nombre == null || nombre.isBlank() || especialidad == null || telefono == null || telefono.isBlank() || costoStr == null || costoStr.isBlank()) {
                lblEstado.setText("Por favor complete todos los campos del formulario de registro.");
                return;
            }

            double costoDesplazamiento = Double.parseDouble(costoStr);

            Tecnico nuevoTecnico = new Tecnico(id, nombre, especialidad, telefono, disponible, costoDesplazamiento);
            controllerSistema.agregarTecnico(nuevoTecnico);
            listaTecnicos.setAll(controllerSistema.obtenerTecnicos());

            lblEstado.setText("¡Técnico " + nombre + " registrado con éxito!");

            txtNuevoId.clear();
            txtNuevoNombre.clear();
            if (cmbNuevoEspecialidad != null) cmbNuevoEspecialidad.getSelectionModel().clearSelection();
            txtNuevoTelefono.clear();
            txtNuevoCostoDesplazamiento.clear();
            if (chkNuevoDisponible != null) chkNuevoDisponible.setSelected(true);

        } catch (NumberFormatException e) {
            lblEstado.setText("Error: El costo de desplazamiento debe ser un valor numérico.");
        } catch (Exception e) {
            lblEstado.setText("Error al registrar el técnico: " + e.getMessage());
        }
    }

    @FXML
    public void onCalcularCosto() {
        String solicitudSeleccionada = cmbSolicitud.getValue();
        if (solicitudSeleccionada == null || solicitudSeleccionada.isBlank()) {
            lblEstado.setText("Seleccione una solicitud para calcular.");
            return;
        }
        double subtotalServicios = 120000;
        double desplazamiento = 25000;
        double total = subtotalServicios + desplazamiento;

        lblSubtotal.setText("$" + subtotalServicios);
        lblDesplazamiento.setText("$" + desplazamiento);
        lblTotal.setText("$" + total);

        txtDesglose.setText("Desglose para: " + solicitudSeleccionada + "\n" +
                "- Servicios incluidos: Base de mantenimiento y soporte.\n" +
                "- Costo de desplazamiento técnico aplicado.\n" +
                "- Estado de la cotización: Calculada correctamente.");

        lblEstado.setText("¡Cálculo realizado con éxito!");
    }
}