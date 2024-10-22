package ejecutacomandotimeout;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/*
Ejemplo: 
Piensa que hace (Linux)
Cópialo
Pruébalo
¿Qué hace redirectStream?
¿Qué pasa si no lo haces?
 */
public class EjecutaComandoTimeOut {

	public static final int MAX_TIEMPO = 1000;
	
	public static void main(String[] args) {
		
		ProcessBuilder pb = new ProcessBuilder(new String[]{"notepad"});
		
		System.out.printf("Se ejecuta comando: %s\n", Arrays.toString(args));
		
		pb.inheritIO();
		pb.redirectErrorStream(true);
		
		try{
			
			Process p = pb.start();
			
			if(!p.waitFor(MAX_TIEMPO, TimeUnit.MILLISECONDS)){
				
				p.destroy();
				System.out.printf("AVISO: No ha terminado en %d ms\n", MAX_TIEMPO);
				
			}
			
		} catch (IOException e){
			
			System.err.println("Error durante la ejecucion del proceso");
			System.err.println("Informacion detallada");
			System.err.println("------------------");
			e.printStackTrace();
			System.err.println("------------------");
			System.exit(1);
			
		} catch (InterruptedException e){
			
			System.err.println("Proceso interrumpido");
			System.exit(2);
			
		}
		
	}
	
}
