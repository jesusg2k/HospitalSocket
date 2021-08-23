package archivos.interfaz;

import archivos.entidad.*;
import archivos.interfaz.TableCell.HabilitadoTableCell;
import archivos.interfaz.TableCell.OcupadoTableCell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.StageStyle;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class ControllerCliente {

    static int puertoServidor = 9876;
    static String direccionServidor = "127.0.0.1";

    @FXML
    private TableView<Hospital> table_view_hospitales;

    @FXML
    private TableColumn<Hospital, Long> column_hospital_id;

    @FXML
    private TableColumn<Hospital, String> column_hospital_nombre;

    @FXML
    private TableColumn<Hospital, Integer> column_hospital_cantidad_camas;

    @FXML
    private TableView<Cama> table_view_camas;

    @FXML
    private TableColumn<Cama, Long> column_cama_cama_id;

    @FXML
    private TableColumn<Cama, Long> column_cama_hospital_id;

    @FXML
    private TableColumn<Cama, Boolean> column_cama_ocupado;

    @FXML
    private TableColumn<Cama, Boolean> column_cama_habilitado;

    @FXML
    private Button btn_estado_hospitales;

    @FXML
    private Button btn_crear_cama;

    @FXML
    private Button btn_eliminar_cama;

    @FXML
    private Button btn_ocupar_cama;

    @FXML
    private Button btn_desocupar_cama;

    @FXML
    private TextField txt_id_hospital;

    @FXML
    public void initialize(){
        configurar_tablas_hospitales();
        configurar_tablas_camas();
    }

    private void configurar_tablas_camas() {
        column_cama_cama_id.setCellValueFactory(new PropertyValueFactory<>("cama_id"));
        column_cama_hospital_id.setCellValueFactory(new PropertyValueFactory<>("hospital_id"));
        column_cama_ocupado.setCellValueFactory(new PropertyValueFactory<>("ocupado"));
        column_cama_ocupado.setCellFactory((TableColumn<Cama, Boolean> param) -> new OcupadoTableCell<>());
        column_cama_habilitado.setCellValueFactory(new PropertyValueFactory<>("habilitado"));
        column_cama_habilitado.setCellFactory((TableColumn<Cama, Boolean> param) -> new HabilitadoTableCell<>());
        table_view_camas.setRowFactory(tv -> new TableRow<Cama>() {
            @Override
            protected void updateItem(Cama item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || item == null){
                    setStyle("-fx-background-color: white;");
                }else{
                    if(item == table_view_camas.getSelectionModel().getSelectedItem()){
                        setStyle("");
                        return;
                    }
                    //if mayor a 15
                    colorear_stock(this, item);
                }
            }
        });
    }

    private void colorear_stock(TableRow<Cama> row, Cama item) {
        if(!item.isOcupado()){
            row.setStyle("-fx-background-color: PALEGREEN;");
            return;
        }else{
            row.setStyle("-fx-background-color: PINK;");
            return;
        }
    }

    private void configurar_tablas_hospitales() {
        column_hospital_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        column_hospital_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        column_hospital_cantidad_camas.setCellValueFactory(new PropertyValueFactory<>("cantidad_camas"));
    }

    @FXML
    void actualizar_lista_hospitales(ActionEvent event) {
        actualizar_lista_hospitales_detalles();
    }

    private void actualizar_lista_hospitales_detalles() {
        try {
            if(!validar_codigo_hospital())return;
            Long idH = Long.valueOf(txt_id_hospital.getText());
            Hospital obj = new Hospital(idH);
            ArrayList<Hospital> hospitales  = new ArrayList<>();
            hospitales.add(obj);
            String hospitalesJson = HospitalJSON.listHospitales_toString(hospitales);
            String datoPaquete =  MensajeJson.paqueteJson(0L, "OK", 1L, 0L, hospitalesJson);
            Mensaje mensaje = enviar_paquete(datoPaquete);
            table_view_hospitales.getItems().clear();
            table_view_hospitales.getItems().addAll((ArrayList<Hospital>)mensaje.getLista());
            table_view_camas.getItems().clear();
            for(Hospital h: (ArrayList<Hospital>)mensaje.getLista()){
                for(Cama c: h.getCamas()){
                    table_view_camas.getItems().add(c);
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void mostrar_alert_error(String texto){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initStyle(StageStyle.TRANSPARENT);
        alert.setTitle("INFORMACION");
        Button btnAceptar = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        btnAceptar.getStyleClass().add("btn_aceptar");
        alert.setHeaderText(null);
        alert.setContentText(texto);
        System.out.println("lanzamos error "+texto);
        alert.showAndWait();
    }


    private Mensaje enviar_paquete(String datoPaquete) throws Exception {
        try{
        // String datoPaquete = PersonaJSON.objetoString(p);
        byte sendData[] = datoPaquete.getBytes();
        byte receiveData[] = new byte[10000];
        DatagramSocket clientSocket = new DatagramSocket();
        System.out.println("Enviar " + datoPaquete + " al servidor. ("+ sendData.length + " bytes)");
        InetAddress IPAddress = InetAddress.getByName(direccionServidor);
        DatagramPacket sendPacket =
                new DatagramPacket(sendData, sendData.length, IPAddress, puertoServidor);
        clientSocket.send(sendPacket);

        DatagramPacket receivePacket =
                new DatagramPacket(receiveData, receiveData.length);
        //Vamos a hacer una llamada BLOQUEANTE entonces establecemos un timeout maximo de espera
        clientSocket.setSoTimeout(10000);

            // ESPERAMOS LA RESPUESTA, BLOQUENTE
            clientSocket.receive(receivePacket);

            String respuesta = new String(receivePacket.getData());
            //Persona presp = PersonaJSON.stringObjeto(respuesta.trim());

            InetAddress returnIPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();

            System.out.println("Respuesta desde =  " + returnIPAddress + ":" + port);
            Mensaje paqueteRecibido = MensajeJson.stringObjeto(respuesta);
            clientSocket.close();
            if(paqueteRecibido.getEstado()>1){
                mostrar_alert_error("Ocurrió error: "+paqueteRecibido.getMensaje());
            }
            return paqueteRecibido;
        } catch (Exception e) {
            System.out.println("TimeOut: El paquete udp se asume perdido.");
            e.printStackTrace();
            mostrar_alert_error("Ocurrió error: "+e);
            throw e;
        }
    }

    @FXML
    void crear_cama(ActionEvent event) {
        boolean valido = validar_codigo_hospital();
        if(!valido) return;
        Hospital obj =  new Hospital(Long.valueOf(txt_id_hospital.getText()));
        ArrayList <Hospital> hospitales  = new ArrayList<>();
        hospitales.add(obj);
        String hospitalesJson = HospitalJSON.listHospitales_toString(hospitales);
        String datoPaquete =  MensajeJson.paqueteJson(0L, "OK", 2L, 0L, hospitalesJson  );
        try{
            enviar_paquete(datoPaquete);
            actualizar_lista_hospitales_detalles();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private boolean validar_codigo_hospital() {
        try{
            int codigo_hospital = Integer.valueOf(txt_id_hospital.getText());
            return true;
        }catch (Exception e){
            mostrar_alert_error("Tenga cuidado eh con su ID");
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    void desocupar_cama(ActionEvent event) {
        boolean valido = validar_codigo_hospital();
        if(!valido) return;
        int cama = table_view_camas.getSelectionModel().getSelectedIndex();
        if(cama == -1){
            mostrar_alert_error("fijese antes de apretar, seleccione");
            return;
        }
        Cama cama_select = table_view_camas.getSelectionModel().getSelectedItem();
        if(!validar_mismo_hospital(cama_select)){
            return;
        }



        ArrayList <Cama> camas  = new ArrayList<>();
        camas.add(cama_select);
        String camasJson = CamaJSON.listCama_toJsonCama(camas);
        String datoPaquete =  MensajeJson.paqueteJson(0L, "OK", 5L, 1L, camasJson );
        try{
            enviar_paquete(datoPaquete);
            actualizar_lista_hospitales_detalles();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void eliminar_cama(ActionEvent event) {
        boolean valido = validar_codigo_hospital();
        if(!valido) return;
        int cama = table_view_camas.getSelectionModel().getSelectedIndex();
        if(cama == -1){
            mostrar_alert_error("fijese antes de apretar, seleccione");
            return;
        }
        Cama cama_select = table_view_camas.getSelectionModel().getSelectedItem();
        if(!validar_mismo_hospital(cama_select)){
            return;
        }

       ArrayList <Cama> camas  = new ArrayList<>();
        camas.add(cama_select);
        String camasJson = CamaJSON.listCama_toJsonCama(camas);
        String datoPaquete =  MensajeJson.paqueteJson(0L, "OK", 3L, 1L, camasJson );
        try{
            enviar_paquete(datoPaquete);
            actualizar_lista_hospitales_detalles();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private boolean validar_mismo_hospital(Cama cama) {
        if(cama.getHospital_id().equals(Long.valueOf(txt_id_hospital.getText()))){
            return true;
        }else{
            mostrar_alert_error("toque cosas de su hospital");
            return false;
        }
    }

    @FXML
    void ocupar_cama(ActionEvent event) {
        boolean valido = validar_codigo_hospital();
        if(!valido) return;
        int cama = table_view_camas.getSelectionModel().getSelectedIndex();
        if(cama == -1){
            mostrar_alert_error("fijese antes de apretar, seleccione");
            return;
        }
        Cama cama_select = table_view_camas.getSelectionModel().getSelectedItem();
        if(!validar_mismo_hospital(cama_select)){
            return;
        }



        ArrayList <Cama> camas  = new ArrayList<>();
        camas.add(cama_select);
        String camasJson = CamaJSON.listCama_toJsonCama(camas);
        String datoPaquete =  MensajeJson.paqueteJson(0L, "OK", 4L, 1L, camasJson );
        try{
            enviar_paquete(datoPaquete);
            actualizar_lista_hospitales_detalles();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
