package clienteservidor;

import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServidorUDP {
    public static void main(String args[]) {
        try (DatagramSocket socketUDP = new DatagramSocket(6789)) {
            byte[] buffer = new byte[1024];
            SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss.SSS"); // Milisegundos

            while (true) {
                // Recibir mensaje del cliente
                DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
                socketUDP.receive(peticion);

                // Capturar la hora exacta en que el servidor recibe el mensaje
                long tiempoRecepcionServidor = System.currentTimeMillis();
                String horaRecepcionServidor = formatoHora.format(new Date(tiempoRecepcionServidor));

                // Convertir mensaje recibido
                String mensajeRecibido = new String(peticion.getData(), 0, peticion.getLength()).trim();

                System.out.println("\n--- Mensaje recibido ---");
                System.out.println("Desde: " + peticion.getAddress() + ":" + peticion.getPort());
                System.out.println("Mensaje: " + mensajeRecibido);
                System.out.println("Hora de recepción en servidor: " + horaRecepcionServidor);

                // Convertir mensaje a mayúsculas
                String mensajeProcesado = mensajeRecibido.toUpperCase();

                // Capturar la hora en que el servidor envía la respuesta
                long tiempoRespuestaServidor = System.currentTimeMillis();
                String horaRespuestaServidor = formatoHora.format(new Date(tiempoRespuestaServidor));

                // *** Formato correcto de la respuesta ***
                String mensajeRespuesta = mensajeProcesado + " | " + tiempoRecepcionServidor + " | " + tiempoRespuestaServidor;

                byte[] respuestaBytes = mensajeRespuesta.getBytes();
                DatagramPacket respuesta = new DatagramPacket(respuestaBytes, respuestaBytes.length,
                                                              peticion.getAddress(), peticion.getPort());

                // Enviar respuesta al cliente
                socketUDP.send(respuesta);

                System.out.println("Hora de respuesta en servidor: " + horaRespuestaServidor);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
