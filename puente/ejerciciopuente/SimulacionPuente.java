package ejerciciopuente;

import java.util.Random;

/*
 * EJERCICIO:
Realiza un programa que simule el siguiente comportamiento:
Un puente bidireccional acepta no más de 4 personas a la vez y no más de 350 kg totales de peso.
Como mucho 3 personas en cada dirección.
Llegan personas aleatorias en intervalos entre 1 y 30 s
El tiempo de cruce va entre 10 y 50s
El peso está entre 40 y 120 kg
 */

public class SimulacionPuente {
	
    public static void main(String[] args) {
    	
        int idPersona = 1;
        int tMinLlegada = 1, tMaxLlegada = 30;
        int tMinPaso = 10, tMaxPaso = 50;
        int minPeso = 40, maxPeso = 120;

        Puente puente = new Puente();
        Random r = new Random();
        
        System.out.println(">>>>>>>>> Comienza simulación.");
        
        while (true) {
            int tParaLlegada = tMinLlegada + r.nextInt(tMaxLlegada - tMinLlegada + 1);
            int pesoPersona = minPeso + r.nextInt(maxPeso - minPeso + 1);
            int direccion = r.nextInt(2); // 0 o 1 (dos direcciones posibles)

            System.out.printf("Siguiente persona llega en %d segundos.\n", tParaLlegada);
            
            try {
                Thread.sleep(tParaLlegada * 1000);
            } catch (InterruptedException ex) {
                System.err.println("Interrumpido proceso principal");
                break;
            }

            Thread hiloPersona = new Thread(new Persona(puente, pesoPersona, tMinPaso, tMaxPaso, "P" + idPersona, direccion));
            hiloPersona.start();
            idPersona++;
        }
    }
}

class Puente {
    
    private final int capacidadMaxima = 4; 
    private final int pesoMaximo = 300; 
    private final int personasDireccionMax = 3;
    private int pesoActual = 0; 
    private int personasEnPuente = 0; 
    private int personasEnDireccion0 = 0; 
    private int personasEnDireccion1 = 0; 

    public synchronized void entrarPuente(int peso, String nombre, int direccion) throws InterruptedException {
        
    	char letraDireccion = 'A';
    	
    	if (direccion == 1) {
    		letraDireccion = 'B';
    	} 
    	
        while (personasEnPuente >= capacidadMaxima || pesoActual + peso > pesoMaximo || 
               (direccion == 0 && personasEnDireccion0 >= personasDireccionMax) || 
               (direccion == 1 && personasEnDireccion1 >= personasDireccionMax)) {
        	        	        	
        	if ((direccion == 0 && personasEnDireccion0 >= personasDireccionMax) || 
        			(direccion == 1 && personasEnDireccion1 >= personasDireccionMax)) {
        		
        		System.out.printf("%s (peso %d kg) espera para cruzar en dirección %c. [Máximo de %d personas por dirección]\n", 
                		nombre, peso, letraDireccion, personasDireccionMax);
        	}
        	
        	if (personasEnPuente >= capacidadMaxima || pesoActual + peso > pesoMaximo) {
        		
        		System.out.printf("%s (peso %d kg) espera para cruzar en dirección %c. [Peso máximo permitido: %d Kg]\n", 
                		nombre, peso, letraDireccion, pesoMaximo);
        	}
        	
            wait();  // Espera hasta que haya espacio o peso disponible
            
        }

        // Aumentar el contador de personas en la dirección correspondiente
        personasEnPuente++;
        pesoActual += peso;
        if (direccion == 0) {
            personasEnDireccion0++;
        } else {
            personasEnDireccion1++;
        }

        // Muestra cuántas personas hay en cada dirección
        System.out.printf("%s (peso %d kg) está cruzando el puente en dirección %c. [Personas en puente: %d/%d, Peso total: %d kg]\n",
                nombre, peso, letraDireccion, personasEnPuente, capacidadMaxima, pesoActual);
        System.out.printf("Estado actual -> Dirección A: %d personas, Dirección B: %d personas\n", personasEnDireccion0, personasEnDireccion1);
    }

    public synchronized void salirPuente(int peso, String nombre, int direccion) {
        
        // Reducir el contador de la dirección correspondiente
        personasEnPuente--;
        pesoActual -= peso;
        if (direccion == 0) {
            personasEnDireccion0--;
        } else {
            personasEnDireccion1--;
        }

        // Muestra cuántas personas quedan en el puente
        System.out.printf("%s ha cruzado el puente y ha salido. [Personas en puente: %d/%d, Peso total: %d kg]\n",
                nombre, personasEnPuente, capacidadMaxima, pesoActual);
        System.out.printf("Estado actual -> Dirección A: %d personas, Dirección B: %d personas\n", personasEnDireccion0, personasEnDireccion1);

        notifyAll();  // Notifica a otros que pueden intentar cruzar
    }
}


class Persona implements Runnable {
	
    private final Puente puente;
    private final int peso;
    private final int tiempoCruce;
    private final String nombre;
    private final int direccion;
    private final Random r = new Random();

    public Persona(Puente puente, int peso, int minPaso, int maxPaso, String nombre, int direccion) {
        this.puente = puente;
        this.peso = peso;
        this.nombre = nombre;
        this.direccion = direccion;
        this.tiempoCruce = minPaso + r.nextInt(maxPaso - minPaso + 1);
    }

    @Override
    public void run() {
    	
        try {
            long tiempoInicio = System.currentTimeMillis();  // Registro de tiempo de inicio
            
            puente.entrarPuente(peso, nombre, direccion);
            
            Thread.sleep(tiempoCruce * 1000); // Simula el tiempo de cruce
            
            long tiempoFin = System.currentTimeMillis();  //Registro de tiempo de fin
            long tiempoTotal = (tiempoFin - tiempoInicio) / 1000; //Conversión a segundos
            
            puente.salirPuente(peso, nombre, direccion);
            
            System.out.printf("%s tardó %d segundos en cruzar el puente.\n", nombre, tiempoTotal);
            
        } catch (InterruptedException e) {
            System.err.println(nombre + " fue interrumpido.");
        }
    }

}
