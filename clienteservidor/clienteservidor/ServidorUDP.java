package clienteservidor;

import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServidorUDP {

    public static void main(String args[]) {

        try (DatagramSocket socketUDP = new DatagramSocket(6789)) {

            byte[] bufer = new byte[1000];

            while (true) {
                // Construimos el DatagramPacket para recibir peticiones
                DatagramPacket peticion = new DatagramPacket(bufer, bufer.length);

                // Leemos una petición del DatagramSocket
                socketUDP.receive(peticion);

                System.out.print("Datagrama recibido del host: " + peticion.getAddress());
                System.out.println(" desde el puerto remoto: " + peticion.getPort());

                // Convierte el mensaje recibido a mayúsculas
                String mensajeRecibido = new String(peticion.getData(), 0, peticion.getLength());
                String mensajeMayusculas = mensajeRecibido.toUpperCase();

                // Obtiene la hora local del servidor cuando recibe el mensaje
                SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
                String horaServidor = formatoHora.format(new Date());
                
                // Agrega la hora del servidor al mensaje
                String mensajeConHoraServidor = mensajeMayusculas + " | Hora servidor: " + horaServidor;

                // Construye el DatagramPacket para enviar la respuesta (mensaje en mayúsculas)
                byte[] respuestaBytes = mensajeConHoraServidor.getBytes();
                DatagramPacket respuesta = new DatagramPacket(respuestaBytes, respuestaBytes.length,
                        peticion.getAddress(), peticion.getPort());

                // Envia la respuesta (mensaje en mayúsculas con la hora del servidor)
                socketUDP.send(respuesta);
            }

        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
    }
}
