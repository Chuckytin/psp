package ejercicio;

public class HiloIncr implements Runnable {

	private final String id;
	private final Contador contador;
	
	public HiloIncr(String id, Contador c) {
		this.id = id;
		this.contador = c;
	}
	
	@Override
	public void run() {
		while(true) {
			synchronized(this.contador) {
				this.contador.incrementa();
				this.contador.notify();
				System.out.printf("Hilo %s incrementa, valor contador: %d\n", this.id, this.contador.getCuenta());
			}
		}
	}
	
}
