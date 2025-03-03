package ejercicio;

public class Contador {

    private int cuenta;
    
    Contador (int valorInicial) {
        this.cuenta = valorInicial;
    }
    
    public synchronized int getCuenta() {
        return cuenta;
    }
    
    public synchronized int incrementa() {
        this.cuenta++;
        return cuenta;
    }
    
    public synchronized int decrementa() {
        this.cuenta--;
        return cuenta;
    }
}
