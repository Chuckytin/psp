package ejercicio;

public class HiloDecr implements Runnable {

	private final String id;
	private final Contador contador;
	
	public HiloDecr(String id, Contador c) {
		this.id = id;
		this.contador = c;
	}
	
	@Override
	public void run() {
		while(true) {
			synchronized(this.contador) {
				while(this.contador.getCuenta() < 1) {
					
					System.out.printf("Hilo %s no puede decrementar, valor contador: %d\n", this.id, this.contador.getCuenta());
					
					try {
						
						this.contador.wait();
						
					} catch (InterruptedException e) {
						e.printStackTrace(System.err);
					}
				}
				
				this.contador.decrementa();
				
				System.out.printf("Hilo %s decrementa, valor contador: %d\n", this.id, this.contador.getCuenta());
			}
		}
	}
	
}
