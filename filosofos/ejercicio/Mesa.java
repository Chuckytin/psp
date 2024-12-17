package ejercicio;

public class Mesa {
    
    public static final String[] NOMBRES_FILOSOFOS = {"Platón", "Russell", "Descartes", "Nietzsche", "Pitágoras"};
    public static final int NUM_FILOSOFOS = NOMBRES_FILOSOFOS.length;

    public static void main(String[] args) {
        
        //creación de los palillos
        Object[] palillos = creacionPalillos(NUM_FILOSOFOS);

        //creación de los filósofos como hilos y se lanzan
        creacionFilosofo(NUM_FILOSOFOS, palillos);
        
    }

    // Método para crear los palillos
    private static Object[] creacionPalillos(int numFilosofos) {
        Object[] palillos = new Object[numFilosofos];
        for (int i = 0; i < numFilosofos; i++) {
            palillos[i] = new Object(); // Cada filósofo tiene un palillo
        }
        return palillos;
    }

    // Método para crear los filósofos y lanzar sus hilos
    private static void creacionFilosofo(int numFilosofos, Object[] palillos) {
        for (int i = 0; i < numFilosofos; i++) {
            // Cada filósofo tiene su palillo izquierdo en i y su palillo derecho en (i+1) % numFilosofos
            Filosofo filosofo = new Filosofo(i + 1, NOMBRES_FILOSOFOS[i], palillos[i], palillos[(i + 1) % numFilosofos]);
            Thread hilo = new Thread(filosofo);
            hilo.start();
        }
    }
}
