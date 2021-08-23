package py.una.server.udp;

import java.io.*;
import java.net.*;

import py.una.bd.HospitalDAO;
import py.una.entidad.Hospital;
import py.una.entidad.HospitalJSON;

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
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];

			
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
                datoRecibido = datoRecibido.trim();
                System.out.println("DatoRecibido: " + datoRecibido );
                Hospital h = HospitalJSON.stringObjeto(datoRecibido);

                InetAddress IPAddress = receivePacket.getAddress();

                int port = receivePacket.getPort();

                System.out.println("De : " + IPAddress + ":" + port);
                //System.out.println("Persona Recibida : " + p.getCedula() + ", " + p.getNombre() + " " + p.getApellido());


                if (opcion == 1){
                    //Ver estado de hospitales
                }else if( opcion == 2){
    
    
                }else if(opcion == 3){
                    //eliminar cama
                }else if( opcion == 4){
                    //ocupar cama
                }else if(opcion == 5){
                    //desocupar cama
                }





                try {
                	pdao.insertar(p);
                	System.out.println("Persona insertada exitosamente en la Base de datos");
                }catch(Exception e) {
                	System.out.println("Persona NO insertada en la Base de datos, razón: " + e.getLocalizedMessage());
                }
                
                // Respondemos agregando a la persona una asignatura
                p.getAsignaturas().add("Algoritmos y Estructuras de datos 2");
                p.getAsignaturas().add("Redes de Computadoras 2");

                // Enviamos la respuesta inmediatamente a ese mismo cliente
                // Es no bloqueante
                sendData = PersonaJSON.objetoString(p).getBytes();
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

