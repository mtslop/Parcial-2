package co.uniquindio.edu.poo.viewcontroller;

import co.uniquindio.edu.poo.Controller.ControllerSistema;
import co.uniquindio.edu.poo.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SolicitudesViewController {

    @FXML private Label lblCodigoSolicitud;
    @FXML private ComboBox<Cliente> cmbCliente;
    @FXML private ComboBox<ElementoTecnologico> cmbElemento;
    @FXML private ListView<ElementoTecnologico> lstElementos;
    @FXML private DatePicker dpFechaProgramada;
    @FXML private ComboBox<String> cmbPrioridad;
    @FXML private TextArea txtObservaciones;
    @FXML private Label lblCostoEstimado;
    @FXML private Label lblMensaje;

    @FXML private TableView<SolicitudServicio> tblSolicitudes;
    @FXML private TableColumn<SolicitudServicio, String> colSolCodigo;
    @FXML private TableColumn<SolicitudServicio, String> colSolCliente;
    @FXML private TableColumn<SolicitudServicio, String> colSolFecha;
    @FXML private TableColumn<SolicitudServicio, String> colSolEstado;
    @FXML private TableColumn<SolicitudServicio, Double> colSolTotal;
    @FXML private TextArea txtDetalle;

    private ControllerSistema controllerSistema;

    private ObservableList<ElementoTecnologico> elementosSeleccionados = FXCollections.observableArrayList();
    private ObservableList<SolicitudServicio> listaSolicitudes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        controllerSistema = ControllerSistema.obtenerInstancia();

        lstElementos.setItems(elementosSeleccionados);
        tblSolicitudes.setItems(listaSolicitudes);

        colSolCodigo.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCodigo()));
        colSolCliente.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCliente().getNombre()));
        colSolFecha.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFechaProgramada() != null ? cellData.getValue().getFechaProgramada().toString() : ""));
        colSolEstado.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().isConfirmada() ? "Confirmada" : "Pendiente"));
        colSolTotal.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().calcularCostoTotal()));

        cargarDatosIniciales();

        tblSolicitudes.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                mostrarDetalleSolicitud(newVal);
            }
        });

        cmbPrioridad.getItems().addAll("Baja", "Media", "Alta", "Urgente");

        lblCodigoSolicitud.setText(controllerSistema.generarCodigoSolicitud());
    }

    private void cargarDatosIniciales() {
        cmbCliente.setItems(FXCollections.observableArrayList(controllerSistema.obtenerClientes()));

        ObservableList<ElementoTecnologico> elementosCatalogo = FXCollections.observableArrayList();
        elementosCatalogo.addAll(controllerSistema.obtenerCatalogo());
        elementosCatalogo.addAll(controllerSistema.obtenerPaquetes());
        cmbElemento.setItems(elementosCatalogo);

        listaSolicitudes.addAll(controllerSistema.obtenerSolicitudes());
    }

    @FXML
    private void onAgregarElemento() {
        ElementoTecnologico seleccionado = cmbElemento.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            if (!elementosSeleccionados.contains(seleccionado)) {
                elementosSeleccionados.add(seleccionado);
                actualizarCostoEstimado();
                lblMensaje.setText("Elemento agregado correctamente.");
            } else {
                lblMensaje.setText("El elemento ya fue agregado a la lista.");
            }
        } else {
            lblMensaje.setText("Por favor seleccione un servicio o paquete.");
        }
    }

    @FXML
    private void onQuitarElemento() {
        ElementoTecnologico seleccionado = lstElementos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            elementosSeleccionados.remove(seleccionado);
            actualizarCostoEstimado();
            lblMensaje.setText("Elemento removido.");
        } else {
            lblMensaje.setText("Seleccione un elemento de la lista para quitar.");
        }
    }

    @FXML
    private void onRegistrarSolicitud() {
        try {
            Cliente cliente = cmbCliente.getSelectionModel().getSelectedItem();
            if (cliente == null) {
                lblMensaje.setText("Error: Debe seleccionar un cliente.");
                return;
            }

            if (elementosSeleccionados.isEmpty()) {
                lblMensaje.setText("Error: Debe agregar al menos un servicio o paquete.");
                return;
            }

            String codigo = lblCodigoSolicitud.getText();
            LocalDate fechaProgramada = dpFechaProgramada.getValue();
            String prioridad = cmbPrioridad.getSelectionModel().getSelectedItem();
            String observaciones = txtObservaciones.getText();

            List<ElementoTecnologico> listaElementos = new ArrayList<>(elementosSeleccionados);

            SolicitudServicio nuevaSolicitud = new SolicitudServicio(codigo, cliente, listaElementos);
            nuevaSolicitud.setFechaProgramada(fechaProgramada);

            controllerSistema.registrarSolicitud(nuevaSolicitud);

            listaSolicitudes.setAll(controllerSistema.obtenerSolicitudes());
            lblMensaje.setText("¡Solicitud registrada con éxito!");
            limpiarFormulario();

        } catch (IllegalStateException e) {
            lblMensaje.setText("No se pudo registrar: " + e.getMessage());
        } catch (Exception e) {
            lblMensaje.setText("Ocurrió un error al registrar la solicitud: " + e.getMessage());
        }
    }

    private void actualizarCostoEstimado() {
        double total = 0.0;
        for (ElementoTecnologico el : elementosSeleccionados) {
            total += el.calcularCosto();
        }
        lblCostoEstimado.setText("$" + total);
    }

    private void mostrarDetalleSolicitud(SolicitudServicio solicitud) {
        StringBuilder sb = new StringBuilder();
        sb.append("Código: ").append(solicitud.getCodigo()).append("\n");
        sb.append("Cliente: ").append(solicitud.getCliente().getNombre()).append("\n");
        sb.append("Estado Confirmada: ").append(solicitud.isConfirmada()).append("\n");
        sb.append("Especialidad Requerida: ").append(solicitud.getEspecialidadRequerida()).append("\n");
        txtDetalle.setText(sb.toString());
    }

    private void limpiarFormulario() {
        lblCodigoSolicitud.setText(controllerSistema.generarCodigoSolicitud());
        cmbCliente.getSelectionModel().clearSelection();
        cmbElemento.getSelectionModel().clearSelection();
        elementosSeleccionados.clear();
        dpFechaProgramada.setValue(null);
        cmbPrioridad.getSelectionModel().clearSelection();
        txtObservaciones.clear();
        lblCostoEstimado.setText("$0");
    }
}