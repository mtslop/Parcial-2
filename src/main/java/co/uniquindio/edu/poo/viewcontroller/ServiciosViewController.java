package co.uniquindio.edu.poo.viewcontroller;

import co.uniquindio.edu.poo.Controller.ControllerSistema;
import co.uniquindio.edu.poo.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class ServiciosViewController {

    @FXML private TableView<ServicioBase> tblServicios;
    @FXML private TableColumn<ServicioBase, String> colCodigo;
    @FXML private TableColumn<ServicioBase, String> colNombre;
    @FXML private TableColumn<ServicioBase, String> colDescripcion;
    @FXML private TableColumn<ServicioBase, Double> colCosto;

    @FXML private ComboBox<String> cmbTipo;
    @FXML private TextField txtCodigo;
    @FXML private TextField txtDescripcion;
    @FXML private Label lblMensajeCreacion;

    @FXML private Label lblServicioSeleccionado;
    @FXML private CheckBox chkPrioritaria;
    @FXML private CheckBox chkSoporte;
    @FXML private CheckBox chkDomicilio;

    @FXML private Label lblCostoBase;
    @FXML private Label lblIncrementos;
    @FXML private Label lblCostoFinal;
    @FXML private Label lblMensajePersonalizado;

    @FXML private ListView<String> lstPersonalizados;

    private ObservableList<ServicioBase> listaServicios;
    private ObservableList<String> listaPersonalizados;
    private ServicioBase servicioSeleccionado;
    private ControllerSistema controllerSistema;

    @FXML
    public void initialize() {
        controllerSistema = ControllerSistema.obtenerInstancia();

        listaServicios = FXCollections.observableArrayList(controllerSistema.obtenerCatalogo());
        listaPersonalizados = FXCollections.observableArrayList();

        colCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colDescripcion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion()));
        colCosto.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getCosto()).asObject());

        tblServicios.setItems(listaServicios);
        lstPersonalizados.setItems(listaPersonalizados);

        cmbTipo.setItems(FXCollections.observableArrayList(
                "Mantenimiento preventivo",
                "Mantenimiento correctivo",
                "Instalacion de redes",
                "Recuperacion de informacion"
        ));

        tblServicios.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                servicioSeleccionado = newVal;
                actualizarPanelPersonalizacion();
            }
        });
    }

    @FXML
    private void onCrearServicio() {
        try {
            String tipo = cmbTipo.getValue();
            String codigo = txtCodigo.getText();
            String descripcion = txtDescripcion.getText();

            if (tipo == null || tipo.isBlank()) {
                lblMensajeCreacion.setText("Por favor, seleccione un tipo de servicio.");
                return;
            }

            ServicioBase nuevoServicio = switch (tipo) {
                case "Mantenimiento preventivo" -> new MantenimientoPreventivo(codigo, descripcion);
                case "Mantenimiento correctivo" -> new MantenimientoCorrectivo(codigo, descripcion);
                case "Instalacion de redes" -> new InstalacionRed(codigo, descripcion);
                case "Recuperacion de informacion" -> new RecuperacionInformacion(codigo, descripcion);
                default -> throw new IllegalArgumentException("Tipo de servicio no válido");
            };

            controllerSistema.agregarServicio(nuevoServicio);
            listaServicios.setAll(controllerSistema.obtenerCatalogo());

            lblMensajeCreacion.setText("¡Servicio creado con éxito!");
            limpiarFormularioCreacion();

        } catch (Exception e) {
            lblMensajeCreacion.setText("Error: " + e.getMessage());
        }
    }

    private void limpiarFormularioCreacion() {
        cmbTipo.setValue(null);
        txtCodigo.clear();
        txtDescripcion.clear();
    }

    @FXML
    private void onComplementoCambiado() {
        actualizarCalculosCostos();
    }

    private void actualizarPanelPersonalizacion() {
        if (servicioSeleccionado != null) {
            lblServicioSeleccionado.setText(servicioSeleccionado.getNombre() + " (" + servicioSeleccionado.getCodigo() + ")");
            actualizarCalculosCostos();
        }
    }

    private void actualizarCalculosCostos() {
        if (servicioSeleccionado == null) {
            lblCostoBase.setText("$0");
            lblIncrementos.setText("$0");
            lblCostoFinal.setText("$0");
            return;
        }

        double costoBase = servicioSeleccionado.getCosto();
        double incrementos = 0;

        if (chkPrioritaria.isSelected()) incrementos += 35000;
        if (chkSoporte.isSelected()) incrementos += 50000;
        if (chkDomicilio.isSelected()) incrementos += 25000;

        double costoFinal = costoBase + incrementos;

        lblCostoBase.setText("$" + costoBase);
        lblIncrementos.setText("$" + incrementos);
        lblCostoFinal.setText("$" + costoFinal);
    }

    @FXML
    private void onGuardarPersonalizado() {
        if (servicioSeleccionado == null) {
            lblMensajePersonalizado.setText("Seleccione un servicio del catálogo primero.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(servicioSeleccionado.getNombre()).append(" - Extras: [");
        double extra = 0;
        boolean tieneExtras = false;

        if (chkPrioritaria.isSelected()) { sb.append("Prioritaria "); extra += 35000; tieneExtras = true; }
        if (chkSoporte.isSelected()) { sb.append("Soporte30d "); extra += 50000; tieneExtras = true; }
        if (chkDomicilio.isSelected()) { sb.append("Domicilio "); extra += 25000; tieneExtras = true; }

        if (!tieneExtras) sb.append("Ninguno");

        double total = servicioSeleccionado.getCosto() + extra;
        sb.append("] Total: $").append(total);

        listaPersonalizados.add(sb.toString());
        lblMensajePersonalizado.setText("¡Personalización guardada con éxito!");
    }
}