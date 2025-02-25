package clienteservidor;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClienteUDP {

    public static void main(String[] args) {
        try {
            DatagramSocket socketUDP = new DatagramSocket();
            
            // Define el mensaje y la IP del servidor
            String mensajeTexto = "Hola servidor desde Eclipse";
            
            // Obtiene la hora local del cliente cuando se envía el mensaje
            SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
            String horaCliente = formatoHora.format(new Date());
            String mensajeConHora = mensajeTexto + " | Hora cliente: " + horaCliente;
            
            byte[] mensaje = mensajeConHora.getBytes();
            
            InetAddress hostServidor = InetAddress.getByName("192.168.4.178"); // Cambia a la IP del servidor
            int puertoServidor = 6789;

            // Envia el mensaje con la hora al servidor
            DatagramPacket peticion = new DatagramPacket(mensaje, mensaje.length, hostServidor, puertoServidor);
            socketUDP.send(peticion);

            // Construye el DatagramPacket que contendrá la respuesta
            byte[] bufer = new byte[1000];
            DatagramPacket respuesta = new DatagramPacket(bufer, bufer.length);
            socketUDP.receive(respuesta);

            // Muestra la respuesta del servidor, que incluye el mensaje en mayúsculas y la hora del servidor
            String mensajeRecibido = new String(respuesta.getData(), 0, respuesta.getLength());
            System.out.println("Respuesta del servidor: " + mensajeRecibido);

            socketUDP.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
