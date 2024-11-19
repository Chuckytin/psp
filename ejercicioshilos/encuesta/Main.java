package encuesta;

public class Main {
    
	private static final int NUM_HILOS = 20;
	
	//main
	public static void main(String[] args) throws InterruptedException {
        
        ResultadosEncuesta resultados = new ResultadosEncuesta();
        
        Thread[] hilos = new Thread[NUM_HILOS];
        
        //Crea los hilos, cada uno con una zona diferente
        for (int i = 0; i < NUM_HILOS; i++) {
        	
            Encuestador encuestador = new Encuestador((i+1), resultados);
            hilos[i] = new Thread(encuestador);
            //hilos[i].start();
            
        }
        
        //Lanza los hilos
        for (Thread t : hilos) {
        	
        	t.start();
        	
        }
        
        //Espera a que todos los hilos terminen
        for (int i = 0; i < NUM_HILOS; i++) {
        	
            hilos[i].join();
            
        }
        

        resultados.mostrarResultados();
        
    }
	
}

