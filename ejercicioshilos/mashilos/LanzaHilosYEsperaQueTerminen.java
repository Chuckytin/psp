package mashilos;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

class Hilo implements Runnable{
	
    private final String nombre;
    
    Hilo (String nombre){
        this.nombre=nombre;
    }
    
    @Override
    public void run(){
    	
        System.out.println("Hola, soy el hilo " + nombre);
        Random r = new Random();
        
        for(int i = 0; i < 5; i++){
        	
            int pausa = 10 + r.nextInt(500-10);
            System.out.println("El hilo " + nombre + " hace una pausa de "+ pausa + "ms.");
            
            try {
            	
                Thread.sleep(pausa);
                
            } catch (InterruptedException ex) {
                Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        System.out.println("Hilo " + nombre + " terminado.");
        
    }
    
}

public class LanzaHilosYEsperaQueTerminen {
	
    public static void main(String[] args) {
    	
        Thread h1 = new Thread(new Hilo("h1"));
        Thread h2 = new Thread(new Hilo("h2"));
        
        h1.start();
        h2.start();
        
        try {
        	
            h1.join();
            h2.join();
            
        } catch (InterruptedException ex) {
            System.out.println("Hilo principal interrumpido.");
        }
        
        System.out.println("Hilo principal terminado.");
        
    }
    
}
