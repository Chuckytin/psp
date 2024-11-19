package encuesta;

import java.util.Random;

public class Encuestador implements Runnable {
	
    static Random random = new Random();
    
    private int idZona;
    private ResultadosEncuesta resultados;

    public Encuestador(int idZona, ResultadosEncuesta resultados) {
        this.idZona = idZona;
        this.resultados = resultados;
    }

    @Override
    public void run() {
        
        int numEncuestas = random.nextInt(101) + 100 ; //100-200
        
        for (int i = 0; i < numEncuestas; i++) {
            
            int respuesta = random.nextInt(10); //0-9
        
            resultados.registrarEncuesta(idZona, respuesta);
            
        }
        
    }
    
}

