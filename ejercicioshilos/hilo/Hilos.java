package hilo;

import java.util.ArrayList;


public class Hilos {

    
    public static void main(String[] args) {
    	
        final int MAX_HILOS = 3;
        
        ArrayList<Thread> hilos = new ArrayList<>();
        
        for(int i = 1; i <= MAX_HILOS; i++){
        	
            hilos.add(new Thread(new Hilo("H"+i)));
            
        }
        
        for(Thread hilo: hilos){
        	
            hilo.start();
                 
        }
        
        try {
        	
			Thread.sleep(1000);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        for(Thread t: hilos){
        	
            try {
            	
                t.join();
                
            } catch (InterruptedException ex) {
            	
                System.err.println("Hilo principal interrumpido.");
                
            }
            
        }
        
        System.out.println("Hilo principal finaliza.");
        
    }
    
}
