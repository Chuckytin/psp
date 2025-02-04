package ejercicio;

public class HiloIncr implements Runnable {

	public static final int LIMITE_SUPERIOR = 100;
	
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

                //Solo incrementa si el contador es menor que 100
                if (this.contador.getCuenta() < LIMITE_SUPERIOR) {

                    this.contador.incrementa();
                    this.contador.notify(); //Notifica a los hilos de decremento (si hay alguno esperando)
                    System.out.printf("Hilo %s incrementa, valor contador: %d\n", this.id, this.contador.getCuenta());

                }

            }
        }
    }

	/*
	@Override
	public void run() {

		while(true) {
			synchronized(this.contador) {

				this.contador.incrementa();
				this.contador.notify(); //Notifica a un solo hilo esperando (en lugar de a todos) para eficiencia
				System.out.printf("Hilo %s incrementa, valor contador: %d\n", this.id, this.contador.getCuenta());

			}
		}

	}
	 */

}
