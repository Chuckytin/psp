package mashilos;

public class Contador {
	
    private int cuenta;

    public Contador() {
        cuenta = 0;
    }
    
    public int getCuenta() {
        return cuenta;
    }
    
    public synchronized int incrementa(){
        this.cuenta++;
        return cuenta;
    }
    
}
