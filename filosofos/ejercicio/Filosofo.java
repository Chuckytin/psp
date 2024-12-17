package ejercicio;

class Filosofo implements Runnable {

    private int idFilosofo;
    private String nombreFilosofo;
    private Object palilloDerecho;
    private Object palilloIzquierdo;

    //constructor
    public Filosofo(int idFilosofo, String nombreFilosofo, Object palilloDerecho, Object palilloIzquierdo) {
        this.idFilosofo = idFilosofo;
        this.nombreFilosofo = nombreFilosofo;
        this.palilloDerecho = palilloDerecho;
        this.palilloIzquierdo = palilloIzquierdo;
    }

    @Override
    public void run() {
    	
        while (true) {
            System.out.println(idFilosofo + " -- " + nombreFilosofo + " con ganas de comer");

            // Si el filósofo es impar, toma el palillo derecho primero.
            if (idFilosofo % 2 == 0) {
                synchronized (palilloIzquierdo) {
                    tomarPalillo("izquierdo");

                    synchronized (palilloDerecho) {
                        tomarPalillo("derecho");
                        comer();

                        liberarPalillo("derecho");
                    }
                    liberarPalillo("izquierdo");
                }
            } else {
                // Si el filósofo es par, toma el palillo izquierdo primero.
                synchronized (palilloDerecho) {
                    tomarPalillo("derecho");

                    synchronized (palilloIzquierdo) {
                        tomarPalillo("izquierdo");
                        comer();

                        liberarPalillo("izquierdo");
                    }
                    liberarPalillo("derecho");
                }
            }

            // Después de comer, pensar un rato
            pensar();
        }
    }

    private void pensar() {
        try {
            System.out.println(idFilosofo + " -- " + nombreFilosofo + " está pensando");
            int pausa = (int) (Math.random() * 3000);
            Thread.sleep(pausa);
        } catch (InterruptedException inEx) {
            System.out.println(inEx.toString());
        }
    }

    private void tomarPalillo(String palillo) {
        try {
            System.out.println(idFilosofo + " -- " + nombreFilosofo + " toma su palillo " + palillo);
            int pausa = (int) (Math.random() * 3000);
            Thread.sleep(pausa);
        } catch (InterruptedException inEx) {
            System.out.println(inEx.toString());
        }
    }

    private void comer() {
        try {
            System.out.println(idFilosofo + " -- " + nombreFilosofo + " está comiendo");
            int pausa = (int) (Math.random() * 3000);
            Thread.sleep(pausa);
            System.out.println(idFilosofo + " -- " + nombreFilosofo + " deja de comer");
        } catch (InterruptedException inEx) {
            System.out.println(inEx.toString());
        }
    }

    private void liberarPalillo(String palillo) {
        try {
            System.out.println(idFilosofo + " -- " + nombreFilosofo + " libera su palillo " + palillo);
            int pausa = (int) (Math.random() * 3000);
            Thread.sleep(pausa);
        } catch (InterruptedException inEx) {
            System.out.println(inEx.toString());
        }
    }
}
