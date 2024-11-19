package encuesta;

import java.util.HashMap;
import java.util.Map;

public class ResultadosEncuesta {
	
    private Map<Integer, Integer> respuestasPorZona;
    private Map<Integer, Integer> respuestasPorTipo;

    public ResultadosEncuesta() {
        respuestasPorZona = new HashMap<>();
        respuestasPorTipo = new HashMap<>();
    }

    //sincronizado porque acceden a él todos los hilos
    public synchronized void registrarEncuesta(int idZona, int respuesta) {
    	
        respuestasPorZona.put(idZona, respuestasPorZona.getOrDefault(idZona, 0) + 1);
        respuestasPorTipo.put(respuesta, respuestasPorTipo.getOrDefault(respuesta, 0) + 1);
        
    }

    public void mostrarResultados() {
    	
        System.out.println("Resultados por zona:");
        
        for (Map.Entry<Integer, Integer> entry : respuestasPorZona.entrySet()) {
            System.out.println("Zona " + entry.getKey() + ": " + entry.getValue() + " encuestas.");
        }

        System.out.println("\nResultados por tipo de respuesta:");
        
        for (Map.Entry<Integer, Integer> entry : respuestasPorTipo.entrySet()) {
            System.out.println("Respuesta " + entry.getKey() + ": " + entry.getValue() + " respuestas.");
        }
        
    }
    
}

