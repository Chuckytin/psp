package clienteservidor;

import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ClienteUDP {
    public static void main(String[] args) {
        try {
            // Crear socket UDP
            DatagramSocket socketUDP = new DatagramSocket();
            Scanner scanner = new Scanner(System.in);
            SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss.SSS"); // Milisegundos

            System.out.print("Ingrese su mensaje: ");
            String mensajeTexto = scanner.nextLine();

            // Capturar la hora exacta de envío en el cliente
            long tiempoEnvioCliente = System.currentTimeMillis();
            String horaEnvioCliente = formatoHora.format(new Date(tiempoEnvioCliente));

            // Convertir mensaje a bytes
            byte[] mensaje = mensajeTexto.getBytes();
            InetAddress hostServidor = InetAddress.getByName("192.168.4.178");
            int puertoServidor = 6789;

            // Enviar mensaje al servidor
            DatagramPacket peticion = new DatagramPacket(mensaje, mensaje.length, hostServidor, puertoServidor);
            socketUDP.send(peticion);

            // Preparar buffer para recibir respuesta
            byte[] bufer = new byte[1024];
            DatagramPacket respuesta = new DatagramPacket(bufer, bufer.length);

            // Recibir respuesta del servidor
            socketUDP.receive(respuesta);

            // Capturar la hora exacta de recepción en el cliente
            long tiempoRecepcionCliente = System.currentTimeMillis();
            String horaRecepcionCliente = formatoHora.format(new Date(tiempoRecepcionCliente));

            // Convertir respuesta a String
            String mensajeRecibido = new String(respuesta.getData(), 0, respuesta.getLength()).trim();
            String[] partes = mensajeRecibido.split("\\|");

            if (partes.length != 3) {
                System.err.println("Error: Formato de respuesta incorrecto. Mensaje recibido: " + mensajeRecibido);
                return;
            }

            String mensajeProcesado = partes[0].trim();
            long tiempoRecepcionServidor = Long.parseLong(partes[1].trim());
            long tiempoRespuestaServidor = Long.parseLong(partes[2].trim());

            // Convertir tiempos a formato legible
            String horaRecepcionServidor = formatoHora.format(new Date(tiempoRecepcionServidor));
            String horaRespuestaServidor = formatoHora.format(new Date(tiempoRespuestaServidor));

            // Calcular latencias
            long latenciaIda = tiempoRecepcionServidor - tiempoEnvioCliente;
            long latenciaVuelta = tiempoRecepcionCliente - tiempoRespuestaServidor;
            long latenciaTotal = tiempoRecepcionCliente - tiempoEnvioCliente;

            // Mostrar resultados con formato de hora
            System.out.println("\n--- Respuesta del Servidor ---");
            System.out.println("Mensaje procesado: " + mensajeProcesado);
            System.out.println("Hora envío cliente: " + horaEnvioCliente);
            System.out.println("Hora recepción en servidor: " + horaRecepcionServidor);
            System.out.println("Hora respuesta del servidor: " + horaRespuestaServidor);
            System.out.println("Hora recepción en cliente: " + horaRecepcionCliente);

            System.out.println("\n--- Latencias ---");
            System.out.println("Latencia de ida (cliente → servidor): " + latenciaIda + " ms");
            System.out.println("Latencia de vuelta (servidor → cliente): " + latenciaVuelta + " ms");
            System.out.println("Latencia total (ida + vuelta): " + latenciaTotal + " ms");

            // Cerrar sockets y scanner
            socketUDP.close();
            scanner.close();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
