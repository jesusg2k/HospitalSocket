package py.una.server.udp;


import java.io.*;
import java.net.*;
import java.util.ArrayList;

import py.una.bd.HospitalDAO;
import py.una.entidad.Cama;
import py.una.entidad.Hospital;
import py.una.entidad.HospitalJSON;
import py.una.entidad.MensajeJson;

class UDPClient {

    public static void main(String a[]) throws Exception {

        // Datos necesario
        String direccionServidor = "127.0.0.1";

        if (a.length > 0) {
            direccionServidor = a[0];
        }

        int puertoServidor = 9876;
        
        try {

            BufferedReader inFromUser =
                    new BufferedReader(new InputStreamReader(System.in));

            DatagramSocket clientSocket = new DatagramSocket();

            InetAddress IPAddress = InetAddress.getByName(direccionServidor);
            System.out.println("Intentando conectar a = " + IPAddress + ":" + puertoServidor +  " via UDP...");

            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];

            System.out.print("Ingrese el número 1 si desea ver estado de Hospitales\n 
                        Ingrese 2 si desea crear CamaUTI\n
                        Ingrese 3 si desea eliminar CamaUTI\n
                        Ingrese 4 si desea ocupar CamaUTI\n
                        Ingrese 5 si desea desocupar CamaUTI ");
            String stropcion = inFromUser.readLine();
            Long opcion = 0L;
            try {
            	opcion = Long.parseLong(stropcion);
            }catch(Exception e1) {
            	
            }
            
            if (opcion == 1){
                try {
                    eleccion = Long.parseLong(stropcion);
                    Hospital obj =  new Hospital(idH);
                    ArrayList <Hospital> hospitales  = new ArrayList<>();
                    hospitales.add(obj);
                    String hospitalesJson = HospitalJSON.listHospitales_toString(hospitales);
                    String datoPaquete =  MensajeJson.paqueteJson(0L, "OK", 1L, 0L, hospitalesJson);
                }catch(Exception e1) {

                }
                

            }else if( opcion == 2){

                Long eleccion = 0L;
                long idH = 1; 
                try {
                    eleccion = Long.parseLong(stropcion);
                    Hospital obj =  new Hospital(idH);
                    ArrayList <Hospital> hospitales  = new ArrayList<>();
                    hospitales.add(obj);
                    String hospitalesJson = HospitalJSON.listHospitales_toString(hospitales);
                    String datoPaquete =  MensajeJson.paqueteJson(0L, "OK", 2L, 0L, hospitalesJson  );
                }catch(Exception e1) {

                }
               
            }else if(opcion == 3){
                //eliminar cama
                System.out.print("Ingrese el id de la cama a eliminar");
                String idcama = inFromUser.readLine();
                Long id = 0L;
                long idH = 1; 
                try {
                    id = Long.parseLong(idcama);
                    Cama obj =  new Cama(id);
                    ArrayList <Cama> camas  = new ArrayList<>();
                    camas.add(obj);
                    String camasJson = CamaJSON.listCamas_toString(camas);
                    String datoPaquete =  MensajeJson.paqueteJson(0L, "OK", 3L, 1L, camasJson);
                }catch(Exception e1) {

                }

            }else if( opcion == 4){

                //ocupar cama
                System.out.print("Ingrese el id de la cama a eliminar");
                String idcama = inFromUser.readLine();
                Long id = 0L;
                long idH = 1; 
                try {
                    id = Long.parseLong(idcama);
                    Cama obj =  new Cama(id);
                    ArrayList <Cama> camas  = new ArrayList<>();
                    camas.add(obj);
                    String camasJson = CamaJSON.listCamas_toString(camas);
                    String datoPaquete =  MensajeJson.paqueteJson(0L, "OK", 4L, 1L, camasJson);
                }catch(Exception e1) {

                }
            }else if(opcion == 5){
                //desocupar cama
                System.out.print("Ingrese el id de la cama a eliminar");
                String idcama = inFromUser.readLine();
                Long id = 0L;
                long idH = 1; 
                try {
                    id = Long.parseLong(idcama);
                    Cama obj =  new Cama(id);
                    ArrayList <Cama> camas  = new ArrayList<>();
                    camas.add(obj);
                    String camasJson = CamaJSON.listCamas_toString(camas);
                    String datoPaquete =  MensajeJson.paqueteJson(0L, "OK", 5L, 1L, camasJson);
                }catch(Exception e1) {

                }

            }
            
           // String datoPaquete = PersonaJSON.objetoString(p); 
            sendData = datoPaquete.getBytes();

            System.out.println("Enviar " + datoPaquete + " al servidor. ("+ sendData.length + " bytes)");
            DatagramPacket sendPacket =
                    new DatagramPacket(sendData, sendData.length, IPAddress, puertoServidor);

            clientSocket.send(sendPacket);

            DatagramPacket receivePacket =
                    new DatagramPacket(receiveData, receiveData.length);

            System.out.println("Esperamos si viene la respuesta.");

            //Vamos a hacer una llamada BLOQUEANTE entonces establecemos un timeout maximo de espera
            clientSocket.setSoTimeout(10000);

            try {
                // ESPERAMOS LA RESPUESTA, BLOQUENTE
                clientSocket.receive(receivePacket);

                String respuesta = new String(receivePacket.getData());
                Persona presp = PersonaJSON.stringObjeto(respuesta.trim());
                
                InetAddress returnIPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();

                System.out.println("Respuesta desde =  " + returnIPAddress + ":" + port);
                //System.out.println("Asignaturas: ");
                
               /* for(String tmp: presp.getAsignaturas()) {
                	System.out.println(" -> " +tmp);
                }*/
                

            } catch (SocketTimeoutException ste) {

                System.out.println("TimeOut: El paquete udp se asume perdido.");
            }
            clientSocket.close();
        } catch (UnknownHostException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
} 

