package archivos.server.udp;


import archivos.entidad.*;

import java.io.*;
import java.net.*;
import java.util.ArrayList;


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
            byte[] receiveData = new byte[2800];
            String datoPaquete = "";
            System.out.print("Ingrese el número 1 si desea ver estado de Hospitales\n       " +
                            "Ingrese 2 si desea crear CamaUTI\n " +
                            "Ingrese 3 si desea eliminar CamaUTI\n " +
                            "Ingrese 4 si desea ocupar CamaUTI\n " +
                            "Ingrese 5 si desea desocupar CamaUTI ");
            String stropcion = inFromUser.readLine();
            int opcion = 0;
            try {
            	opcion = Integer.valueOf(stropcion);
            }catch(Exception e1) {
                e1.printStackTrace();
                System.exit(0);
            }
            if((opcion < 1 || opcion >5)){
                System.out.println("el numero no estaba entre las opciones");
                System.exit(0);
            }
            
            if (opcion == 1){
                try {
                    System.out.println("Opcion: "+opcion);
                    Long idH = 1L;
                    Hospital obj = new Hospital(idH);
                    ArrayList <Hospital> hospitales  = new ArrayList<>();
                    hospitales.add(obj);
                    String hospitalesJson = HospitalJSON.listHospitales_toString(hospitales);
                    datoPaquete =  MensajeJson.paqueteJson(0L, "OK", 1L, 0L, hospitalesJson);
                    System.out.println(datoPaquete);
                }catch(Exception e1) {
                    e1.printStackTrace();
                }
            }else if( opcion == 2){
                System.out.println("Opcion: "+opcion);
                long idH = 1L;
                try {
                    Hospital obj =  new Hospital(idH);
                    ArrayList <Hospital> hospitales  = new ArrayList<>();
                    hospitales.add(obj);
                    String hospitalesJson = HospitalJSON.listHospitales_toString(hospitales);
                    datoPaquete =  MensajeJson.paqueteJson(0L, "OK", 2L, 0L, hospitalesJson  );
                }catch(Exception e1) {
                    e1.printStackTrace();
                }
               
            }else if(opcion == 3){
                System.out.println("Opcion: "+opcion);
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
                    String camasJson = CamaJSON.listCama_toJsonCama(camas);
                    datoPaquete =  MensajeJson.paqueteJson(0L, "OK", 3L, 1L, camasJson);
                }catch(Exception e1) {
                    e1.printStackTrace();
                }

            }else if( opcion == 4){
                System.out.println("Opcion: "+opcion);
                //ocupar cama
                System.out.print("Ingrese el id de la cama a ocupar");
                String idcama = inFromUser.readLine();
                Long id = 0L;
                long idH = 1; 
                try {
                    id = Long.parseLong(idcama);
                    Cama obj =  new Cama(id);
                    obj.setHospital_id(idH);
                    ArrayList <Cama> camas  = new ArrayList<>();
                    camas.add(obj);
                    String camasJson = CamaJSON.listCama_toJsonCama(camas);
                    datoPaquete =  MensajeJson.paqueteJson(0L, "OK", 4L, 1L, camasJson);
                }catch(Exception e1) {
                    e1.printStackTrace();
                }
            }else if(opcion == 5){
                System.out.println("Opcion: "+opcion);
                //desocupar cama
                System.out.print("Ingrese el id de la cama a desocupar");
                String idcama = inFromUser.readLine();
                Long id = 0L;
                long idH = 1; 
                try {
                    id = Long.parseLong(idcama);
                    Cama obj =  new Cama(id);
                    ArrayList <Cama> camas  = new ArrayList<>();
                    camas.add(obj);
                    String camasJson = CamaJSON.listCama_toJsonCama(camas);
                    datoPaquete =  MensajeJson.paqueteJson(0L, "OK", 5L, 1L, camasJson);
                }catch(Exception e1) {
                    e1.printStackTrace();
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
                //Persona presp = PersonaJSON.stringObjeto(respuesta.trim());
                
                InetAddress returnIPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();

                System.out.println("Respuesta desde =  " + returnIPAddress + ":" + port);

                Mensaje paqueteRecibido = MensajeJson.stringObjeto(respuesta);
                Long estado = paqueteRecibido.getEstado();
                if(estado>1L){
                    System.out.println(paqueteRecibido);
                    System.out.println("Ocurrió un error: "+paqueteRecibido.getMensaje());
                }else if(estado == -1){
                    System.out.println("El paquete es incierto");
                }else{
                    int tipo_op = Integer.valueOf(paqueteRecibido.getTipo_operacion()+"");
                    switch(tipo_op){
                        case 1:
                            for(Hospital h: (ArrayList<Hospital>) paqueteRecibido.getLista()){
                                System.out.println("Hospital: "+h);
                                for(Cama c: h.getCamas()){
                                    System.out.println("\t"+c);
                                }
                            }
                            break;
                        case 2:
                            System.out.println("La cama fue creada con éxito");
                            System.out.println((paqueteRecibido.getLista().get(0)));
                            break;
                        case 3:
                            System.out.println("La cama fue eliminada con éxito");
                            System.out.println((paqueteRecibido.getLista().get(0)));
                            break;
                        case 4:
                            System.out.println("La cama fue ocupada con éxito");
                            System.out.println((paqueteRecibido.getLista().get(0)));
                            break;
                        case 5:
                            System.out.println("La cama fue desocupada con éxito");
                            System.out.println((paqueteRecibido.getLista().get(0)));
                            break;
                    }
                }

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

