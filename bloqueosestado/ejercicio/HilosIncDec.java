package ejercicio;

public class HilosIncDec {

	private static final int NUM_HILOS_INC = 10;
	private static final int NUM_HILOS_DEC = 10;
	
	public static void main(String[] args) {
		
		Contador c = new Contador(0);
		Thread[] hilosIncrementa = new Thread[NUM_HILOS_INC];
		
		for (int i = 0; i < NUM_HILOS_INC; i++) {
			
			Thread thread = new Thread(new HiloIncr("INC" + i, c));
			hilosIncrementa[i] = thread;
		}
		
		Thread[] hilosDecrementa = new Thread[NUM_HILOS_DEC];
		for (int i = 0; i < NUM_HILOS_DEC; i++) {
			
			Thread thread = new Thread(new HiloDecr("DEC" + i, c));
			hilosDecrementa[i] = thread;
		}
		
		for (int i = 0; i < NUM_HILOS_INC; i++) {
			hilosIncrementa[i].start();
		}
		
		for (int i = 0; i < NUM_HILOS_DEC; i++) {
			hilosDecrementa[i].start();
		}
		
	}
	
}
