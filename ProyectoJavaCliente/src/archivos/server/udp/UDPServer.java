package archivos.server.udp;

import archivos.bd.CamaDAO;
import archivos.bd.HospitalDAO;
import archivos.entidad.*;

import java.net.*;
import java.util.ArrayList;


public class UDPServer {
	
	
    public static void main(String[] a){
        
        // Variables
        int puertoServidor = 9876;
        HospitalDAO hdao = new HospitalDAO();
        
        try {
            //1) Creamos el socket Servidor de Datagramas (UDP)
            DatagramSocket serverSocket = new DatagramSocket(puertoServidor);
			System.out.println("Servidor Sistemas Distribuidos - UDP ");
			
            //2) buffer de datos a enviar y recibir
            byte[] receiveData = new byte[3000];
            byte[] sendData = new byte[3000];

			
            //3) Servidor siempre esperando
            while (true) {

                receiveData = new byte[1024];

                DatagramPacket receivePacket =
                        new DatagramPacket(receiveData, receiveData.length);


                System.out.println("Esperando a algun cliente... ");

                // 4) Receive LLAMADA BLOQUEANTE
                serverSocket.receive(receivePacket);
				
				System.out.println("________________________________________________");
                System.out.println("Aceptamos un paquete");

                // Datos recibidos e Identificamos quien nos envio
                String datoRecibido = new String(receivePacket.getData());
                System.out.println(datoRecibido);
                Mensaje paqueteRecibido = MensajeJson.stringObjeto(datoRecibido);

                InetAddress IPAddress = receivePacket.getAddress();

                int port = receivePacket.getPort();

                System.out.println("De : " + IPAddress + ":" + port);
                //System.out.println("Persona Recibida : " + p.getCedula() + ", " + p.getNombre() + " " + p.getApellido());

                Long opcion = paqueteRecibido.getTipo_operacion();
                String paquete_a_enviar = null;
                if((opcion < 1L || opcion >5L)){
                    System.out.println("el tipo de operacion no estaba entre las opciones");
                    paquete_a_enviar = MensajeJson.paqueteJson(-1L,"el tipo de operacion no estaba entre las opciones: "+opcion,0L,0L,HospitalJSON.listHospitales_toString(new ArrayList<>()));
                }else{
                    if (opcion == 1){
                        System.out.println("Opcion: "+opcion);
                        ArrayList<Hospital> hospitales = new HospitalDAO().hospitales_detalles();
                        String json_hospitales = HospitalJSON.listHospitales_toString(hospitales);
                        paquete_a_enviar = MensajeJson.paqueteJson(0L,"OK",1L,0L,json_hospitales);
                    }else if( opcion == 2){
                        System.out.println("Opcion: "+opcion);
                        Hospital hospital = (Hospital) paqueteRecibido.getLista().get(0);
                        Long id = new CamaDAO().insertar(hospital);
                        if(id == -1L){
                            paquete_a_enviar = MensajeJson.paqueteJson(2L,"No se pudo crear la cama ",opcion,0L,HospitalJSON.listHospitales_toString(new ArrayList<>()));
                        }else{
                            Cama cama = new CamaDAO().seleccionarByID_Cama(id);
                            ArrayList<Cama> camas = new ArrayList<>();
                            camas.add(cama);
                            String camasJson = CamaJSON.listCama_toJsonCama(camas);
                            paquete_a_enviar = MensajeJson.paqueteJson(0L,"OK",opcion,1L,camasJson);
                        }
                    }else if(opcion == 3){
                        System.out.println("Opcion: "+opcion);
                        //eliminar cama
                        try{
                            Cama cama = (Cama) paqueteRecibido.getLista().get(0);
                            Cama cama_bd = new CamaDAO().seleccionarByID_Cama(cama.getCama_id());
                            int exito = 0;
                            if(cama_bd.isHabilitado()){
                                exito = new CamaDAO().eliminar_cama(cama_bd);
                                cama_bd = new CamaDAO().seleccionarByID_Cama(cama.getCama_id());
                            }else{
                                exito = 0;
                            }
                            if(exito == 0){
                                paquete_a_enviar = MensajeJson.paqueteJson(2L,"No se elimino ninguna cama porque ya estaba eliminada",opcion,0L,HospitalJSON.listHospitales_toString(new ArrayList<>()));
                            }else{
                                ArrayList<Cama> camas = new ArrayList<>();
                                camas.add(cama_bd);
                                String camasJson = CamaJSON.listCama_toJsonCama(camas);
                                paquete_a_enviar = MensajeJson.paqueteJson(0L,"OK",opcion,1L,camasJson);
                            }
                        }catch(Exception e){
                            /*Error la cama no existe*/
                            paquete_a_enviar = MensajeJson.paqueteJson(2L,"No existe una cama con ese ID",opcion,0L,HospitalJSON.listHospitales_toString(new ArrayList<>()));
                        }
                    }else if( opcion == 4){
                        System.out.println("Opcion: "+opcion);
                        //ocupar cama
                        try{
                            Cama cama = (Cama) paqueteRecibido.getLista().get(0);
                            Cama cama_bd = new CamaDAO().seleccionarByID_Cama(cama.getCama_id());
                            int exito = 0;
                            if(!cama_bd.isOcupado() && cama_bd.isHabilitado()){
                                exito = new CamaDAO().ocupar_cama(cama_bd);
                                cama_bd = new CamaDAO().seleccionarByID_Cama(cama.getCama_id());
                            }else{
                                exito = 0;
                            }

                            if(exito == 0){
                                paquete_a_enviar = MensajeJson.paqueteJson(2L,"No se ocupo ninguna cama porque "+((!cama_bd.isHabilitado())?"ya está eliminada":"ya estaba ocupada"),opcion,0L,HospitalJSON.listHospitales_toString(new ArrayList<>()));
                            }else{
                                ArrayList<Cama> camas = new ArrayList<>();
                                camas.add(cama_bd);
                                String camasJson = CamaJSON.listCama_toJsonCama(camas);
                                paquete_a_enviar = MensajeJson.paqueteJson(0L,"OK",opcion,1L,camasJson);
                            }
                        }catch(Exception e){
                            /*Error la cama no existe*/
                            paquete_a_enviar = MensajeJson.paqueteJson(2L,"No existe ESA cama con ese ID",opcion,0L,HospitalJSON.listHospitales_toString(new ArrayList<>()));
                        }

                    }else if(opcion == 5){
                        System.out.println("Opcion: "+opcion);
                        //desocupar cama
                        //ocupar cama
                        try{
                            Cama cama = (Cama) paqueteRecibido.getLista().get(0);
                            Cama cama_bd = new CamaDAO().seleccionarByID_Cama(cama.getCama_id());
                            int exito = 0;
                            if(cama_bd.isOcupado() && cama_bd.isHabilitado()){
                                exito = new CamaDAO().desocupar_cama(cama_bd);
                                cama_bd = new CamaDAO().seleccionarByID_Cama(cama.getCama_id());
                            }else{
                                exito = 0;
                            }

                            if(exito == 0){
                                paquete_a_enviar = MensajeJson.paqueteJson(2L,"No se desocupo la cama porque "+((!cama_bd.isHabilitado())?"ya está eliminada":"ya estaba desocupada"),opcion,0L,HospitalJSON.listHospitales_toString(new ArrayList<>()));
                            }else{
                                ArrayList<Cama> camas = new ArrayList<>();
                                camas.add(cama_bd);
                                String camasJson = CamaJSON.listCama_toJsonCama(camas);
                                paquete_a_enviar = MensajeJson.paqueteJson(0L,"OK",opcion,1L,camasJson);
                            }
                        }catch(Exception e){
                            /*Error la cama no existe*/
                            paquete_a_enviar = MensajeJson.paqueteJson(2L,"No existe ESA cama con ese ID",opcion,0L,HospitalJSON.listHospitales_toString(new ArrayList<>()));
                        }
                    }

                }


                // Enviamos la respuesta inmediatamente a ese mismo cliente
                // Es no bloqueante
                sendData = paquete_a_enviar.getBytes();
                DatagramPacket sendPacket =
                        new DatagramPacket(sendData, sendData.length, IPAddress,port);

                serverSocket.send(sendPacket);
            }

        } catch (Exception ex) {
        	ex.printStackTrace();
            System.exit(1);
        }

    }
}  

