package ejecutacomando;

/*
 * Crea un programa que reciba un comando por arg y lo ejecute. Comprueba el resultado. 
 * Y si no existe el comando?
 */
public class EjecutaComando {
	
	public static void main(String[] args) {
		
		try {
			
			Runtime.getRuntime().exec(args);
			
		} catch (Exception e) {
			
			e.printStackTrace();			
			
		}
		
	}
	
}
