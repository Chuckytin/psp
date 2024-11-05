package mashilos;

public class HiloCooperante implements Runnable {
	
    int numHilo, miParte, miCuenta=0;
    private final Contador cont;

    public int getMiCuenta(){
    	
        return this.miCuenta;
        
    }

    public HiloCooperante(int numHilo, int miParte, Contador cont) {
    	
        this.numHilo = numHilo;
        this.miParte = miParte;
        this.cont = cont;
        
    }
    
    @Override
    public String toString() {
    	
        return "HiloCooperante: " + numHilo + " ";
        
    }
    
    @Override
    public void run() {
    	
        for(int i=0; i<miParte; i++){
        	
            this.cont.incrementa();
            miCuenta++;
            
        }
        
        System.out.println(this+" terminado. Cuenta: " + getMiCuenta());
        
    }
}
