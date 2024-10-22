package lanzaproceso;

import java.io.IOException;
import java.util.Arrays;

/*
Ejemplo: 
Piensa que hace
Cópialo
Pruébalo
¿Si el programa existe y acaba bien?
¿Si no existe?
¿Si acaba en código de error?
*/
public class LanzaProceso {

	public static void main (String[] args) {
		
		if(args.length <= 0){
			System.out.println("Debe indicarse comando a ejecutar.");
			System.exit(1);
		}
		
		ProcessBuilder pb = new ProcessBuilder(args);
		pb.inheritIO();
		
		try{
			
			Process p = pb.start();
			int codRet = p.waitFor();
			
			System.out.println("La ejecucion de " 
				+ Arrays.toString(args) + " devuelve " 
				+ codRet + " " 
				+ (codRet == 0? "(ejecucion correcta)" : "(ERROR)" ));
			
		} catch (IOException e) {
			
			System.err.println("Error durante la ejecucion del proceso");
			System.err.println("Informacion detallada");
			System.err.println("------------------");
			e.printStackTrace();
			System.err.println("------------------");
			System.exit(2);
			
		} catch (InterruptedException e) {
			
			System.err.println("Proceso interrumpido");
			System.exit(3);
			
		}
		
	}
	
}