package hilo;

import java.util.Scanner;

/*
 * El ejercicio permite indicar cuantos hilos lanzar
 * Permite lanzar varias simulaciones
 * Almacena los datos de cada simulación (quien acaba primero cada vez y muestra al acabar las estadísticas)
 */
public class LanzaHilos {
	
	public static void main(String[] args) {
		
		Scanner entrada = new Scanner(System.in);
		
		System.out.println("Indica el número de hilos: ");
		int numHilos = entrada.nextInt();
		
		for (int i = 0; i < numHilos; i++) {
			
			String nombreHilo = "H" + (i+1); 

			Thread hiloNuevo = new Thread(new Hilo(nombreHilo));
			
			hiloNuevo.start();
			
			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
			
		}
		
		System.out.println("Hilo principal terminado.");
		
		entrada.close();
		
	}
	
}

class Hilo implements Runnable {

	private String nombre;
	
	//Constructor
	Hilo (String nombre) {this.nombre = nombre;}
	
	@Override
	public void run() {
		
		System.out.format("Soy el hilo %s.\n", nombre);
		System.out.format("Hilo %s terminado.\n", nombre);
		
	}	
	
}